import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.*;

public class TCP_9 {

    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2210;

        String studentCode = "B23DCCN833";
        String qCode = "i97z54aG";

        try  {

            Socket s = new Socket(host, port);

            s.setSoTimeout(5000);

            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();

            // a
            send(out, studentCode + ";" + qCode);

            // b
            String str = receive(s, in);
            System.out.println("Nhan: [" + str + "]");

            // c
            String rev =
                    new StringBuilder(str)
                            .reverse()
                            .toString();

            String b64 =
                    Base64.getEncoder()
                            .encodeToString(
                                    rev.getBytes(StandardCharsets.UTF_8)
                            );

            String result = rev + "|" + b64;

            System.out.println("Gui: [" + result + "]");

            send(out, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void send(OutputStream out, String str)
            throws IOException {

        GZIPOutputStream gzip =
                new GZIPOutputStream(out);

        gzip.write(
                (str + "\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        gzip.finish();
        out.flush();
    }

    static String receive(Socket s, InputStream in)
            throws Exception {

        ByteArrayOutputStream data =
                new ByteArrayOutputStream();

        byte[] buf = new byte[4096];

        int n = in.read(buf);

        if (n == -1)
            throw new EOFException();

        data.write(buf, 0, n);

        int old = s.getSoTimeout();

        s.setSoTimeout(300);

        try {
            while (true) {

                try {
                    n = in.read(buf);

                    if (n == -1)
                        break;

                    data.write(buf, 0, n);

                } catch (SocketTimeoutException e) {
                    break;
                }
            }
        } finally {
            s.setSoTimeout(old);
        }

        byte[] raw = data.toByteArray();

        Inflater inflater = new Inflater(true);

        inflater.setInput(
                raw,
                10,
                raw.length - 10
        );

        ByteArrayOutputStream result =
                new ByteArrayOutputStream();

        byte[] tmp = new byte[4096];

        while (!inflater.finished()
                && !inflater.needsInput()) {

            int len = inflater.inflate(tmp);

            if (len > 0)
                result.write(tmp, 0, len);
            else
                break;
        }

        inflater.end();

        String text =
                result.toString(StandardCharsets.UTF_8);

        if (text.endsWith("\n"))
            text = text.substring(
                    0,
                    text.length() - 1
            );

        return text;
    }
}