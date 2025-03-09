#!/bin/bash

# Compile the test classes
echo "Compiling test classes..."
javac -cp "target/DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" \
    -d target/classes \
    src/main/java/org/BMO/CPO/DynamicFileValidation/ExcelTest.java \
    src/main/java/org/BMO/CPO/DynamicFileValidation/GenerateExcel.java

# Run the Excel test
echo -e "\n=== Testing current Excel file ==="
java -cp "target/classes:target/DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" \
    org.BMO.CPO.DynamicFileValidation.ExcelTest

# Regenerate Excel file
echo -e "\n=== Generating new Excel file ==="
java -cp "target/classes:target/DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" \
    org.BMO.CPO.DynamicFileValidation.GenerateExcel

# Test again after regenerating
echo -e "\n=== Testing newly generated Excel file ==="
java -cp "target/classes:target/DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" \
    org.BMO.CPO.DynamicFileValidation.ExcelTest

# Run the main application
echo -e "\n=== Running main application ==="
java -jar target/DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar
