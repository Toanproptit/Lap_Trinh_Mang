
package UDP;

import java.io.*;
import java.net.*;


//UDP: Che EMAIL, PHONE, TOKEN trong log (cổng 2208)

public class De17 {
    public static void main(String[] args) throws IOException {

        String host = "YOUR_SERVER_IP";
        int port = 2208;

        String studentCode = "B23DCCN833";
        String qCode = "YOUR_QCODE";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(host);

            // 1. Gui yeu cau
            String request = ";" + studentCode + ";" + qCode;

            byte[] sendData = request.getBytes();

            DatagramPacket packet = new DatagramPacket(
                    sendData, sendData.length, address, port
            );

            socket.send(packet);

            // 2. Nhan du lieu
            byte[] buffer = new byte[8192];

            DatagramPacket receivePacket =
                    new DatagramPacket(buffer, buffer.length);

            socket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()
            );

            // 3. Tach requestId va log
            String[] parts = response.split(";", 2);

            String requestId = parts[0];
            String log = parts[1];

            // 4. Che email
            log = log.replaceAll(
                    "(?i)\\bemail=[^\\s|]+",
                    "email=[EMAIL]"
            );

            // 5. Che so dien thoai
            log = log.replaceAll(
                    "(?i)\\bphone=[^\\s|]+",
                    "phone=[PHONE]"
            );

            // 6. Che token
            log = log.replaceAll(
                    "(?i)\\btoken=[^\\s|]+",
                    "token=[TOKEN]"
            );

            // 7. Tao ket qua
            String answer = requestId + ";" + log;

            // 8. Gui ket qua
            byte[] answerData = answer.getBytes();

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData, answerData.length, address, port
            );

            socket.send(answerPacket);

            System.out.println(answer);
        }
    }
}
