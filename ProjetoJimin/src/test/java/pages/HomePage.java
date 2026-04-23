package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage{
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	private By homePageButton = By.cssSelector("#logo a");
	private By activeCarouselImage = By.cssSelector("#carousel-banner-0 .carousel-item.active img");
	private By homeButton = By.cssSelector("#logo a");
	
	public void returnToHomePage() {
		click(homePageButton);
	}
	
	public String getCarouselImageSrc() {
		return waitForVisibility(activeCarouselImage).getAttribute("src");
	}
	
	public void waitForCarouselImageToChange(String previousImage) {
		wait.until(driver -> {
			String currentImage = getCarouselImageSrc();
			return !currentImage.equals(previousImage);
		});
	}
	
	public List<WebElement> getMainCategories() {
	    return waitForAllVisible(By.cssSelector(".nav-item.dropdown > a"));
	}
	
	public void clickCategory(WebElement category) {
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", category);
	}
	
	public WebElement getOpenedMenu() {
	    return waitForVisibility(By.cssSelector(".dropdown-menu.show"));
	}
	
	public void clickAnySubCategory() {
	    List<WebElement> subCategories = waitForAllVisible(By.cssSelector(".dropdown-menu.show a"));

	    if (!subCategories.isEmpty()) {
	        subCategories.get(0).click();
	    }
	}
	
	public void goToHomePage() {
	    click(homeButton);
	}
	
	public void waitForMenu() {
		waitForVisibility(By.cssSelector(".dropdown-menu.show"));
	}
	
}