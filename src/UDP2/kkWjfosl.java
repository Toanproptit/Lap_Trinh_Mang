package UDP2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class kkWjfosl {
    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port  = 2207;

        String studentCode = "B23DCCN833";
        String qCode = "kkWjfosl";

        try {
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

            String response = new String(packet1.getData() , 0 , packet1.getLength());

            String[] parts = response.trim().split(";");

            String[] numbers = parts[1].split(",");

            List<Integer> listSo = new ArrayList<>();
            for(String s :numbers){
                listSo.add(Integer.parseInt(s));
            }

            Collections.sort(listSo);

            String result = parts[0] + ";" + listSo.get(listSo.size()-1) + "," + listSo.get(0);

            DatagramPacket packet2 = new DatagramPacket(
                    result.getBytes(),
                    result.length(),
                    InetAddress.getByName(host),
                    port
            );
            socket.send(packet2);

            socket.close();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
