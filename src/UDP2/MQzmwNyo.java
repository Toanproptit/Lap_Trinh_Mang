package UDP2;


import java.io.*;
import java.net.*;

class Customer implements Serializable{

    private static final long serialVersionUID = 20151107;

    String id , code , name ,dayOfBirth, userName;
}

public class MQzmwNyo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "36.50.135.242";
        int port = 2209;

        String studentCode = "B23DCCN833";
        String qCode = "MQzmwNyo";

        DatagramSocket socket = new DatagramSocket();

        String request = ";" + studentCode + ";" + qCode;

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

        String requestId = new String(packet1.getData() , 0 , 8);

        ByteArrayInputStream bis = new ByteArrayInputStream(packet1.getData() , 8 , packet1.getLength()-8);

        ObjectInputStream in = new ObjectInputStream(bis);

        Customer student = (Customer) in.readObject();

        String name = student.name;
        String[] ten = name.split("\\s+");

        String ten1 = ten[ten.length-1].toUpperCase()+",";

        for(int i=0;i<ten.length-1;++i){

            String ten2 = "";
            StringBuilder str = new StringBuilder(ten[i]);
            ten2 = str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
            ten1+=(" "+ten2);
        }
        student.name = ten1;

        String[] ngaysinh = student.dayOfBirth.split("-");
        student.dayOfBirth = ngaysinh[1]+"/"+ngaysinh[0]+"/"+ngaysinh[2];

        String username = "";

        for(int i=0;i<ten.length-1;++i){
            username += Character.toLowerCase(ten[i].charAt(0));
        }

        username += ten[ten.length-1].toLowerCase();

        student.userName = username;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(student);
        out.flush();

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        output.write(requestId.getBytes());
        output.write(bos.toByteArray());


        DatagramPacket packet2 = new DatagramPacket(
                output.toByteArray(),
                output.size(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet2);


        socket.close();


    }
}
