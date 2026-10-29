
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;


//Tìm các giá trị còn thiếu, dãy có thể trùng (cổng 2207)

public class De10 {
    public static void main(String[] args) throws IOException {

        String host = "YOUR_SERVER_IP";
        int port = 2207;

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
            String[] parts = response.split(";", 3);

            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);

            String[] values = parts[2].split(",");

            // 4. Luu cac so da xuat hien
            Set<Integer> set = new HashSet<>();

            for (String s : values) {
                if (!s.trim().isEmpty()) {
                    set.add(Integer.parseInt(s.trim()));
                }
            }

            // 5. Tim cac so con thieu
            StringBuilder result = new StringBuilder();

            for (int i = 1; i <= n; i++) {
                if (!set.contains(i)) {
                    if (result.length() > 0) {
                        result.append(",");
                    }

                    result.append(i);
                }
            }

            // 6. Tao ket qua
            String answer = requestId + ";" + result;

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
