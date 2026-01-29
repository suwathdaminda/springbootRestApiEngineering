package au.com.anz.wholeSaleEngineering.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Utility class to encrypt passwords using Jasypt
 * Usage: java au.com.anz.wholeSaleEngineering.util.JasyptPasswordEncryptor
 * @author Suwath Mihindukulasooriya
 */
public class JasyptPasswordEncryptor {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java JasyptPasswordEncryptor <password> <secret-key>");
            System.out.println("Example: java JasyptPasswordEncryptor daminda@77 my-encryption-key");
            return;
        }

        String passwordToEncrypt = args[0];
        String secretKey = args[1];

        String encryptedPassword = encryptPassword(passwordToEncrypt, secretKey);

        System.out.println("Original Password: " + passwordToEncrypt);
        System.out.println("Secret Key: " + secretKey);
        System.out.println("Encrypted Password: " + encryptedPassword);
        System.out.println("\nUpdate your application.properties with:");
        System.out.println("spring.datasource.password=ENC(" + encryptedPassword + ")");
        System.out.println("\nAnd set environment variable before running:");
        System.out.println("export JASYPT_ENCRYPTOR_PASSWORD=" + secretKey);
    }

    public static String encryptPassword(String password, String secretKey) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secretKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setStringOutputType("hex");
        encryptor.setConfig(config);
        return encryptor.encrypt(password);
    }
}
