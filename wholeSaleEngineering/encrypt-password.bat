@echo off
echo ========================================
echo Jasypt Password Encryption Utility
echo ========================================
echo.

echo Compiling the project...
call mvn clean compile -DskipTests

echo.
echo Running password encryptor...
echo.

java -cp target/classes au.com.anz.wholeSaleEngineering.util.JasyptPasswordEncryptor

echo.
echo ========================================
echo Copy the encrypted password above and update it in:
echo src/main/resources/application.properties
echo.
echo Replace: spring.datasource.password=ENC(YourEncryptedPasswordHere)
echo ========================================
pause
