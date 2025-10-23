package test;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
	
	// Loga no site com as informacoes que forem passadas
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
	
	// retorna para a pagina inicial
	@Test
	public void deveVoltarTelaInicial() {
		dsl.voltaPaginaInicial();
	}
	
	// verifica se a imagem do carrossel esta funcionando normalmente e trocando de imagem
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
	
	// Verifica se o titulo da pagina é o esperado.
	@Test
	public void deveValidarTitulo() {
		dsl.comparaValores("Your Store", dsl.retornaTitulo());
	}
	
	//Verifica se todos os menus da barra superior estao funcionando. Clica em cada um deles e verifica se o titulo é o esperado ao trocar de pagina
	@Test
	public void devevalidarMenuSuperior() {
		deveEntrarNoLogin();
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
	
	// Verifica cada uma das categorias principais da pagina e quais subcategorias tem produtos cadastrados
	@Test
	public void devevalidarCategoriasPrincipais() throws InterruptedException {
		dsl.validaTodosOsDropdowns();
	}
	
	//Clica na seta do carrossel para verificar se esta trocando de imagem normalmente ao clicar nele
	@Test
	public void deveTestarBotaoCarrossel() {
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
	
	// Envia um nome de produto e verifica se esse produto se encontra cadastrado no site
	@Test
	public void devetestarProdutoExistente() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("Your Store"));
		dsl.pesquisaProduto("iMac");
		wait.until(ExpectedConditions.titleContains("Search"));
		String produto = driver.findElement(By.cssSelector(".content .description a")).getText();
		
		dsl.comparaValores("iMac", produto);		
	}
	
	// Envia um nome de produto e verifica se esse produto nao se encontra cadastrado no site
	@Test
	public void devetestarProdutoInexistente() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("Your Store"));
		dsl.pesquisaProduto("iMac");
		wait.until(ExpectedConditions.titleContains("Search"));
		String texto = driver.findElement(By.cssSelector("#product-search p")).getText();
		
		dsl.comparaValores("Nao existe nenhum produto com esse nome.", texto);
	}
	
	// Valida se as informacoes principais do produto estao preenchidas, como marca, disponibilidade e nome.
	@Test
	public void deveValidarInformacoesProduto() {
		devetestarProdutoExistente();
		dsl.clicaProduto();
		Assertions.assertTrue(dsl.validaNomeProduto(), "O nome do produto está vazio!");
		Assertions.assertTrue(dsl.validaInformacaoProduto("content", "Brand"), "A marca do produto esta vazia!");
		Assertions.assertTrue(dsl.validaInformacaoProduto("content", "Availability"));
		Assertions.assertTrue(dsl.validaPreçoProduto(), "O preço do produto esta vazio!");
		Assertions.assertTrue(dsl.validaInformacaoProduto("content", "Ex Tax"));		
	}
	
	@Test
	public void deveAdicionarProdutoCarrinho() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveEntrarNoLogin();
		String produtoEsperado = "iMac";
		dsl.pesquisaProduto(produtoEsperado);
		wait.until(ExpectedConditions.titleContains("Search"));
		dsl.adicionaProdutoCarrinho(produtoEsperado);
		dsl.fechaAlertCarrinho();
		dsl.abreDropdownCarrinho();
		if(dsl.validaProdutoCarrinho(produtoEsperado)) {
			System.out.println("Produto encontrado no carrinho!");
		}
		else {
			System.out.println("produto NÃO encontrado no carrinho!");
		}
	}
	
	@Test
	public void deveValidarListaProdutosCarrinho() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveEntrarNoLogin();
		//String produtoEsperado1 = "iMac";
		//String produtoEsperado2 = "Samsung SyncMaster 941BW";
		String[] listaProdutos = {"iMac", "Samsung SyncMaster 941BW"};
		dsl.pesquisaProduto(listaProdutos[0]);
		wait.until(ExpectedConditions.titleContains("Search"));
		dsl.adicionaProdutoCarrinho(listaProdutos[0]);
		dsl.fechaAlertCarrinho();
		dsl.pesquisaProduto(listaProdutos[1]);
		wait.until(ExpectedConditions.titleContains("Search"));
		dsl.adicionaProdutoCarrinho(listaProdutos[1]);
		dsl.fechaAlertCarrinho();
		dsl.abreDropdownCarrinho();
		dsl.validaListaProdutosCarrinho(listaProdutos);
	}
	
	@Test
	public void deveValidarRemocaoItemCarrinho() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveAdicionarProdutoCarrinho();
		dsl.removeItemCarrinho();
		dsl.fechaAlertCarrinho();
		dsl.abreDropdownCarrinho();
		wait.until(driver -> dsl.retornaString(By.cssSelector("#cart .dropdown-menu.dropdown-menu-end.p-2.show li")));
		dsl.validaCarrinhoVazio();
	}
	
	@Test
	public void deveAtualizarQuantidadeCarrinho() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveAdicionarProdutoCarrinho();
		dsl.entraCarrinho();
		dsl.alteraQuantidadeProdutosCarrinho("2");
		dsl.atualizaQuantidadeCarrinho();
		dsl.fechaAlertCarrinho();
	}
	
	@Test
	public void teste() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveAdicionarProdutoCarrinho();
		
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
