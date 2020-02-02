package com.mikesterry.excel;

import com.mikesterry.objects.Lake;
import com.mikesterry.util.Constants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Xlsx {
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private CellStyle headerStyle;
    private CellStyle cellStyle;
    private Row row;
    private Cell cell;
    private int cellNumber;

    public Xlsx() {
        cellNumber = 0;
        createWorkbook();
        createSheet();
        setHeaderStyle();
        setCellStyle();
    }

    public void createSpeadsheet(List<Lake>lakeList) {
        createRow();
        createHeader();
    }

    public void createWorkbook() {
        this.workbook = new XSSFWorkbook();
    }

    public void createSheet() {
        this.sheet = workbook.createSheet("Surveys");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
    }

    public void createHeader() {
        List<String> headerValues = Constants.SPREADSHEET_HEADERS;
        for(String headerValue : headerValues) {
            createHeaderCell(headerValue);
        }
        createRow();
    }

    private void createRow() {
        row = sheet.createRow(row.getRowNum() + 1);
    }

    public void createHeaderCell(String cellValue) {
            createCellWithStyle(cellValue, cellStyle);
    }

    public void createCell(String cellValue) {
            createCellWithStyle(cellValue, headerStyle);
    }

    private void createCellWithStyle(String cellValue, CellStyle cellStyle) {
        if(cell != null) {
            cell = row.createCell(0);
        } else {
            cell = row.createCell(cellNumber);
        }

        cell.setCellValue(cellValue);
        cell.setCellStyle(cellStyle);
    }

    private void setHeaderStyle() {
        headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);
    }

    private void setCellStyle() {
        this.cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
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
