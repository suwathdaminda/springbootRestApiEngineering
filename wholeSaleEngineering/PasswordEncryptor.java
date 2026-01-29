import org.jasypt.util.password.BasicPasswordEncryptor;

public class PasswordEncryptor {
    public static void main(String[] args) {
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        
        String rawPassword = "daminda@77";
        String encryptedPassword = encryptor.encryptPassword(rawPassword);
        
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Encrypted Password: " + encryptedPassword);
        System.out.println("\nUse in application.properties:");
        System.out.println("spring.datasource.password=ENC(" + encryptedPassword + ")");
    }
}
