
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

//Loại ký tự đặc biệt, số và ký tự trùng (cổng 2208)

public class De7 {
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
            String strInput = parts[1];

            // 4. Loai ky tu dac biet, so, trung
            Set<Character> set = new HashSet<>();
            StringBuilder result = new StringBuilder();

            for (char c : strInput.toCharArray()) {
                if (Character.isLetter(c) && set.add(c)) {
                    result.append(c);
                }
            }

            // 5. Gui ket qua
            String answer = requestId + ";" + result;

            byte[] answerData = answer.getBytes();

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData, answerData.length, address, port
            );

            socket.send(answerPacket);

            System.out.println(answer);
        }
    }
}
