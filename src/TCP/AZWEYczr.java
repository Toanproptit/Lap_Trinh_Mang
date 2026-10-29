package TCP;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

class Customer implements Serializable {

    private static final long serialVersionUID = 20170711L;
    int id;
    String code , name , dayOfBirth, userName;
}

public class AZWEYczr {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int post = 2209;

        String studentCode = "B23DCCN833";
        String qCode =  "AZWEYczr";

        try{
            Socket  socket = new Socket( host, post);
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());

            String result = studentCode + ";" + qCode;
            out.writeObject(result);
            out.flush();

            Customer customer = (Customer) in.readObject();

            String[] listTen = customer.name.trim().split("\\s+");

            String ten = listTen[listTen.length-1].toUpperCase() + ", ";

            for(int i=0;i<listTen.length-1;++i){

                StringBuilder str = new StringBuilder(listTen[i]);
                String chuoi="";
                for(int j = 0;j<str.length();++j){
                    if(j==0){
                        chuoi+=Character.toUpperCase(str.charAt(j));
                    }
                    else {
                        chuoi+=Character.toLowerCase(str.charAt(j));
                    }
                }
                ten+=(chuoi+" ");
            }

            String ngaysinh = customer.dayOfBirth;
            String[] listNgaySinh = ngaysinh.split("-");
            customer.dayOfBirth = listNgaySinh[1]+"/" + listNgaySinh[0] +"/"+listNgaySinh[2];

            customer.name = ten.trim();

            String username = "";
            for(int i=0;i<listTen.length;++i){
                if(i!=listTen.length-1){
                    username+=listTen[i].charAt(0);
                }
                else username+=listTen[i];
            }
            customer.userName = username.toLowerCase();

            out.writeObject(customer);
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
