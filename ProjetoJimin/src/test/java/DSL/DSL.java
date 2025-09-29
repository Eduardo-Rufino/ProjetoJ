package DSL;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DSL {
	
	private WebDriver driver;

	public DSL(WebDriver driver) {
		this.driver = driver;
	}

	public void clicaLink(String link) {
		driver.findElement(By.linkText(link)).click();
	}
	
	public void clicaDropdown(String textoBotao, String textoItem) {
	    // 1️⃣ Clicar no botão que abre o dropdown (pode ser span, div, etc.)
	    WebElement botao = driver.findElement(By.xpath("//*[text()='" + textoBotao + "']"));
	    botao.click();

	    // 2️⃣ Esperar o item dentro do dropdown ficar clicável (pode ser a, li, span, etc.)
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement item = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//*[text()='" + textoItem + "']"))
	    );

	    // 3️⃣ Clicar no item
	    item.click();
	}
	
	public void logaSistema(String idemail, String idSenha, String textoBotao) {
		WebElement email = driver.findElement(By.id(idemail));
		email.sendKeys("teste@teste.com");
		WebElement senha = driver.findElement(By.id(idSenha));
		senha.sendKeys("teste123");
		WebElement botao = driver.findElement(By.xpath("//*[text()='" + textoBotao + "']"));
		botao.click();
	}
	
	
	
}
