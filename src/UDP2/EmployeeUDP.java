package UDP2;


import java.io.*;
import java.net.*;

class Employee implements Serializable{

    private static final long serialVersionUID = 20261107L;

    String id , name , hireDate;
    double salary;

    public Employee(String id, double salary, String name, String hireDate) {
        this.id = id;
        this.salary = salary;
        this.name = name;
        this.hireDate = hireDate;
    }
}





public class EmployeeUDP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String host = "asdasd";
        int port = 2209;

        String studentCode = "B23DCCN833";
        String qCode = "asdasd";

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

        Employee employee = (Employee) in.readObject();

        String name = employee.name;
        String[] listTu = name.split("\\s+");
        String name1 = "";

        for(String s:listTu){
            StringBuilder str = new StringBuilder(s);
            name1+= str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase()+" ";
        }

        employee.name = name1.trim();

        double salary = employee.salary;

        salary = salary*1.08;
        employee.salary = Math.round(salary*100)/100.0;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(employee);
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
