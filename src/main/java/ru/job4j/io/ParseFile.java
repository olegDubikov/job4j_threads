package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = input.readAllBytes();
            for (var b : bytes) {
                int letter = b & 0xFF;
                char ch = (char) letter;
                if (filter.test(ch)) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public String getContent() throws IOException {
        return content(ch -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(ch -> ch < 0x80);
    }
}
