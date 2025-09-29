package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import DSL.DSL;
import pages.LoginPage;

public class LoginTest {
	
	String url = "http://localhost/opencart/";
	
	private WebDriver driver;
	
	private LoginPage page;
	
	private DSL dsl;
	
	@Before
	public void inicializaTeste() {
		driver = new FirefoxDriver();
		page = new LoginPage(driver);
		dsl = new DSL(driver);
		driver.manage().window().maximize();
		driver.get(url);
	}
	
	@After
	public void finalizaTeste() {
		//driver.quit();
	}
	
	@Test
	public void deveEntrarNoLogin() {
		//page.clicaLogin();
		
		dsl.clicaDropdown("My Account", "Login");
		//dsl.clicaLink("Login");
		Assert.assertEquals("Account Login", driver.getTitle());
		dsl.logaSistema("input-email", "input-password","Login");
	}
	
	/*@Test
	public void teste() {
	    WebElement campo = driver.findElement(By.name("search"));
	    campo.click();          // foca no campo
	    campo.clear();          // limpa texto antigo
	    campo.sendKeys("teste"); 
	    Assert.assertEquals("teste", campo.getAttribute("value"));
	}
	*/
}
