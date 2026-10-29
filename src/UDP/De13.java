package UDP;

import java.io.IOException;
import java.net.*;

public class De13 {
    public static void main(String[] args) throws IOException {
        String host = "ASdasd";
        int port  = 2208;

        String studentCode = "B23DCCN833";
        String qCode = "asdasd";

        DatagramSocket socket = new DatagramSocket();

        String result = ";" + studentCode + ";" +qCode;

        DatagramPacket packet = new DatagramPacket(
                result.getBytes(),
                result.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet);



    }
}
