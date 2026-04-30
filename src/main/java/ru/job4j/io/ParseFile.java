package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = input.readAllBytes();
            return IntStream.range(0,bytes.length)
                    .mapToObj(i ->(char)(bytes[i]&0xFF))
                    .filter(filter)
                    .map(String::valueOf)
                    .collect(Collectors.joining());
        }
}

    public String getContent() throws IOException {
        return content(ch -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(ch -> ch < 0x80);
    }
}
