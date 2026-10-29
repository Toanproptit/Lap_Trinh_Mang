package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class De4 {
    public static void main(String[] args) throws IOException {
        String host = "asasda";
        int port = 2027;

        String studentCode = "B23DCCN833";
        String qCode = "asasda";

        DatagramSocket socket = new DatagramSocket();

        String request = ";"+studentCode + ";" +qCode;

        DatagramPacket packet = new DatagramPacket(
                request.getBytes(),
                request.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet);

        byte[] buffer = new byte[1024];

        DatagramPacket packet1 = new DatagramPacket(buffer ,  buffer.length);

        socket.receive(packet1);

        String[] parts = new String(packet1.getData() , 0 , packet1.getLength()).split(";");

        String[] listSo = parts[1].split(",");

        float trungbinh = 0;

        for(int i=0;i<listSo.length;++i){
            trungbinh+= Integer.parseInt(listSo[i]);
        }

        List<Integer>list1 = new ArrayList<>();
        trungbinh/=listSo.length;

        int vitri = (int)Math.ceil(listSo.length*0.9)-1;

        int dem = 0;

        for(int i=0;i<listSo.length;++i){
            if(Integer.parseInt(listSo[i])>trungbinh){
                dem++;
            }
            list1.add(Integer.parseInt(listSo[i]));
        }

        Collections.sort(list1);

        String result = parts[0] + ";p90=" +list1.get(vitri) + ";" +"aboveAvg=" + dem;

        DatagramPacket packet2 = new DatagramPacket(
                result.getBytes() ,
                result.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet2);

        socket.close();

        

    }
}
