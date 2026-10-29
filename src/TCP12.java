import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP12 {
    public static void main(String[] args) {
        String host = "sdfsdfsdf";
        int port = 2207;

        String studentCode = "B23DCCN833";
        String qCode = "sldkfjsdf";

        try {
            Socket socket = new Socket(host  , port);
            socket.setSoTimeout(5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String request = studentCode + ";" + qCode;

            out.writeUTF(request);
            out.flush();

            int n = in.readInt();

            String s1 = Integer.toBinaryString(n);
            String s2 = Integer.toHexString(n).toUpperCase();

            out.writeUTF(s1+";"+s2);
            out.flush();

            socket.close();



        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
