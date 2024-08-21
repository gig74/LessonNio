package org.example.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static ByteBuffer buffer_in;
    private static NIOClient instance;

    public static NIOClient start() {
        if (instance == null)
            instance = new NIOClient();
     return instance;
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    private NIOClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            buffer = ByteBuffer.allocate(256);
            buffer_in = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            client.write(buffer);
            buffer.clear();
//            buffer = ByteBuffer.wrap("".getBytes());
            client.read(buffer_in);
            response = new String(buffer_in.array()).trim();
            System.out.println("response=" + response);
            buffer_in.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
