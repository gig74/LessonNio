package org.example.nio.server;

import java.io.IOException;

public class MyTestNioClient {
    public static void main(String[] args) throws IOException {
        NIOClient client;
        client = NIOClient.start();
        String resp1 = client.sendMessage("halloween234");
        String resp2 = client.sendMessage("234world");
        String resp3 = client.sendMessage("POISON__PILL");
        String resp4 = client.sendMessage("POISON__PILL");
        String resp5 = client.sendMessage("hello");
        NIOClient.stop();
    }
}
