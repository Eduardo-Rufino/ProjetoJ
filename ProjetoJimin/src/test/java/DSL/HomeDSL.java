package DSL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pages.HeaderComponent;
import pages.HomePage;

public class HomeDSL {
	
	private HeaderComponent header;
	private HomePage homePage;
	
	public HomeDSL(WebDriver driver) {
        this.header = new HeaderComponent(driver);
        this.homePage = new HomePage(driver);
    }
	
	public List<String> navigateThroughAllCategories() {
	    List<String> visitedPages = new ArrayList<>();

	    int totalCategories = homePage.getMainCategories().size();

	    for (int i = 0; i < totalCategories; i++) {
	        List<WebElement> dropdowns = homePage.getMainCategories();
	        WebElement dropdown = dropdowns.get(i);

	        homePage.clickCategory(dropdown);
	        homePage.waitForMenu();
	        homePage.clickAnySubCategory();

	        visitedPages.add(homePage.getTitle());

	        homePage.goToHomePage();
	    }

	    return visitedPages;
	}
	
}