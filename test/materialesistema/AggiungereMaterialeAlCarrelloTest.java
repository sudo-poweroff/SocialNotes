// Generated by Selenium IDE
package materialesistema;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.util.*;
public class AggiungereMaterialeAlCarrelloTest {
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
  public void testAggiungiMaterialeAlCarrello() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("fry");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Despacit0");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    driver.findElement(By.cssSelector(".fa-cart-arrow-down")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".col-xs-4"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiMaterialeAlCarrelloGiaPresente() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("fry");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Despacit0");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
}
