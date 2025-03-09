@echo off
setlocal enabledelayedexpansion

echo ======================================================
echo Dynamic File Validation - Build and Run Script
echo ======================================================
echo.

REM Check if target folder exists and delete if it does
if exist "target" (
    echo Target folder found - removing...
    rmdir /s /q "target"
    echo Target folder removed successfully.
) else (
    echo No target folder found - skipping cleanup.
)
echo.

REM Check Java version
echo Checking Java version...
java -version 2>&1 | findstr /C:"openjdk version" /C:"java version" > temp.txt
set /p JAVA_VERSION=<temp.txt
del temp.txt

echo Detected: !JAVA_VERSION!

REM Extract major version number from Java version string
for /f "tokens=3" %%a in ("!JAVA_VERSION!") do (
    set VERSION=%%a
)
set VERSION=!VERSION:"=!
for /f "tokens=1,2 delims=." %%a in ("!VERSION!") do (
    set JAVA_MAJOR=%%a
)

REM Check Maven version
echo.
echo Checking Maven version...
mvn --version > temp.txt
findstr /C:"Apache Maven" temp.txt > temp2.txt
set /p MVN_VERSION=<temp2.txt
del temp.txt
del temp2.txt

echo Detected: !MVN_VERSION!

REM Extract Maven version number
for /f "tokens=3" %%a in ("!MVN_VERSION!") do (
    set MVN_VER=%%a
)

REM Check if Java is version 11
set JAVA_OK=false
if "!JAVA_MAJOR!"=="11" (
    set JAVA_OK=true
    echo Java version 11 detected - OK
) else (
    echo WARNING: Java version 11 is required, but detected version !JAVA_MAJOR!
)

REM Check if Maven is version 3.8.x
set MVN_OK=false
echo !MVN_VER! | findstr /r "^3\.8\." > nul
if !errorlevel! equ 0 (
    set MVN_OK=true
    echo Maven version 3.8.x detected - OK
) else (
    echo WARNING: Maven version 3.8.x is required, but detected version !MVN_VER!
)
echo.

REM Check overall environment status
if "!JAVA_OK!"=="true" if "!MVN_OK!"=="true" (
    echo Environment setup is correct. Proceeding with build.
) else (
    echo ERROR: Environment setup issues detected. Please ensure Java 11 and Maven 3.8.x are installed and in your PATH.
    echo Current Java version: !JAVA_MAJOR!
    echo Current Maven version: !MVN_VER!
    goto :end
)
echo.

REM Run Maven build
echo Building project with Maven...
call mvn clean package
if !errorlevel! neq 0 (
    echo Maven build failed with error code !errorlevel!
    goto :end
)
echo.

REM Check if build was successful and JAR exists
if not exist "target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo ERROR: JAR file not found after build. Check for Maven errors.
    goto :end
)

REM Run the application
echo.
echo ======================================================
echo Running the application...
echo ======================================================
echo.

REM Create the necessary folders if they don't exist
if not exist "input" mkdir input
if not exist "compare" mkdir compare
if not exist "report" mkdir report

REM Ask user which output method they prefer
echo Choose output method:
echo 1. Show output directly in console
echo 2. Save output to output.log file
echo.
set /p OUTPUT_CHOICE="Enter your choice (1 or 2): "

if "!OUTPUT_CHOICE!"=="1" (
    echo Running with console output...
    java -jar target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar
) else if "!OUTPUT_CHOICE!"=="2" (
    echo Running with output to output.log...
    java -jar target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar > output.log 2>&1
    echo Application completed. Check output.log for results.
) else (
    echo Invalid choice. Running with console output by default...
    java -jar target\DynamicFileValidation-1.0-SNAPSHOT-jar-with-dependencies.jar
)

:end
echo.
echo Script completed.
pause
