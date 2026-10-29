import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class TCP_BYTE_RAW {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int post = 2206;
        String studentCode = "B23DCCN833";
        String qCode = "hgN8QLUL";

        try{
            Socket socket = new Socket(host , post);

            DataInputStream in = new DataInputStream(socket.getInputStream());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.write((studentCode+";"+qCode).getBytes());
            out.flush();



            System.out.println("data wrote");

            byte[] buffer = new byte[1024];
            int bytes = in.read(buffer);

            System.out.println("data read");


            String listNumber = new String(buffer , 0 ,bytes);


            String[] result = listNumber.split(",");

            List<Integer> listSo = new ArrayList<>();

            for(String s : result){
                listSo.add(Integer.parseInt(s));
            }

            int max1 = (int)-1e9 ;
            int max2 = (int)-1e9;
            int ketqua = (int)1e9;
            int n = listSo.size();
            for(int i=0;i<n;++i){
                for(int j = i+1;j<n;++j){
                    int hieu  = Math.abs(listSo.get(i) - listSo.get(j));
                    if(hieu<=ketqua){
                        ketqua = hieu;
                        int x = listSo.get(i);
                        int y = listSo.get(j);
                        if(x<y){
                            int r = x;
                            x = y;
                            y =r;
                        }
                        max1 = x;
                        max2 = y;
                    }
                }
            }
            String res = "" + ketqua+","+ max2 + "," + max1;
            System.out.println(res);

            out.write(res.getBytes());
            out.flush();


            socket.close();



        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}