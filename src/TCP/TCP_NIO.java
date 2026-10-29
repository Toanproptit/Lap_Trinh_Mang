package TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCP_NIO {

    public static String readFrame(SocketChannel socket) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);

        while(buffer.hasRemaining()) socket.read(buffer);

        buffer.flip();

        int len = buffer.getInt();

        buffer = ByteBuffer.allocate(len);

        while(buffer.hasRemaining()) socket.read(buffer);

        buffer.flip();

        return StandardCharsets.UTF_8.decode(buffer).toString();

    }
    public static void writeFrame(SocketChannel socket , String s){
        byte[] buffer = s.getBytes(StandardCharsets.UTF_8);
        ByteBuffer b = ByteBuffer.allocate(4 + buffer.length);
    }
}