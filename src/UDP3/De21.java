
package UDP;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class De21 {
    public static void main(String[] args) throws IOException {
        String host = "YOUR_SERVER_IP";
        int port = 2206;

        String studentCode = "B23DCCN833";
        String qCode = "YOUR_QCODE";

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);

        // a. Gui yeu cau
        String request = studentCode + ";" + qCode;

        byte[] sendData = request.getBytes();

        DatagramPacket packet = new DatagramPacket(
                sendData,
                sendData.length,
                InetAddress.getByName(host),
                port
        );

        socket.send(packet);

        // b. Nhan du lieu
        byte[] buffer = new byte[4096];

        DatagramPacket response = new DatagramPacket(
                buffer,
                buffer.length
        );

        socket.receive(response);

        String data = new String(
                response.getData(),
                0,
                response.getLength()
        );

        String[] parts = data.trim().split(",");
        int n = parts.length;

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i].trim());
        }

        // c. Tim day con tang ngat dai nhat
        int[] dp = new int[n];
        int[] prev = new int[n];

        int maxLen = 0;
        int endIndex = -1;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;

            for (int j = 0; j < i; j++) {
                if (a[j] < a[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }

            if (dp[i] > maxLen) {
                maxLen = dp[i];
                endIndex = i;
            }
        }

        // Truy vet day con
        List<Integer> result = new ArrayList<>();

        while (endIndex != -1) {
            result.add(a[endIndex]);
            endIndex = prev[endIndex];
        }

        Collections.reverse(result);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(result.get(i));
        }

        // Gui ket qua: dayCon;doDai
        String output = sb.toString() + ";" + maxLen;

        byte[] outputData = output.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(
                outputData,
                outputData.length,
                response.getAddress(),
                response.getPort()
        );

        socket.send(sendPacket);

        // d. Dong socket
        socket.close();
    }
}
