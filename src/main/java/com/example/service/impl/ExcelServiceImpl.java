package com.example.service.impl;

import com.example.entity.Book;
import com.example.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_TITLE = 1;
    public static final int COLUMN_INDEX_PRICE = 2;
    public static final int COLUMN_INDEX_QUANTITY = 3;
    public static final int COLUMN_INDEX_TOTAL = 4;
    public boolean writeExcel() throws IOException, NoSuchMethodException {
        final List<Book> books = getBooks();
        Constructor<?>ctor = Book.class.getConstructor();
        System.out.println(ctor.getParameterCount());
        Arrays.stream(ctor.getParameters()).collect(Collectors.toList()).forEach(item -> System.out.println(item.getName()));
        final String excelFilePath = "D:/books.xlsx";
        writeExcelFile(books, excelFilePath);
        return true;
    }

    private void writeExcelFile(List<Book> books, String path) throws IOException {
        Workbook workbook = getWorkbook(path);
        Sheet sheet = workbook.createSheet("Book2");
        int rowIndex = 0;
        writeHeader(sheet, rowIndex);

        rowIndex++;
        for (Book book : books) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(book, row);
            rowIndex++;
        }

        // Write footer
        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, path);
        System.out.println("Done!!!");
    }
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
        cell.setCellFormula("SUM(E2:E6)");
    }
    private void writeHeader(Sheet sheet, int rowIndex) {
        CellStyle cellStyle = createStyleForHeader(sheet);
        Row row = sheet.createRow(rowIndex);

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Id");

        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Title");

        cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Price");

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Quantity");

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total");


    }
        //write data
    private void writeBook(Book book, Row row){
        CellStyle cellStyleFormatNumber = null;
        if(cellStyleFormatNumber == null){
           short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");

           Workbook workbook = row.getSheet().getWorkbook();
           cellStyleFormatNumber = workbook.createCellStyle();
           cellStyleFormatNumber.setDataFormat(format);
        }
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(book.getId());

        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellValue(book.getTitle());

        cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellValue(book.getPrice());
        cell.setCellStyle(cellStyleFormatNumber);

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellValue(book.getQuantity());

        // Create cell formula
        // totalMoney = price * quantity
        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
        cell.setCellStyle(cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    private CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Roboto");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        return cellStyle;
    }

    private Workbook getWorkbook(String path){
        Workbook workbook = null;
        if (path.endsWith("xlsx")) {//excel 2007
            workbook = new XSSFWorkbook();
        } else if (path.endsWith("xls")) {//up to excel 2007
            workbook = new HSSFWorkbook();

        } else {
            throw new IllegalArgumentException("the specified file is not excel file");
        }
        return workbook;
    }

    private List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Book.builder().id(i).price(i * 1000.0).quantity(i).title("Book " + i).totalPrice(i * 1000.0).build());
        }
        return list;
    }
}
