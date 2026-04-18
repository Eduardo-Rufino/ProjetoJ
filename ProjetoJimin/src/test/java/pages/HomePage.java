package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	private By homePageButton = By.cssSelector("#logo a");
	private By activeCarouselImage = By.cssSelector("#carousel-banner-0 .carousel-item.active img");
	
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
	
}