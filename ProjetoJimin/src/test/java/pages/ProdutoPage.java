package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProdutoPage extends BasePage{
	
	public ProdutoPage(WebDriver driver) {
		super(driver);
	}
	
	private By searchInput = By.cssSelector(".input-group.mb-3 .form-control.form-control-lg");
	private By searchButton = By.cssSelector(".input-group.mb-3 .btn.btn-light.btn-lg");
	private By productLink = By.cssSelector(".product-thumb h4 a");
	private By addToCartButton = By.id("button-cart");
	
	public void searchProduct(String name) {
		scrollToElement(searchInput);
		write(searchInput, name);
		click(searchButton);
	}
	
	public void clickProduct() {
		click(productLink);
	}
	
	public void addToCart() {
		click(addToCartButton);
	}
	
}
	
	
	/*
    private WebDriver driver;
    private BasePage basepage;

    public ProdutoPage(WebDriver driver) {
        this.driver = driver;
    }

    public void pesquisarProduto(String nome) {
    	WebElement searchInput = driver.findElement(By.cssSelector(".input-group.mb-3 .form-control.form-control-lg"));	
		
		basepage.scrollToElement(By.cssSelector(".input-group.mb-3 .form-control.form-control-lg"));
		
		searchInput.clear();
		searchInput.sendKeys(nome);
		WebElement botao = driver.findElement(By.cssSelector(".input-group.mb-3 .btn.btn-light.btn-lg"));
		botao.click();
    	
    }

    public void clicarProduto() {
        driver.findElement(By.cssSelector(".product-thumb h4 a")).click();
    }

    public void adicionarAoCarrinho() {
        driver.findElement(By.id("button-cart")).click();
    }
    */
