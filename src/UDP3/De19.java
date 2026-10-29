
package UDP;

import java.io.*;
import java.net.*;
import java.util.Locale;


//UDP: Chuẩn hóa chuỗi thành dạng gạch ngang (cổng 2208)

public class De19 {
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

            // 3. Tach du lieu
            String[] parts = response.split(";", 2);

            String requestId = parts[0];
            String data = parts[1];

            // 4. Chuyen thanh chu thuong
            data = data.toLowerCase(Locale.ROOT);

            // 5. Loai bo dau cau
            data = data.replaceAll("\\p{P}", "");

            // 6. Xoa khoang trang thua
            data = data.trim().replaceAll("\\s+", " ");

            // 7. Thay khoang trang thanh gach ngang
            data = data.replace(" ", "-");

            // 8. Tao ket qua
            String answer = requestId + ";" + data;

            // 9. Gui ket qua
            byte[] answerData = answer.getBytes();

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData, answerData.length, address, port
            );

            socket.send(answerPacket);

            System.out.println(answer);
        }
    }
}
