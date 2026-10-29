import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP13 {
    public static void main(String[] args) {
        String host = "sdfsdfsdf";
        int port = 2207;

        String studentCode = "B23DCCN833";
        String qCode = "sldkfjsdf";

        try {
            Socket socket = new Socket(host  , port);
            socket.setSoTimeout(5000);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request = studentCode + ";" + qCode;

           out.write(request);
           out.newLine();
           out.flush();

           String tu = in.readLine();
           String[] listTu = tu.trim().split("\\s+");

           StringBuilder s = new StringBuilder(tu);

           int vitri = 0;
           int vitri2 = 0;
           for(int i=1;i<listTu.length;++i){
               if(listTu[i].length()>listTu[vitri].length()){
                   vitri = i;
               }
           }

           for(int i=0;i<s.length()-listTu[vitri].length();++i){
               if(s.substring(i,i+listTu[vitri].length()).equals(listTu[vitri])){
                   vitri2 = i;
                   break;
               }
           }



           out.write(listTu[vitri]);
           out.write(vitri2);

           out.newLine();
           out.flush();

           socket.close();


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
