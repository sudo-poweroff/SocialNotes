// Generated by Selenium IDE
package acquistosistema;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.util.*;
public class AcquistoMaterialeNelCarrelloTest {
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
  public void testAcquistoMaterialeCarrelloVuoto() {
    driver.get("http://localhost:8080/SocialNotes/homepage.jsp");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("fry");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Despacit0");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".fa-cart-arrow-down")).click();
    driver.findElement(By.cssSelector(".btn-lg")).click();
    assertThat(driver.findElement(By.cssSelector("p")).getText(), is("Inserisci prima qualcosa nel tuo carrello!"));
  }
  @Test
  public void testAcquistoMaterialeCoinsInsufficienti() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("frai");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Frai1");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("ricerca")).click();
    driver.findElement(By.id("ricerca")).sendKeys("prog");
    driver.findElement(By.cssSelector(".btn-dark")).click();
    driver.findElement(By.cssSelector(".col-xs-12:nth-child(4) h4")).click();
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    driver.findElement(By.cssSelector(".fa-cart-arrow-down")).click();
    driver.findElement(By.cssSelector(".btn-lg")).click();
    assertThat(driver.findElement(By.cssSelector("p")).getText(), is("Non hai abbastanza coin!"));
  }
  @Test
  public void testAcquistoMaterialeEffettuato() {
    driver.get("http://localhost:8080/SocialNotes/homepage.jsp");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("fry");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Despacit0");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    driver.findElement(By.cssSelector(".fa-cart-arrow-down > path")).click();
    driver.findElement(By.cssSelector(".btn-lg")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".card"));
      assert(elements.size() > 0);
    }
  }
}
