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
    public void checkNavBrandMenuButtonIsDisplayed() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement submitButton = driver.findElement(By.className("navbar-brand"));
        assertTrue(submitButton.isDisplayed());
    }

    @Test
    public void checkTitleLabel() {
        String title = driver.getTitle();
        assertEquals("Air Tickets", title);
    }

    @Test
    public void checkNavBarMainLinkButtonEnabled() {

        WebElement element = driver.findElement(By.id("main-link"));
        assertTrue(element.isEnabled());
    }

    @Test
    public void checkNavBarAccountLinkButtonEnabled() {

        WebElement element = driver.findElement(By.id("account-link"));
        assertTrue(element.isEnabled());
    }

    @Test
    public void checkNavBarCartLinkButtonEnabled() {

        WebElement element = driver.findElement(By.id("cart-link"));
        assertTrue(element.isEnabled());
    }

    @Test
    public void checkNavBarCartLinkButtonDisplayed() {

        WebElement element = driver.findElement(By.id("cart-link"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void checkNavBarAccountLinkButtonDisplayed() {

        WebElement element = driver.findElement(By.id("account-link"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void checkNavBarMainLinkButtonDisplayed() {

        WebElement element = driver.findElement(By.id("main-link"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void checkNavBarMainLinkButtonSelected() {

        WebElement element = driver.findElement(By.id("main-link"));
        assertTrue(element.isSelected());
    }

    @Test
    public void checkNavBarAccountLinkButtonSelected() {

        WebElement element = driver.findElement(By.id("account-link"));
        assertTrue(element.isSelected());
    }

    @Test
    public void checkNavBarCartLinkButtonSelected() {

        WebElement element = driver.findElement(By.id("cart-link"));
        assertTrue(element.isSelected());
    }
}

