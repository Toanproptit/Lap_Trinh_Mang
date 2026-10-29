import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RltzxdKj {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN833";
        String qCode = "RltzxdKj";

        try{
            Socket socket = new Socket(host , port);
            socket.setSoTimeout(5000);


            String request = studentCode + ";" +qCode;

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(request);
            out.flush();

            String chuoi = in.readUTF();
            int s = in.readInt();
            int chan1 = 'A';
            int chan2 = 'Z';



            String result = "";

            for(int i=0;i<chuoi.length();++i){
                if(Character.isDigit(chuoi.charAt(i))){
                    result+=chuoi.charAt(i);
                    continue;
                }
                char a = (char)('A' + (chuoi.charAt(i)-'A'-s+26)%26);
                result+=a;
            }

            System.out.println(result);

            out.writeUTF(result);
            out.flush();

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
