package au.com.anz.wholeSaleEngineering.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jasypt Configuration for Password Encryption
 * Requires environment variable: JASYPT_ENCRYPTOR_PASSWORD
 * @author Suwath Mihindukulasooriya
 */
@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.password:my-secret-key}")
    private String encryptorPassword;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptorPassword);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setStringOutputType("hex");
        encryptor.setConfig(config);
        return encryptor;
    }
}
