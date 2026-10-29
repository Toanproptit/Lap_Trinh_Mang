
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;


//UDP.Employee: Chuẩn hóa tên, tăng lương 8% (cổng 2209)

class Employee implements Serializable {

    private static final long serialVersionUID = 20261107L;

    private String id;
    private String name;
    private double salary;
    private String hireDate;

    public Employee(String id, String name,
                    double salary, String hireDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }
}

public class De15 {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        String host = "YOUR_SERVER_IP";
        int port = 2209;

        String studentCode = "B23DCCN833";
        String qCode = "YOUR_QCODE";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(host);

            // 1. Gui yeu cau
            String request = ";" + studentCode + ";" + qCode;

            byte[] sendData = request.getBytes();

            DatagramPacket packet = new DatagramPacket(
                    sendData, sendData.length, address, port
            );

            socket.send(packet);

            // 2. Nhan du lieu
            byte[] buffer = new byte[65507];

            DatagramPacket receivePacket =
                    new DatagramPacket(buffer, buffer.length);

            socket.receive(receivePacket);

            // 3. Tach requestId
            byte[] requestId = Arrays.copyOfRange(
                    buffer, 0, 8
            );

            // 4. Doc Object
            ByteArrayInputStream bis = new ByteArrayInputStream(
                    buffer, 8, receivePacket.getLength() - 8
            );

            ObjectInputStream ois = new ObjectInputStream(bis);

            Employee e = (Employee) ois.readObject();

            // 5. Chuan hoa name
            String[] words = e.getName().trim()
                    .toLowerCase(Locale.ROOT).split("\\s+");

            StringBuilder name = new StringBuilder();

            for (String word : words) {
                if (word.isEmpty()) continue;

                if (name.length() > 0) {
                    name.append(" ");
                }

                name.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            }

            e.setName(name.toString());

            // 6. Tang luong 8%
            double salary = e.getSalary() * 1.08;

            salary = Math.round(salary * 100.0) / 100.0;

            e.setSalary(salary);

            // 7. Chuyen Object thanh byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(e);
            oos.flush();

            byte[] objectData = bos.toByteArray();

            // 8. Ghep requestId + Object
            byte[] result = new byte[8 + objectData.length];

            System.arraycopy(requestId, 0, result, 0, 8);

            System.arraycopy(
                    objectData, 0, result, 8, objectData.length
            );

            // 9. Gui ket qua
            DatagramPacket answerPacket = new DatagramPacket(
                    result, result.length, address, port
            );

            socket.send(answerPacket);

            System.out.println(e.getName());
            System.out.println(e.getSalary());
            System.out.println(e.getHireDate());
        }
    }
}
