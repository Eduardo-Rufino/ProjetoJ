package test;



import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import base.BaseTest;
import pages.HeaderComponent;
import pages.HomePage;
import DSL.HomeDSL;
import DSL.LoginDSL;

public class HomeTest extends BaseTest {
	
	private LoginDSL loginDSL;
	private HomeDSL homeDSL;
	private HomePage homePage;
	private HeaderComponent headerComponent;
	
	@Before
	public void setupTest() {
		loginDSL = new LoginDSL(driver);
		homeDSL = new HomeDSL(driver);
		homePage = new HomePage(driver);
		headerComponent = new HeaderComponent(driver);
	}
	
	@Test
	public void shouldRotateCarouselImages() {
		String firstImage = homePage.getCarouselImageSrc();
		homePage.waitForCarouselImageToChange(firstImage);
		String currentImage = homePage.getCarouselImageSrc();
		
		Assert.assertNotNull(firstImage);
		Assert.assertNotEquals(firstImage, currentImage);
	}
	
	@Test
	public void shouldLoadHomeWithCorrectTitle() {
		homePage.waitForTitle("Your Store");
		Assert.assertEquals("Your Store", homePage.getTitle());
	}
	
	@Test
	public void shouldValidateHeaderMenu() {
		loginDSL.loginWithDefaultUser();
		homePage.waitForTitle("Your Store");
		headerComponent.goToContactUs();
		Assert.assertEquals("Contact Us", homePage.getTitle());
		headerComponent.goToWishList();
		Assert.assertEquals("My Wishlist", homePage.getTitle());
		headerComponent.goToCart();
		Assert.assertEquals("Shopping Cart", homePage.getTitle());
	}
	
	@Test
	public void shouldNavigateThroughAllCategories() {
	    List<String> pages = homeDSL.navigateThroughAllCategories();

	    for (String title : pages) {
	        Assert.assertFalse(title.isEmpty());
	    }
	}
	
	
}