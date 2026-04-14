package DSL;
import org.openqa.selenium.WebDriver;


import pages.HeaderComponent;
import pages.HomePage;

public class HomeDSL {
	
	private HeaderComponent header;
	private HomePage homePage;
	
	public HomeDSL(WebDriver driver) {
        this.header = new HeaderComponent(driver);
        this.homePage = new HomePage(driver);
    }
	
	
	
}