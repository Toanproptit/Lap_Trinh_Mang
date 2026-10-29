
package UDP;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;


//Giải mã URL query string, sắp key (cổng 2208, hai pha)

public class De3 {
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

            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

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
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            // 3. Tach requestId va data
            String[] parts = response.split(";", 2);

            String requestId = parts[0];
            String data = parts[1];

            // 4. Tach cac cap key=value
            String[] pairs = data.split("&");

            TreeMap<String, String> map = new TreeMap<>();

            for (String pair : pairs) {

                String[] kv = pair.split("=", 2);

                String key = URLDecoder.decode(
                        kv[0], StandardCharsets.UTF_8
                );

                String value = URLDecoder.decode(
                        kv.length > 1 ? kv[1] : "",
                        StandardCharsets.UTF_8
                );

                map.put(key, value);
            }

            // 5. Tao chuoi ket qua
            StringBuilder result = new StringBuilder(requestId);

            for (Map.Entry<String, String> entry : map.entrySet()) {
                result.append(";")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }

            // 6. Gui ket qua
            byte[] answerData = result.toString()
                    .getBytes(StandardCharsets.UTF_8);

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
