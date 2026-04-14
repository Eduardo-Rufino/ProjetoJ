package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	private String firstName = "First";
	private String lastName = "Last";
	private String standardEmail = "teste@teste.com";
	private String standardPassword = "teste123";
	private By loginButton = By.cssSelector("#form-login .text-end .btn.btn-primary");
	private By passwordButton = By.cssSelector("#column-right .list-group.mb-3 a[href*='route=account/password']");
	private By emailInput = By.id("input-email");
	private By passwordInput = By.id("input-password");
	private By firstNameInput = By.id("input-firstname");
	private By lastNameInput = By.id("input-lastname");
	private By privacyPoliceCheck = By.cssSelector("#form-register .text-end .form-check-input");
	private By registerButton = By.cssSelector("#form-register .btn.btn-primary");
	private By loginErrorMessage = By.cssSelector("#alert .alert.alert-danger.alert-dismissible");
	private By forgottenPasswordButton = By.cssSelector("#form-login a[href*='route=account/forgotten']");
	private By recoverPasswordButton = By.cssSelector("#form-forgotten .text-end .btn.btn-primary");
	private By recoveryPasswordAlert = By.cssSelector("#account-login .alert");
	private By logoutMessage = By.xpath("//div[@id='content']//p[contains(text(),'logged off')]");
	private By myAccountDropdown = By.cssSelector("#top .col.text-end .dropdown-toggle");
	
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
	
	public void register() {
		write(firstNameInput, firstName);
		write(lastNameInput, lastName);
		write(emailInput, standardEmail);
		write(passwordInput, standardPassword);
		click(registerButton);
		click(privacyPoliceCheck);
	}
	
	public void register(String firstName, String lastName, String email, String password) {
		write(firstNameInput, firstName);
		write(lastNameInput, lastName);
		write(emailInput, email);
		write(passwordInput, password);
		click(privacyPoliceCheck);
		waitElementSelected(privacyPoliceCheck);
		click(registerButton);
	}
	
	public void recoverPassword() {
		click(forgottenPasswordButton);
		write(emailInput, standardEmail);
		click(recoverPasswordButton);
	}
	
	public void waitForLoginPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
	    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
	}
	
	public void waitForRegisterPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
		wait.until(ExpectedConditions.elementToBeClickable(registerButton));
	}
	
	public void waitForMyAccountPage() {
		wait.until(ExpectedConditions.elementToBeClickable(passwordButton));
	}
	
	public String getLoginErrorMessage() {
		waitForVisibility(loginErrorMessage);
		return getText(loginErrorMessage);
	}
	
	public String getPasswordRecoverySuccessMessage() {
		waitForVisibility(recoveryPasswordAlert);
		return getText(recoveryPasswordAlert);
	}
	
	public String getLogoutMessage() {
		waitForVisibility(logoutMessage);
		return getText(logoutMessage);
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

