package org.BMO.CPO.DynamicFileValidation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateExcel {
    public static void main(String[] args) {
        try {
            // Create new workbook
            Workbook workbook = new XSSFWorkbook();
            
            // Create a sheet
            Sheet sheet = workbook.createSheet("Students");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "FirstName", "LastName", "Gender", "Age", "Major", "GPA", "AdmissionDate", "Email", "Phone"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // Create data rows
            createStudentRecord(sheet, 1, "S001", "John", "Smith", "Male", 19, "Computer Science", 3.7, "2023-09-01", "john.smith@university.edu", "555-123-4567");
            createStudentRecord(sheet, 2, "S002", "Emma", "Johnson", "Female", 20, "Biology", 3.9, "2022-09-01", "emma.j@university.edu", "555-234-5678");
            createStudentRecord(sheet, 3, "S003", "Michael", "Williams", "Male", 21, "Physics", 3.5, "2022-09-01", "michael.w@university.edu", "555-345-6789");
            createStudentRecord(sheet, 4, "S004", "Sophia", "Brown", "Female", 19, "Psychology", 3.8, "2023-09-01", "sophia.b@university.edu", "555-456-7890");
            createStudentRecord(sheet, 5, "S005", "David", "Jones", "Male", 22, "Business", 3.2, "2021-09-01", "david.j@university.edu", "555-567-8901");
            createStudentRecord(sheet, 6, "S006", "Olivia", "Garcia", "Female", 20, "Engineering", 4.0, "2022-09-01", "olivia.g@university.edu", "555-678-9012");
            createStudentRecord(sheet, 7, "S007", "James", "Miller", "Male", 19, "Mathematics", 3.6, "2023-09-01", "james.m@university.edu", "555-789-0123");
            createStudentRecord(sheet, 8, "S008", "Ava", "Davis", "Female", 21, "Chemistry", 3.4, "2022-09-01", "ava.d@university.edu", "555-890-1234");
            createStudentRecord(sheet, 9, "S009", "Ethan", "Rodriguez", "Male", 20, "History", 3.3, "2022-09-01", "ethan.r@university.edu", "555-901-2345");
            createStudentRecord(sheet, 10, "S010", "Isabella", "Martinez", "Female", 19, "English", 3.7, "2023-09-01", "isabella.m@university.edu", "555-012-3456");
            createStudentRecord(sheet, 11, "S011", "Alexander", "Wilson", "Male", 22, "Economics", 3.1, "2021-09-01", "alex.w@university.edu", "555-123-7890");
            createStudentRecord(sheet, 12, "S012", "Mia", "Anderson", "Female", 20, "Sociology", 3.8, "2022-09-01", "mia.a@university.edu", "555-234-8901");
            createStudentRecord(sheet, 13, "S013", "Benjamin", "Taylor", "Male", 21, "Computer Science", 3.9, "2022-09-01", "ben.t@university.edu", "555-345-9012");
            createStudentRecord(sheet, 14, "S014", "Charlotte", "Thomas", "Female", 19, "Art", 3.5, "2023-09-01", "charlotte.t@university.edu", "555-456-0123");
            createStudentRecord(sheet, 15, "S015", "Daniel", "Jackson", "Male", 20, "Physics", 3.6, "2022-09-01", "daniel.j@university.edu", "555-567-1234");
            createStudentRecord(sheet, 16, "S016", "Amelia", "White", "Female", 22, "Chemistry", 3.7, "2021-09-01", "amelia.w@university.edu", "555-678-2345");
            createStudentRecord(sheet, 17, "S017", "Henry", "Harris", "Male", 19, "Engineering", 3.4, "2023-09-01", "henry.h@university.edu", "555-789-3456");
            createStudentRecord(sheet, 18, "S018", "Elizabeth", "Clark", "Female", 21, "Business", 3.2, "2022-09-01", "elizabeth.c@university.edu", "555-890-4567");
            createStudentRecord(sheet, 19, "S019", "William", "Lewis", "Male", 20, "Mathematics", 4.0, "2022-09-01", "william.l@university.edu", "555-901-5678");
            createStudentRecord(sheet, 20, "S020", "Sofia", "Lee", "Female", 19, "Psychology", 3.8, "2023-09-01", "sofia.l@university.edu", "555-012-6789");
            
            // Create statistics sheet
            Sheet statsSheet = workbook.createSheet("Statistics");
            
            // Add some statistics
            Row statRow1 = statsSheet.createRow(0);
            statRow1.createCell(0).setCellValue("Statistic");
            statRow1.createCell(1).setCellValue("Value");
            
            Row statRow2 = statsSheet.createRow(1);
            statRow2.createCell(0).setCellValue("Total Students");
            statRow2.createCell(1).setCellValue(20);
            
            Row statRow3 = statsSheet.createRow(2);
            statRow3.createCell(0).setCellValue("Average GPA");
            statRow3.createCell(1).setCellValue(3.605);
            
            Row statRow4 = statsSheet.createRow(3);
            statRow4.createCell(0).setCellValue("Male Students");
            statRow4.createCell(1).setCellValue(10);
            
            Row statRow5 = statsSheet.createRow(4);
            statRow5.createCell(0).setCellValue("Female Students");
            statRow5.createCell(1).setCellValue(10);
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write the output to file
            String filePath = "input/student_records.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            
            workbook.close();
            System.out.println("Excel file generated successfully at: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Error generating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createStudentRecord(Sheet sheet, int rowNum, String id, String firstName, String lastName,
                                            String gender, int age, String major, double gpa, String admissionDate,
                                            String email, String phone) {
        Row row = sheet.createRow(rowNum);
        
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(firstName);
        row.createCell(2).setCellValue(lastName);
        row.createCell(3).setCellValue(gender);
        row.createCell(4).setCellValue(age);
        row.createCell(5).setCellValue(major);
        row.createCell(6).setCellValue(gpa);
        row.createCell(7).setCellValue(admissionDate);
        row.createCell(8).setCellValue(email);
        row.createCell(9).setCellValue(phone);
    }
}
