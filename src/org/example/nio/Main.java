package org.example.nio;
import org.example.nio.server.NIOServer;

import java.io.IOException;
import java.nio.file.*;

/**
 * Примеры классов Files и Path
 */

public class Main {
    public static void main(String[] args) throws IOException {
        ex1();
        ex2();
        ex3();
        ex4();
    }

//}

    public static void ex1() {

        // Cоздание объекта Path через вызов статического метода get() класса Paths
        Path testFilePath = Paths.get("C:\\TEMP\\7\\1.txt");

        //Пример строки создания объекта Path пути для запуска в Windows
        //Path testFilePath = Paths.get("...\\testfile.txt");

        //Вывод информации о файле
        System.out.println("\t file name: " + testFilePath.getFileName());
        System.out.println("\t root of the path: " + testFilePath.getRoot());
        System.out.println("\t parent of the target: "
                + testFilePath.getParent());

        //Вывод элементов пути
        for (Path element : testFilePath) {
            System.out.println("\t\t path element: " + element);
        }
    }

    public static void ex2() throws IOException {
        Path testFilePath = Paths.get("./Test");

        //Пример строки пути для запуска в Windows

        System.out.println("The file name is: " + testFilePath.getFileName());
        System.out.println("It's URI is: " + testFilePath.toUri());
        System.out.println("It's absolute path is: "
                + testFilePath.toAbsolutePath());
        System.out.println("It's normalized path is: "
                + testFilePath.normalize());

        //Получение другого объекта строки по нормализованному относительному пути
        Path testPathNormalized = Paths
                .get(testFilePath.normalize().toString());
        System.out.println("It's normalized absolute path is: "
                + testPathNormalized.toAbsolutePath());
        System.out.println("It's normalized real path is: "
                + testFilePath.toRealPath(LinkOption.NOFOLLOW_LINKS));
    }

    public static void ex3() {
        //Проверка для файла
        Path path = Paths.get(".\\Test\\1.txt");
        Path testPathNormalized = Paths
                .get(path.normalize().toString());
        System.out.println("\tTest File normalized absolute path is : "
                + testPathNormalized .toAbsolutePath());
        //Проверка для директории
        //Path path = Paths.get("/home/heorhi/workspace/OCPJP/src/test");

        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            System.out.println("\tThe file/directory " + path.getFileName()
                    + " exists");
            if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                System.out.println("\t" + path.getFileName() + " is a directory");
            } else {
                System.out.println("\t" + path.getFileName() + " is a file");
            }
        } else {
            System.out.println("\tThe file/directory " + path.getFileName() + " does not exist");
        }
    }

    public static void ex4() {
        Path path = Paths.get("C:\\TEMP\\7\\1.txt");
        try {
            Object object = Files.getAttribute(path, "creationTime");
            System.out.println("Creation time: " + object);

            //Здесь указан третий параметр
            object = Files.getAttribute(path, "lastModifiedTime",
                    LinkOption.NOFOLLOW_LINKS);
            System.out.println("Last modified time: " + object);

            object = Files.getAttribute(path, "size");
            System.out.println("Size: " + object);

            object = Files.getAttribute(path, "isDirectory");
            System.out.println("isDirectory: " + object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
