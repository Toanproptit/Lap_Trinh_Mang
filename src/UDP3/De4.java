
package UDP;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


//Phân vị 90 và số phần tử lớn hơn trung bình (cổng 2207, hai pha)

public class De4 {
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

            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packet = new DatagramPacket(
                    sendData,
                    sendData.length,
                    address,
                    port
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
                    receivePacket.getOffset(),
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            // 3. Tach requestId va data
            String[] parts = response.split(";", 2);

            String requestId = parts[0];
            String[] values = parts[1].split(",");

            int n = values.length;
            int[] a = new int[n];

            long sum = 0;

            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(values[i].trim());
                sum += a[i];
            }

            // 4. Tinh trung binh
            double avg = (double) sum / n;

            // 5. Dem phan tu lon hon trung binh
            int aboveAvg = 0;

            for (int x : a) {
                if (x > avg) {
                    aboveAvg++;
                }
            }

            // 6. Sap xep tang dan
            Arrays.sort(a);

            // 7. Tinh phan vi 90
            int index = (int) Math.ceil(n * 0.9) - 1;
            int p90 = a[index];

            // 8. Tao ket qua
            String result = requestId
                    + ";p90=" + p90
                    + ";aboveAvg=" + aboveAvg;

            // 9. Gui ket qua
            byte[] answerData = result.getBytes(StandardCharsets.UTF_8);

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
