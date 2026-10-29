package UDP;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Bai1 {
    public static void main(String[] args) throws IOException {

        String host = "36.50.135.242";
        int port = 2207;

        String studentCode  = "B23DCCN833";
        String qCode = "z4j7U6Sx";

        DatagramSocket socket = new DatagramSocket();

        String request  = ";" + studentCode + ";" + qCode;

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

        System.out.println(parts[1]);

        int[] danhdau = new int[105];

        for(int i=0;i<105;++i){
            danhdau[i] = 0;
        }

        System.out.println(parts[2]);

        for(String s : parts[2].trim().split(",")){
            int n = Integer.parseInt(s);
            danhdau[n] = 1;
        }
        int n = Integer.parseInt(parts[1]);
        List<String> list = new ArrayList<>();

        for(int i=1;i<=n;++i){
            if(danhdau[i] == 0){
                list.add(String.valueOf(i));
            }
        }
        String result = parts[0]  + ";" + String.join("," , list);

        System.out.println(result);

        DatagramPacket packet2 = new DatagramPacket(
                result.getBytes(),
                result.length(),
                InetAddress.getByName(host) ,
                port
        );

        socket.send(packet2);

        socket.close();

    }
}
