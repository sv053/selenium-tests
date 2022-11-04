package air.tests;

import air.tests.config.WebDriverFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTests {
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
    @Test
    public void findDamagedlinks() {

        String homePage = "http://localhost:3000/";
        String url = "";
        HttpURLConnection huc = null;
        int respCode = 200;

        List<WebElement> links = driver.findElements(By.tagName("a"));

        Iterator<WebElement> it = links.iterator();

        while (it.hasNext()) {

            url = it.next().getAttribute("href");

            System.out.println(url);

            if (url == null || url.isEmpty()) {
                continue;
            }

            if (!url.startsWith(homePage)) {
                continue;
            }

            try {
                huc = (HttpURLConnection) (new URL(url).openConnection());

                huc.setRequestMethod("HEAD");

                huc.connect();

                respCode = huc.getResponseCode();

                if (respCode >= 400) {
                    System.out.println(url + " is a broken link");
                } else {
                    System.out.println(url + " is a valid link");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

