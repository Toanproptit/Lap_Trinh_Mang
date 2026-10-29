
package UDP;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;


//Đề 1. Loại ký tự của str1 xuất hiện trong str2 (cổng 2208)

public class De1 {
    public static void main(String[] args) throws IOException {

        String host = "YOUR_SERVER_IP";
        int port = 2208;

        String studentCode = "B23DCCN833";
        String qCode = "YOUR_QCODE";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);

            // 1. Gui yeu cau
            String request = ";" + studentCode + ";" + qCode;

            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    InetAddress.getByName(host),
                    port
            );

            socket.send(sendPacket);

            // 2. Nhan du lieu
            byte[] buffer = new byte[4096];

            DatagramPacket receivePacket = new DatagramPacket(
                    buffer, buffer.length
            );

            socket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(),
                    receivePacket.getOffset(),
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            String[] parts = response.split(";", 3);

            String requestId = parts[0];
            String str1 = parts[1];
            String str2 = parts[2];

            // 3. Xu ly chuoi
            Set<Character> set = new HashSet<>();

            for (char c : str2.toCharArray()) {
                set.add(c);
            }

            StringBuilder result = new StringBuilder();

            for (char c : str1.toCharArray()) {
                if (!set.contains(c)) {
                    result.append(c);
                }
            }

            // 4. Gui ket qua
            String answer = requestId + ";" + result;

            byte[] answerData = answer.getBytes(StandardCharsets.UTF_8);

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData,
                    answerData.length,
                    InetAddress.getByName(host),
                    port
            );

            socket.send(answerPacket);

            System.out.println(answer);
        }
    }
}
