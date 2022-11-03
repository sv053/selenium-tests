package air.tests;

import air.tests.config.WebDriverFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontendTests {
    private static WebDriver driver;

    @BeforeAll
    public static void init() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://localhost:3000/");
    }

    @AfterAll
    public static void shutDown() {
        driver.quit();
    }

    @Test
    public void test() {
        assertEquals(1, 1);
    }
}
