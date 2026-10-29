package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.TreeMap;

public class De3 {
    public static void main(String[] args) throws IOException {
        String host = "asdasda";
        int port = 2208;
        String studentCode = "B23DCCN833";
        String qCode = "asdasd";

        String request = ";" + studentCode +";"+qCode;

        DatagramSocket socket = new DatagramSocket();

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

        String data = new String(packet1.getData() ,0 , packet1.getLength());

        String[] parts = data.split(";");

        String[] list = parts[1].split("&");

        TreeMap<String , String> map = new TreeMap<>();

        for(String s :list){
            String[] listKey = s.split("=");

            String key = URLDecoder.decode(listKey[0] , "UTF-8");
            String value = URLDecoder.decode(listKey[1] , "UTF-8");
            map.put(key,value);
        }

        String result =  parts[0];
        for(Map.Entry<String, String> m: map.entrySet()){
            String key = m.getKey();
            String value = m.getValue();

            result+=";"+key+"="+value;
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
