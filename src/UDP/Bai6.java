package UDP;


import java.io.*;
import java.net.*;

class Customer implements Serializable{

    private static final long serialVersionUID = 20151107;

    String id , code , name , dayOfBirth , userName;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }
}

public class Bai6 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "36.50.135.242";
        int port = 2209;

        String studentCode =  "B23DCCN833";
        String qCode = "MQzmwNyo";

        DatagramSocket socket = new DatagramSocket();

        String request = ";" + studentCode  + ";" +qCode;

        DatagramPacket packet = new DatagramPacket(
                request.getBytes(),
                request.length(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet);


        byte[] buffer = new byte[1024];

        DatagramPacket packet1 = new DatagramPacket(buffer , buffer.length);

        socket.receive(packet1);

        String requestId = new String(packet1.getData()  , 0 , 8);

        ByteArrayInputStream bis = new ByteArrayInputStream(packet1.getData() , 8 , packet1.getLength()-8);
        ObjectInputStream in = new ObjectInputStream(bis);

        Customer customer  = (Customer) in.readObject();

        String[] listTen =  customer.name.split("\\s+");

        String name = "";
        String username = "";
        int n = listTen.length;
        for(int i=0;i<n-1;++i){
            String s = listTen[i];
            StringBuilder str = new StringBuilder(s);
            name+=" " +str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
            username+=Character.toLowerCase(str.charAt(0));
        }

        customer.userName = username+listTen[listTen.length-1].toLowerCase();
        customer.name = listTen[listTen.length-1].toLowerCase().toUpperCase() +"," + name;

        String[] listNgay = customer.dayOfBirth.split("-");
        customer.dayOfBirth = listNgay[1] + "/" + listNgay[0] + "/" + listNgay[2];

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(customer);
        out.flush();

        byte[] requestIdByte = requestId.getBytes();
        byte[] objectwrite = bos.toByteArray();

        ByteArrayOutputStream outWrite = new ByteArrayOutputStream();
        outWrite.write(requestIdByte);
        outWrite.write(objectwrite);

        DatagramPacket packet2 = new DatagramPacket(
                outWrite.toByteArray(),
                outWrite.size(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet2);
        socket.close();

    }
}
