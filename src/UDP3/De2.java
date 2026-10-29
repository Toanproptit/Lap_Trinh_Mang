
package UDP;

import java.io.*;
import java.net.*;

class Product implements Serializable {
    private static final long serialVersionUID = 20161107L;

    String id, code, name;
    int quantity;

    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
}

// Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: "lenovo thinkpad T520" bị chuyển thành "T520 thinkpad lenovo"

public class De2 {
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

            // 3. Lay requestId (8 byte dau)
            byte[] requestId = new byte[8];

            System.arraycopy(
                    buffer, 0, requestId, 0, 8
            );

            // 4. Doc Object
            ByteArrayInputStream bis = new ByteArrayInputStream(
                    buffer, 8, receivePacket.getLength() - 8
            );

            ObjectInputStream ois = new ObjectInputStream(bis);

            Product p = (Product) ois.readObject();

            // 5. Dao tu dau va tu cuoi
            String[] words = p.name.trim().split("\\s+");

            if (words.length > 1) {
                String temp = words[0];
                words[0] = words[words.length - 1];
                words[words.length - 1] = temp;
            }

            p.name = String.join(" ", words);

            // 6. Dao nguoc quantity
            p.quantity = Integer.parseInt(
                    new StringBuilder(String.valueOf(p.quantity))
                            .reverse()
                            .toString()
            );

            // 7. Ghi Object thanh byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(p);
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

            System.out.println(p.name);
            System.out.println(p.quantity);
        }
    }
}
