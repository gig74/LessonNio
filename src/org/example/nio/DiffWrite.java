package org.example.nio;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiffWrite {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {
        File in = new File(".\\Test\\in.txt");
        File out = new File(".\\Test\\out.txt");
        System.out.println("copyFileNio");
        copyFileNio(in, out);
        System.out.println("----------------------");
        System.out.println("copyFileIo");
        copyFileIo(in, out);
    }

    public static void copyFileIo(File in, File out) throws Exception {
        System.out.println("Before Read :" + sdf.format(new Date()));

        try (FileInputStream fis = new FileInputStream(in); FileOutputStream fos = new FileOutputStream(out);) {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        }

        System.out.println("After Read :" + sdf.format(new Date()));

    }

    public static void copyFileNio(File in, File out) throws IOException {
        System.out.println("Before Read :" + sdf.format(new Date()));

//        try (FileChannel inChannel = new FileInputStream(in).getChannel(); FileChannel outChannel = new FileOutputStream(out).getChannel();) {
          try (FileChannel inChannel = new RandomAccessFile(in,"r").getChannel(); FileChannel outChannel = new RandomAccessFile(out,"rw").getChannel();) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw e;
        }

        System.out.println("After Read :" + sdf.format(new Date()));
    }
}