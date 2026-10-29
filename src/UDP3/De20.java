
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

//Số lớn thứ hai và số nhỏ thứ hai (cổng 2207)

public class De20 {
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

            // 4. Sap xep va loai trung
            TreeSet<Integer> set = new TreeSet<>();

            for (String s : values) {
                set.add(Integer.parseInt(s.trim()));
            }

            if (set.size() < 2) {
                throw new IllegalArgumentException(
                        "Khong du 2 gia tri phan biet"
                );
            }

            // 5. Tim so lon thu hai, nho thu hai
            int secondMax = set.lower(set.last());
            int secondMin = set.higher(set.first());

            // 6. Tao ket qua
            String answer = requestId + ";"
                    + secondMax + "," + secondMin;

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
