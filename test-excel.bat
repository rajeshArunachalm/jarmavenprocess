@echo off
REM Windows batch file for testing Excel functionality

REM Compile the test classes
echo Compiling test classes...
javac -cp "target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" ^
    -d target\classes ^
    src\main\java\org\BMO\CPO\DynamicFileValidation\ExcelTest.java ^
    src\main\java\org\BMO\CPO\DynamicFileValidation\GenerateExcel.java

REM Run the Excel test
echo.
echo === Testing current Excel file ===
java -cp "target\classes;target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" ^
    org.BMO.CPO.DynamicFileValidation.ExcelTest

REM Regenerate Excel file
echo.
echo === Generating new Excel file ===
java -cp "target\classes;target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" ^
    org.BMO.CPO.DynamicFileValidation.GenerateExcel

REM Test again after regenerating
echo.
echo === Testing newly generated Excel file ===
java -cp "target\classes;target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" ^
    org.BMO.CPO.DynamicFileValidation.ExcelTest

REM Run the main application
echo.
echo === Running main application ===
java -jar target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar

pause
