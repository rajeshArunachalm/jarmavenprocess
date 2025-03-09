package org.BMO.CPO.DynamicFileValidation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to represent the validation result for a single student record
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    private String studentId;
    private String studentName;
    private boolean passedValidation;
    private String details;
    
    /**
     * Factory method to create a successful validation result
     */
    public static ValidationResult success(StudentRecord record) {
        return ValidationResult.builder()
                .studentId(record.getId())
                .studentName(record.getFullName())
                .passedValidation(true)
                .details("All fields match correctly")
                .build();
    }
    
    /**
     * Factory method to create a failed validation result
     */
    public static ValidationResult failure(StudentRecord excelRecord, StudentRecord inaccurateRecord) {
        return ValidationResult.builder()
                .studentId(excelRecord.getId())
                .studentName(excelRecord.getFullName())
                .passedValidation(false)
                .details(excelRecord.getDifferenceDetails(inaccurateRecord))
                .build();
    }
}
