# Wholesale Engineering Application

A RESTful API application for managing customer accounts and transactions, refactored to use Java 21, Spring Boot 3.x, and PostgreSQL database.

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: For ORM and database operations
- **PostgreSQL**: 18+ (Database)
- **Jasypt**: For password encryption
- **OpenAPI 3.0**: API documentation (Swagger UI)
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for tests
- **Maven**: Build tool
- **Lombok**: To reduce boilerplate code

## Features

- ✅ CRUD operations for Accounts
- ✅ CRUD operations for Account Transactions
- ✅ PostgreSQL database integration
- ✅ Encrypted database password using Jasypt
- ✅ OpenAPI 3.0 / Swagger UI documentation
- ✅ Comprehensive logging with SLF4J
- ✅ JUnit 5 unit tests
- ✅ Mockito integration tests
- ✅ RESTful API design

## Prerequisites

1. **Java 21** - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
2. **PostgreSQL 18+** - Local installation
3. **Maven 3.8+** - For building the project

## Database Setup

### Step 1: Create Database

Open PostgreSQL command line or pgAdmin and execute:

```sql
CREATE DATABASE "wholeSaleEngineering2";
```

### Step 2: Connect to Database

```sql
\c wholeSaleEngineering2
```

### Step 3: Create Tables (Spring Boot will auto-create via JPA)

The application uses `spring.jpa.hibernate.ddl-auto=update` which will automatically create tables based on entity definitions.

### Step 4: Insert Sample Data (Optional)

Run the SQL script located at `src/main/resources/data.sql` or let Spring Boot execute it automatically on startup.

## Configuration

### Database Configuration

Edit `src/main/resources/application.properties`:

```properties
# Database URL
spring.datasource.url=jdbc:postgresql://localhost:5432/wholeSaleEngineering2

# Database Username
spring.datasource.username=postgres

# Encrypted Database Password
spring.datasource.password=ENC(encrypted_password_here)
```

### Encrypting Database Password

1. Run the `JasyptPasswordEncryptor` utility:

```bash
mvn clean compile
java -cp target/classes au.com.anz.wholeSaleEngineering.util.JasyptPasswordEncryptor
```

2. Copy the encrypted password output
3. Update `application.properties` with: `spring.datasource.password=ENC(your_encrypted_password)`

### Environment Variable (Optional)

You can set the Jasypt encryption key as an environment variable:

```bash
# Windows
set JASYPT_ENCRYPTOR_PASSWORD=wholesale_secret_key

# Linux/Mac
export JASYPT_ENCRYPTOR_PASSWORD=wholesale_secret_key
```

## Building the Application

### Clean and Build

```bash
mvn clean install
```

### Run Tests Only

```bash
mvn test
```

### Package Application

```bash
mvn package
```

## Running the Application

### Method 1: Using Maven

```bash
mvn spring-boot:run
```

### Method 2: Using JAR

```bash
java -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

### Method 3: With Custom Jasypt Key

```bash
java -Djasypt.encryptor.password=wholesale_secret_key -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

## API Documentation

Once the application is running, access the Swagger UI:

**Swagger UI**: http://localhost:8080/swagger-ui.html

**OpenAPI JSON**: http://localhost:8080/api-docs

## API Endpoints

### Account Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/accountDetail` | Get all accounts |
| GET | `/api/accounts/{id}` | Get account by ID |
| GET | `/api/accounts/byNumber/{accountNo}` | Get account by account number |
| POST | `/api/accounts` | Create new account |
| PUT | `/api/accounts/{id}` | Update account |
| DELETE | `/api/accounts/{id}` | Delete account |

### Transaction Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/transactions` | Get all transactions |
| GET | `/api/accountTransaction/{accountNo}` | Get transactions by account number |
| GET | `/api/transactions/{id}` | Get transaction by ID |
| POST | `/api/transactions` | Create new transaction |
| PUT | `/api/transactions/{id}` | Update transaction |
| DELETE | `/api/transactions/{id}` | Delete transaction |

## Testing

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=AccountServiceTest
```

### Test Coverage

- ✅ Unit tests for Service layer
- ✅ Integration tests for Controllers
- ✅ Mockito mocking
- ✅ MockMvc for REST API testing

## Example API Usage

### Create Account (POST)

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "accountNo": "123456789",
    "accountName": "Test Account",
    "accountType": "Savings",
    "balanceDate": "2024-01-15",
    "currency": "USD",
    "openingAvailBal": 10000.00
  }'
```

### Get All Accounts (GET)

```bash
curl http://localhost:8080/api/accountDetail
```

### Get Transactions by Account Number (GET)

```bash
curl http://localhost:8080/api/accountTransaction/585309209
```

## Logging

Logs are configured in `application.properties`:

- **Console Output**: Enabled with pattern
- **Log Levels**: DEBUG for application, INFO for root
- **SQL Logging**: Enabled for debugging

View logs in the console or check application logs.

## Project Structure

```
wholeSaleEngineering/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── au/com/anz/wholeSaleEngineering/
│   │   │       ├── config/
│   │   │       │   └── JasyptConfig.java
│   │   │       ├── repository/
│   │   │       │   ├── AccountRepository.java
│   │   │       │   └── AccountTransactionRepository.java
│   │   │       ├── service/
│   │   │       │   ├── AccountService.java
│   │   │       │   ├── AccountServiceController.java
│   │   │       │   ├── AccountTransactionService.java
│   │   │       │   └── AccountTransactionServiceController.java
│   │   │       ├── util/
│   │   │       │   └── JasyptPasswordEncryptor.java
│   │   │       ├── Account.java
│   │   │       ├── AccountTransaction.java
│   │   │       └── WholeSaleEngrApp.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│       └── java/
│           └── au/com/anz/wholeSaleEngineering/
│               ├── service/
│               │   ├── AccountServiceTest.java
│               │   ├── AccountServiceControllerTest.java
│               │   ├── AccountTransactionServiceTest.java
│               │   └── AccountTransactionServiceControllerTest.java
│               └── WholeSaleEngrAppTest.java
├── pom.xml
└── README.md
```

## Troubleshooting

### Issue: Database Connection Failed

**Solution**: 
- Ensure PostgreSQL is running
- Verify database name: `wholeSaleEngineering2`
- Check username/password
- Verify port: 5432

### Issue: Encrypted Password Not Working

**Solution**:
- Re-run `JasyptPasswordEncryptor` to generate a new encrypted password
- Ensure `JASYPT_ENCRYPTOR_PASSWORD` environment variable matches the encryption key
- Update `application.properties` with the new encrypted password

### Issue: Tests Failing

**Solution**:
- Ensure database is accessible
- Run `mvn clean install` to rebuild
- Check logs for specific error messages

### Issue: Port 8080 Already in Use

**Solution**:
- Change port in `application.properties`: `server.port=8081`
- Or kill the process using port 8080

## Success Criteria

✅ **Build Successful**: `mvn clean install` completes without errors

✅ **All Tests Pass**: JUnit 5 and Mockito tests pass successfully

✅ **Database Integration**: Data is persisted and retrieved from PostgreSQL

✅ **OpenAPI Documentation**: Swagger UI accessible at http://localhost:8080/swagger-ui.html

✅ **Logging**: Application logs show INFO/DEBUG messages

✅ **Password Encryption**: Database password encrypted using Jasypt

## Contributors

- **Original Author**: Suwath Mihindukulasooriya (2019)
- **Refactored for Java 21**: 2024

## License

Copyright © 2024 ANZ Wholesale Engineering. All rights reserved.
