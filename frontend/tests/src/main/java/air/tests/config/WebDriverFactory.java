package air.tests.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {

    public static WebDriver getDriver() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_win.exe");
            return new ChromeDriver();
        } else if (os.toLowerCase().contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_linux");
            return new ChromeDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_mac");
            return new ChromeDriver();
        }
    }
}
