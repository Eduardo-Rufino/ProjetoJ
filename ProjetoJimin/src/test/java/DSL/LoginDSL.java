package DSL;
import org.openqa.selenium.WebDriver;


import pages.HeaderComponent;
import pages.HomePage;
import pages.LoginPage;

public class LoginDSL {
	
	private HeaderComponent header;
	private LoginPage loginPage;
	private HomePage homePage;
	
	public LoginDSL(WebDriver driver) {
        this.header = new HeaderComponent(driver);
        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
    }
	
	public void mustLogin(String email, String password) {
		header.expandMyAccount();
		header.goToLogin();
		loginPage.waitForLoginPage();
		loginPage.login(email, password);
		loginPage.waitForTitle("My Account");
	}
	
	public void mustRegister(String fisrtName, String lastName, String email, String password) {
		header.expandMyAccount();
		header.goToRegister();
		loginPage.waitForRegisterPage();
		loginPage.register(fisrtName, lastName, email, password);
		loginPage.waitForTitle("Your Account Has Been Created!");
	}
	
	public void mustLogin() {
	    mustLogin("teste@teste.com", "teste123");
	}
}