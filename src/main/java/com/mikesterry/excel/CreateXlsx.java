package com.mikesterry.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateXlsx {
    private Workbook workbook;
    private Sheet sheet;
    private CellStyle style;

    public CreateXlsx() {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Surveys");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        this.style = workbook.createCellStyle();
        style.setWrapText(true);
    }

    public void createHeader() {
        Row header = sheet.createRow( 0 );
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);
    }

    public void createRow() {
        Row row = sheet.createRow(1);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);
    }

    public void createOutputFile() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "surveys.xlsx";

        try {
            FileOutputStream outputStream = new FileOutputStream( fileLocation );
            workbook.write( outputStream );
            workbook.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }
}
