package org.BMO.CPO.DynamicFileValidation;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Class to generate HTML validation reports
 */
@Slf4j
public class ReportGenerator {

    /**
     * Generate an HTML report from validation results
     */
    public String generateHtmlReport(List<ValidationResult> results) {
        log.info("Generating HTML report for {} validation results", results.size());
        
        int totalTests = results.size();
        int passedTests = (int) results.stream().filter(ValidationResult::isPassedValidation).count();
        int failedTests = totalTests - passedTests;
        double passPercentage = totalTests > 0 ? (passedTests * 100.0 / totalTests) : 0;
        
        StringBuilder htmlBuilder = new StringBuilder();
        
        // HTML header
        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html lang=\"en\">\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("    <meta charset=\"UTF-8\">\n");
        htmlBuilder.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        htmlBuilder.append("    <title>File Validation Report</title>\n");
        htmlBuilder.append("    <style>\n");
        htmlBuilder.append("        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; color: #333; }\n");
        htmlBuilder.append("        .container { max-width: 1200px; margin: 0 auto; }\n");
        htmlBuilder.append("        h1 { color: #2c3e50; }\n");
        htmlBuilder.append("        .summary { background-color: #f8f9fa; border-radius: 5px; padding: 20px; margin-bottom: 20px; }\n");
        htmlBuilder.append("        .summary-grid { display: grid; grid-template-columns: repeat(2, 1fr); grid-gap: 20px; }\n");
        htmlBuilder.append("        .card { background-color: white; border-radius: 5px; padding: 15px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }\n");
        htmlBuilder.append("        .card h3 { margin-top: 0; }\n");
        htmlBuilder.append("        .status { font-weight: bold; }\n");
        htmlBuilder.append("        .pass { color: #27ae60; }\n");
        htmlBuilder.append("        .fail { color: #e74c3c; }\n");
        htmlBuilder.append("        table { width: 100%; border-collapse: collapse; margin: 20px 0; }\n");
        htmlBuilder.append("        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }\n");
        htmlBuilder.append("        th { background-color: #f2f2f2; }\n");
        htmlBuilder.append("        tr:hover { background-color: #f5f5f5; }\n");
        htmlBuilder.append("        .details { background-color: #f8f9fa; padding: 10px; border-radius: 5px; margin-top: 10px; }\n");
        htmlBuilder.append("    </style>\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        htmlBuilder.append("    <div class=\"container\">\n");
        
        // Report header
        htmlBuilder.append("        <h1>File Validation Report</h1>\n");
        htmlBuilder.append("        <p>Generated on: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</p>\n");
        
        // Summary section
        htmlBuilder.append("        <div class=\"summary\">\n");
        htmlBuilder.append("            <h2>Summary</h2>\n");
        htmlBuilder.append("            <div class=\"summary-grid\">\n");
        htmlBuilder.append("                <div class=\"card\">\n");
        htmlBuilder.append("                    <h3>Total Test Cases</h3>\n");
        htmlBuilder.append("                    <p>").append(totalTests).append("</p>\n");
        htmlBuilder.append("                </div>\n");
        htmlBuilder.append("                <div class=\"card\">\n");
        htmlBuilder.append("                    <h3>Pass Rate</h3>\n");
        htmlBuilder.append("                    <p>").append(String.format("%.2f%%", passPercentage)).append("</p>\n");
        htmlBuilder.append("                </div>\n");
        htmlBuilder.append("                <div class=\"card\">\n");
        htmlBuilder.append("                    <h3>Passed Tests</h3>\n");
        htmlBuilder.append("                    <p class=\"status pass\">").append(passedTests).append("</p>\n");
        htmlBuilder.append("                </div>\n");
        htmlBuilder.append("                <div class=\"card\">\n");
        htmlBuilder.append("                    <h3>Failed Tests</h3>\n");
        htmlBuilder.append("                    <p class=\"status fail\">").append(failedTests).append("</p>\n");
        htmlBuilder.append("                </div>\n");
        htmlBuilder.append("            </div>\n");
        htmlBuilder.append("        </div>\n");
        
        // Results table
        htmlBuilder.append("        <h2>Detailed Results</h2>\n");
        htmlBuilder.append("        <table>\n");
        htmlBuilder.append("            <thead>\n");
        htmlBuilder.append("                <tr>\n");
        htmlBuilder.append("                    <th>Student ID</th>\n");
        htmlBuilder.append("                    <th>Student Name</th>\n");
        htmlBuilder.append("                    <th>Status</th>\n");
        htmlBuilder.append("                    <th>Details</th>\n");
        htmlBuilder.append("                </tr>\n");
        htmlBuilder.append("            </thead>\n");
        htmlBuilder.append("            <tbody>\n");
        
        // Add each result as a table row
        for (ValidationResult result : results) {
            htmlBuilder.append("                <tr>\n");
            htmlBuilder.append("                    <td>").append(result.getStudentId()).append("</td>\n");
            htmlBuilder.append("                    <td>").append(result.getStudentName()).append("</td>\n");
            
            if (result.isPassedValidation()) {
                htmlBuilder.append("                    <td><span class=\"status pass\">PASS</span></td>\n");
            } else {
                htmlBuilder.append("                    <td><span class=\"status fail\">FAIL</span></td>\n");
            }
            
            htmlBuilder.append("                    <td><div class=\"details\">").append(result.getDetails()).append("</div></td>\n");
            htmlBuilder.append("                </tr>\n");
        }
        
        htmlBuilder.append("            </tbody>\n");
        htmlBuilder.append("        </table>\n");
        
        // HTML footer
        htmlBuilder.append("    </div>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>");
        
        log.info("HTML report generated successfully");
        return htmlBuilder.toString();
    }
}
