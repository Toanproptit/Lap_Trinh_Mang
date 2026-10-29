
package UDP;

import java.io.*;
import java.net.*;

//UDP: Tính Checksum (cổng 2207)UDP: Tính Checksum (cổng 2207)

public class De18 {
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

            // 4. Tinh checksum
            long sum = 0;

            for (int i = 0; i < values.length; i++) {
                int x = Integer.parseInt(values[i].trim());

                sum += (long) (i + 1) * x;
            }

            long checksum = Math.floorMod(sum, 100000);

            // 5. Tao ket qua
            String answer = requestId + ";" + checksum;

            // 6. Gui ket qua
            byte[] answerData = answer.getBytes();

            DatagramPacket answerPacket = new DatagramPacket(
                    answerData, answerData.length, address, port
            );

            socket.send(answerPacket);

            System.out.println(answer);
        }
    }
}
