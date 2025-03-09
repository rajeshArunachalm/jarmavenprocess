package org.BMO.CPO.DynamicFileValidation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a student record with all relevant fields
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRecord {
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String major;
    private double gpa;
    private String admissionDate;
    private String email;
    private String phone;
    
    /**
     * Get full name by combining first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Check if this record is equal to another StudentRecord, ignoring case for string fields
     */
    public boolean isEqualTo(StudentRecord other) {
        if (other == null) {
            return false;
        }
        
        if (!id.equalsIgnoreCase(other.id)) {
            return false;
        }
        
        if (!firstName.equalsIgnoreCase(other.firstName)) {
            return false;
        }
        
        if (!lastName.equalsIgnoreCase(other.lastName)) {
            return false;
        }
        
        if (!gender.equalsIgnoreCase(other.gender)) {
            return false;
        }
        
        if (age != other.age) {
            return false;
        }
        
        if (!major.equalsIgnoreCase(other.major)) {
            return false;
        }
        
        if (Math.abs(gpa - other.gpa) > 0.01) {
            return false;
        }
        
        if (!admissionDate.equalsIgnoreCase(other.admissionDate)) {
            return false;
        }
        
        if (!email.equalsIgnoreCase(other.email)) {
            return false;
        }
        
        return phone.equalsIgnoreCase(other.phone);
    }
    
    /**
     * Create a formatted difference string between this record and another StudentRecord
     */
    public String getDifferenceDetails(StudentRecord other) {
        if (other == null) {
            return "No comparison record found";
        }
        
        StringBuilder differences = new StringBuilder();
        
        if (!firstName.equalsIgnoreCase(other.firstName) || !lastName.equalsIgnoreCase(other.lastName)) {
            differences.append("Name: ").append(getFullName())
                    .append(" vs ").append(other.getFullName()).append("<br>");
        }
        
        if (!gender.equalsIgnoreCase(other.gender)) {
            differences.append("Gender: ").append(gender)
                    .append(" vs ").append(other.gender).append("<br>");
        }
        
        if (age != other.age) {
            differences.append("Age: ").append(age)
                    .append(" vs ").append(other.age).append("<br>");
        }
        
        if (!major.equalsIgnoreCase(other.major)) {
            differences.append("Major: ").append(major)
                    .append(" vs ").append(other.major).append("<br>");
        }
        
        if (Math.abs(gpa - other.gpa) > 0.01) {
            differences.append("GPA: ").append(gpa)
                    .append(" vs ").append(other.gpa).append("<br>");
        }
        
        if (!admissionDate.equalsIgnoreCase(other.admissionDate)) {
            differences.append("Admission Date: ").append(admissionDate)
                    .append(" vs ").append(other.admissionDate).append("<br>");
        }
        
        if (!email.equalsIgnoreCase(other.email)) {
            differences.append("Email: ").append(email)
                    .append(" vs ").append(other.email).append("<br>");
        }
        
        if (!phone.equalsIgnoreCase(other.phone)) {
            differences.append("Phone: ").append(phone)
                    .append(" vs ").append(other.phone).append("<br>");
        }
        
        return differences.toString();
    }
}
