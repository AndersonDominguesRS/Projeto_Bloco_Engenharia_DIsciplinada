package org.example.sprintrestapi.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ProdutoUITest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown( ) {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCadastrarEExcluirProduto( ) {
        driver.get("http://localhost:8080");

        driver.findElement(By.id("nome")).sendKeys("teste automatizado");
        driver.findElement(By.id("quantidade")).sendKeys("10");
        driver.findElement(By.id("preco")).sendKeys("99.00");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("tabelaCorpo"), "teste automatizado"));
        assertTrue(driver.getPageSource().contains("teste automatizado"));

        driver.findElement(By.className("btn-danger")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test
    public void testErroAoCadastrarProdutoInvalido( ) {
        driver.get("http://localhost:8080");

        driver.findElement(By.id("nome")).sendKeys("erro");
        driver.findElement(By.id("quantidade")).sendKeys("1");
        driver.findElement(By.id("preco")).sendKeys("-100");
        driver.findElement(By.id("btnCadastrar")).click();

        WebElement msgErro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagemErro")));
        assertTrue(msgErro.getText().contains("valor preco nao pode ser negativo"));
    }
}