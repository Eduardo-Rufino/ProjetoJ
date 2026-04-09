import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    protected WebDriver driver;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost/opencart/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}