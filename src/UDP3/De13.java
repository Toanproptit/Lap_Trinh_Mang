package UDP3;

import java.io.IOException;
import java.net.*;

public class De13 {
    public static void main(String[] args) throws IOException {
        String host = "asdasd";
        int port = 2208;

        String studentCode = "B23DCCN833";
        String qCode = "asdasd";


        DatagramSocket socket = new DatagramSocket();

        String request = ";" + studentCode + ";" + qCode;

        DatagramPacket packet = new DatagramPacket(
                request.getBytes(),
                request.length(),
                InetAddress.getByName(host) ,
                port
        );

        socket.send(packet);

        byte[] buffer = new byte[1024];

        DatagramPacket packet1 = new DatagramPacket(buffer , buffer.length);

        socket.receive(packet1);

        String[] parts = new String(packet1.getData() , 0 , packet1.getLength()).split(";");

        String log = parts[1].replaceAll("(?i)(\\bemail=)[^\\s|]+" , "email=[EMAIL]")
                            .replaceAll("(?i)(\\bphone=)[^\\s|]+" , "phone=[PHONE]")
                            .replaceAll("(?i)(\\btoken=)[^\\s|]+" , "token=[TOKEN]");

        String[] line  = log.split("\\|\\|");

        int error = 0 , info = 0 , warn = 0;

        for(String s : line){
            if(s.startsWith("ERROR"))error++;
            else if(s.startsWith("INFO"))info++;
            else if(s.startsWith("WARN")) warn++;
        }
        String result = parts[0] + ";" +log +"##"+"ERROR=" +error +";" + "INFO="+info+";"+"WARN="+warn;

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
