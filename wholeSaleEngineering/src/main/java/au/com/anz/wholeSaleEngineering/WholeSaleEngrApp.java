package au.com.anz.wholeSaleEngineering;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Whole Sale Engineering Application provides customer, a user interface 
 * to view accounts over internet login
 * Refactored to use Java 21, Spring Boot 3.x, and PostgreSQL
 * 
 * @author Suwath Mihindukulasooriya
 * @author Refactored for Java 21 with PostgreSQL
 * @date 2024
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableEncryptableProperties
@OpenAPIDefinition(
		info = @Info(
				title = "Wholesale Engineering API",
				version = "2.0",
				description = "RESTful API for managing customer accounts and transactions with PostgreSQL integration",
				contact = @Contact(
						name = "ANZ Wholesale Engineering",
						email = "support@anz.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html"
				)
		)
)
public class WholeSaleEngrApp 
{
	private static final Logger logger = LoggerFactory.getLogger(WholeSaleEngrApp.class);
	
	public static void main(String[] args) {
		logger.info("Starting Wholesale Engineering Application...");
		SpringApplication.run(WholeSaleEngrApp.class, args);
		logger.info("Wholesale Engineering Application started successfully!");
		logger.info("Swagger UI available at: http://localhost:8080/swagger-ui.html");
	}
}
