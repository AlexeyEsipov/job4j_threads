package ru.job4j.net;

import java.io.*;
import java.net.URL;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        String file =
        "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("txt.txt")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            // включили секундомер - t0
            long t0 = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                // выключили секундомер и засекли время - t2
                long t2 = System.nanoTime() - t0;
                System.out.println(t2);
                // расчет задержки - с
                long c = 1000 - t2 / 1000000;
                if (c < 0) {
                    c = 0;
                }
                System.out.println(c);
                Thread.sleep(c);
                // включили секундомер
                t0 = System.nanoTime();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
