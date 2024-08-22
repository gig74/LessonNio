package org.example.nio.homework;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.util.Arrays.fill;

public class ExchangeConcoleFile {

    private static final String fileName = ".\\Test\\file.txt";

    /* Запускать только ТЕРМИНАЛОМ
       в run от Idea такой консольный ввод валится по Exception
     */
    public static void main(String[] args) {
        write();
        read();
    }

    private static void write() {
//        Path filePath = Paths.get(fileName);
        Path filePath = Path.of(fileName);
        // Создание файла и получение канала для записи
        try (FileChannel fileChannel = FileChannel.open(
                filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE
        )) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                String input = System.console().readLine();
                // проверим пустую строку
                if (input.isEmpty()) {
                    break;
                }
//                buffer.clear();
                fill(buffer.array(), (byte)0); // ОТ МЕНЯ . Для надёжности
                buffer.put(input.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    // запишем текст
                    fileChannel.write(buffer);
                }
                fileChannel.write(ByteBuffer.wrap(("\n").getBytes()));
                //  НЕПРАВИЛЬНО закроем буфер
                buffer.clear();
            }
        } catch (IOException e) {
            // не забудем про ошибку
            e.printStackTrace();
        }
    }

    private static void read() {
//        Path filePath = Paths.get(fileName);
        Path filePath = Path.of(fileName);
        System.out.println("Файл " + filePath);
        try (FileChannel fileChannel = FileChannel.open(
                filePath,
                StandardOpenOption.READ
        )) {
            System.out.println("Начало");
            // получение размера файла
            long fileSize = fileChannel.size();
            // создание буфера для чтения
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            System.out.println("Размер файла " + fileSize + "\n");
            // чтение данных из файла в буфер
            fileChannel.read(buffer);
            // подготовка буфера для чтения
            buffer.flip();
            // преобразование байтов в строку и вывод на консоль
            System.out.println(new String(buffer.array()));
        } catch (IOException e) {
            // не забудем про ошибку
            e.printStackTrace();
        }
    }
}
