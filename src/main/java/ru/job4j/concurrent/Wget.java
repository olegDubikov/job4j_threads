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
        String fileName = "file";
        try {
            String path = new URL(url).getPath();
            fileName = path.substring(path.lastIndexOf('/') + 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(fileName);
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        long startTime = System.currentTimeMillis();
        long bytesReadTotal = 0;
        try (InputStream input = new URL(this.url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
                bytesReadTotal += bytesRead;
                if (bytesReadTotal >= speed) {
                    long endTime = System.currentTimeMillis();
                    long spentTime = endTime - startTime;
                    if (spentTime >= 1000) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    bytesReadTotal = 0;
                    startTime = System.currentTimeMillis();
                }
            }
            System.out.println("Download finished.");

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла");
        }
        try {
            System.out.println("File size: " + Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException();
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
