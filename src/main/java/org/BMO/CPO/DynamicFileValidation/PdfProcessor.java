package org.BMO.CPO.DynamicFileValidation;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to process PDF files and extract student records
 */
@Slf4j
public class PdfProcessor {

    private static final Pattern ID_PATTERN = Pattern.compile("\\*\\*ID:\\*\\*\\s+(S\\d+)");
    private static final Pattern NAME_PATTERN = Pattern.compile("\\*\\*Name:\\*\\*\\s+([\\w\\s]+)");
    private static final Pattern GENDER_PATTERN = Pattern.compile("\\*\\*Gender:\\*\\*\\s+(\\w+)");
    private static final Pattern AGE_PATTERN = Pattern.compile("\\*\\*Age:\\*\\*\\s+(\\d+)");
    private static final Pattern MAJOR_PATTERN = Pattern.compile("\\*\\*Major:\\*\\*\\s+([\\w\\s]+)");
    private static final Pattern GPA_PATTERN = Pattern.compile("\\*\\*GPA:\\*\\*\\s+(\\d+\\.\\d+)");
    private static final Pattern ADMISSION_DATE_PATTERN = Pattern.compile("\\*\\*Admission Date:\\*\\*\\s+(\\d{4}-\\d{2}-\\d{2})");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\*\\*Email:\\*\\*\\s+([\\w\\.@]+)");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\*\\*Phone:\\*\\*\\s+(\\d{3}-\\d{3}-\\d{4})");
    private static final Pattern STUDENT_SECTION_PATTERN = Pattern.compile("### Student (\\d+)");

    /**
     * Process a PDF file and extract student records
     */
    public List<StudentRecord> processPdfFile(File pdfFile) throws IOException {
        log.info("Processing PDF file: {}", pdfFile.getAbsolutePath());
        
        List<StudentRecord> records = new ArrayList<>();
        
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            
            // Split the PDF text by student sections
            String[] studentSections = text.split("### Student");
            
            // Skip the first section (usually contains title, etc.)
            for (int i = 1; i < studentSections.length; i++) {
                String section = "### Student" + studentSections[i];
                StudentRecord record = extractStudentRecord(section);
                if (record != null) {
                    records.add(record);
                    log.debug("Extracted student record from PDF: {}", record.getId());
                }
            }
        }
        
        log.info("Extracted {} student records from PDF", records.size());
        return records;
    }
    
    /**
     * Extract a student record from a text section
     */
    private StudentRecord extractStudentRecord(String text) {
        try {
            String id = extractWithPattern(text, ID_PATTERN);
            if (id == null || id.isEmpty()) {
                return null;
            }
            
            String fullName = extractWithPattern(text, NAME_PATTERN);
            String[] nameParts = splitName(fullName);
            
            return StudentRecord.builder()
                    .id(id)
                    .firstName(nameParts[0])
                    .lastName(nameParts[1])
                    .gender(extractWithPattern(text, GENDER_PATTERN))
                    .age(parseIntSafely(extractWithPattern(text, AGE_PATTERN)))
                    .major(extractWithPattern(text, MAJOR_PATTERN))
                    .gpa(parseDoubleSafely(extractWithPattern(text, GPA_PATTERN)))
                    .admissionDate(extractWithPattern(text, ADMISSION_DATE_PATTERN))
                    .email(extractWithPattern(text, EMAIL_PATTERN))
                    .phone(extractWithPattern(text, PHONE_PATTERN))
                    .build();
        } catch (Exception e) {
            log.error("Error extracting student record from PDF section", e);
            return null;
        }
    }
    
    /**
     * Extract text using regex pattern
     */
    private String extractWithPattern(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : "";
    }
    
    /**
     * Split full name into first and last name parts
     */
    private String[] splitName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return new String[]{"", ""};
        }
        
        String[] parts = fullName.split("\\s+");
        if (parts.length == 1) {
            return new String[]{parts[0], ""};
        } else {
            String firstName = parts[0];
            StringBuilder lastName = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                lastName.append(parts[i]);
                if (i < parts.length - 1) {
                    lastName.append(" ");
                }
            }
            return new String[]{firstName, lastName.toString()};
        }
    }
    
    /**
     * Safely parse integer
     */
    private int parseIntSafely(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Safely parse double
     */
    private double parseDoubleSafely(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
