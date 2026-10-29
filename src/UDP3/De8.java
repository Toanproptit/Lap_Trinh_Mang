
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;


//Employee: Chuẩn hóa tên, tăng lương x%, đổi ngày (cổng 2209)

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

public class De8 {
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

            // 3. Lay requestId
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
                if (name.length() > 0) {
                    name.append(" ");
                }

                name.append(
                        Character.toUpperCase(word.charAt(0))
                ).append(word.substring(1));
            }

            e.setName(name.toString());

            // 6. Tinh x = tong chu so cua nam
            String[] date = e.getHireDate().split("-");

            String year = date[0];
            int x = 0;

            for (char c : year.toCharArray()) {
                x += c - '0';
            }

            // 7. Tang luong x%
            double salary = e.getSalary() * (1 + x / 100.0);

            salary = Math.round(salary * 100.0) / 100.0;

            e.setSalary(salary);

            // 8. Doi ngay yyyy-mm-dd -> dd/mm/yyyy
            String newDate = date[2] + "/" + date[1] + "/" + date[0];

            e.setHireDate(newDate);

            // 9. Ghi Object thanh byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(e);
            oos.flush();

            byte[] objectData = bos.toByteArray();

            // 10. Ghep requestId + Object
            byte[] result = new byte[8 + objectData.length];

            System.arraycopy(requestId, 0, result, 0, 8);

            System.arraycopy(
                    objectData, 0, result, 8, objectData.length
            );

            // 11. Gui ket qua
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
