package UDP;


import java.io.*;
import java.net.*;

class  Student implements Serializable {

    private static final long serialVersionUID = 20171107;

    String id , code , name , email;

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }


}

public class Bai5 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "36.50.135.242";
        int port = 2209;

        String studentCode = "B23DCCN833";
        String qCode = "m6yy73W3";

        DatagramSocket socket = new DatagramSocket();

        String request = ";" + studentCode +";" + qCode;

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

        Student student = (Student) in.readObject();

        String[] listten = student.name.split("\\s+");

        String tenmoi = "";
        String email = listten[listten.length-1].toLowerCase();
        for(int i=0;i<listten.length;++i){
            String s = listten[i];
            StringBuilder str = new StringBuilder(s);
            tenmoi+=str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase()+" ";
            if(i!=listten.length-1){
                email+=Character.toLowerCase(s.charAt(0));
            }
        }


        student.email = email + "@ptit.edu.vn";
        System.out.println(student.code);
        student.name = tenmoi.trim();

        byte[] requestByte = requestId.getBytes();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(student);
        out.flush();

        byte[] studentByte = bos.toByteArray();

        ByteArrayOutputStream writeObject = new ByteArrayOutputStream();
        writeObject.write(requestByte);
        writeObject.write(studentByte);

        DatagramPacket packet2 = new DatagramPacket(
                writeObject.toByteArray(),
                writeObject.size(),
                InetAddress.getByName(host),
                port
        );

        socket.send(packet2);

        socket.close();



    }
}
