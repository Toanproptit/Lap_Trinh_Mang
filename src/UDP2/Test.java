package UDP2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        String host = "36.50.135.242";
        int port = 2206;

        String studentCode = "B23DCCN833";
        String qCode = "m6yy73W3";

        DatagramSocket socket = new DatagramSocket();

        String request =  studentCode + ";" + qCode;

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

        String response = new String(packet1.getData() , 0 ,packet1.getLength());

        String[] parts = response.split(";");

        String[] numbers = parts[1].split(",");

        Integer[] number = new Integer[numbers.length];

        for(int i=0;i<numbers.length;++i){
            number[i] = Integer.parseInt(numbers[i]);
        }

        int[] dp = new int[number.length];
        int[] truyvet = new int[number.length];


        int maxLen = 0;
        int vitricuoi =0;

        for(int i=0;i<number.length;++i){

            dp[i]=1;
            truyvet[i] = -1;

            for(int j=0;j<i;++j){
                if(number[i]>number[j]){
                    if(dp[j]+1>dp[i]){
                        dp[i] = dp[j]+1;
                        truyvet[i] = j;
                    }
                }
            }

            if(dp[i]>maxLen){
                maxLen = dp[i];
                vitricuoi = i;
            }
        }

        List<Integer> listKetqua  = new ArrayList<>();


        int current = vitricuoi;

        while(current!=-1){
            listKetqua.add(0,number[current]);
            current = truyvet[current];
        }

        String result = parts[0] +  String.join(",", (CharSequence)listKetqua);
        result+=(";"+maxLen);

        DatagramPacket packet2 = new DatagramPacket(
                result.getBytes(),
                result.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet2);

    }
}
