import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP18 {
    public static void main(String[] args) {
        try{

            String host = "dasdasd";
            int port = 2204;

            Socket socket = new Socket(host , port);

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            byte[] buffer = new byte[1024];
            int bytes = in.read(buffer);

            int n = Integer.parseInt(new String(buffer , 0 , bytes));

            int dem = 0;

            String result ="";

            while(n!=1){
                result+=(n+" ");
                dem++;
                if(n%2==0){
                     n/=2;
                }
                else {
                    n = 3*n +1;
                }
            }
            result+=1;
            result = result.trim()+"; "+dem;

            out.write(result.getBytes());
            out.flush();



        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
