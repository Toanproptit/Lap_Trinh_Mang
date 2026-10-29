package TCP;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;


class Laptop implements Serializable{
    private static final long serialVersionUID = 20150711L;
    public int id , quantity;
    public String code, name;
}

public class yufE0iSc {
    public static void main(String[] args) {
        String host  = "36.50.135.242";
        int post = 2209;

        String studentCode = "B23DCCN833";
        String qCode = "yufE0iSc";

        try{
            Socket socket = new Socket(host , post);
            socket.setSoTimeout(5000);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            String result = studentCode + ";" + qCode;

            out.writeObject(result);
            out.flush();


            Laptop Laptop = (Laptop) in.readObject();

            String[] listTen = Laptop.name.split(" ");
            String a = listTen[0];
            listTen[0] = listTen[listTen.length-1];
            listTen[listTen.length-1] = a;

            Laptop.name = String.join(" ",listTen);

            Laptop.quantity = Integer.parseInt(new StringBuilder(""+Laptop.quantity).reverse().toString());

            out.writeObject(Laptop);
            out.flush();

            socket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
