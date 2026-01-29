# Testing Guide - Wholesale Engineering Application

## Overview

This guide covers all testing aspects of the Wholesale Engineering Application including:
- Unit Tests (JUnit 5)
- Integration Tests (MockMvc)
- Manual API Testing (Swagger UI)
- Database Testing

## Test Structure

```
src/test/java/
└── au/com/anz/wholeSaleEngineering/
    ├── WholeSaleEngrAppTest.java (Application Context Test)
    └── service/
        ├── AccountServiceTest.java (Unit Test)
        ├── AccountServiceControllerTest.java (Integration Test)
        ├── AccountTransactionServiceTest.java (Unit Test)
        └── AccountTransactionServiceControllerTest.java (Integration Test)
```

## Running Tests

### Run All Tests

```bash
mvn test
```

**Expected Output:**
```
[INFO] Tests run: XX, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Run Specific Test Class

```bash
# Run AccountServiceTest only
mvn test -Dtest=AccountServiceTest

# Run all service tests
mvn test -Dtest=*ServiceTest

# Run all controller tests
mvn test -Dtest=*ControllerTest
```

### Run Tests with Coverage Report

```bash
mvn clean test jacoco:report
```

### Run Tests in IDE

#### IntelliJ IDEA
1. Right-click on test class
2. Select "Run 'ClassName'"
3. View results in Run panel

#### Eclipse
1. Right-click on test class
2. Select "Run As" → "JUnit Test"
3. View results in JUnit panel

## Unit Tests Details

### AccountServiceTest.java

Tests business logic layer for Account operations.

**Test Cases:**
- ✅ `testGetAllAccounts_Success()` - Retrieve all accounts
- ✅ `testGetAccountById_Found()` - Find account by ID
- ✅ `testGetAccountById_NotFound()` - Account not found scenario
- ✅ `testGetAccountByAccountNo_Found()` - Find account by number
- ✅ `testCreateAccount_Success()` - Create new account
- ✅ `testCreateAccount_AlreadyExists()` - Duplicate account error
- ✅ `testUpdateAccount_Success()` - Update existing account
- ✅ `testUpdateAccount_NotFound()` - Update non-existing account
- ✅ `testDeleteAccount_Success()` - Delete account
- ✅ `testDeleteAccount_NotFound()` - Delete non-existing account

**Technologies Used:**
- JUnit 5 (@Test, @BeforeEach, @ExtendWith)
- Mockito (@Mock, @InjectMocks, when(), verify())
- AssertJ (assertThat, assertions)

**Run Command:**
```bash
mvn test -Dtest=AccountServiceTest
```

### AccountTransactionServiceTest.java

Tests business logic layer for Transaction operations.

**Test Cases:**
- ✅ `testGetAllTransactions_Success()` - Retrieve all transactions
- ✅ `testGetTransactionById_Found()` - Find transaction by ID
- ✅ `testGetTransactionById_NotFound()` - Transaction not found
- ✅ `testGetTransactionsByAccountNo_Success()` - Find by account number
- ✅ `testGetTransactionsByAccountNo_EmptyList()` - No transactions found
- ✅ `testCreateTransaction_Success()` - Create new transaction
- ✅ `testUpdateTransaction_Success()` - Update transaction
- ✅ `testUpdateTransaction_NotFound()` - Update non-existing transaction
- ✅ `testDeleteTransaction_Success()` - Delete transaction
- ✅ `testDeleteTransaction_NotFound()` - Delete non-existing transaction

**Run Command:**
```bash
mvn test -Dtest=AccountTransactionServiceTest
```

## Integration Tests Details

### AccountServiceControllerTest.java

Tests REST API endpoints for Account operations using MockMvc.

**Test Cases:**
- ✅ `testGetAllAccounts_Success()` - GET /api/accountDetail
- ✅ `testGetAccountById_Found()` - GET /api/accounts/{id}
- ✅ `testGetAccountById_NotFound()` - GET /api/accounts/{id} (404)
- ✅ `testGetAccountByAccountNo_Found()` - GET /api/accounts/byNumber/{accountNo}
- ✅ `testCreateAccount_Success()` - POST /api/accounts
- ✅ `testUpdateAccount_Success()` - PUT /api/accounts/{id}
- ✅ `testDeleteAccount_Success()` - DELETE /api/accounts/{id}

**Technologies Used:**
- MockMvc (REST API testing)
- @WebMvcTest (Controller layer testing)
- @MockBean (Service mocking)
- JSON assertions

**Run Command:**
```bash
mvn test -Dtest=AccountServiceControllerTest
```

### AccountTransactionServiceControllerTest.java

Tests REST API endpoints for Transaction operations.

**Test Cases:**
- ✅ `testGetAllTransactions_Success()` - GET /api/transactions
- ✅ `testGetAccountTransaction_Found()` - GET /api/accountTransaction/{accountNo}
- ✅ `testGetAccountTransaction_NotFound()` - GET /api/accountTransaction/{accountNo} (404)
- ✅ `testGetTransactionById_Found()` - GET /api/transactions/{id}
- ✅ `testGetTransactionById_NotFound()` - GET /api/transactions/{id} (404)
- ✅ `testCreateTransaction_Success()` - POST /api/transactions
- ✅ `testUpdateTransaction_Success()` - PUT /api/transactions/{id}
- ✅ `testDeleteTransaction_Success()` - DELETE /api/transactions/{id}

**Run Command:**
```bash
mvn test -Dtest=AccountTransactionServiceControllerTest
```

## Manual API Testing with Swagger UI

### Access Swagger UI

1. Start the application:
   ```bash
   mvn spring-boot:run
   ```

2. Open browser: **http://localhost:8080/swagger-ui.html**

### Test Scenarios

#### Scenario 1: View All Accounts

1. Navigate to **Account Management** section
2. Click on **GET /api/accountDetail**
3. Click **"Try it out"**
4. Click **"Execute"**
5. **Expected Result**: List of 5 accounts with status 200 OK

```json
[
  {
    "id": 1,
    "accountNo": "585309209",
    "accountName": "SGSavings726",
    "accountType": "Savings",
    "balanceDate": "2018-11-08",
    "currency": "SGD",
    "openingAvailBal": 84327.51
  },
  ...
]
```

#### Scenario 2: View Transactions for Account

1. Navigate to **Account Transaction Management**
2. Click on **GET /api/accountTransaction/{accountNo}**
3. Click **"Try it out"**
4. Enter `accountNo`: **585309209**
5. Click **"Execute"**
6. **Expected Result**: List of transactions for that account

#### Scenario 3: Create New Account

1. Click on **POST /api/accounts**
2. Click **"Try it out"**
3. Edit the request body:
```json
{
  "accountNo": "999888777",
  "accountName": "New Test Account",
  "accountType": "Savings",
  "balanceDate": "2024-01-15",
  "currency": "USD",
  "openingAvailBal": 25000.00
}
```
4. Click **"Execute"**
5. **Expected Result**: Status 201 Created with created account details

#### Scenario 4: Update Account

1. Click on **PUT /api/accounts/{id}**
2. Click **"Try it out"**
3. Enter `id`: **1**
4. Edit the request body (change account name):
```json
{
  "accountNo": "585309209",
  "accountName": "SGSavings726 - Updated",
  "accountType": "Savings",
  "balanceDate": "2024-01-15",
  "currency": "SGD",
  "openingAvailBal": 90000.00
}
```
5. Click **"Execute"**
6. **Expected Result**: Status 200 OK with updated account

#### Scenario 5: Delete Account

1. Click on **DELETE /api/accounts/{id}**
2. Click **"Try it out"**
3. Enter `id`: **5**
4. Click **"Execute"**
5. **Expected Result**: Status 204 No Content

#### Scenario 6: Create Transaction

1. Click on **POST /api/transactions**
2. Click **"Try it out"**
3. Edit the request body:
```json
{
  "accountNo": "585309209",
  "accountName": "SGSavings726",
  "valueDate": "2024-01-20",
  "currency": "SGD",
  "creditAmt": 5000.00,
  "debitAmt": null,
  "txType": "Credit",
  "txNarrative": "New deposit test"
}
```
4. Click **"Execute"**
5. **Expected Result**: Status 201 Created

## Testing with cURL (Command Line)

### Get All Accounts
```bash
curl -X GET "http://localhost:8080/api/accountDetail" -H "accept: application/json"
```

### Get Account by ID
```bash
curl -X GET "http://localhost:8080/api/accounts/1" -H "accept: application/json"
```

### Get Transactions by Account Number
```bash
curl -X GET "http://localhost:8080/api/accountTransaction/585309209" -H "accept: application/json"
```

### Create New Account
```bash
curl -X POST "http://localhost:8080/api/accounts" ^
  -H "accept: application/json" ^
  -H "Content-Type: application/json" ^
  -d "{\"accountNo\":\"111222333\",\"accountName\":\"Test Account\",\"accountType\":\"Current\",\"balanceDate\":\"2024-01-15\",\"currency\":\"AUD\",\"openingAvailBal\":15000.00}"
```

### Update Account
```bash
curl -X PUT "http://localhost:8080/api/accounts/1" ^
  -H "accept: application/json" ^
  -H "Content-Type: application/json" ^
  -d "{\"accountNo\":\"585309209\",\"accountName\":\"Updated Name\",\"accountType\":\"Savings\",\"balanceDate\":\"2024-01-15\",\"currency\":\"SGD\",\"openingAvailBal\":95000.00}"
```

### Delete Account
```bash
curl -X DELETE "http://localhost:8080/api/accounts/5" -H "accept: application/json"
```

## Testing with Postman

### Import Collection

1. Open Postman
2. Import → Raw Text
3. Paste OpenAPI spec from: http://localhost:8080/api-docs
4. Collection will be auto-generated

### Test Requests

Create requests for each endpoint:

**Collection Structure:**
```
Wholesale Engineering API
├── Accounts
│   ├── Get All Accounts (GET)
│   ├── Get Account by ID (GET)
│   ├── Get Account by Number (GET)
│   ├── Create Account (POST)
│   ├── Update Account (PUT)
│   └── Delete Account (DELETE)
└── Transactions
    ├── Get All Transactions (GET)
    ├── Get Transactions by Account (GET)
    ├── Get Transaction by ID (GET)
    ├── Create Transaction (POST)
    ├── Update Transaction (PUT)
    └── Delete Transaction (DELETE)
```

## Database Verification

### Using pgAdmin

1. Open pgAdmin 4
2. Connect to `wholeSaleEngineering2` database
3. Execute queries:

```sql
-- Check accounts
SELECT * FROM accounts ORDER BY account_no;

-- Check transactions
SELECT * FROM account_transactions ORDER BY account_no, value_date DESC;

-- Count records
SELECT 'Accounts' AS table_name, COUNT(*) AS count FROM accounts
UNION ALL
SELECT 'Transactions', COUNT(*) FROM account_transactions;

-- Transactions for specific account
SELECT * FROM account_transactions 
WHERE account_no = '585309209' 
ORDER BY value_date DESC;
```

### Using psql Command Line

```bash
psql -U postgres -d wholeSaleEngineering2

# Query accounts
SELECT * FROM accounts;

# Query transactions
SELECT * FROM account_transactions WHERE account_no = '585309209';

# Exit
\q
```

## Test Results Verification

### Success Criteria

✅ **All Unit Tests Pass**
```
[INFO] AccountServiceTest ........................ SUCCESS
[INFO] AccountTransactionServiceTest ............. SUCCESS
```

✅ **All Integration Tests Pass**
```
[INFO] AccountServiceControllerTest .............. SUCCESS
[INFO] AccountTransactionServiceControllerTest ... SUCCESS
```

✅ **Application Context Loads**
```
[INFO] WholeSaleEngrAppTest ...................... SUCCESS
```

✅ **API Endpoints Respond Correctly**
- GET requests return 200 OK
- POST requests return 201 Created
- PUT requests return 200 OK
- DELETE requests return 204 No Content
- Not found scenarios return 404

✅ **Database Operations Work**
- Data persists correctly
- Queries return expected results
- Transactions are atomic

## Troubleshooting Tests

### Issue: Tests fail with database connection error

**Solution:**
- Ensure PostgreSQL is running
- Verify database `wholeSaleEngineering2` exists
- Check credentials in `application-test.properties`

### Issue: Tests fail with "Table does not exist"

**Solution:**
```bash
# Use create-drop for tests
# In application-test.properties:
spring.jpa.hibernate.ddl-auto=create-drop
```

### Issue: Mock not working in tests

**Solution:**
```java
// Ensure proper Mockito annotations
@ExtendWith(MockitoExtension.class)
class YourTest {
    @Mock
    private YourRepository repository;
    
    @InjectMocks
    private YourService service;
}
```

### Issue: JSON serialization errors in tests

**Solution:**
```java
// Use ObjectMapper for JSON conversion
@Autowired
private ObjectMapper objectMapper;

String json = objectMapper.writeValueAsString(object);
```

## Test Coverage Goals

Target coverage metrics:
- **Line Coverage**: > 80%
- **Branch Coverage**: > 70%
- **Method Coverage**: > 85%

Generate coverage report:
```bash
mvn clean test jacoco:report
```

View report: `target/site/jacoco/index.html`

## Continuous Testing

### Pre-commit Testing
```bash
# Run before committing
mvn clean test
```

### Build Pipeline Testing
```bash
# Full build with tests
mvn clean install

# Skip tests if needed (not recommended)
mvn clean install -DskipTests
```

## Summary

All tests cover:
- ✅ Service layer business logic
- ✅ REST API endpoints
- ✅ Database operations
- ✅ Error handling
- ✅ Data validation
- ✅ CRUD operations
- ✅ Edge cases

**Total Test Count**: 30+ tests across all test classes

**Test Execution Time**: < 30 seconds

**Test Framework**: JUnit 5 + Mockito + MockMvc + AssertJ

Happy Testing! 🧪✅
