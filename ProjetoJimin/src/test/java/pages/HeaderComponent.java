package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage{

	public HeaderComponent(WebDriver driver) {
		super(driver);
	}
	
	private By myAccountDropdown = By.cssSelector("#top .col.text-end .dropdown-toggle");
	private By goToLoginButton = By.cssSelector("#top a.dropdown-item[href*='route=account/login']");
	private By goToRegisterButton = By.cssSelector("#top a.dropdown-item[href*='route=account/register']");
	private By logoutButton = By.cssSelector("#top a.dropdown-item[href*='route=account/logout']");
	private By contactUsButton = By.xpath("//li[@class='list-inline-item']//span[@class='d-none d-lg-inline']");
	private By wishListButton = By.id("wishlist-total");
	private By cartButton = By.xpath("//li[@class='list-inline-item']//a[@title='Shopping Cart']");
	
	public void expandMyAccount() {
		click(myAccountDropdown);
	}
	
	public void goToLogin() {
		click(goToLoginButton);
	}
	
	public void goToRegister() {
		click(goToRegisterButton);
	}
	
	public void logout() {
		click(myAccountDropdown);
		waitForVisibility(logoutButton).click();
	}
	
	public void goToContactUs() {
		click(contactUsButton);
	}
	
	public void goToWishList() {
		click(wishListButton);
	}
	
	public void goToCart() {
		click(cartButton);
	}
}