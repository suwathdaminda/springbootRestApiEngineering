# Quick Start Guide - Wholesale Engineering Application

## Prerequisites Check

✅ Java 21 installed: `java -version`
✅ Maven installed: `mvn -version`
✅ PostgreSQL running: Check in Services or `pg_ctl status`

## 5-Minute Setup

### Step 1: Setup PostgreSQL Database (2 minutes)

1. Open **pgAdmin 4** or **PostgreSQL Command Line (psql)**

2. Connect to PostgreSQL:
   - Host: localhost
   - Port: 5432
   - Username: postgres
   - Password: daminda@77

3. Execute the database setup script:
   ```sql
   -- Create database
   CREATE DATABASE "wholeSaleEngineering2";
   ```

4. Run the full setup script:
   ```bash
   psql -U postgres -d postgres -f database-setup.sql
   ```
   
   OR manually in pgAdmin:
   - Right-click on Databases → Create → Database
   - Name: `wholeSaleEngineering2`
   - Click Save

### Step 2: Encrypt Database Password (1 minute)

The password `daminda@77` needs to be encrypted. Run:

```bash
cd C:\dev\projects\CodePreferencesSprint\springbootRestApiEngineering_beforeRefactor\wholeSaleEngineering

# Compile the project first
mvn clean compile

# Run the password encryptor
java -cp target/classes au.com.anz.wholeSaleEngineering.util.JasyptPasswordEncryptor
```

**Output will be something like:**
```
Original Password: daminda@77
Encrypted Password: ENC(YourEncryptedPasswordHere)

Add this to application.properties:
spring.datasource.password=ENC(YourEncryptedPasswordHere)
```

**Copy the encrypted password** and update `src/main/resources/application.properties`:

```properties
spring.datasource.password=ENC(YourEncryptedPasswordHere)
```

### Step 3: Build and Test (1 minute)

```bash
# Build the project
mvn clean install

# This will:
# - Compile Java 21 code
# - Run all JUnit 5 tests
# - Run Mockito tests
# - Package the application
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX s
Tests run: XX, Failures: 0, Errors: 0, Skipped: 0
```

### Step 4: Run the Application (1 minute)

```bash
# Method 1: Using Maven
mvn spring-boot:run

# Method 2: Using JAR
java -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

**Look for these logs:**
```
Starting Wholesale Engineering Application...
Wholesale Engineering Application started successfully!
Swagger UI available at: http://localhost:8080/swagger-ui.html
```

### Step 5: Test the Application

#### Open Swagger UI
Navigate to: **http://localhost:8080/swagger-ui.html**

#### Test API Endpoints

1. **Get All Accounts**
   - Click on `Account Management` → `GET /api/accountDetail`
   - Click "Try it out" → "Execute"
   - You should see 5 accounts

2. **Get Transactions for an Account**
   - Click on `Account Transaction Management` → `GET /api/accountTransaction/{accountNo}`
   - Enter account number: `585309209`
   - Click "Execute"
   - You should see transactions for that account

## Alternative: Using Command Line

### Test with cURL

```bash
# Get all accounts
curl http://localhost:8080/api/accountDetail

# Get transactions for account
curl http://localhost:8080/api/accountTransaction/585309209

# Create new account
curl -X POST http://localhost:8080/api/accounts ^
  -H "Content-Type: application/json" ^
  -d "{\"accountNo\":\"999888777\",\"accountName\":\"Test Account\",\"accountType\":\"Savings\",\"balanceDate\":\"2024-01-15\",\"currency\":\"USD\",\"openingAvailBal\":50000.00}"
```

### Test with PowerShell

```powershell
# Get all accounts
Invoke-RestMethod -Uri "http://localhost:8080/api/accountDetail" -Method Get

# Get transactions
Invoke-RestMethod -Uri "http://localhost:8080/api/accountTransaction/585309209" -Method Get
```

## Verification Checklist

✅ **Database Connection**: Application connects to PostgreSQL
✅ **Data Loaded**: 5 accounts and multiple transactions visible
✅ **Swagger UI**: Accessible at http://localhost:8080/swagger-ui.html
✅ **API Working**: Can retrieve accounts and transactions
✅ **Tests Passing**: `mvn test` shows all tests passing
✅ **Logging**: Console shows INFO/DEBUG logs

## Common Issues & Quick Fixes

### Issue 1: "Database does not exist"
**Fix**: Run the database setup script again
```bash
psql -U postgres -d postgres -f database-setup.sql
```

### Issue 2: "Authentication failed for user postgres"
**Fix**: 
- Verify password: `daminda@77`
- Re-encrypt the password using JasyptPasswordEncryptor
- Update application.properties with new encrypted password

### Issue 3: "Port 8080 already in use"
**Fix**: 
- Change port in application.properties: `server.port=8081`
- Or kill the process: `netstat -ano | findstr :8080` then `taskkill /PID <PID> /F`

### Issue 4: "Tests failing"
**Fix**:
```bash
# Clean everything and rebuild
mvn clean
mvn clean install -DskipTests
mvn test
```

### Issue 5: "Encrypted password not working"
**Fix**:
```bash
# Use environment variable
set JASYPT_ENCRYPTOR_PASSWORD=wholesale_secret_key
mvn spring-boot:run

# OR specify in command
java -Djasypt.encryptor.password=wholesale_secret_key -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

## Success Indicators

When everything is working correctly, you should see:

```
  _____ _   _  __  __  ____    _____ _____
 / ____| | | |/ _|/ _||  _ \  / ____|  __ \
| (___ | |_| | |_| |_ | |_) || |  __| |__) |
 \___ \|  _  |  _|  _||  _ < | | |_ |  _  /
 ____) | | | | | | |  | |_) || |__| | | \ \
|_____/|_| |_|_| |_|  |____/  \_____|_|  \_\

Starting Wholesale Engineering Application...
Connected to PostgreSQL database: wholeSaleEngineering2
Hibernate: create table accounts...
Hibernate: create table account_transactions...
Data initialization completed
Wholesale Engineering Application started successfully!
Swagger UI available at: http://localhost:8080/swagger-ui.html
```

## Next Steps

1. ✅ Explore Swagger UI: http://localhost:8080/swagger-ui.html
2. ✅ Test all CRUD operations for Accounts and Transactions
3. ✅ Check logs for SQL queries
4. ✅ Run unit tests: `mvn test`
5. ✅ Review code structure and implementation

## Project Structure Overview

```
wholeSaleEngineering/
├── src/main/java/
│   ├── Account.java (Entity)
│   ├── AccountTransaction.java (Entity)
│   ├── repository/ (Spring Data JPA)
│   ├── service/ (Business Logic)
│   ├── config/ (Jasypt Config)
│   └── util/ (Password Encryptor)
├── src/main/resources/
│   ├── application.properties (Configuration)
│   └── data.sql (Sample Data)
├── src/test/java/ (JUnit 5 Tests)
└── database-setup.sql (DB Setup Script)
```

## Support & Documentation

- **README.md**: Detailed documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/api-docs
- **Logs**: Check console output

## Time to Success: ~5 minutes! 🚀

Happy Coding! 💻
