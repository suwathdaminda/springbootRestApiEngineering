package au.com.anz.wholeSaleEngineering.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Utility class to encrypt passwords for application.properties
 * Run this class to generate encrypted password
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
public class JasyptPasswordEncryptor {

    public static void main(String[] args) {
        String password = "daminda@77";
        String secretKey = "wholesale_secret_key";
        
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        config.setPassword(secretKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        
        encryptor.setConfig(config);
        
        String encryptedPassword = encryptor.encrypt(password);
        System.out.println("Original Password: " + password);
        System.out.println("Encrypted Password: ENC(" + encryptedPassword + ")");
        System.out.println("\nAdd this to application.properties:");
        System.out.println("spring.datasource.password=ENC(" + encryptedPassword + ")");
        
        // Verify decryption
        String decrypted = encryptor.decrypt(encryptedPassword);
        System.out.println("\nDecrypted Password (verification): " + decrypted);
        System.out.println("Encryption successful: " + password.equals(decrypted));
    }
}
