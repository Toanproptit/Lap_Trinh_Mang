import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ua2dJR9G {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int post = 2208;

        String studentCode = "B23DCCN833";
        String qCode = "ua2dJR9G";

        try {
            Socket socket = new Socket(host,post);

            socket.setSoTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = studentCode + ";" + qCode;

            out.write(request);
            out.newLine();
            out.flush();

            System.out.println("da gui du lieu len server " + request);

            String response = in.readLine();


            System.out.println("da nhan du lieu + "  + response);
            String[] list = response.split(" ");
            Map<Character,Integer> map = new LinkedHashMap<>();

            String result = "";

            for(String s:list){
                for(int i=0;i<s.length();++i){
                    map.put(s.charAt(i),map.getOrDefault(s.charAt(i),0)+1);
                }
            }
            for(Map.Entry<Character,Integer> mp : map.entrySet()){
                if(mp.getValue()>1){
                    result+=(mp.getKey()+":"+mp.getValue()+",");    
                }
            }


            out.write(result);
            out.newLine();
            out.flush();

            System.out.println("da gui du lieu len server + " + result);

            out.close();
            in.close();
            socket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
