package profilosistema;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModificaInteressiTest {
    private WebDriver driver;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver");
        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        js = (JavascriptExecutor) driver;
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAggiungiInteressi() {
        driver.get("http://localhost:8080/SocialNotes/");
        driver.manage().window().setSize(new Dimension(1680, 964));
        driver.findElement(By.linkText("Accedi")).click();
        driver.findElement(By.id("inputEmail")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("ggallocca1");
        driver.findElement(By.id("inputPassword")).sendKeys("Password1");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
        driver.findElement(By.linkText("Modifica profilo")).click();
        driver.findElement(By.cssSelector(".row-group:nth-child(6) .grid__item > .btn")).click();
        {
            WebElement dropdown = driver.findElement(By.name("addInteressi"));
            List<WebElement> options = dropdown.findElements(By.tagName("option"));
            WebElement lastOption = options.get(options.size() - 1);
            lastOption.click();
        }
        driver.findElement(By.cssSelector(".btn--primary")).click();
        driver.findElement(By.cssSelector(".alert")).click();
        assertThat(driver.findElement(By.cssSelector(".alert")).getText(), is("Fatto! Le modifiche sono state salvate con successo: Interessi aggiunti\n" + "×"));
    }

    @Test
    public void testRimuoviInteressi() {
        driver.get("http://localhost:8080/SocialNotes/");
        driver.manage().window().setSize(new Dimension(1680, 964));
        driver.findElement(By.linkText("Accedi")).click();
        driver.findElement(By.id("inputEmail")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("ggallocca1");
        driver.findElement(By.id("inputPassword")).sendKeys("Password1");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
        driver.findElement(By.linkText("Modifica profilo")).click();
        driver.findElement(By.cssSelector(".row-group:nth-child(6) .grid__item > .btn")).click();
        {
            WebElement dropdown = driver.findElement(By.name("removeInteressi"));
            List<WebElement> options = dropdown.findElements(By.tagName("option"));
            WebElement lastOption = options.get(options.size() - 1);
            lastOption.click();
        }
        driver.findElement(By.cssSelector(".btn--primary")).click();
        driver.findElement(By.cssSelector(".alert")).click();
        assertThat(driver.findElement(By.cssSelector(".alert")).getText(), is("Fatto! Le modifiche sono state salvate con successo: Interessi rimossi\n" + "×"));
    }

}
