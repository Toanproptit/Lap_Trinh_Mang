package UDP2;

import java.io.IOException;
import java.net.*;

public class QAKRp6bK {
    public static void main(String[] args) {
        String host =   "36.50.135.242";
        int port = 2208;

        String studentCode = "B23DCCN833";
        String qCode =  "QAKRp6bK";

        try {
            DatagramSocket socket = new DatagramSocket();

            String request = ";" + studentCode + ";" +qCode;

            DatagramPacket packet = new DatagramPacket(
                    request.getBytes() ,
                    request.length(),
                    InetAddress.getByName(host) ,
                    port
            );

            socket.send(packet);


            byte[] buffer = new byte[1024];
            DatagramPacket packet1 = new DatagramPacket(buffer , buffer.length);

            socket.receive(packet1);

            String[] parts = new String(packet1.getData() , 0 ,packet1.getLength()).trim().split(";");

            String[] listTu = parts[1].split("\\s+");

            String result = parts[0]+";";

            for(String s : listTu){
                StringBuilder str = new StringBuilder(s);
                String tu= str.substring(0,1).toUpperCase()+ str.substring(1).toLowerCase();
                result+=(tu+" ");
            }

            request.trim();

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
