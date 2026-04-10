package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage{

	public HeaderComponent(WebDriver driver) {
		super(driver);
	}
	
	private By myAccountDropdown = By.xpath("//*[text()='My Account ']");
	private By goToLoginButton = By.cssSelector("#top a.dropdown-item[href*='route=account/login']");
	
	
	public void expandMyAccount() {
		click(myAccountDropdown);
	}
	
	public void goToLogin() {
		click(goToLoginButton);
	}
	
	
	
}