package com.mikesterry.handlers;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileHandler {

    public void createOutputFileFromWorkbook(String filename, Workbook workbook) {
        String fileLocation = createFilePath() + filename;

        try {
            FileOutputStream outputStream = new FileOutputStream( fileLocation );
            workbook.write(outputStream);
            workbook.close();
        } catch(IOException fnfe) {
            fnfe.printStackTrace();
        }
    }

    private String createFilePath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        // Create date and append to filename
        return path.substring(0, path.length() - 1);
    }

    public String loadResourceFileAsString(String filename) throws IOException {
        return new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)).readAllBytes());
    }
}
