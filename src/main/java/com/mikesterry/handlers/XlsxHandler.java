package com.mikesterry.handlers;

import com.mikesterry.objects.Fish;
import com.mikesterry.objects.Lake;
import com.mikesterry.sorters.SortLakesByCountyNameAndLakeName;
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

public class XlsxHandler {
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private CellStyle headerStyle;
    private CellStyle cellStyle;
    private Row row;
    private Cell cell;
    private int cellNumber;

    public XlsxHandler() {
        cellNumber = 0;
    }

    public void createSpeadsheet(List<Lake>lakeList) {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Surveys");
        setHeaderStyle();
        setCellStyle();

        createRow();
        createHeader();

        lakeList.sort(new SortLakesByCountyNameAndLakeName());
        for(Lake lake : lakeList) {
            for(Fish fish : lake.getFishList()) {
                createCell(lake.getCounty().getName());
                createCell(lake.getName());
                createCell(lake.getId());
                createCell(lake.getMostRecentSurveyDate());
                createCell(fish.getCommonName());
                createCell(fish.getTotalCount());
                createCell(fish.getFishCountWithinRange(0, 5));
                createCell(fish.getFishCountWithinRange(6, 7));
                createCell(fish.getFishCountWithinRange(8, 9));
                createCell(fish.getFishCountWithinRange(10, 11));
                createCell(fish.getFishCountWithinRange(12, 14));
                createCell(fish.getFishCountWithinRange(15, 19));
                createCell(fish.getFishCountWithinRange(20, 24));
                createCell(fish.getFishCountWithinRange(25, 29));
                createCell(fish.getFishCountWithinRange(30, 34));
                createCell(fish.getFishCountWithinRange(35, 39));
                createCell(fish.getFishCountWithinRange(40, 44));
                createCell(fish.getFishCountWithinRange(45, 49));
                createCell(fish.getFishCountGreaterThan(50));
                createRow();
            }
        }
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
        if(row != null) {
            row = sheet.createRow(row.getRowNum() + 1);
        } else {
            row = sheet.createRow(0);
        }
    }

    private void createCell(int cellValue) {
        createCellWithStyle(cellValue, headerStyle);
    }

    public void createHeaderCell(String cellValue) {
        createCellWithStyle(cellValue, cellStyle);
    }

    public void createCell(String cellValue) {
        createCellWithStyle(cellValue, headerStyle);
    }

    private void createCellWithStyle(String cellValue, CellStyle cellStyle) {
        createCellWithStyle(cellStyle);
        cell.setCellValue(cellValue);
        cellNumber += 1;
    }

    private void createCellWithStyle(int cellValue, CellStyle cellStyle) {
        createCellWithStyle(cellStyle);
        cell.setCellValue(cellValue);
        cellNumber += 1;
    }

    private void createCellWithStyle(CellStyle cellStyle) {
        if(cell != null) {
            cell = row.createCell(0);
        } else {
            cell = row.createCell(cellNumber);
        }

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
