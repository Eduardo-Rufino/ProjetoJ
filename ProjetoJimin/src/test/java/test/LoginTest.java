package test;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTest;

import DSL.LoginDSL;

public class LoginTest extends BaseTest {
	
	@Test
	public void mustLoginSuccessfully() {
		LoginDSL loginDSL = new LoginDSL(driver);
		
		loginDSL.mustLogin();		
		Assert.assertEquals("My Account", driver.getTitle());
	}
	
	@Test
	public void mustRegisterSucceefully() {
		LoginDSL loginDSL = new LoginDSL(driver);
		
		loginDSL.mustRegister("First", "Last", "teste33@teste.com", "teste123");
		Assert.assertEquals("Your Account Has Been Created!", driver.getTitle());
	}
	
}