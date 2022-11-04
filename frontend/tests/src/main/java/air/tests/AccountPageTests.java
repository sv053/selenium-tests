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
import java.util.Iterator;
import java.util.List;

public class AccountPageTests {

    private static WebDriver driver;

    @BeforeAll
    public static void init() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://localhost:3000/#/account/sign-in");

    }

    @AfterAll
    public static void shutDown() {
        driver.quit();
    }

    @Test
    public void checkSignInFormWithWrongCreds() {

        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("cart-link")).click();
        driver.findElement(By.id("auth-form")).submit();
    }

    @Test
    public void linkToSignUp() {

        driver.findElement(By.className("forgot-password"))
                .click();
    }

    @Test
    public void linkToRestoreThePassword() {

        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("cart-link")).click();
        driver.findElement(By.id("auth-form")).submit();
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

