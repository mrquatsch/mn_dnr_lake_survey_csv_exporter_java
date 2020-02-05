package com.mikesterry.handlers;

import java.io.IOException;
import java.util.Objects;

public class FileHandler {

    public String loadResourceTestFileAsString(String filename) throws IOException {
        return new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)).readAllBytes());
    }
}
