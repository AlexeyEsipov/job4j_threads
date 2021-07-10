package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String urlSource;
    private final String fileDest;
    private final int speed;

    public Wget(String urlSource, String fileDest, int speed) {
        this.urlSource = urlSource;
        this.fileDest = fileDest;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlSource).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileDest)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timeStart = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long timePhase = System.nanoTime() - timeStart;
                long delay = speed * 1000L - timePhase / 1000000;
                if (delay < 0) {
                    delay = 0;
                }
                Thread.sleep(delay);
                timeStart = System.nanoTime();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ValidateArgs.parseArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileDest = args[2];
        Thread wget = new Thread(new Wget(url, fileDest, speed));
        wget.start();
        wget.join();
    }
}
