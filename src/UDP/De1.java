package UDP;

import java.io.IOException;
import java.net.*;

public class De1 {
    public static void main(String[] args) throws IOException {
        String host = "Sdasdasd";
        int port = 2208;

        String studentCode = "B23DCCN833";
        String qCode = "asdasd";

        DatagramSocket socket = new DatagramSocket();

        String request = ";" + studentCode + ";" + qCode;

        DatagramPacket packet = new DatagramPacket(
                request.getBytes(),
                request.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet);

        byte[] buffer = new byte[1024];

        DatagramPacket packet1 = new DatagramPacket(buffer , buffer.length);

        socket.receive(packet1);

        String data = new String(packet1.getData() , 0 , packet1.getLength());

        String[] parts = data.split(";");
        String result = parts[0]+";";
        for(int i=0;i<parts[1].length();++i){
            if(parts[2].indexOf(parts[1].charAt(i)) == -1){
                result+=parts[1].charAt(i);
            }
        }

        DatagramPacket packet2 = new DatagramPacket(
                result.getBytes(),
                result.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet2);

        socket.close();


    }
}
