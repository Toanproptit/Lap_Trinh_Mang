import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP22 {


    public static Boolean checknto(int n){
        for(int i=2;i<=Math.sqrt(n);++i){
            if(n%i==0)return false;
        }
        return n>1;
    }

    public static void main(String[] args) throws IOException {

        String host = "asdasdas";
        int port = 2206;

        Socket socket = new Socket(host , port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytes = in.read(buffer);
        String[] lisSo = new String(buffer , 0, bytes).split(",");

        int tong = 0;

        for(int i=0;i<lisSo.length;++i){
            if(checknto(i+1)){
                tong = (tong%65536 + Integer.parseInt(lisSo[i])%65536);
            }
        }
        tong%=65536;

        int xor = 0;
        for(int i=0;i< lisSo.length;++i){
            xor = Integer.parseInt(lisSo[i])^xor;
        }
        out.write(tong);
        out.write(xor);
        out.flush();
    }
}
