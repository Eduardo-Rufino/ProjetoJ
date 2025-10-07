package DSL;

import java.util.List;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	
	public void voltaPaginaInicial() {
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement botao = driver.findElement(
				By.xpath("//div[@id='logo']//img[@class='img-fluid']"));
		botao.click();
	}
	
	public WebElement encontraCarrossel() {
		//WebElement carrossel = driver.findElement(By.id("carousel-banner-0"));
		WebElement carrossel = driver.findElement(By.xpath("//div[@id='carousel-banner-0']//div[@class='carousel-item active']//img[@class='img-fluid']"));
		return carrossel;
	}
	
	public String retornaImagemCarrossel(String idCarrossel) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement imagemAtual = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(
		        		By.cssSelector("#" + idCarrossel + " .carousel-item.active img")
		        )
		    );
		
		return imagemAtual.getAttribute("src");
		
	}
	
	public String retornaTitulo() {
		String titulo = driver.getTitle();
		return titulo;
	}
	
	public void clicaTelefone() {
		WebElement span = driver.findElement(By.xpath("//li[@class='list-inline-item']//span[@class='d-none d-lg-inline']"));
		span.click();
	}
	
	public void clicaWishList() {
		WebElement span = driver.findElement(By.id("wishlist-total"));
		span.click();
	}
	
	public void clicaCarrinho() {
		WebElement span = driver.findElement(By.xpath("//li[@class='list-inline-item']//a[@title='Shopping Cart']"));
		span.click();
	}
	
	public void clicaMoeda() {
		WebElement span = driver.findElement(By.xpath("//form[@id='form-currency']//span[@class='d-none d-md-inline']"));
		span.click();
	}
	
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
	
	public String retornaMoeda() {
		String moeda = driver.findElement(By.cssSelector(".dropdown-toggle strong")).getText();
		return moeda;
	}
	
	public void comparaValores(String textoEsperado, String textoAtual) {
		Assert.assertEquals(textoEsperado, textoAtual);
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

	public void validaTodosOsDropdowns() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Captura todos os links principais de dropdown (ex: Desktops, Components, etc.)
	    List<WebElement> dropdowns = driver.findElements(By.cssSelector(".nav-item.dropdown > a"));

	    for (int i = 0; i < dropdowns.size(); i++) {
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


	
}
