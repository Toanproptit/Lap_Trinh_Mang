
package UDP;

import java.io.*;
import java.net.*;
import java.math.BigInteger;


//Cộng hai số nhị phân, chuyển sang thập phân (cổng 2208)


public class De12 {
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
            byte[] buffer = new byte[4096];

            DatagramPacket receivePacket =
                    new DatagramPacket(buffer, buffer.length);

            socket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()
            );

            // 3. Tach du lieu
            String[] parts = response.split(";", 2);

            String requestId = parts[0];

            String[] binary = parts[1].split(",");

            String b1 = binary[0].trim();
            String b2 = binary[1].trim();

            // 4. Chuyen nhi phan sang thap phan
            BigInteger a = new BigInteger(b1, 2);
            BigInteger b = new BigInteger(b2, 2);

            // 5. Tinh tong
            BigInteger sum = a.add(b);

            // 6. Tao ket qua
            String answer = requestId + ";" + sum;

            // 7. Gui ket qua
            byte[] answerData = answer.getBytes();

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData, answerData.length, address, port
            );

            socket.send(answerPacket);

            System.out.println(answer);
        }
    }
}
