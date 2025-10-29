package DSL;

import java.util.Arrays;
import java.util.List;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DSL {
	
	private WebDriver driver;

	public DSL(WebDriver driver) {
		this.driver = driver;
	}

	// Recebe uma string com o link e clica nele
	public void clicaLink(String link) {
		driver.findElement(By.linkText(link)).click();
	}
	
	
	/* Clica no botão principal do dropdown para expandir o menu */
	public void expandeDropDown(String textoBotao) {
		WebElement botao = driver.findElement(By.xpath("//*[text()='" + textoBotao + "']"));
	    botao.click();
	}
	
	public void clicaDropdown(String textoItem) {
	    // 1️⃣ Clicar no botão que abre o dropdown (pode ser span, div, etc.)
	    //WebElement botao = driver.findElement(By.xpath("//*[text()='" + textoBotao + "']"));
	    //botao.click();
		
	    // 2️⃣ Esperar o item dentro do dropdown ficar clicável (pode ser a, li, span, etc.)
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement item = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//*[text()='" + textoItem + "']"))
	    );

	    // 3️⃣ Clicar no item
	    item.click();
	}
	
	// loga no sistema recebendo uma String para o email, uma para a senha e uma para o botão de logar
	public void logaSistema(String idemail, String idSenha, String textoBotao) {
		WebElement email = driver.findElement(By.id(idemail));
		email.sendKeys("teste@teste.com");
		WebElement senha = driver.findElement(By.id(idSenha));
		senha.sendKeys("teste123");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(
		    By.xpath("//form[@id='form-login']//button[text()='Login']"))
		);
		botao.click();		
	}
	
	// clica no icone da logo do site, o que faz retornar para a página inicial
	public void voltaPaginaInicial() {
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement botao = driver.findElement(
				By.xpath("//div[@id='logo']//img[@class='img-fluid']"));
		botao.click();
	}
	
	// enocntra um elemento de carrossel na página
	public WebElement encontraCarrossel() {
		//WebElement carrossel = driver.findElement(By.id("carousel-banner-0"));
		WebElement carrossel = driver.findElement(By.xpath("//div[@id='carousel-banner-0']//div[@class='carousel-item active']//img[@class='img-fluid']"));
		return carrossel;
	}
	
	// Recebe uma string com o id do carrossel e retorna o link da imagem que está no carrossel no momento
	public String retornaImagemCarrossel(String idCarrossel) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement imagemAtual = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(
		        		By.cssSelector("#" + idCarrossel + " .carousel-item.active img")
		        )
		    );
		
		return imagemAtual.getAttribute("src");
		
	}
	
	// retorna o titulo da pagina
	public String retornaTitulo() {
		String titulo = driver.getTitle();
		return titulo;
	}
	
	// clica no botao de telefone na barra superior da pagina
	public void clicaTelefone() {
		WebElement span = driver.findElement(By.xpath("//li[@class='list-inline-item']//span[@class='d-none d-lg-inline']"));
		span.click();
	}
	
	// clica no botao de wishlist na barra superior da pagina
	public void clicaWishList() {
		WebElement span = driver.findElement(By.id("wishlist-total"));
		span.click();
	}
	
	// clica no botao de carrinho da barra superior da pagina
	public void clicaCarrinho() {
		WebElement span = driver.findElement(By.xpath("//li[@class='list-inline-item']//a[@title='Shopping Cart']"));
		span.click();
	}
	
	//  clica no menu de moeda no topo da pagina para abrir a lista disponivel
	public void clicaMoeda() {
		WebElement span = driver.findElement(By.xpath("//form[@id='form-currency']//span[@class='d-none d-md-inline']"));
		span.click();
	}
	
	
	// depois de aberta a lista de moedas disponivel, seleciona uma delas de acordo com a String recebida
	public void trocaMoeda(String moeda) {
		WebElement botao = null;
		if(moeda == "Euro") {
			botao = driver.findElement(By.xpath("//a[@href='EUR']"));
		}
		if(moeda == "Libra") {
			botao = driver.findElement(By.xpath("//a[@href='GBP']"));
		}
		if(moeda == "Dolar") {
			botao = driver.findElement(By.xpath("//a[@href='USD']"));
		}
		botao.click();
	}
	
	// Retorna o icone da moeda selecionada na barra superior da pagina
	public String retornaMoeda() {
		String moeda = driver.findElement(By.cssSelector("#form-currency .dropdown-toggle strong")).getText();
		return moeda;
	}
	
	// Compara dois valores recebidos, para reaproveitar o assertEquals
	public void comparaStrings(String textoEsperado, String textoAtual) {
		Assert.assertEquals(textoEsperado, textoAtual);
	}
	
	// confirma se os dois valores recebidos sao diferentes, para reaproveritar o assertNotEquals
	public void comparaStringsDiferentes(String valor1, String valor2) {
	    Assert.assertNotEquals(valor1, valor2);
	}
	
	public void comparaFloatDiferentes(float valor1, float valor2) {
	    Assert.assertNotEquals(valor1, valor2);
	}
	public void comparaFloat(float valor1, float valor2) {
	    Assert.assertEquals(valor2, valor1, 0.01f);
	}
	
	/*public void clicaCategoria(String classeDropdown) {
	    // Ajuste do seletor CSS para múltiplas classes
	    List<WebElement> itens = driver.findElements(By.cssSelector("." + classeDropdown.replace(" ", "." ) + " div ul li a"));

	    for (WebElement item : itens) {
	        String texto = item.getText();

	        int numeroProdutos = 0;
	        if (texto.matches(".*\\(\\d+\\).*")) {
	            String numero = texto.replaceAll(".*\\((\\d+)\\).*", "$1");
	            numeroProdutos = Integer.parseInt(numero);
	            System.out.println("Número de produtos: " + numeroProdutos);
	        }

	        if (numeroProdutos > 0) {
	            System.out.println("Categoria " + texto + " tem produtos, clicando...");
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
	            break; // opcional: se quiser clicar somente no primeiro item com produtos
	        }
	    }
	}*/
	
	/* percorre os itens de um dropdown e clica no primeiro que tiver produtos*/
	public void clicaCategoria(WebElement menuAberto) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Captura novamente os itens ativos do menu para evitar StaleElementReference
	    List<WebElement> itens = menuAberto.findElements(By.cssSelector("div ul li a"));

	    for (int i = 0; i < itens.size(); i++) {
	        WebElement item = menuAberto.findElements(By.cssSelector("div ul li a")).get(i); // recaptura
	        String texto = item.getText();

	        int numeroProdutos = 0;
	        if (texto.matches(".*\\(\\d+\\).*")) {
	            String numero = texto.replaceAll(".*\\((\\d+)\\).*", "$1");
	            numeroProdutos = Integer.parseInt(numero);
	            System.out.println("Número de produtos: " + numeroProdutos);
	        }

	        if (numeroProdutos > 0) {
	            System.out.println("Categoria " + texto + " tem produtos, clicando...");
	            // Usa JavaScript para garantir o clique mesmo em elementos complexos
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);

	            // Espera até que algum elemento da nova página esteja visível (exemplo: título ou lista de produtos)
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-layout, .product-thumb, .product-list")));
	            break; // Clica apenas no primeiro item com produtos
	        }
	    }
	}

	// percorre todos as categorias principais da página, entra nas subcategorias e entrana pagina de subcategorias que tiverem algum produto cadastrado e valida se realmente tem algum produto aprensentado na pagina
	public void validaTodosOsDropdowns() {
		
		WebElement produto;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Captura todos os links principais de dropdown (ex: Desktops, Components, etc.)
	    List<WebElement> dropdowns = driver.findElements(By.cssSelector(".nav-item.dropdown > a"));

	    for (int i = 0; i < dropdowns.size(); i++) {
	    	produto = null;
	        // ⚠️ Recaptura a lista de dropdowns, pois o DOM pode mudar
	        dropdowns = driver.findElements(By.cssSelector(".nav-item.dropdown > a"));
	        WebElement dropdown = dropdowns.get(i);

	        String nomeCategoria = dropdown.getText();
	        System.out.println("\n=== Testando categoria: " + nomeCategoria + " ===");

	        // Expande o dropdown
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

	        // Espera até que o menu seja visível
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dropdown-menu.show")));

	        // Captura o menu aberto correspondente
	        List<WebElement> menus = driver.findElements(By.cssSelector(".dropdown-menu.show"));
	        if (!menus.isEmpty()) {
	            WebElement menuAberto = menus.get(0);

	            // Chama o método que valida subcategorias e clica em uma que tenha produtos
	            clicaCategoria(menuAberto);
	        } else {
	            System.out.println("⚠️ Nenhum menu encontrado para " + nomeCategoria);
	        }
	        
	        // Volta para a página inicial antes de testar o próximo dropdown
	        driver.navigate().back();

	        // Espera até que o dropdown principal esteja visível novamente
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nav-item.dropdown > a")));
	    }

	    System.out.println("\n✅ Validação de todos os dropdowns finalizada!");
	}

	// Recebe o nome de um carrossel e clica no botao de proxima imagem desse carrossel
	public void clicaCarrossel(String nomeCarrossel) {
		WebElement botao = driver.findElement(
			    By.cssSelector("button.carousel-control-next[data-bs-target='#carousel-banner-0']")
			);
			botao.click();
	}
	
	// recebe o nome de um produto, escreve ele na caixa de pesquisa e clica no botao de pesquisar
	public void pesquisaProduto(String produto) {
		WebElement input = driver.findElement(By.cssSelector(".input-group.mb-3 .form-control.form-control-lg"));
		input.sendKeys(produto);
		WebElement botao = driver.findElement(By.cssSelector(".input-group.mb-3 .btn.btn-light.btn-lg"));
		botao.click();
	}
	
	
	public void clicaProduto() {
		WebElement produto = driver.findElement(By.cssSelector("#product-list .col.mb-3 .product-thumb .content .description h4 a"));
		produto.click();
	}
	
	// Valida se o nome do produto da pagina nao esta vazio
	public boolean validaNomeProduto() {
		String nome = driver.findElement(By.cssSelector("#content .row.mb-3 .col-sm h1")).getText();
		if(!nome.isEmpty()) {
			System.out.println(nome);
			return true;
		}
		else {
			return false;
		}
	}
	
	// Recebe uma div para especificar onde produrar e o texto inicial para definir qual a informacao que deve ser validada, depois verifica se a infoamação apos o ":" nao esta vazio
	public boolean validaInformacaoProduto(String idDiv, String textoInicial) {
		String informacao = driver.findElement(By.xpath("//div[@id='" + idDiv + "']//li[starts-with(normalize-space(.), '" + textoInicial + "')]")).getText();
		//String marca = driver.findElement(By.xpath("//li[starts-with(normalize-space(.), 'Brand:')]")).getText();
		if(!informacao.isEmpty()) {
			System.out.println(informacao);
			return true;
		}
		else {
			return false;
		}
	}
	
	// verifica se o preço do produto nao esta vazio
	public boolean validaPreçoProduto() {
		String preco = driver.findElement(By.cssSelector("#content .row.mb-3 .col-sm h2 span")).getText();
		if(!preco.isEmpty()) {
			System.out.println(preco);
			return true;
		}
		else {
			return false;
		}
	}	
	
	//ver uma forma melhor de pegar o produto correto depois
	//Adiciona um produto ao carrinho
	public void adicionaProdutoCarrinho(String productId) {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Localiza o botão "Add to Cart" pelo atributo formaction
            WebElement addToCartButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(@formaction, 'cart.add')]")
            ));

            // Scroll até o botão
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addToCartButton);

            // Opcional: aguardar até que esteja clicável
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

            // Clicar no botão
            addToCartButton.click();
    }
	
	//Fecha alert de confirmação de produto adicionado ao carrinho
	public void fechaAlertCarrinho() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#alert .alert.alert-success.alert-dismissible .btn-close")));
		wait.until(ExpectedConditions.elementToBeClickable(alert));
		alert.click();
	}
	
	//Abre o dropdown com a lista de produtos 
	public void abreDropdownCarrinho() {
		WebElement botao = driver.findElement(By.cssSelector("#cart"));
		botao.click();
		//WebElement produto = driver.findElement(By.cssSelector(".dropdown-menu.dropdown-menu-end.p-2.show"));
		
	}
	
	//Valida se o produto adicionado ao carrinho foi o mesmo que foi pesquisado
	public boolean validaProdutoCarrinho(String nomeProduto) {
		List<WebElement> produtosNoCarrinho = driver.findElements(By.xpath("//div[@id='cart']//table//td[2]/a"));
		
		for (WebElement produto: produtosNoCarrinho) {
			if(produto.getText().equalsIgnoreCase(nomeProduto)) {
				return true;
			}
		}
		return false;
	}
	
	//valida se os produtos adicionados ao carrinho foram os mesmos recebidos em um vetor
	public void validaListaProdutosCarrinho(String[] listaProdutos) {
		List<String> produtosEsperados = Arrays.asList(listaProdutos);
		List<WebElement> produtosNoCarrinho = driver.findElements(By.xpath("//div[@id='cart']//table//td[2]/a"));
		
		for(String esperado : produtosEsperados) {
			boolean encontrado = false;
			
			for(WebElement produto : produtosNoCarrinho) {
				if(produto.getText().equalsIgnoreCase(esperado)) {
					encontrado = true;
					break;
				}
			}
			
			if(!encontrado) {
				System.out.println("Produto não encontrado no carrinho: " + esperado);
			} else {
				System.out.println("Produto encontrado: " + esperado);
			}
		}
	}
	
	public void removeItemCarrinho() {
		WebElement botao = driver.findElement(By.cssSelector(".table.table-striped.mb-2 form[data-oc-target='#cart']"));
		botao.click();
	}
	
	public void validaCarrinhoVazio() {
		WebElement carrinho = driver.findElement(By.cssSelector("#cart .dropdown-menu.dropdown-menu-end.p-2.show li"));
		if(carrinho.getText().equalsIgnoreCase("Your shopping cart is empty!")) {
			System.out.println("O carrinho esta vazio");
		} else {
			System.out.println("Existem produtos no carrinho");
		}
		
	}
	
	/*
	public String retornaString(String css) {
		String texto = driver.findElement(By.cssSelector(css)).getText();
		return texto;
	}
	*/
	
	public String retornaString(By selector) {
		String texto = driver.findElement(selector).getText();
		return texto;
	}
	
	public void entraCarrinho() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#top .list-inline-item a[href*='route=checkout/cart']")));
		WebElement carrinho = driver.findElement(By.cssSelector("#top .list-inline-item a[href*='route=checkout/cart']"));
		carrinho.click();
	}
	
	public void alteraQuantidadeProdutosCarrinho(String quantidade) {
		WebElement input = driver.findElement(By.cssSelector("#output-cart .input-group .form-control"));
		input.clear();
		input.sendKeys(quantidade);
	}
	
	public void atualizaQuantidadeCarrinho() {
		WebElement botao = driver.findElement(By.cssSelector("#output-cart .input-group .btn.btn-primary"));
		botao.click();
	}
	
	public void clicaBotaoGenericoCssSelector(String caminhoBotao) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(caminhoBotao)));
		WebElement botao = driver.findElement(By.cssSelector(caminhoBotao));
		botao.click();
	}
	
	public void validaSeEstaPaginaInicial() {
		comparaStrings("Your Store", driver.getTitle());
	}
	
	public float retornaValorComBaseColunaAnterior(String linha) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tfoot//tr[td/strong[normalize-space()='" + linha + "']]/td[last()]")));
		String texto = driver.findElement(By.xpath("//tfoot//tr[td/strong[normalize-space()='" + linha + "']]/td[last()]")).getText();
		texto = texto.replaceAll("[^\\d.-]", "");
		System.out.println(linha + ": " + texto);
		return Float.parseFloat(texto);
	}
	
	public float retornaValorTotalComBaseSubTotal(float subTotal, float ecoTax, float vat) {
		float total = subTotal + ecoTax + vat;
		return total;
	}
	
	

	
	
	/*
	public float retornaValorCarrinho() {
		String texto = driver.findElement(By.cssSelector("#cart .dropdown-menu.dropdown-menu-end.p-2.show .table.table-striped.mb-2")).getText();
		
	}
	*/

	
}
