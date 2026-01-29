package au.com.anz.wholeSaleEngineering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Whole Sale Engineering Application provides customer, a user interface 
 * to view accounts and transactions over internet login
 * 
 * Technology Stack:
 * - Java 21
 * - Spring Boot 3.0
 * - PostgreSQL Database
 * - Spring Data JPA
 * - OpenAPI 3.0 / Swagger UI
 * - JUnit 5 and Mockito for testing
 * 
 * @author suwath mihindukulasooriya
 * @date 20/07/2019
 * @modified 2025-01-29
 *
 */
@SpringBootApplication
public class WholeSaleEngrApp {

	private static final Logger logger = LoggerFactory.getLogger(WholeSaleEngrApp.class);

	public static void main(String[] args) {
		logger.info("Starting WholeSale Engineering Application...");
		SpringApplication.run(WholeSaleEngrApp.class, args);
		logger.info("WholeSale Engineering Application started successfully!");
	}

	/**
	 * Configure OpenAPI 3.0 documentation
	 * @return OpenAPI configuration
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		logger.debug("Configuring OpenAPI 3.0 documentation");
		return new OpenAPI()
				.info(new Info()
						.title("WholeSale Engineering API")
						.version("1.0.0")
						.description("RESTful API for managing accounts and transactions with PostgreSQL database integration")
						.contact(new Contact()
								.name("Suwath Mihindukulasooriya")
								.email("contact@wholesale-engineering.com")));
	}
}
