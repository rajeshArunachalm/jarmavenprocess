package org.BMO.CPO.DynamicFileValidation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main application class for Dynamic File Validation
 */
@Slf4j
public class App {
    private static final String INPUT_FOLDER = "input";
    private static final String COMPARE_FOLDER = "compare";
    private static final String REPORT_FOLDER = "report";

    public static void main(String[] args) {
        log.info("Starting Dynamic File Validation");
        
        // Create instance and run the validation process
        App app = new App();
        try {
            app.runValidation();
        } catch (Exception e) {
            log.error("Error during validation process", e);
            System.exit(1);
        }
        
        log.info("Dynamic File Validation completed successfully");
    }
    
    /**
     * Main validation process
     */
    public void runValidation() throws IOException {
        // Ensure required folders exist
        ensureDirectories();
        
        // Find files in directories
        File excelFile = findExcelFile(INPUT_FOLDER);
        if (excelFile == null) {
            throw new IOException("No Excel file found in input folder");
        }
        
        File accuratePdf = findPdfFile(COMPARE_FOLDER, "accurate");
        File inaccuratePdf = findPdfFile(COMPARE_FOLDER, "inaccurate");
        
        if (accuratePdf == null || inaccuratePdf == null) {
            throw new IOException("Required PDF files not found in compare folder");
        }
        
        // Process the Excel file to get student records
        ExcelProcessor excelProcessor = new ExcelProcessor();
        List<StudentRecord> excelRecords = excelProcessor.processExcelFile(excelFile);
        
        // Process the PDF files to get student records
        PdfProcessor pdfProcessor = new PdfProcessor();
        List<StudentRecord> accurateRecords = pdfProcessor.processPdfFile(accuratePdf);
        List<StudentRecord> inaccurateRecords = pdfProcessor.processPdfFile(inaccuratePdf);
        
        // Compare the records and generate validation results
        RecordComparator comparator = new RecordComparator();
        List<ValidationResult> results = comparator.compareRecords(excelRecords, accurateRecords, inaccurateRecords);
        
        // Generate the HTML report
        ReportGenerator reportGenerator = new ReportGenerator();
        String reportContent = reportGenerator.generateHtmlReport(results);
        
        // Save the HTML report
        String reportFileName = "validation_report_" + System.currentTimeMillis() + ".html";
        Path reportPath = Paths.get(REPORT_FOLDER, reportFileName);
        Files.write(reportPath, reportContent.getBytes());
        
        log.info("Validation report generated: {}", reportPath.toAbsolutePath());
    }
    
    /**
     * Ensure required directories exist
     */
    private void ensureDirectories() throws IOException {
        createDirectoryIfNotExists(INPUT_FOLDER);
        createDirectoryIfNotExists(COMPARE_FOLDER);
        createDirectoryIfNotExists(REPORT_FOLDER);
    }
    
    /**
     * Create directory if it doesn't exist
     */
    private void createDirectoryIfNotExists(String dirName) throws IOException {
        Path dirPath = Paths.get(dirName);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            log.info("Created directory: {}", dirPath.toAbsolutePath());
        }
    }
    
    /**
     * Find Excel file in specified directory
     */
    private File findExcelFile(String directory) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls"));
        return files != null && files.length > 0 ? files[0] : null;
    }
    
    /**
     * Find PDF file in specified directory matching the name pattern
     */
    private File findPdfFile(String directory, String namePattern) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> 
                name.toLowerCase().endsWith(".pdf") && name.toLowerCase().contains(namePattern));
        return files != null && files.length > 0 ? files[0] : null;
    }
}
