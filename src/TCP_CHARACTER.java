import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class TCP_CHARACTER {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int post = 2208;
        String msv = "B23DCCN833";
        String qCode = "udZH2k2i";

        try {
            Socket socket = new Socket(serverAddress,post);
            socket.setSoTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()
            ));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = msv + ";" + qCode;
            out.write(request);
            out.newLine();
            out.flush();

            System.out.println(" da gui" + request + "len server");

            String response = in.readLine();
            System.out.println("Data Receipted");

            String[] listDoMain = response.split(",");

            List<String> list = new ArrayList<>();


            String result = String.join(", ",list);

            for(int i =0;i<list.size();++i){
                result+=list.get(i);
                if(i!= list.size()-1) result+=", ";
            }
            out.write(result);
            out.newLine();
            out.flush();

            in.close();
            out.close();
            socket.close();


        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
