package UDP2;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class PbzyI5Vf {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int post = 2208;
        String studentCode = "B23DCCN833";
        String qCode = "PbzyI5Vf";

        try {
            DatagramSocket socket = new DatagramSocket();

            String request =  ";"+studentCode+";"+qCode;

            DatagramPacket packet = new DatagramPacket(
                    request.getBytes(),
                    request.length(),
                    InetAddress.getByName(host),
                    post
            );

            socket.send(packet);

            byte[] buffer = new byte[1024];

            DatagramPacket packet1 = new DatagramPacket(buffer , buffer.length);
            socket.receive(packet1);


            String response = new String(packet1.getData(), 0 ,packet1.getLength());

            String[] response1 = response.split(";");

            Map<Character , List<String>> map = new LinkedHashMap<>();

            for(int i=0;i<response1[1].length();++i){

                List<String> list = map.getOrDefault(response1[1].charAt(i) , new ArrayList<>());
                list.add(String.valueOf(i+1));
                map.put(response1[1].charAt(i) , list);
            }

            int max = 0;


            for(Map.Entry<Character , List<String>> map1 : map.entrySet()){
                if(map1.getValue().size()>max){
                    max = map1.getValue().size();
                }
            }
            String result = response1[0]+";";

            for(Map.Entry<Character , List<String>> map1 : map.entrySet()){
                if(map1.getValue().size() == max){
                    result += map1.getKey() + ":" + String.join( ",", map1.getValue());
                    result+=",";
                    break;
                }
            }

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
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
