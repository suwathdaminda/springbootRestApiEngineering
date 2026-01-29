# Password Encryption Setup - Jasypt Integration

## Overview
This project uses Jasypt (Java Simplified Encryption) to encrypt sensitive database credentials in the `application.properties` file.

## How It Works

1. **Encrypted Password in Properties**: The database password is stored as an encrypted value in `application.properties`:
   ```properties
   spring.datasource.password=ENC(fT4K6nCr4qT8vX3mN9pL2wQ5rJ7sB1dF)
   ```

2. **Secret Key from Environment**: The decryption secret key is provided via environment variable:
   ```bash
   export JASYPT_ENCRYPTOR_PASSWORD=my-encryption-key
   ```

3. **Runtime Decryption**: When the application starts, Jasypt automatically decrypts the password using the secret key.

## Running the Application with Encrypted Passwords

### Step 1: Set the Environment Variable
**Windows Command Prompt:**
```batch
set JASYPT_ENCRYPTOR_PASSWORD=my-secret-key
java -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

**Windows PowerShell:**
```powershell
$env:JASYPT_ENCRYPTOR_PASSWORD="my-secret-key"
java -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

**Linux/Mac:**
```bash
export JASYPT_ENCRYPTOR_PASSWORD=my-secret-key
java -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

### Step 2: Alternatively, Pass as JVM Argument
```bash
java -Djasypt.encryptor.password=my-secret-key \
     -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

## Encrypting Your Own Passwords

If you need to encrypt a new password for the database or other services:

### Option 1: Using the Encryption Utility
```bash
java -cp target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar \
     au.com.anz.wholeSaleEngineering.util.JasyptPasswordEncryptor \
     "your-password" "your-secret-key"
```

Example:
```bash
java -cp target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar \
     au.com.anz.wholeSaleEngineering.util.JasyptPasswordEncryptor \
     "daminda@77" "wholeSale2024Key"
```

This will output:
```
Original Password: daminda@77
Secret Key: wholeSale2024Key
Encrypted Password: abc123def456ghi789jkl012
Update your application.properties with:
spring.datasource.password=ENC(abc123def456ghi789jkl012)
```

### Option 2: Using Online Tool
Visit: https://www.jasypt.org/api-for-use.html

## Configuration Files

### application.properties
Contains the encrypted database password with `ENC()` wrapper:
```properties
spring.datasource.password=ENC(fT4K6nCr4qT8vX3mN9pL2wQ5rJ7sB1dF)
```

Fallback to environment variable or JVM argument for decryption key.

### JasyptConfig.java
Spring configuration bean that sets up the encryption/decryption engine with:
- Algorithm: PBEWithMD5AndDES
- Key Iterations: 1000
- Output Type: hex

## Security Best Practices

1. **Never commit the secret key** to version control
2. **Use strong, unique secret keys** for production environments
3. **Rotate secret keys** periodically
4. **Use different keys** for different environments (dev, staging, prod)
5. **Store secret keys** in secure vaults (AWS Secrets Manager, HashiCorp Vault, etc.)

## Environment-Specific Configurations

For multiple environments, use Spring profiles:

**application-dev.properties:**
```properties
spring.datasource.password=ENC(dev-encrypted-password)
```

**application-prod.properties:**
```properties
spring.datasource.password=ENC(prod-encrypted-password)
```

Run with specific profile:
```bash
java -Dspring.profiles.active=prod \
     -Djasypt.encryptor.password=prod-secret-key \
     -jar target/wholeSaleEngineering-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

### Error: "Unable to decrypt property"
- Ensure `JASYPT_ENCRYPTOR_PASSWORD` environment variable is set correctly
- Verify the encrypted password in `application.properties` matches the one generated with the correct secret key
- Check that the secret key used matches the one used to encrypt the password

### Error: "Encryptor not found"
- Ensure Jasypt dependency is in pom.xml: `jasypt-spring-boot-starter`
- Verify `JasyptConfig.java` is in the correct package for component scanning

### Password Not Decrypting
1. Verify the secret key is correct
2. Check that the encrypted value hasn't been corrupted
3. Re-encrypt the password with the correct secret key

## References
- Jasypt Spring Boot: https://github.com/ulisesbocchio/jasypt-spring-boot
- Spring Cloud Config: https://cloud.spring.io/spring-cloud-config/
- Jasypt Documentation: https://www.jasypt.org/

## Support
For questions or issues, refer to the Jasypt documentation or Spring Boot documentation on property encryption.
