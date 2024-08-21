package org.example.nio.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FillingTestFile {
    public static void main(String[] args) throws IOException {
        File fileOut = new File(".\\Test\\in.txt");
        List<String> stringList = new ArrayList<>();
        String testLine = "kjlkjganvnvjknfdgfherifjnv lkdsklvzx%$$$%^%&kjdfkal АПУЫАВт олчятлотафа";
        for(int i=0; i < 1000000; i++) {
            stringList.add(testLine);
        }
        writeListOut(fileOut, stringList);

    }

    public static void writeListOut(File fileOut, List<String> listStringOut) throws IOException {
        long start = System.currentTimeMillis();
        try (FileWriter fileWriter = new FileWriter(fileOut, Charset.forName("UTF8"), false)) {
            for (String stringOut : listStringOut) {
                fileWriter.write(stringOut + System.lineSeparator());
            }
            fileWriter.flush(); // Без этого не сохранялось
        } catch (IOException ex) {
            System.out.println(ex.getMessage()); // Написали про возникшее исключение
            throw ex; // И "прокинули" выше
        }
        long finish = System.currentTimeMillis();
        return;
    }
}


