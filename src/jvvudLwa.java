import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class jvvudLwa {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int post = 2207;
        String studentCode = "B23DCCN833";
        String qCode = "jvvudLwa";

        try {
            Socket socket = new Socket(host , post);
            socket.setSoTimeout(5000);

            DataInputStream in= new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String request  = studentCode + ";" + qCode;

            out.writeUTF(request);
            out.flush();

            int a = in.readInt();
            int b = in.readInt();

            System.out.println(a + " "+ b);

            int tong = a+b;
            int tich = a*b;

            System.out.println(tong + " " + tich);
            out.writeInt(tong);
            out.writeInt(   tich);
            out.flush();

            out.close();
            in.close();
            socket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
