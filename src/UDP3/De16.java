
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;
import java.math.*;


//Bài 16 – UDP: Tìm trung vị (cổng 2207)

public class De16 {
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
            String[] parts = response.split(";", 2);

            String requestId = parts[0];
            String[] values = parts[1].split(",");

            int n = values.length;
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(values[i].trim());
            }

            // 4. Sap xep tang dan
            Arrays.sort(a);

            // 5. Tinh trung vi
            String median;

            if (n % 2 == 1) {
                median = String.valueOf(a[n / 2]);
            } else {
                long sum = (long) a[n / 2 - 1] + a[n / 2];

                median = BigDecimal.valueOf(sum)
                        .divide(BigDecimal.valueOf(2), 2,
                                RoundingMode.HALF_UP)
                        .toPlainString();
            }

            // 6. Tao ket qua
            String answer = requestId + ";" + median;

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
