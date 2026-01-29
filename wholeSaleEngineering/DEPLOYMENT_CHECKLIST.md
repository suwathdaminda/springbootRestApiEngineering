# Deployment Checklist - Wholesale Engineering Application

## Pre-Deployment Verification

### 1. Environment Setup
- [ ] Java 21 installed and configured (`java -version`)
- [ ] Maven 3.8+ installed (`mvn -version`)
- [ ] PostgreSQL 18+ installed and running
- [ ] Git repository accessible (if using version control)

### 2. Database Setup
- [ ] PostgreSQL service is running
- [ ] Database `wholeSaleEngineering2` created
- [ ] Database user `postgres` has proper permissions
- [ ] Password `daminda@77` is working
- [ ] Sample data loaded via `database-setup.sql`
- [ ] Tables created: `accounts`, `account_transactions`

### 3. Application Configuration
- [ ] `application.properties` configured correctly
- [ ] Database password encrypted using Jasypt
- [ ] Database URL points to correct host/port
- [ ] Server port configured (default: 8080)
- [ ] Logging levels set appropriately

### 4. Build Verification
- [ ] `mvn clean compile` - Successful compilation
- [ ] `mvn test` - All tests passing (30+ tests)
- [ ] `mvn package` - JAR file created
- [ ] No compilation errors or warnings

### 5. Code Quality
- [ ] No hardcoded sensitive data
- [ ] All TODOs addressed
- [ ] Code formatted consistently
- [ ] Comments and documentation updated
- [ ] Unused imports removed

## Deployment Steps

### Step 1: Pre-Build Checks
```bash
# Navigate to project directory
cd C:\dev\projects\CodePreferencesSprint\springbootRestApiEngineering_beforeRefactor\wholeSaleEngineering

# Check Java version
java -version
# Expected: java version "21.x.x"

# Check Maven
mvn -version
# Expected: Apache Maven 3.x
```

### Step 2: Database Verification
```bash
# Connect to PostgreSQL
psql -U postgres -d wholeSaleEngineering2

# Verify tables
\dt

# Check data
SELECT COUNT(*) FROM accounts;
SELECT COUNT(*) FROM account_transactions;

# Exit
\q
```

Expected Results:
- `accounts` table exists with 5 records
- `account_transactions` table exists with 10+ records

### Step 3: Encrypt Password
```bash
# Run encryption utility
encrypt-password.bat

# Copy the encrypted password output
# Update application.properties
```

### Step 4: Build Application
```bash
# Clean and build
mvn clean install

# Expected output:
# [INFO] BUILD SUCCESS
# [INFO] Tests run: XX, Failures: 0, Errors: 0
```

### Step 5: Run Tests
```bash
# Run all tests
mvn test

# Verify test results
# All tests should PASS
```

### Step 6: Start Application
```bash
# Method 1: Using Maven
mvn spring-boot:run

# Method 2: Using JAR
java -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar

# Method 3: Using batch script
run-application.bat
```

### Step 7: Verify Application Startup
Look for these log messages:
```
Starting Wholesale Engineering Application...
Connected to database: wholeSaleEngineering2
Wholesale Engineering Application started successfully!
Swagger UI available at: http://localhost:8080/swagger-ui.html
```

### Step 8: API Testing
```bash
# Test Account endpoint
curl http://localhost:8080/api/accountDetail

# Test Transaction endpoint
curl http://localhost:8080/api/accountTransaction/585309209
```

### Step 9: Swagger UI Verification
- [ ] Open: http://localhost:8080/swagger-ui.html
- [ ] Verify API endpoints are listed
- [ ] Test GET /api/accountDetail
- [ ] Test GET /api/accountTransaction/{accountNo}
- [ ] All endpoints respond correctly

### Step 10: Database Verification
```sql
-- Check if data persists
SELECT * FROM accounts;
SELECT * FROM account_transactions WHERE account_no = '585309209';

-- Verify new records (if created via API)
SELECT * FROM accounts ORDER BY id DESC LIMIT 5;
```

## Post-Deployment Verification

### Functional Tests
- [ ] GET all accounts returns 200 OK
- [ ] GET account by ID returns correct data
- [ ] GET account by number returns correct data
- [ ] GET transactions by account returns correct data
- [ ] POST create account works (201 Created)
- [ ] PUT update account works (200 OK)
- [ ] DELETE account works (204 No Content)
- [ ] POST create transaction works (201 Created)
- [ ] PUT update transaction works (200 OK)
- [ ] DELETE transaction works (204 No Content)

### Error Handling Tests
- [ ] GET non-existent account returns 404
- [ ] GET non-existent transaction returns 404
- [ ] POST duplicate account returns 400
- [ ] Invalid data returns proper error messages

### Performance Tests
- [ ] Application starts in < 30 seconds
- [ ] API response time < 500ms
- [ ] Database queries execute quickly
- [ ] No memory leaks observed

### Security Tests
- [ ] Database password is encrypted
- [ ] No sensitive data in logs
- [ ] SQL injection prevention working
- [ ] Input validation working

### Logging Verification
- [ ] INFO level logs visible
- [ ] DEBUG level logs for application
- [ ] SQL queries logged (if enabled)
- [ ] No ERROR or WARN logs on startup
- [ ] Logs are readable and formatted

## Production Deployment Considerations

### Environment Variables
```bash
# Set Jasypt encryption key
set JASYPT_ENCRYPTOR_PASSWORD=your_production_secret_key

# Set active profile (if applicable)
set SPRING_PROFILES_ACTIVE=production
```

### Production Configuration
Update `application.properties` for production:
```properties
# Disable SQL logging
spring.jpa.show-sql=false

# Use production database
spring.datasource.url=jdbc:postgresql://production-host:5432/wholeSaleEngineering2

# Set appropriate log levels
logging.level.root=INFO
logging.level.au.com.anz.wholeSaleEngineering=INFO

# Disable Swagger in production (optional)
springdoc.swagger-ui.enabled=false
```

### Health Check Endpoints
- [ ] Application responds at: http://host:8080/api/accountDetail
- [ ] Database connection is stable
- [ ] Memory usage is acceptable
- [ ] CPU usage is normal

### Backup Strategy
- [ ] Database backup scheduled
- [ ] Application logs archived
- [ ] Configuration files backed up
- [ ] JAR file versioned

### Monitoring Setup
- [ ] Application logs monitored
- [ ] Database performance monitored
- [ ] API response times tracked
- [ ] Error alerts configured

## Rollback Plan

If deployment fails:

### Step 1: Stop Application
```bash
# Press Ctrl+C or kill process
taskkill /F /IM java.exe
```

### Step 2: Restore Database
```sql
-- Restore from backup
pg_restore -U postgres -d wholeSaleEngineering2 backup_file.sql
```

### Step 3: Revert Code
```bash
# If using Git
git checkout previous_version
mvn clean install
```

### Step 4: Investigate Issues
- Check logs for errors
- Verify database connection
- Review configuration changes
- Test in development environment

## Success Indicators

✅ **Application Running**
- Process is active
- No crash or restart loops
- Logs show successful startup

✅ **Database Connected**
- Connection pool active
- Queries executing successfully
- No connection timeout errors

✅ **API Functional**
- All endpoints responding
- Data retrieval working
- CRUD operations successful

✅ **Tests Passing**
- All unit tests pass
- All integration tests pass
- Manual API tests succeed

✅ **Documentation Available**
- Swagger UI accessible
- OpenAPI spec available
- README documentation complete

✅ **Logging Working**
- Logs are being generated
- Log levels appropriate
- No excessive logging

## Support Information

### Quick Commands
```bash
# Check application status
curl http://localhost:8080/api/accountDetail

# View logs (if using file logging)
tail -f logs/application.log

# Check database connection
psql -U postgres -d wholeSaleEngineering2 -c "SELECT 1"

# Restart application
run-application.bat
```

### Troubleshooting Contacts
- Database Issues: Check PostgreSQL logs
- Application Issues: Check application logs
- API Issues: Check Swagger UI

### Documentation References
- README.md - Full documentation
- QUICK_START.md - Quick setup guide
- TESTING_GUIDE.md - Testing procedures
- REFACTORING_SUMMARY.md - Change summary

## Sign-Off Checklist

### Development Team
- [ ] Code reviewed and approved
- [ ] All tests passing
- [ ] Documentation complete
- [ ] Code checked into repository

### QA Team
- [ ] Functional testing complete
- [ ] Integration testing complete
- [ ] Performance testing complete
- [ ] Security testing complete

### Operations Team
- [ ] Environment prepared
- [ ] Monitoring configured
- [ ] Backup strategy in place
- [ ] Rollback plan ready

### Deployment Lead
- [ ] Pre-deployment checks complete
- [ ] Deployment executed successfully
- [ ] Post-deployment verification complete
- [ ] Sign-off approved

---

**Deployment Date**: _______________
**Deployed By**: _______________
**Approved By**: _______________
**Status**: [ ] Success [ ] Failed [ ] Rollback

## Deployment Complete! 🚀
