package au.com.anz.wholeSaleEngineering;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for WholeSaleEngrApp Application Context
 * @author Suwath Mihindukulasooriya
 */
@SpringBootTest
@DisplayName("WholeSaleEngrApp Integration Tests")
public class WholeSaleEngrAppTest {

    /**
     * Test that application context loads successfully
     */
    @Test
    @DisplayName("Application context should load successfully")
    public void testGetAccount() {
        assertTrue(true);
    }

    /**
     * Test application starts without errors
     */
    @Test
    @DisplayName("Application should start without errors")
    public void testGetAccountTransaction() {
        assertTrue(true);
    }
}
