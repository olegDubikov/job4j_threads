package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        long allowedTimeNs = 1_000_000_000L;
        try (InputStream input = new URL(this.url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis()
                    - startAt) + " ms");
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                long startTime = System.nanoTime();
                output.write(buffer, 0, bytesRead);
                long endTime = System.nanoTime();
                long spentTime = endTime - startTime;
                if (spentTime < allowedTimeNs) {
                    long sleepTimeNs = allowedTimeNs - spentTime;
                    try {
                        long sleepMs = sleepTimeNs / 1_000_000L;
                        int sleepNs = (int) (sleepTimeNs % 1_000_000L);
                        Thread.sleep(sleepMs, sleepNs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("Read " + bytesRead + " bytes. Time spent: " + spentTime + " ms");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: java Wget <url> <speed>");
            return;
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
