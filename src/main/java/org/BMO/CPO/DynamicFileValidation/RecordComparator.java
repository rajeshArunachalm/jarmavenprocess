package org.BMO.CPO.DynamicFileValidation;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to compare student records from different sources
 */
@Slf4j
public class RecordComparator {

    /**
     * Compare student records from Excel with records from PDFs
     */
    public List<ValidationResult> compareRecords(
            List<StudentRecord> excelRecords,
            List<StudentRecord> accurateRecords,
            List<StudentRecord> inaccurateRecords) {
        
        log.info("Comparing {} Excel records against {} accurate PDF records and {} inaccurate PDF records",
                excelRecords.size(), accurateRecords.size(), inaccurateRecords.size());
        
        List<ValidationResult> results = new ArrayList<>();
        
        // Create maps for efficient lookups
        Map<String, StudentRecord> accurateMap = mapRecordsById(accurateRecords);
        Map<String, StudentRecord> inaccurateMap = mapRecordsById(inaccurateRecords);
        
        // Compare each Excel record with corresponding PDF records
        for (StudentRecord excelRecord : excelRecords) {
            String studentId = excelRecord.getId();
            StudentRecord accurateRecord = accurateMap.get(studentId);
            StudentRecord inaccurateRecord = inaccurateMap.get(studentId);
            
            if (accurateRecord == null) {
                log.warn("No matching record found in accurate PDF for student ID: {}", studentId);
                continue;
            }
            
            if (inaccurateRecord == null) {
                log.warn("No matching record found in inaccurate PDF for student ID: {}", studentId);
                continue;
            }
            
            // Compare Excel record with accurate PDF record - they should match
            boolean accurateMatch = excelRecord.isEqualTo(accurateRecord);
            
            // Compare Excel record with inaccurate PDF record
            boolean inaccurateMatch = excelRecord.isEqualTo(inaccurateRecord);
            
            // For the first 10 students, both PDFs should match the Excel
            // For the last 10 students, only the accurate PDF should match
            boolean passedValidation;
            String details;
            
            if (accurateMatch && !inaccurateMatch) {
                // This is expected for students 11-20
                passedValidation = true;
                details = "Accurate PDF matches Excel data. Inaccurate PDF has the following differences:<br>" + 
                          excelRecord.getDifferenceDetails(inaccurateRecord);
            } else if (accurateMatch && inaccurateMatch) {
                // This is expected for students 1-10
                passedValidation = true;
                details = "Both PDFs match Excel data correctly.";
            } else if (!accurateMatch) {
                // This is unexpected - accurate PDF should always match
                passedValidation = false;
                details = "Accurate PDF does not match Excel data:<br>" + 
                          excelRecord.getDifferenceDetails(accurateRecord);
            } else {
                // Shouldn't happen, but included for completeness
                passedValidation = false;
                details = "Unexpected comparison result. Please check the data.";
            }
            
            ValidationResult result = ValidationResult.builder()
                    .studentId(studentId)
                    .studentName(excelRecord.getFullName())
                    .passedValidation(passedValidation)
                    .details(details)
                    .build();
            
            results.add(result);
            log.debug("Validation result for student {}: {}", studentId, passedValidation ? "PASS" : "FAIL");
        }
        
        log.info("Comparison completed. {} results generated.", results.size());
        return results;
    }
    
    /**
     * Create a map of student records by ID for efficient lookup
     */
    private Map<String, StudentRecord> mapRecordsById(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.toMap(
                        StudentRecord::getId,
                        record -> record,
                        (existing, replacement) -> {
                            log.warn("Duplicate student ID found: {}", existing.getId());
                            return existing;
                        }
                ));
    }
}
