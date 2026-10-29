import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TCP_BYTE_RAW2 {
    public static void main(String[] args) {
        String host =  "36.50.135.242";
        int post = 2206;

        String studentCode = "B23DCCN833";
        String qCode = "hgN8QLUL";

        try{
            Socket socket = new Socket(host , post);
            socket.setSoTimeout(5000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();


            String request = studentCode + ";" + qCode;

            out.write(request.getBytes());
            out.flush();

            System.out.println("data requested" +request);

            byte[] bytes = new byte[1024];
            int bytess = in.read(bytes);

            String response = new String(bytes , 0 , bytess);
            String[] listNumber = response.trim().split(",");

            System.out.println("data receipted" + response );


            List<Integer> listSo = new ArrayList<>();

            for(String s : listNumber){
                listSo.add(Integer.parseInt(s));
            }

//            Collections.sort(listSo , Collections.reverseOrder());

            int max1 = (int)-1e9;
            int max2 = (int)-1e9;
            int vitri = 0;
            for(int i=0;i<listSo.size();++i){
                if(listSo.get(i)>=max1){
                    max1 = listSo.get(i);
                }
            }
            for(int i=0;i<listSo.size();++i){
                if(listSo.get(i)<max1 && listSo.get(i)>max2){
                    max2 = listSo.get(i);
                    vitri = i;
                }
            }

            String result = max2 + "," + vitri;

            out.write(result.getBytes());
            out.flush();

            System.out.println("data requested" + result);


            socket.close();


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
