package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage{

	public HeaderComponent(WebDriver driver) {
		super(driver);
	}
	
	private By myAccountDropdown = By.cssSelector("#top .col.text-end .dropdown-toggle");
	private By goToLoginButton = By.cssSelector("#top a.dropdown-item[href*='route=account/login']");
	private By goToRegisterButton = By.cssSelector("#top a.dropdown-item[href*='route=account/register");
	
	
	public void expandMyAccount() {
		click(myAccountDropdown);
	}
	
	public void goToLogin() {
		click(goToLoginButton);
	}
	
	public void goToRegister() {
		click(goToRegisterButton);
	}
	
}