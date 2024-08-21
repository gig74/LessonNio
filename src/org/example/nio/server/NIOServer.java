package org.example.nio.server;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.util.UUID.randomUUID;

public class NIOServer {

    private static final String POISON__PILL = "POISON__PILL";

    // Селектор предоставляет механизм для мониторинга одного или нескольких каналов NIO
    //
    // один поток может использоваться для управления несколькими каналами и,
    // следовательно, несколькими сетевыми подключениями.

    public static void main(String[] args) throws IOException {

        // Переключение контекста между потоками является дорогостоящим для операционной системы,
        // каждый поток занимает память.
        //
        // селекторы не просто помогают вам читать данные; они также могут прослушивать входящие
        // сетевые соединения
        // и записывать данные по каналам.


        Selector selector = Selector.open();


        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        serverSocket.configureBlocking(false);


        /*
         * Мы можем различные события, каждое из которых представлено константой в классе SelectionKey :
         *
         * Connect - клиент пытается подключиться к серверу.
         *
         * SelectionKey.OP CONNECT Accept__ - ** когда сервер принимает соединение от клиента.
         *
         * SelectionKey.OP ACCEPT Read__ - ** когда сервер готов к чтению с канала.
         *
         * SelectionKey.OP READ Write__ - ** когда сервер готов к записи в канал.
         */

        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        // Java NIO использует буферно-ориентированную модель, отличную от потоково-ориентированной модели.
        //  Поэтому общение через сокеты происходит путем записи в буфер и чтения из него.
        //
        // создаем ByteBuffer , с которого сервер будет писать и читать.

        ByteBuffer buffer = ByteBuffer.allocate(256);
        ByteBuffer buffer2 = ByteBuffer.allocate(256);


        while (true) {

            selector.select();
            // получаем набор выбранных ключей для обработки
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            SelectionKey key = null;
            while (iter.hasNext()) {
                try {
                    // Чтобы селектор мог отслеживать любые каналы,
                    //мы должны зарегистрировать эти каналы в селекторе.

                    key = iter.next();

                    // набор событий, которые мы хотим, чтобы селектор наблюдал на этом канале

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }

                    if (key.isReadable()) {
                        answerWithEcho(buffer, key, buffer2);
                    }
                    iter.remove();
                } catch (IOException e) {
                    System.out.println("Cycle main:whiler");
                    key.cancel();
//                    throw e;
                }

            }
        }
    }

    // SelectionKey -  объект содержит данные, представляющие регистрацию канала.

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key, ByteBuffer buffer2)
            throws IOException {
        try {
            System.out.println("Process answerWithEcho");
            SocketChannel client = (SocketChannel) key.channel();
            client.read(buffer);
            if (new String(buffer.array()).trim().equals(POISON__PILL)) {
                client.close();
                System.out.println("Not accepting client messages anymore");
            }
//        buffer.clear();

            buffer.flip();
//        buffer.clear();
//            String in = new String(buffer.array()).trim() ;
            buffer2 = ByteBuffer.wrap(("My " + new String(buffer.array()).trim() + " " + randomUUID()).getBytes());
//        buffer2.rewind();

            buffer.flip();
            client.write(buffer2);
            buffer.clear();
        } catch (IOException e) {
            System.out.println("Method answerWithEcho");
            throw e;
        }

    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        try {
            SocketChannel client = serverSocket.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.out.println("Method register");
            throw e;
        }

    }

    public static Process start() throws IOException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = NIOServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.inheritIO().start();
    }
}
