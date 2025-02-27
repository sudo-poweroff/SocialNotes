// Generated by Selenium IDE
package profilosistema;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class RegistrazioneTest {
  private WebDriver driver;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    //System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver");
	System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver.exe");
    driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
    js = (JavascriptExecutor) driver;
  }
  @After
  public void tearDown() {
    driver.quit();
  }
 
  @Test
  public void testRegistrazioneEffettuata() throws InterruptedException{
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1382, 736));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).sendKeys("rocco");
    driver.findElement(By.id("lastName")).click();
    driver.findElement(By.id("lastName")).sendKeys("rocco");
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).sendKeys("rock1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("Mensa0");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("rocco.iul2000@gmail.com");
    driver.findElement(By.id("uni")).click();
    {
      WebElement dropdown = driver.findElement(By.id("uni"));
      dropdown.findElement(By.xpath("//option[. = 'Politecnico di Milano']")).click();
    }
    driver.findElement(By.cssSelector("option:nth-child(10)")).click();
    driver.findElement(By.id("corso")).click();
    {
      WebElement dropdown = driver.findElement(By.id("corso"));
      Thread.sleep(5000);
      dropdown.findElement(By.xpath("//option[. = 'Dipartimento di Informatica']")).click();
    }
    driver.findElement(By.cssSelector("#corso > option")).click();
    driver.findElement(By.id("nascita")).sendKeys("03-09-2021");
    driver.findElement(By.cssSelector(".btn")).click();
    String successString= driver.findElement(By.cssSelector("h1")).getText();
    System.out.println(successString);
    assertEquals(successString, "Registrazione completata");
    driver.close();
  }
  
  @Test
  public void testRegistrazioneNomeNonValido() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).sendKeys("anton1o");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".row:nth-child(1) > .col-md-6:nth-child(1) > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazioneCognomeNonValido() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("lastName")).click();
    driver.findElement(By.id("lastName")).sendKeys("r0ss1");
    driver.findElement(By.id("firstName")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".row:nth-child(1) > .col-md-6:nth-child(2) > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazioneUsernamePresente() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).sendKeys("francesco");
    driver.findElement(By.id("lastName")).click();
    driver.findElement(By.id("lastName")).sendKeys("di lauro");
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).sendKeys("fry");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("username-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazioneUsernameNonValido() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).sendKeys("aa");
    driver.findElement(By.id("firstName")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("username-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazionePasswordNonValida() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("aa");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".form-group > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazioneEmailEsistente() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("acalifano123@gmail.com");
    {
      WebElement element = driver.findElement(By.cssSelector(".row:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".bg-light"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".bg-light")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("email-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazioneIndirizzoEmailNonValido() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("aa.it");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("email-feedback"));
      assert(elements.size() > 0);
    }
  }

  @Test
  public void testRegistrazioneUniversitaNonPresente() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).sendKeys("francesco");
    driver.findElement(By.id("lastName")).click();
    driver.findElement(By.id("lastName")).sendKeys("di lauro");
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).sendKeys("frai123");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("Franc3");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("francesco123@frai.it");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    driver.findElement(By.id("nascita")).click();
    driver.findElement(By.id("nascita")).click();
    driver.findElement(By.id("nascita")).sendKeys("0002-05-01");
    driver.findElement(By.id("nascita")).sendKeys("0020-05-01");
    driver.findElement(By.id("nascita")).sendKeys("0200-05-01");
    driver.findElement(By.id("nascita")).sendKeys("2000-05-01");
    driver.findElement(By.id("reg")).click();
    driver.findElement(By.cssSelector(".btn")).click();
    assertThat(driver.switchTo().alert().getText(), is("Inserisci un\' universita\' !"));
  }

  @Test
  public void testRegistrazioneDataNonValida() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1181, 852));
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("nascita")).click();
    driver.findElement(By.id("nascita")).sendKeys("12-03-2024");
    driver.findElement(By.cssSelector(".row:nth-child(2)")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".mb-3:nth-child(5) > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }

}
