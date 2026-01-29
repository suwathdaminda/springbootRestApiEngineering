package au.com.anz.wholeSaleEngineering;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for WholeSaleEngrApp
 * Refactored to use JUnit 5
 * 
 * @author Suwath Mihindukulasooriya
 * @author Refactored for Java 21
 * @date 2024
 */
@SpringBootTest
class WholeSaleEngrAppTest {

    @Test
    void contextLoads() {
        // This test will fail if the application context cannot start
        assertThat(true).isTrue();
    }

    @Test
    void testApplicationStartup() {
        // Test that the application can be instantiated
        WholeSaleEngrApp app = new WholeSaleEngrApp();
        assertThat(app).isNotNull();
    }
}
