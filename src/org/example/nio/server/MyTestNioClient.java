package org.example.nio.server;

import java.io.IOException;

public class MyTestNioClient {
    public static void main(String[] args) throws IOException {
        NIOClient client;
        client = NIOClient.start();
        String resp1 = client.sendMessage("hello234");
        String resp2 = client.sendMessage("22334world");
        NIOClient.stop();
    }
}
