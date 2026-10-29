
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

//UDP.Book: Chuẩn hóa title, author, ISBN, publishDate (cổng 2209)

class Book implements Serializable {

    private static final long serialVersionUID = 20251107L;

    private String id, title, author, isbn, publishDate;

    public Book(String id, String title, String author,
                String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}

public class De11 {

    public static String chuanHoa(String s) {
        String[] words = s.trim().toLowerCase(Locale.ROOT).split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) continue;

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }

        return result.toString();
    }

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

            Book b = (Book) ois.readObject();

            // 5. Chuan hoa title
            b.setTitle(chuanHoa(b.getTitle()));

            // 6. Chuan hoa author: HO, Ten
            String author = chuanHoa(b.getAuthor());
            String[] words = author.split("\\s+", 2);

            if (words.length > 1) {
                b.setAuthor(
                        words[0].toUpperCase(Locale.ROOT) + ", " + words[1]
                );
            } else {
                b.setAuthor(words[0].toUpperCase(Locale.ROOT));
            }

            // 7. Chuan hoa ISBN: 978-3-16-148410-0
            String isbn = b.getIsbn().replaceAll("[^0-9]", "");

            if (isbn.length() == 13) {
                String newIsbn =
                        isbn.substring(0, 3) + "-" +
                                isbn.substring(3, 4) + "-" +
                                isbn.substring(4, 6) + "-" +
                                isbn.substring(6, 12) + "-" +
                                isbn.substring(12, 13);

                b.setIsbn(newIsbn);
            }

            // 8. Chuyen ngay yyyy-mm-dd -> mm/yyyy
            String[] date = b.getPublishDate().split("-");

            String newDate = date[1] + "/" + date[0];

            b.setPublishDate(newDate);

            // 9. Chuyen Object thanh byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(b);
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

            System.out.println(b.getTitle());
            System.out.println(b.getAuthor());
            System.out.println(b.getIsbn());
            System.out.println(b.getPublishDate());
        }
    }
}
