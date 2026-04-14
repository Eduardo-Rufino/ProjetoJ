package DSL;
import org.openqa.selenium.WebDriver;


import pages.HeaderComponent;
import pages.LoginPage;

public class LoginDSL {
	
	private HeaderComponent header;
	private LoginPage loginPage;
	
	public LoginDSL(WebDriver driver) {
        this.header = new HeaderComponent(driver);
        this.loginPage = new LoginPage(driver);
    }
	
	public void login(String email, String password) {
		header.expandMyAccount();
		header.goToLogin();
		loginPage.waitForLoginPage();
		loginPage.login(email, password);
		loginPage.waitForMyAccountPage();
	}
	
	public void loginWithDefaultUser() {
	    login("teste@teste.com", "teste123");
	}
	
	public void register(String firstName, String lastName, String email, String password) {
		header.expandMyAccount();
		header.goToRegister();
		loginPage.waitForRegisterPage();
		loginPage.register(firstName, lastName, email, password);
	}
	
	public void recoverPassword() {
		header.expandMyAccount();
		header.goToLogin();
		loginPage.waitForLoginPage();
		loginPage.recoverPassword();
	}
	
	public void logout() {
		header.logout();	
	}
	
}