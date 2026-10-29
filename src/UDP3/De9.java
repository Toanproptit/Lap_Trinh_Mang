
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;


//PricedProduct: Tính finalPrice không có fee (cổng 2209)

class PricedProduct implements Serializable {

    private static final long serialVersionUID = 20260517L;

    private String id;
    private String name;
    private double basePrice;
    private double taxRate;
    private double discountRate;
    private double finalPrice;

    public PricedProduct() {}

    public PricedProduct(String id, String name,
                         double basePrice, double taxRate,
                         double discountRate) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.taxRate = taxRate;
        this.discountRate = discountRate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double v) { basePrice = v; }

    public double getTaxRate() { return taxRate; }
    public void setTaxRate(double v) { taxRate = v; }

    public double getDiscountRate() { return discountRate; }
    public void setDiscountRate(double v) { discountRate = v; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double v) { finalPrice = v; }
}

public class De9 {
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

            // 3. Lay 8 byte requestId
            byte[] requestId = Arrays.copyOfRange(
                    buffer, 0, 8
            );

            // 4. Doc Object
            ByteArrayInputStream bis = new ByteArrayInputStream(
                    buffer, 8, receivePacket.getLength() - 8
            );

            ObjectInputStream ois = new ObjectInputStream(bis);

            PricedProduct p = (PricedProduct) ois.readObject();

            // 5. Tinh finalPrice (khong co fee)
            double finalPrice = p.getBasePrice()
                    * (1 + p.getTaxRate() / 100)
                    * (1 - p.getDiscountRate() / 100);

            // 6. Lam tron 2 chu so
            finalPrice = Math.round(finalPrice * 100.0) / 100.0;

            p.setFinalPrice(finalPrice);

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

            System.out.println(p.getFinalPrice());
        }
    }
}
