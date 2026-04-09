package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void scrollToElement(By by) {      	
    	WebElement element = waitForVisibility(by);
    	
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});",
            element
        );
        
        waitForClickable(by);
    }
    
    protected void click(By by) {
    	waitForClickable(by).click();
    }
    
    protected void write(By by, String text) {
    	WebElement  input = waitForVisibility(by);
    	input.clear();
    	input.sendKeys(text);
    }
    
    protected WebElement waitForVisibility(By by) {    	
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(by));       	 
    }
    
    protected WebElement waitForClickable(By by) {
    	return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    
    protected String getText(By by) {
        return waitForVisibility(by).getText();
    }
}