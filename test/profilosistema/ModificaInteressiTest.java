package profilosistema;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModificaInteressiTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver");
        //System.setProperty("webdriver.chrome.driver","test/profilosistema/chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAggiungiInteressi() {
        driver.get("http://127.0.0.1:8080/SocialNotes/");
        driver.manage().window().setSize(new Dimension(1936, 1056));
        driver.findElement(By.linkText("Accedi")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("califano03");
        driver.findElement(By.id("inputPassword")).click();
        driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
        driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector(".bi-person-lines-fill > path")).click();
        driver.findElement(By.linkText("Modifica profilo")).click();
        driver.findElement(By.cssSelector(".editable-main-row .grid__item > .btn")).click();
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();
        driver.findElement(By.id("picture")).sendKeys("C:\\Users\\PC\\Downloads\\Lezione03_06_2020.pdf");
        assertThat(driver.switchTo().alert().getText(), is("Tipo del file non valido"));
    }
}
