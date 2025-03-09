Save this as build-and-run.bat in your project's root directory.
This script will:

Check if the target folder exists and delete it if found
Verify your Java version (must be JDK 11)
Verify your Maven version (must be 3.8.x)
Show appropriate messages based on your environment setup
Run the Maven build command if prerequisites are met
Create any missing required folders (input, compare, report)
Ask you whether you want to:

Show output directly in the console, or
Save output to an output.log file


Run the application with your preferred output method

To use this script:

Save it as build-and-run.bat in your project root directory
Double-click the file or run it from Command Prompt
Follow the prompts and check the results

The script provides clear feedback at each step and will only proceed with the build and execution if your environment is properly configured with Java 11 and Maven 3.8.x.

::::::::::::::::;How to run ::::::::::::::::::
To run the build-and-run.bat file on Windows, follow these simple steps:

Save the file properly:

Make sure the file is saved with the exact name build-and-run.bat (not .txt or any other extension)
Save it in the root directory of your project (the same folder where your pom.xml file is located)


Method 1: Double-click to run:

Open Windows Explorer and navigate to your project folder
Find the build-and-run.bat file
Double-click on it to execute


Method 2: Run from Command Prompt:

Open Command Prompt (you can search for "cmd" in the Windows start menu)
Navigate to your project directory using the cd command, for example

cd C:\Users\YourUsername\Documents\jarmavenprocess

Type the name of the batch file and press Enter:
build-and-run.bat





:::::::::::::::::example output:::::::::::::::::

======================================================
Dynamic File Validation - Build and Run Script
======================================================

Target folder found - removing...
Target folder removed successfully.

Checking Java version...
Detected: openjdk version "11.0.15" 2022-04-19

Java version 11 detected - OK

Checking Maven version...
Detected: Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)

Maven version 3.8.x detected - OK

Environment setup is correct. Proceeding with build.

Building project with Maven...
[INFO] Scanning for projects...
[INFO] 
[INFO] ------< org.BMO.CPO.DynamicFileValidation:DynamicFileValidation >-------
[INFO] Building DynamicFileValidation 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ DynamicFileValidation ---
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ DynamicFileValidation ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory C:\Users\username\projects\jarmavenprocess\src\main\resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ DynamicFileValidation ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 7 source files to C:\Users\username\projects\jarmavenprocess\target\classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ DynamicFileValidation ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory C:\Users\username\projects\jarmavenprocess\src\test\resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ DynamicFileValidation ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ DynamicFileValidation ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ DynamicFileValidation ---
[INFO] Building jar: C:\Users\username\projects\jarmavenprocess\target\DynamicFileValidation-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-assembly-plugin:3.4.2:single (make-assembly) @ DynamicFileValidation ---
[INFO] Building jar: C:\Users\username\projects\jarmavenprocess\target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.226 s
[INFO] Finished at: 2025-03-09T15:02:38+05:30
[INFO] ------------------------------------------------------------------------

======================================================
Running the application...
======================================================

Choose output method:
1. Show output directly in console
2. Save output to output.log file

Enter your choice (1 or 2): 1
Running with console output...
[main] INFO org.BMO.CPO.DynamicFileValidation.App - Starting Dynamic File Validation
[main] INFO org.BMO.CPO.DynamicFileValidation.ExcelProcessor - Processing Excel file: C:\Users\username\projects\jarmavenprocess\input\student_records.xlsx
[main] INFO org.BMO.CPO.DynamicFileValidation.ExcelProcessor - Extracted 20 student records from Excel
[main] INFO org.BMO.CPO.DynamicFileValidation.PdfProcessor - Processing PDF file: C:\Users\username\projects\jarmavenprocess\compare\accurate-pdf-students.pdf
[main] INFO org.BMO.CPO.DynamicFileValidation.PdfProcessor - Extracted 20 student records from PDF
[main] INFO org.BMO.CPO.DynamicFileValidation.PdfProcessor - Processing PDF file: C:\Users\username\projects\jarmavenprocess\compare\inaccurate-pdf-students.pdf
[main] INFO org.BMO.CPO.DynamicFileValidation.PdfProcessor - Extracted 20 student records from PDF
[main] INFO org.BMO.CPO.DynamicFileValidation.RecordComparator - Comparing 20 Excel records against 20 accurate PDF records and 20 inaccurate PDF records
[main] INFO org.BMO.CPO.DynamicFileValidation.RecordComparator - Comparison completed. 20 results generated.
[main] INFO org.BMO.CPO.DynamicFileValidation.ReportGenerator - Generating HTML report for 20 validation results
[main] INFO org.BMO.CPO.DynamicFileValidation.ReportGenerator - HTML report generated successfully
[main] INFO org.BMO.CPO.DynamicFileValidation.App - Validation report generated: C:\Users\username\projects\jarmavenprocess\report\validation_report_1712394798654.html
[main] INFO org.BMO.CPO.DynamicFileValidation.App - Dynamic File Validation completed successfully

Script completed.
Press any key to continue . . .

