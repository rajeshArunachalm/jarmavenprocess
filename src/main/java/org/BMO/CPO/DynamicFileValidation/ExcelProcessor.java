package org.BMO.CPO.DynamicFileValidation;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to process Excel files and extract student records
 */
@Slf4j
public class ExcelProcessor {

    /**
     * Process an Excel file and extract student records
     */
    public List<StudentRecord> processExcelFile(File excelFile) throws IOException {
        log.info("Processing Excel file: {}", excelFile.getAbsolutePath());
        
        List<StudentRecord> records = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(excelFile);
             // Use XSSFWorkbook directly instead of WorkbookFactory
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            // Extract header row to determine column positions
            Map<String, Integer> columnMap = extractHeaderMap(sheet);
            
            // Process data rows
            int physicalRowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < physicalRowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    StudentRecord record = extractStudentRecord(row, columnMap);
                    if (record != null) {
                        records.add(record);
                        log.debug("Extracted student record: {}", record.getId());
                    }
                }
            }
        }
        
        log.info("Extracted {} student records from Excel", records.size());
        return records;
    }
    
    /**
     * Extract header map from the first row of the sheet
     */
    private Map<String, Integer> extractHeaderMap(Sheet sheet) {
        Map<String, Integer> columnMap = new HashMap<>();
        Row headerRow = sheet.getRow(0);
        
        if (headerRow != null) {
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String header = cell.getStringCellValue().trim();
                    columnMap.put(header, i);
                }
            }
        }
        
        return columnMap;
    }
    
    /**
     * Extract student record from a row using column mapping
     */
    private StudentRecord extractStudentRecord(Row row, Map<String, Integer> columnMap) {
        try {
            // Check for ID column - required field
            if (!columnMap.containsKey("ID")) {
                log.error("ID column not found in Excel header");
                return null;
            }
            
            String id = getCellValueAsString(row, columnMap.get("ID"));
            if (id == null || id.isEmpty()) {
                return null; // Skip rows without ID
            }
            
            return StudentRecord.builder()
                    .id(id)
                    .firstName(getCellValueAsString(row, columnMap.getOrDefault("FirstName", -1)))
                    .lastName(getCellValueAsString(row, columnMap.getOrDefault("LastName", -1)))
                    .gender(getCellValueAsString(row, columnMap.getOrDefault("Gender", -1)))
                    .age(getCellValueAsInt(row, columnMap.getOrDefault("Age", -1)))
                    .major(getCellValueAsString(row, columnMap.getOrDefault("Major", -1)))
                    .gpa(getCellValueAsDouble(row, columnMap.getOrDefault("GPA", -1)))
                    .admissionDate(getCellValueAsString(row, columnMap.getOrDefault("AdmissionDate", -1)))
                    .email(getCellValueAsString(row, columnMap.getOrDefault("Email", -1)))
                    .phone(getCellValueAsString(row, columnMap.getOrDefault("Phone", -1)))
                    .build();
        } catch (Exception e) {
            log.error("Error extracting student record from row {}", row.getRowNum(), e);
            return null;
        }
    }
    
    /**
     * Get cell value as string
     */
    private String getCellValueAsString(Row row, int columnIndex) {
        if (columnIndex < 0) {
            return "";
        }
        
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (Exception ex) {
                        return "";
                    }
                }
            default:
                return "";
        }
    }
    
    /**
     * Get cell value as integer
     */
    private int getCellValueAsInt(Row row, int columnIndex) {
        if (columnIndex < 0) {
            return 0;
        }
        
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return 0;
        }
        
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else {
            try {
                return Integer.parseInt(getCellValueAsString(row, columnIndex));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
    
    /**
     * Get cell value as double
     */
    private double getCellValueAsDouble(Row row, int columnIndex) {
        if (columnIndex < 0) {
            return 0.0;
        }
        
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return 0.0;
        }
        
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else {
            try {
                return Double.parseDouble(getCellValueAsString(row, columnIndex));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }
}
