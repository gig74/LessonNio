package org.example.nio.server;

import java.io.IOException;
import java.util.Scanner;

public class StartStopServer {
    public static void main(String[] args) throws IOException {
        Process server;
        server = NIOServer.start();
        System.out.println("Для останова сервера нажмите 0 + ENTER");
        Scanner in = new Scanner(System.in);
        int st = in.nextInt();
        server.destroy();
    }
}
