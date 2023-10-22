package profilosistema;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InserimentoInteressiTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver");
        //System.setProperty("webdriver.chrome.driver","test/profilosistema/chromedriver.exe");
        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testInserimentoInteressiOK(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=rock1&mail=rocco.iuliano65@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(1382, 736));
        driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
        driver.findElement(By.id("inputEmail")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("rock1");
        driver.findElement(By.id("inputPassword")).click();
        driver.findElement(By.id("inputPassword")).sendKeys("Password0");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector("#\\35 1 span")).click();
        driver.findElement(By.id("avantiButton")).click();
        assertThat(driver.getTitle(), is("SocialNotes - Home"));
        driver.findElement(By.linkText("Logout")).click();
        driver.close();
    }

    @Test
    public void testInserimentoInteressiNonInseriti(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=onco1&mail=oncovision1@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(1382, 736));
        driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
        driver.findElement(By.id("inputEmail")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("onco1");
        driver.findElement(By.id("inputPassword")).click();
        driver.findElement(By.id("inputPassword")).sendKeys("Password0");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.id("avantiButton")).click();
        assertThat(driver.switchTo().alert().getText(), is("Errore, interessi non inseriti!."));
    }
}
