package UDP2;


import java.io.*;
import java.net.*;

class Students implements Serializable {

    private static final long serialVersionUID = 20171107;

    String id ,code , name , email;

    public Students(String id, String email, String name, String code) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.code = code;
    }

    public Students(String code) {
        this.code = code;
    }
}

public class m6yy73W3 {

    public static void main(String[] args) throws SocketException {

        String host = "36.50.135.242";
        int port = 2209;

        String studentCode = "B23DCCN833";
        String qCode = "m6yy73W3";

        try {
            DatagramSocket socket = new DatagramSocket();

            String request = ";" +studentCode + ";" + qCode;


            DatagramPacket packet = new DatagramPacket(
                    request.getBytes(),
                    request.length(),
                    InetAddress.getByName(host),
                    port
            );

            socket.send(packet);

            byte[] buffer = new byte[1024];

            DatagramPacket packet1  = new DatagramPacket(buffer , buffer.length);

            socket.receive(packet1);

            String requestId  = new String(packet1.getData() , 0 , 8);

            ByteArrayInputStream bis = new ByteArrayInputStream(packet1.getData() , 8 , packet1.getLength()-8);

            ObjectInputStream in =  new ObjectInputStream(bis);

            Students student = (Students) in.readObject();

            String[] listTen = student.name.split("\\s+");

            String ten = "";

            String email = ""+listTen[listTen.length-1].toLowerCase();

            for(int i=0;i< listTen.length-1;++i){
                email+=Character.toLowerCase(listTen[i].charAt(0));
            }

            for(String s : listTen){
                StringBuilder str = new StringBuilder(s);
                ten+=(str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase()+" ");
            }

            student.email = email+"@ptit.edu.vn";

            student.name = ten.trim();


            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream out = new ObjectOutputStream(bos);

            out.writeObject(student);
            out.flush();


            byte[] objectWrite = bos.toByteArray();

            byte[] requestIdWrite = requestId.getBytes();

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            output.write(requestIdWrite);
            output.write(objectWrite);



            DatagramPacket packet2  = new DatagramPacket(
                    output.toByteArray(),
                    output.size(),
                    InetAddress.getByName(host),
                    port
            );

            socket.send(packet2);

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
