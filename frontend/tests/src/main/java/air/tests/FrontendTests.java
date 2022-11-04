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
    public void clickNavBrandMenuButtonIsDisplayed() {

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
    public void clickNavBarMainLinkButtonEnabled() {

        WebElement element = driver.findElement(By.id("main-link"));
        assertTrue(element.isEnabled());
    }

    @Test
    public void clickNavBarAccountLinkButtonEnabled() {

        WebElement element = driver.findElement(By.id("account-link"));
        assertTrue(element.isEnabled());
    }

    @Test
    public void clickNavBarCartLinkButtonEnabled() {

        WebElement element = driver.findElement(By.id("cart-link"));
        assertTrue(element.isEnabled());
    }

    @Test
    public void clickNavBarCartLinkButtonDisplayed() {

        WebElement element = driver.findElement(By.id("cart-link"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void clickNavBarAccountLinkButtonDisplayed() {

        WebElement element = driver.findElement(By.id("account-link"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void clickNavBarMainLinkButtonDisplayed() {

        WebElement element = driver.findElement(By.id("main-link"));
        assertTrue(element.isDisplayed());
    }
}

