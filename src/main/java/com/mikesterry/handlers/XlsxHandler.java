package com.mikesterry.handlers;

import com.mikesterry.objects.Fish;
import com.mikesterry.objects.Lake;
import com.mikesterry.sorters.SortFishByCommonName;
import com.mikesterry.sorters.SortLakesByLakeNameCountyName;
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
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Surveys");
        this.row = sheet.createRow(0);
        this.cell = row.createCell(0);
        this.cellNumber = 0;
    }

    public void createSpeadsheet(List<Lake>lakeList) {
        setHeaderStyle();
        setCellStyle();

        for(String headerName : Constants.SPREADSHEET_HEADERS) {
            createHeaderCell(headerName);
        }
        this.row = sheet.createRow(row.getRowNum() + 1);
        this.cellNumber = 0;

        lakeList.sort(new SortLakesByLakeNameCountyName());
        createCellsForLakesAndFish(lakeList);
    }

    private void createCellsForLakesAndFish(List<Lake> lakeList) {
        for(Lake lake : lakeList) {
            List<Fish> fishList = lake.getFishList();
            fishList.sort(new SortFishByCommonName());
            if (fishList.size() <= 0) {
                System.out.println("No fish found for lake: " + lake.getName());
            }
            for (Fish fish : fishList) {
                createNormalCell(lake.getCounty().getName());
                createNormalCell(lake.getName());
                createNormalCell(lake.getId());
                createNormalCell(lake.getNearestTown());
                createNormalCell(lake.getMostRecentSurveyDate());
                createNormalCell(fish.getCommonName());
                createNormalCell(fish.getTotalCount());
                createNormalCell(fish.getFishCountWithinRange(0, 5));
                createNormalCell(fish.getFishCountWithinRange(6, 7));
                createNormalCell(fish.getFishCountWithinRange(8, 9));
                createNormalCell(fish.getFishCountWithinRange(10, 11));
                createNormalCell(fish.getFishCountWithinRange(12, 14));
                createNormalCell(fish.getFishCountWithinRange(15, 19));
                createNormalCell(fish.getFishCountWithinRange(20, 24));
                createNormalCell(fish.getFishCountWithinRange(25, 29));
                createNormalCell(fish.getFishCountWithinRange(30, 34));
                createNormalCell(fish.getFishCountWithinRange(35, 39));
                createNormalCell(fish.getFishCountWithinRange(40, 44));
                createNormalCell(fish.getFishCountWithinRange(45, 49));
                createNormalCell(fish.getFishCountGreaterThan(50));
                this.row = sheet.createRow(row.getRowNum() + 1);
                this.cellNumber = 0;
            }
        }
    }

    public void createHeaderCell(String cellValue) {
//        System.out.println("Creating header cell - row: " + row.getRowNum() + ", cell number: " + cellNumber + " and value: " + cellValue);
        cell = row.createCell(cellNumber);
        cell.setCellValue(cellValue);
        cell.setCellStyle(headerStyle);
        cellNumber += 1;
    }

    public void createNormalCell(String cellValue) {
//        System.out.println("Creating normal cell - row: " + row.getRowNum() + ", cell number: " + cellNumber + " and value: " + cellValue);
        cell = row.createCell(cellNumber);
        cell.setCellValue(cellValue);
        cell.setCellStyle(cellStyle);
        cellNumber += 1;
    }

    public void createNormalCell(int cellValue) {
//        System.out.println("Creating normal cell - row: " + row.getRowNum() + ", cell number: " + cellNumber + " and value: " + cellValue);
        cell = row.createCell(cellNumber);
        cell.setCellValue(cellValue);
        cell.setCellStyle(cellStyle);
        cellNumber += 1;
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
        // Create date and append to filename
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
