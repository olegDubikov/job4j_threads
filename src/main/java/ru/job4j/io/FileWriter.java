package ru.job4j.io;

import java.io.*;

public final class FileWriter {

    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
            output.write(content.getBytes());
        }
    }
}