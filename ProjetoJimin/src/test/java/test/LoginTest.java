package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import base.BaseTest;
import pages.LoginPage;
import DSL.LoginDSL;

public class LoginTest extends BaseTest {
	
	private LoginDSL loginDSL;
	private LoginPage loginPage;
	
	@Before
	public void setupTest() {
		loginDSL = new LoginDSL(driver);
		loginPage = new LoginPage(driver);
	}
	
	@Test
	public void shouldLoginSuccessfullyWithValidCredentials() {		
		loginDSL.loginWithDefaultUser();
		loginPage.waitForTitle("My Account");
		Assert.assertEquals("My Account", driver.getTitle());
	}
	
	@Test
	public void shouldNotLoginWithInvalidCredentials() {		
		loginDSL.login("teste@teste.com", "WrongPassword");
		Assert.assertTrue(loginPage.getLoginErrorMessage().contains("No match"));
	}
	
	@Test
	public void shouldRegisterSuccessfullyWithValidCredentials() {		
		loginDSL.register("First", "Last", "teste35@teste.com", "teste123");
		loginPage.waitForTitle("Your Account Has Been Created!");
		Assert.assertEquals("Your Account Has Been Created!", loginPage.getTitle());
	}	
	
	@Test
	public void shouldNotRegisterWithEmailAlreadyRegistered() {
		loginDSL.register("first", "last", "teste@teste.com", "teste234");
		Assert.assertTrue(loginPage.getLoginErrorMessage().contains("already registered!"));
	}
	
	@Test
	public void shouldRecoverPasswordSuccessfullyWithValidEmail() {
		loginDSL.recoverPassword();
		loginPage.waitForTitle("Account Login");
		Assert.assertTrue(loginPage.getPasswordRecoverySuccessMessage().contains("success"));
	}
	
}