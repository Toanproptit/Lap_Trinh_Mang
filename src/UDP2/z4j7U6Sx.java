package UDP2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class z4j7U6Sx {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int post = 2207;

        String studentCode = "B23DCCN833";
        String qCode = "z4j7U6Sx";

        try{

            DatagramSocket socket = new DatagramSocket();

            String request = ";"+studentCode + ";" + qCode;


            DatagramPacket packet = new DatagramPacket(
                    request.getBytes()
                    ,request.getBytes().length
                    , InetAddress.getByName(host), post);

            socket.send(packet);

            byte[] bytes = new byte[1024];

            DatagramPacket packet1 = new DatagramPacket(bytes , bytes.length);

            socket.receive(packet1);

            String response = new String (packet1.getData() , 0 , packet1.getLength());

            String[] parts = response.trim().split(";");

            int n = Integer.parseInt(parts[1]);

            boolean[] mark = new boolean[n+1];

            String[] number = parts[2].split(",");

            for(int i=2;i<number.length;++i){
                mark[Integer.parseInt(number[i])] = true;
            }

            String result  = parts[0]+";";

            List<String> list = new ArrayList<>();

            for(int i=1;i<=n;++i){
                if(!mark[i]){
                    list.add(String.valueOf(i));
                }
            }
            result += String.join("," , list);

            DatagramPacket packet2 = new DatagramPacket(
                    result.getBytes(),
                    result.length(),
                    InetAddress.getByName(host),
                    post
            );

            socket.send(packet2);

            socket.close();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
