package base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    protected WebDriver driver;
    protected String url = "http://localhost/opencart/";

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}