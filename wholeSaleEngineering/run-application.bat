@echo off
echo ========================================
echo Wholesale Engineering Application
echo ========================================
echo.

echo Building the application...
call mvn clean install

echo.
echo Starting the application...
echo.
echo The application will be available at:
echo - Application: http://localhost:8080
echo - Swagger UI: http://localhost:8080/swagger-ui.html
echo - API Docs: http://localhost:8080/api-docs
echo.
echo Press Ctrl+C to stop the application
echo ========================================
echo.

java -Djasypt.encryptor.password=wholesale_secret_key -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
