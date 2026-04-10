package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	private String standardEmail = "teste@teste.com";
	private String standardPassword = "teste123";
	private By loginButton = By.cssSelector("#form-login .text-end .btn.btn-primary");
	private By emailInput = By.id("input-email");
	private By passwordInput = By.id("input-password");
	
	public void login() {
		write(emailInput, standardEmail);
		write(passwordInput, standardPassword);
		click(loginButton);
	}
	
	public void login(String email, String password) {
		write(emailInput, email);
		write(passwordInput, password);
		click(loginButton);
	}
	
	public void waitForLoginPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
	    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
	}
	
}
	
	
	
	
	/*
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
	
	
	--------------------------------------------------------------------------------------------------------
	@Test
	public void clicaLogin() {
		dsl.clicaSpan("d-none d-lg-inline");
		dsl.clicaLink("http://localhost/opencart/index.php?route=account/login&language=en-gb");
		Assert.assertEquals("Account Login", driver.findElement(By.tagName("title")));
	}
	*/

