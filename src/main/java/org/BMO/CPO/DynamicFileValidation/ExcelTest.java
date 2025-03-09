package org.BMO.CPO.DynamicFileValidation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {
    public static void main(String[] args) {
        try {
            // Path to the Excel file
            String filePath = "input/student_records.xlsx";
            System.out.println("Testing Excel file: " + filePath);
            File file = new File(filePath);
            
            if (!file.exists()) {
                System.out.println("File does not exist!");
                return;
            }
            
            System.out.println("File exists, size: " + file.length() + " bytes");
            
            // Try to create a new Excel file first to verify POI works
            createNewExcelFile("test_output.xlsx");
            
            // Then try to read the existing file
            readExcelFile(file);
            
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createNewExcelFile(String fileName) throws IOException {
        System.out.println("Creating new Excel file: " + fileName);
        
        // Create workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Sheet");
        
        // Create a row and put some cells in it
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Test Cell");
        
        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        
        workbook.close();
        System.out.println("New Excel file created successfully");
    }
    
    private static void readExcelFile(File file) throws IOException {
        System.out.println("Attempting to read Excel file using different methods...");
        
        // Method 1: Try with XSSFWorkbook directly
        try {
            System.out.println("Method 1: Using XSSFWorkbook directly");
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            System.out.println("Success! Number of sheets: " + workbook.getNumberOfSheets());
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("First sheet name: " + workbook.getSheetName(0));
            System.out.println("Number of rows: " + sheet.getPhysicalNumberOfRows());
            workbook.close();
            fis.close();
        } catch (Exception e) {
            System.err.println("Method 1 failed: " + e.getMessage());
        }
        
        // Method 2: Try with WorkbookFactory
        try {
            System.out.println("\nMethod 2: Using WorkbookFactory");
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fis);
            System.out.println("Success! Number of sheets: " + workbook.getNumberOfSheets());
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("First sheet name: " + workbook.getSheetName(0));
            System.out.println("Number of rows: " + sheet.getPhysicalNumberOfRows());
            workbook.close();
            fis.close();
        } catch (Exception e) {
            System.err.println("Method 2 failed: " + e.getMessage());
        }
    }
}
