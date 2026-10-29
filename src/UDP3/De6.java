
package UDP;

import java.io.*;
import java.net.*;


// Đoạn con tăng nghiêm ngặt dài nhất (cổng 2207, hai pha)
public class De6 {
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

            DatagramPacket receivePacket = new DatagramPacket(
                    buffer, buffer.length
            );

            socket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()
            );

            // 3. Tach du lieu
            String[] parts = response.split(";", 2);

            String requestId = parts[0];
            String[] values = parts[1].split(",");

            int n = values.length;
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(values[i].trim());
            }

            // 4. Tim doan tang nghiem ngat dai nhat
            int start = 0;
            int bestStart = 0;
            int bestLength = 1;

            for (int i = 1; i < n; i++) {

                if (a[i] <= a[i - 1]) {
                    start = i;
                }

                int length = i - start + 1;

                if (length > bestLength) {
                    bestLength = length;
                    bestStart = start;
                }
            }

            // 5. Lay doan con va tinh tong
            StringBuilder segment = new StringBuilder();
            long sum = 0;

            for (int i = bestStart; i < bestStart + bestLength; i++) {
                if (segment.length() > 0) {
                    segment.append(",");
                }

                segment.append(a[i]);
                sum += a[i];
            }

            // 6. Tao ket qua
            String result = requestId
                    + ";segment=" + segment
                    + ";length=" + bestLength
                    + ";sum=" + sum;

            // 7. Gui ket qua
            byte[] answerData = result.getBytes();

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData,
                    answerData.length,
                    address,
                    port
            );

            socket.send(answerPacket);

            System.out.println(result);
        }
    }
}
