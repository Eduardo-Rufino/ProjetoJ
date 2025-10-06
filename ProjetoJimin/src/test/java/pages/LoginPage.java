package pages;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import DSL.DSL;


public class LoginPage {

	private WebDriver driver;
	
	private DSL dsl;
	
	public LoginPage(WebDriver driver) {
		dsl = new DSL(driver);
	}

	public void logaSistema(WebDriver driver) {
		this.driver = driver;
		dsl.expandeDropDown("My Account");		
		dsl.clicaDropdown("Login");
		//dsl.clicaLink("Login");
		Assert.assertEquals("Account Login", driver.getTitle());
		dsl.logaSistema("input-email", "input-password","Login");
		dsl.voltaPaginaInicial();
	}
	
	
	/*
	@Test
	public void clicaLogin() {
		dsl.clicaSpan("d-none d-lg-inline");
		dsl.clicaLink("http://localhost/opencart/index.php?route=account/login&language=en-gb");
		Assert.assertEquals("Account Login", driver.findElement(By.tagName("title")));
	}
	*/
}
