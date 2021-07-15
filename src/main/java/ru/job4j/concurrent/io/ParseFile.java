package ru.job4j.concurrent.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return findContent((i) -> true);
    }

    public String getContentWithoutUnicode() {
        return findContent((i) -> i < 0x80);
    }

    private synchronized String findContent(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = in.read()) > 0) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
