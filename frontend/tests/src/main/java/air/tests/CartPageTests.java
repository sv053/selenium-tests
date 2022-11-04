package air.tests;

import air.tests.config.WebDriverFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPageTests {

    private static WebDriver driver;

    @BeforeAll
    public static void init() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://localhost:3000/#/cart");
    }

    @AfterAll
    public static void shutDown() {
        driver.quit();
    }

    @Test
    public void checkTextCenteredLabel() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement returnButton = driver.findElement(By.className("btn-primary"));
        assertEquals(returnButton.getText(), "Back to flights");
    }


}
