package profilosistema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

public class ModificaEmailTest {
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
  public void testModificaEmailOK() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1382, 736));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("rock1");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Mensa0");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".editable-main-row .grid__item > .btn")).click();
    driver.findElement(By.name("mail")).click();
    driver.findElement(By.name("mail")).sendKeys("rocco.iuliano65@gmail.com");
    driver.findElement(By.cssSelector(".editable-main-row > .hidden-form")).click();
    driver.findElement(By.cssSelector(".one-third:nth-child(4) .btn")).click();
    driver.findElement(By.cssSelector(".btn--primary")).click();
    assertThat(driver.getTitle(), is("SocialNotes"));
    driver.close();
  }

  @Test
  public void testModificaEmailEsistente() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1366, 720));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Califano03");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".editable-main-row .grid__item > .btn")).click();
    driver.findElement(By.name("mail")).click();
    driver.findElement(By.name("mail")).sendKeys("acalifano123@gmail.com");
    driver.findElement(By.cssSelector(".editable-main-row > .hidden-form")).click();
    assertThat(driver.findElement(By.id("email-feedback")).getText(), is("L'indirizzo scelto è già esistente."));
    {
      WebElement element = driver.findElement(By.cssSelector(".btn--primary"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".one-half:nth-child(4)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".one-half:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      Assert.assertEquals(elements.size(),0);
    }
    driver.findElement(By.linkText("Logout")).click();
    driver.close();
  }
  @Test
  public void testModificaEmailFormatoNonCorretto() {
    driver.get("http://localhost:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1382, 736));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Califano03");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".editable-main-row .grid__item > .btn")).click();
    driver.findElement(By.name("mail")).click();
    driver.findElement(By.name("mail")).sendKeys("acalifano123gmail.com");
    driver.findElement(By.cssSelector(".one-third > h1")).click();
    assertThat(driver.findElement(By.id("email-feedback")).getText(), is("Per favore inserisci un indirizzo Email valido."));
    driver.findElement(By.linkText("Logout")).click();
    driver.close();
  }

}
