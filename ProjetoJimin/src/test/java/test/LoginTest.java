package test;

import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import DSL.DSL;
import pages.LoginPage;

public class LoginTest {
	
	String url = "http://localhost/opencart/";
	String primeiroNome = "Teste";
	String sobreNome = "Teste";
	String novoEmail = "modelo4@modelo.com";
	String novaSenha = "modelo";
	
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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//page.clicaLogin();		
		dsl.expandeDropDown("My Account");		
		dsl.clicaBotaoGenericoCssSelector("#top a.dropdown-item[href*='route=account/login']");
		//dsl.clicaLink("Login");
		dsl.comparaStrings("Account Login", driver.getTitle());
		dsl.logaSistema("teste@teste.com", "teste123","Login");		
		dsl.aguardaTitulo("My Account");
		dsl.voltaPaginaInicial();
		dsl.comparaStrings("img-fluid", dsl.encontraCarrossel().getAttribute("Class")); 
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
		
		dsl.comparaStringsDiferentes(primeiraImagem, segundaImagem);
		
		//Assert.assertEquals("carousel slide", dsl.encontraCarrossel().getAttribute("class")); 
	}
	
	// Verifica se o titulo da pagina é o esperado.
	@Test
	public void deveValidarTitulo() {
		dsl.comparaStrings("Your Store", dsl.retornaTitulo());
	}
	
	//Verifica se todos os menus da barra superior estao funcionando. Clica em cada um deles e verifica se o titulo é o esperado ao trocar de pagina
	@Test
	public void devevalidarMenuSuperior() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		dsl.aguardaTitulo("Your Store");
		dsl.clicaTelefone();
		dsl.comparaStrings("Contact Us", dsl.retornaTitulo());
		dsl.clicaWishList();
		dsl.comparaStrings("My Wishlist", dsl.retornaTitulo());
		dsl.clicaCarrinho();
		dsl.comparaStrings("Shopping Cart", dsl.retornaTitulo());
		dsl.clicaMoeda();
		dsl.trocaMoeda("Dolar");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("div.dropdown a strong"), "$"
			 )
		);
		dsl.comparaStrings("$", dsl.retornaMoeda());
		dsl.clicaMoeda();
		dsl.trocaMoeda("Libra");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
					 By.cssSelector("div.dropdown a strong"), "£"
			 )
		);
		dsl.comparaStrings("£", dsl.retornaMoeda());
		dsl.clicaMoeda();
		dsl.trocaMoeda("Euro");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
					 By.cssSelector("div.dropdown a strong"), "€"
			 )
		);
		dsl.comparaStrings("€", dsl.retornaMoeda());
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
	    dsl.comparaStringsDiferentes(primeiraImagem, segundaImagem);
	}
	
	// Envia um nome de produto e verifica se esse produto se encontra cadastrado no site
	@Test
	public void devetestarProdutoExistente() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		dsl.aguardaTitulo("Your Store");
		dsl.pesquisaProduto("iMac");
		wait.until(ExpectedConditions.titleContains("Search"));
		String produto = driver.findElement(By.cssSelector(".content .description a")).getText();
		
		dsl.comparaStrings("iMac", produto);		
	}
	
	// Envia um nome de produto e verifica se esse produto nao se encontra cadastrado no site
	@Test
	public void devetestarProdutoInexistente() {
		deveEntrarNoLogin();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		dsl.aguardaTitulo("Your Store");
		dsl.pesquisaProduto("Produto Invalido");
		wait.until(ExpectedConditions.titleContains("Search"));
		String texto = driver.findElement(By.cssSelector("#product-search p")).getText();
		
		dsl.comparaStrings("There is no product that matches the search criteria.", texto);
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
		dsl.adicionaProdutoCarrinho();
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
		dsl.adicionaProdutoCarrinho();
		dsl.fechaAlertCarrinho();
		dsl.pesquisaProduto(listaProdutos[1]);
		wait.until(ExpectedConditions.titleContains("Search"));
		dsl.adicionaProdutoCarrinho();
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
		String quantidadeDesejada = "2";
		deveAdicionarProdutoCarrinho();
		dsl.entraCarrinho();
		//dsl.retornaMoeda();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='output-cart']//table//td[4]")));
		float precoUnitario = Float.parseFloat(driver.findElement(By.xpath("//div[@id='output-cart']//table//td[4]")).getText().replaceAll("[^\\d.]", ""));
		dsl.alteraQuantidadeProdutosCarrinho(quantidadeDesejada);
		dsl.atualizaQuantidadeCarrinho();
		dsl.fechaAlertCarrinho();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
			    "//div[@id='output-cart']//tfoot[@id='checkout-total']" +
			    	    "//tr[td/strong[text()='Total']]//td[2]")));
		float precoTotal = Float.parseFloat(driver.findElement(By.xpath(
			    "//div[@id='output-cart']//tfoot[@id='checkout-total']" +
			    	    "//tr[td/strong[text()='Total']]//td[2]")).getText().replaceAll("[^\\d.]", ""));
		System.out.println(precoUnitario);
		System.out.println(precoTotal);
		System.out.println(Float.parseFloat(quantidadeDesejada));
		dsl.comparaFloat(precoUnitario*Float.parseFloat(quantidadeDesejada), precoTotal);
	}
	
	@Test
	public void deveValidarCompraComCarrinhoVazio() {
		dsl.expandeDropDown("Shopping Cart");
		dsl.clicaBotaoGenericoCssSelector("#checkout-cart .btn.btn-primary");
		dsl.validaSeEstaPaginaInicial();
	}
	
	@Test
	public void deveValidarSubTotalETotal() {
		deveAdicionarProdutoCarrinho();
		dsl.entraCarrinho();
		float subTotal = dsl.retornaValorComBaseColunaAnterior("Sub-Total");
		float ecoTax = dsl.retornaValorComBaseColunaAnterior("Eco Tax (-2.00)");
		float vat = dsl.retornaValorComBaseColunaAnterior("VAT (20%)");
		float totalEsperado = dsl.retornaValorComBaseColunaAnterior("Total");
		float total = dsl.retornaValorTotalComBaseSubTotal(subTotal, ecoTax, vat);
		System.out.println("Total esperado: " + totalEsperado + " Total apresentado: " + total);
		dsl.comparaFloat(totalEsperado, total);
	}
	
	@Test
	public void deveValidarVisualizaçãoCarrinho() {
		dsl.entraCarrinho();
		String titulo = dsl.retornaTitulo();
		dsl.comparaStrings(titulo, "Shopping Cart");
	}
	
	/*
	@Test
	public void deveCadastrarNovoEndereco() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		dsl.preencheInputGenerico("input-shipping-firstname", "Eduardo");
		dsl.preencheInputGenerico("input-shipping-lastname", "Rufino");
		dsl.preencheInputGenerico("input-shipping-address-1", "Rua 123");
		dsl.preencheInputGenerico("input-shipping-city", "Cidade 123");
		dsl.preencheInputGenerico("input-shipping-postcode", "12345678");
		dsl.selecionaDropDownPorId("input-shipping-country", "Brazil");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("input-shipping-zone")));
		dsl.selecionaDropDownPorId("input-shipping-zone", "São Paulo");
		dsl.clicaBotaoGenericoCssSelector("#button-shipping-address");
	}
	*/
	
	//arrumar erro de nao selecionar o radio de pagamento as vezes.
	@Test
	public void deveFinalizarCompraComUsuarioLogado() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    deveAdicionarProdutoCarrinho();
	    dsl.entraCarrinho();
	    
	    dsl.clicaBotaoGenericoCssSelector("#shopping-cart .btn.btn-primary[href*='route=checkout/checkout']");
	    
	    // Seleciona endereço de entrega existente
	    dsl.clicaBotaoGenericoCssSelector("#input-shipping-existing");
	    dsl.selecionaDropDownPorId("input-shipping-address", 1);
	    dsl.fechaAlertGenerico("#alert .alert.alert-success.alert-dismissible .btn-close");
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".alert-success.alert-dismissible")));
	    dsl.aguardaElementoInvisivel(".alert-success.alert-dismissible");
	    
	    // Seleciona método de entrega
	    dsl.clicaBotaoGenericoCssSelector("#button-shipping-methods");
	    dsl.clicaBotaoGenericoCssSelector("#input-shipping-method-flat-flat");    
	    dsl.clicaBotaoGenericoCssSelector("#button-shipping-method");
	    
	    dsl.fechaAlertGenerico("#alert .alert.alert-success.alert-dismissible .btn-close");
	    
	    // Espera até que qualquer alerta de sucesso desapareça antes de prosseguir
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".alert-success.alert-dismissible")));
	    dsl.aguardaElementoInvisivel(".alert-success.alert-dismissible");
	    
	    // Seleciona método de pagamento
	    dsl.clicaBotaoGenericoCssSelector("#button-payment-methods");
	    dsl.clicaBotaoGenericoCssSelector("#input-payment-method-cod-cod");
	    dsl.clicaBotaoGenericoCssSelector("#button-payment-method");
	    dsl.fechaAlertGenerico("#alert .alert.alert-success.alert-dismissible .btn-close");
	    
	    // Espera até que qualquer alerta de sucesso desapareça antes de prosseguir
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".alert-success.alert-dismissible")));
	    dsl.aguardaElementoInvisivel(".alert-success.alert-dismissible");
	    
	    // Clica no botão de finalizar pedido
	    By botaoSelector = By.cssSelector("#checkout-payment .text-end .btn.btn-primary");
	    dsl.scrollParaElemento(botaoSelector);
	    dsl.clicaBotaoGenericoCssSelector("#checkout-payment .text-end .btn.btn-primary");
	}
	
	
	@Test
	public void deveVerificarErroDadoObrigatorio() {
	    deveEntrarNoLogin();
	    dsl.expandeDropDown("My Account");
	    dsl.clicaBotaoGenericoCssSelector("#top .dropdown-menu.dropdown-menu-right.show a[href*='route=account']");
	    dsl.clicaBotaoGenericoCssSelector("#content .list-unstyled a[href*='route=account/edit']");
	    dsl.limpaInputGenerico("input-firstname");
	    dsl.limpaInputGenerico("input-lastname");
	    dsl.limpaInputGenerico("input-email");
	    dsl.clicaBotaoGenericoCssSelector("#form-customer .btn.btn-primary");
	    dsl.validaCampoObrigatorioGenerico("error-firstname", "First Name must be between 1 and 32 characters!");
	    dsl.validaCampoObrigatorioGenerico("error-lastname", "Last Name must be between 1 and 32 characters!");
	    dsl.validaCampoObrigatorioGenerico("error-email", "E-Mail Address does not appear to be valid!");
	}
	
	@Test
	public void deveValidarPagamentoEEnvio() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    deveAdicionarProdutoCarrinho();
	    dsl.entraCarrinho();
	    
	    dsl.clicaBotaoGenericoCssSelector("#shopping-cart .btn.btn-primary[href*='route=checkout/checkout']");
	    
	    // Seleciona endereço de entrega existente
	    dsl.clicaBotaoGenericoCssSelector("#input-shipping-existing");
	    dsl.selecionaDropDownPorId("input-shipping-address", 1);
	    dsl.fechaAlertGenerico("#alert .alert.alert-success.alert-dismissible .btn-close");
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".alert-success.alert-dismissible")));
	    dsl.aguardaElementoInvisivel(".alert-success.alert-dismissible");
	    
	    // Seleciona método de entrega
	    dsl.clicaBotaoGenericoCssSelector("#button-shipping-methods");
	    dsl.clicaBotaoGenericoCssSelector("#input-shipping-method-flat-flat");    
	    dsl.clicaBotaoGenericoCssSelector("#button-shipping-method");
	    
	    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alert")));
	    dsl.validaAlert("Success: You have changed shipping method!", "alert");
	    
	    //dsl.comparaStrings("Success: You have changed shipping method! ", url);
	    
	    // Espera até que qualquer alerta de sucesso desapareça antes de prosseguir
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".alert-success.alert-dismissible")));
	    dsl.aguardaElementoInvisivel(".alert-success.alert-dismissible");
	    
	    // Seleciona método de pagamento
	    dsl.clicaBotaoGenericoCssSelector("#button-payment-methods");
	    dsl.clicaBotaoGenericoCssSelector("#input-payment-method-cod-cod");
	    dsl.clicaBotaoGenericoCssSelector("#button-payment-method");
	    dsl.fechaAlertGenerico("#alert .alert.alert-success.alert-dismissible .btn-close");
	    
	    // Espera até que qualquer alerta de sucesso desapareça antes de prosseguir
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".alert-success.alert-dismissible")));
	    dsl.aguardaElementoInvisivel(".alert-success.alert-dismissible");
	}
	
	@Test
	public void deveCriarContaNova() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		if(dsl.estaLogado() == true) {
			dsl.clicaBotaoGenericoCssSelector("#top a.dropdown-item[href*='route=account/logout']");
		}
		dsl.clicaBotaoGenericoCssSelector("#top a.dropdown-item[href*='route=account/register']");
		dsl.preencheInputGenerico("input-firstname", primeiroNome);
		dsl.preencheInputGenerico("input-lastname", sobreNome);
		dsl.preencheInputGenerico("input-email", novoEmail);
		dsl.preencheInputGenerico("input-password", novaSenha);
		dsl.clicaBotaoGenericoCssSelector("#form-register .text-end .form-check-input");
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-firstname"));
			return !campo.getAttribute("value").isEmpty();
		});
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-lastname"));
			return !campo.getAttribute("value").isEmpty();
		});
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-email"));
			return !campo.getAttribute("value").isEmpty();
		});
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-password"));
			return !campo.getAttribute("value").isEmpty();
		});
		//wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#form-register .text-end .form-check-input")));
		dsl.aguardaElementoSelecionado("#form-register .text-end .form-check-input");
		dsl.clicaBotaoGenericoCssSelector("#form-register .btn.btn-primary");
		//wait.until(ExpectedConditions.titleIs("Your Account Has Been Created!"));
		dsl.aguardaTitulo("Your Account Has Been Created!");
		dsl.comparaStrings("Your Account Has Been Created!", driver.getTitle());
	}
	
	@Test
	public void deveValidarEmailExistente() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		if(dsl.estaLogado() == true) {
			dsl.clicaBotaoGenericoCssSelector("#top a.dropdown-item[href*='route=account/logout']");
		}
		dsl.clicaBotaoGenericoCssSelector("#top a.dropdown-item[href*='route=account/register']");
		dsl.preencheInputGenerico("input-firstname", "teste");
		dsl.preencheInputGenerico("input-lastname", "teste");
		dsl.preencheInputGenerico("input-email", "teste@teste.com");
		dsl.preencheInputGenerico("input-password", "teste123");
		dsl.clicaBotaoGenericoCssSelector("#form-register .text-end .form-check-input");
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-firstname"));
			return !campo.getAttribute("value").isEmpty();
		});
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-lastname"));
			return !campo.getAttribute("value").isEmpty();
		});
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-email"));
			return !campo.getAttribute("value").isEmpty();
		});
		wait.until(driver -> {
			WebElement campo = driver.findElement(By.id("input-password"));
			return !campo.getAttribute("value").isEmpty();
		});
		//wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#form-register .text-end .form-check-input")));
		dsl.aguardaElementoSelecionado("#form-register .text-end .form-check-input");
		dsl.clicaBotaoGenericoCssSelector("#form-register .btn.btn-primary");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alert")));
		dsl.aguardaElementoVisivel("#alert");
		dsl.comparaStrings(driver.findElement(By.cssSelector("#alert .alert.alert-danger.alert-dismissible")).getText(), "Warning: E-Mail Address is already registered!");
	}
	
	//------------------------------até aqui os waits foram substituidos--------------------------------
	
	/*
	@Test
	public void deveLogarComDadosInvalidos() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		dsl.expandeDropDown("My Account");
		
		dsl.clicaDropdown("Login");
		//dsl.clicaLink("Login");
		dsl.comparaStrings("Account Login", driver.getTitle());
		dsl.logaSistema("email_nao_cadastrado@teste.com", "SenhaErrada","Login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alert")));
		dsl.comparaStrings(driver.findElement(By.cssSelector("#alert .alert.alert-danger.alert-dismissible")).getText(), "Warning: No match for E-Mail Address and/or Password.");
	}
	*/
	
	@Test
	public void deveLogarComDadosInvalidos() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    dsl.expandeDropDown("My Account");
	    dsl.clicaDropdown("Login");

	    wait.until(ExpectedConditions.titleIs("Account Login"));

	    // Deixa o email vazio e tenta submeter
	    dsl.logaSistema("teste", "qualquerSenha", "Login");

	    WebElement email = driver.findElement(By.id("input-email"));

	    String inputValidacao = dsl.estaComErroInput();

	    Assert.assertFalse(inputValidacao.isEmpty());
	}
	
	@Test
	public void deveLogarComSenhaErrada() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		dsl.expandeDropDown("My Account");
		
		dsl.clicaDropdown("Login");
		//dsl.clicaLink("Login");
		dsl.comparaStrings("Account Login", driver.getTitle());
		dsl.logaSistema("teste@teste.com", "SenhaErrada","Login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alert")));
		dsl.comparaStrings(driver.findElement(By.cssSelector("#alert .alert.alert-danger.alert-dismissible")).getText(), "Warning: No match for E-Mail Address and/or Password.");
	}
	
	@Test
	public void deveRecuperarSenha() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		dsl.expandeDropDown("My Account");
		
		dsl.clicaDropdown("Login");
		dsl.clicaBotaoGenericoCssSelector("#form-login a[href*='route=account/forgotten']");
		dsl.preencheInputGenerico("input-email", "teste@teste.com");
		dsl.clicaBotaoGenericoCssSelector("#form-forgotten .text-end .btn.btn-primary");
		wait.until(ExpectedConditions.titleIs("Account Login"));
		String mensagem = driver.findElement(By.cssSelector("#account-login .alert")).getText();
		dsl.comparaStrings(mensagem, "text_success");;
	
	}
	
	@Test
	public void deveRealizarLogout() {
		if(dsl.estaLogado() == false) {	
			dsl.expandeDropDown("My Account");
			deveEntrarNoLogin();
		}		
		dsl.expandeDropDown("My Account");
		dsl.clicaBotaoGenericoCssSelector("#top a.dropdown-item[href*='route=account/logout']");
		dsl.aguardaLogout();
		dsl.comparaStrings(driver.getTitle(), "Account Logout");
	}
	
	@Test
	public void deveAlterarInformaçõesPessoais() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveEntrarNoLogin();
		dsl.expandeDropDown("My Account");
		dsl.clicaBotaoGenericoCssSelector("#top .list-inline-item .dropdown-menu.show a.dropdown-item[href*='route=account/account']");
		dsl.clicaBotaoGenericoCssSelector("#content .list-unstyled a[href*='route=account/edit']");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("input-firstname")));
		dsl.limpaInputGenerico("input-firstname");
		dsl.preencheInputGenerico("input-firstname", "teste1");
		dsl.limpaInputGenerico("input-lastname");
		dsl.preencheInputGenerico("input-lastname", "teste2");
		dsl.limpaInputGenerico("input-email");
		dsl.preencheInputGenerico("input-email", "teste@teste.com");
		dsl.clicaBotaoGenericoCssSelector("#content .btn.btn-primary");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#alert .alert.alert-success.alert-dismissible")));
		String alerta = driver.findElement(By.cssSelector("#alert .alert.alert-success.alert-dismissible")).getText();
		dsl.comparaStrings(alerta, "Success: Your account has been successfully updated.");
	}
	
	@Test
	public void deveAlterarSenha() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String senha = "teste123";
		deveEntrarNoLogin();
		dsl.expandeDropDown("My Account");
		dsl.clicaBotaoGenericoCssSelector("#top .list-inline-item .dropdown-menu.show a.dropdown-item[href*='route=account/account']");
		dsl.clicaBotaoGenericoCssSelector("#content .list-unstyled a[href*='route=account/password']");
		dsl.preencheInputGenerico("input-password", senha);
		dsl.preencheInputGenerico("input-confirm", senha);
		dsl.clicaBotaoGenericoCssSelector("#form-password .col.text-end .btn.btn-primary");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#alert .alert.alert-success.alert-dismissible")));
		dsl.comparaStrings("Success: Your password has been successfully updated.", driver.findElement(By.cssSelector("#alert .alert")).getText());
	}
	
	@Test
	public void deveVerificarHistoricoDePedidos() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveEntrarNoLogin();
		dsl.expandeDropDown("My Account");
		dsl.clicaBotaoGenericoCssSelector("#top .list-inline-item .dropdown-menu.show a.dropdown-item[href*='route=account/order']");
		dsl.aguardaTitulo("Orders");
		dsl.comparaStrings(driver.getTitle(), "Orders");
	}
	
	@Test
	public void deveGerenciarListaDeDesejos() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		deveEntrarNoLogin();
		String produto = "iMac";
		dsl.pesquisaProduto(produto);
		dsl.adicionaProdutoFavorito();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#alert .alert.alert-success.alert-dismissible")));
		dsl.comparaStrings("Success: You have added " + produto + " to your wish list!", driver.findElement(By.cssSelector("#alert .alert.alert-dismissible")).getText());
		dsl.expandeDropDown("My Account");
		dsl.clicaBotaoGenericoCssSelector("#top .list-inline-item .dropdown-menu.show a.dropdown-item[href*='route=account/account']");
		dsl.clicaBotaoGenericoCssSelector("#content a[href*='route=account/wishlist']");
		Assert.assertTrue(
			    "Produto não encontrado na wishlist",
			    dsl.produtoEstaNaWishlist(produto)
			);
		
	}
	
	@Test
	public void deveAvaliarLogado() {
		deveEntrarNoLogin();
		dsl.pesquisaProduto("iMac");
		dsl.clicaProduto();
		WebElement reviewsTab = driver.findElement(
			    By.cssSelector("a[href='#tab-review']")
			);
		
		((JavascriptExecutor) driver).executeScript(
			    "arguments[0].scrollIntoView({block: 'center'});",
			    reviewsTab
			);

			reviewsTab.click();
			
		dsl.preencheInputGenerico("input-text", "texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto ");
		driver.findElement(By.cssSelector("input[type='radio'][name='rating'][value='4']")).click();
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
