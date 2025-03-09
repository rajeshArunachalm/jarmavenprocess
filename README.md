# jarmavenprocess
pdf compare process based on input excel and give us report
git clone https://github.com/rajeshArunachalm/jarmavenprocess.git
cd jarmavenprocess
vim README.md
git status
git add .
git status
git commit -m "testpush"
git remote set-url origin https://rajeshArunachalm@github.com/rajeshArunachalm/jarmavenprocess.git
git push -u origin main



open generate_excel.html
go to ui page and download input excel file


# Install Java 11
brew install openjdk@11

# Check the installation path
brew info openjdk@11
# Set JAVA_HOME for the current session (adjust path if needed)
export JAVA_HOME=/opt/homebrew/opt/openjdk@11
export PATH=$JAVA_HOME/bin:$PATH


# Download the archive
curl -O https://archive.apache.org/dist/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz
# Extract it
tar -xzvf apache-maven-3.8.8-bin.tar.gz
# Move it to a suitable location
sudo mv apache-maven-3.8.8 /opt/
export M2_HOME=/opt/apache-maven-3.8.8
export PATH=$M2_HOME/bin:$PATH
mvn --version




java -version  # Should show Java 11
mvn -version   # Should show Java 11 as the runtime



Java Version: Java 11

I selected this because it's an LTS (Long Term Support) version with good stability
It's widely adopted and supported across different operating systems (Windows, Mac, Linux)


Maven Version: 3.8.x or newer

Compatible with Java 11
The POM file is configured with Maven 3.x compatibility


Key Dependencies:

Apache POI 5.2.3 for Excel processing
Apache PDFBox 2.0.27 for PDF processing
SLF4J 1.7.36 for logging
Lombok 1.18.26 for reducing boilerplate code
