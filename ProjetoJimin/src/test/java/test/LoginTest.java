package test;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		
		dsl.expandeDropDown("My Account");
		
		dsl.clicaDropdown("Login");
		//dsl.clicaLink("Login");
		dsl.comparaValores("Account Login", driver.getTitle());
		dsl.logaSistema("input-email", "input-password","Login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleContains("My Account"));
		dsl.voltaPaginaInicial();
		dsl.comparaValores("img-fluid", dsl.encontraCarrossel().getAttribute("Class")); 
	}
	
	@Test
	public void deveVoltarTelaInicial() {
		dsl.voltaPaginaInicial();
	}
	
	@Test
	public void deveVerificarCarrossel() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String primeiraImagem = dsl.retornaImagemCarrossel("carousel-banner-0");
		wait.until(driver1 -> {
			String imagemAtual = dsl.retornaImagemCarrossel("carousel-banner-0");
			return !imagemAtual.equals(primeiraImagem);
		});
		String segundaImagem= dsl.retornaImagemCarrossel("carousel-banner-0");
		
		dsl.comparaValoresDiferentes(primeiraImagem, segundaImagem);
		
		//Assert.assertEquals("carousel slide", dsl.encontraCarrossel().getAttribute("class")); 
	}
	
	@Test
	public void deveValidarTitulo() {
		dsl.comparaValores("Your Store", dsl.retornaTitulo());
	}
	
	@Test
	public void devevalidarMenuSuperior() {
		page.logaSistema(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.titleIs("Your Store"));
		dsl.clicaTelefone();
		dsl.comparaValores("Contact Us", dsl.retornaTitulo());
		dsl.clicaWishList();
		dsl.comparaValores("My Wishlist", dsl.retornaTitulo());
		dsl.clicaCarrinho();
		dsl.comparaValores("Shopping Cart", dsl.retornaTitulo());
		dsl.clicaMoeda();
		dsl.trocaMoeda("Dolar");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("div.dropdown a strong"), "$"
			 )
		);
		dsl.comparaValores("$", dsl.retornaMoeda());
		dsl.clicaMoeda();
		dsl.trocaMoeda("Libra");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
					 By.cssSelector("div.dropdown a strong"), "£"
			 )
		);
		dsl.comparaValores("£", dsl.retornaMoeda());
		dsl.clicaMoeda();
		dsl.trocaMoeda("Euro");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
					 By.cssSelector("div.dropdown a strong"), "€"
			 )
		);
		dsl.comparaValores("€", dsl.retornaMoeda());
	}
	
	@Test
	public void devevalidarCategoriasPrincipais() throws InterruptedException {
		dsl.validaTodosOsDropdowns();
	}
	
	
	@Test
	public void devetestarBotaoCarrossel() {
	    // Captura a primeira imagem
	    String primeiraImagem = dsl.retornaImagemCarrossel("carousel-banner-0");

	    // Clica na seta do carrossel
	    dsl.clicaCarrossel("carousel-banner-0");

	    // Espera até que a imagem mude
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(driver -> {
	        String novaImagem = dsl.retornaImagemCarrossel("carousel-banner-0");
	        return !novaImagem.equals(primeiraImagem); // retorna true quando a imagem mudou
	    });

	    // Captura a segunda imagem
	    String segundaImagem = dsl.retornaImagemCarrossel("carousel-banner-0");

	    // Valida que são diferentes
	    dsl.comparaValoresDiferentes(primeiraImagem, segundaImagem);
	}
	
	@Test
	public void devetetsarProdutoExistente() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("Your Store"));
		dsl.pesquisaProduto("iMac");
		wait.until(ExpectedConditions.titleContains("Search"));
		String produto = driver.findElement(By.cssSelector(".content .description a")).getText();
		
		dsl.comparaValores("iMac", produto);		
	}
	
	@Test
	public void devetestarProdutoInexistente() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("Your Store"));
		dsl.pesquisaProduto("Prduto que nao existe");
		wait.until(ExpectedConditions.titleContains("Search"));
		String texto = driver.findElement(By.cssSelector("#product-search p")).getText();
		
		dsl.comparaValores("There is no product that matches the search criteria.", texto);
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
