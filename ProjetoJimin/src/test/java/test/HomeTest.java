package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import base.BaseTest;
import pages.HomePage;
import DSL.LoginDSL;

public class HomeTest extends BaseTest {
	
	private LoginDSL loginDSL;
	private HomePage homePage;
	
	@Before
	public void setupTest() {
		loginDSL = new LoginDSL(driver);
		homePage = new HomePage(driver);
	}
	
	@Test
	public void shouldValidateLoadingOfTheHomepage() {
		
	}
	
}