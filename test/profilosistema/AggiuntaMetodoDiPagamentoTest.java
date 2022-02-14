package profilosistema;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class AggiuntaMetodoDiPagamentoTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	  System.setProperty("webdriver.chrome.driver","test/profilosistema/chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testAggiungiPagamentoNomeCorrCognomeCorrNumeroCartaCorrCVCCorrDataScadenzaCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("Mario");
    driver.findElement(By.name("cognomecarta")).click();
    driver.findElement(By.name("cognomecarta")).sendKeys("Verdi");
    {
      WebElement element = driver.findElement(By.name("numcarta"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
    driver.findElement(By.name("numcarta")).sendKeys("9999999999991111");
    driver.findElement(By.name("month")).click();
    {
      WebElement dropdown = driver.findElement(By.name("month"));
      dropdown.findElement(By.xpath("//option[. = '01']")).click();
    }
    driver.findElement(By.name("year")).click();
    {
      WebElement dropdown = driver.findElement(By.name("year"));
      dropdown.findElement(By.xpath("//option[. = '2026']")).click();
    }
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).sendKeys("323");
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiPagamentoNomeCorrCognomeCorrNumeroCartaCorrCVCCorrDataScadenzaNonCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("Mario");
    driver.findElement(By.name("cognomecarta")).click();
    driver.findElement(By.name("cognomecarta")).sendKeys("Verdi");
    driver.findElement(By.name("numcarta")).click();
    driver.findElement(By.name("numcarta")).sendKeys("9999999999991111");
    {
      WebElement element = driver.findElement(By.name("month"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".form-group:nth-child(3)")).click();
    {
      WebElement dropdown = driver.findElement(By.name("month"));
      dropdown.findElement(By.xpath("//option[. = 'scegli']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.name("year"));
      dropdown.findElement(By.xpath("//option[. = 'scegli']")).click();
    }
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).sendKeys("311");
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiPagamentoNomeCorrCognomeCorrNumeroCartaCorrCVCNonCorrDataScadenzaCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("Mario");
    driver.findElement(By.name("cognomecarta")).click();
    driver.findElement(By.name("cognomecarta")).click();
    driver.findElement(By.name("cognomecarta")).sendKeys("Verdi");
    driver.findElement(By.name("numcarta")).click();
    driver.findElement(By.name("numcarta")).sendKeys("9999999999991111");
    driver.findElement(By.name("month")).click();
    {
      WebElement dropdown = driver.findElement(By.name("month"));
      dropdown.findElement(By.xpath("//option[. = '01']")).click();
    }
    driver.findElement(By.name("year")).click();
    {
      WebElement dropdown = driver.findElement(By.name("year"));
      dropdown.findElement(By.xpath("//option[. = '2024']")).click();
    }
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).sendKeys("aaa");
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .one-third")).click();
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiPagamentoNomeCorrCognomeCorrNumeroCartaCorrPresenteCVCCorrDataScadenzaCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill > path")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("Mario");
    {
      WebElement element = driver.findElement(By.name("cognomecarta"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".row-group:nth-child(4) .form-group:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .form-group:nth-child(1)")).click();
    driver.findElement(By.name("cognomecarta")).sendKeys("Verdi");
    {
      WebElement element = driver.findElement(By.name("numcarta"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
    driver.findElement(By.name("numcarta")).sendKeys("9999999999991111");
    driver.findElement(By.name("numcarta")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
    driver.findElement(By.name("month")).click();
    {
      WebElement dropdown = driver.findElement(By.name("month"));
      dropdown.findElement(By.xpath("//option[. = '02']")).click();
    }
    driver.findElement(By.name("year")).click();
    {
      WebElement dropdown = driver.findElement(By.name("year"));
      dropdown.findElement(By.xpath("//option[. = '2024']")).click();
    }
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).sendKeys("323");
    driver.findElement(By.cssSelector(".form-group:nth-child(3)")).click();
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiPagamentoNomeCorrCognomeCorrNumeroCartaNonCorrCVCCorrDataScadenzaCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill > path")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("Mario");
    {
      WebElement element = driver.findElement(By.name("cognomecarta"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".row-group:nth-child(4) .form-group:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .form-group:nth-child(1)")).click();
    driver.findElement(By.name("cognomecarta")).sendKeys("Verdi");
    driver.findElement(By.name("month")).click();
    {
      WebElement dropdown = driver.findElement(By.name("month"));
      dropdown.findElement(By.xpath("//option[. = '02']")).click();
    }
    driver.findElement(By.name("year")).click();
    {
      WebElement dropdown = driver.findElement(By.name("year"));
      dropdown.findElement(By.xpath("//option[. = '2025']")).click();
    }
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).sendKeys("656");
    driver.findElement(By.name("numcarta")).click();
    driver.findElement(By.name("numcarta")).sendKeys("11111111111");
    {
      WebElement element = driver.findElement(By.cssSelector(".row-group:nth-child(4) .one-third"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(2) .help-block"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .one-third")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".form-group:nth-child(2) .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiPagamentoNomeCorrCognomeNonCorrNumeroCartaCorrCVCCorrDataScadenzaCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("Mario");
    {
      WebElement element = driver.findElement(By.name("numcarta"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
    driver.findElement(By.name("numcarta")).sendKeys("9999999999991111");
    driver.findElement(By.name("numcarta")).sendKeys("9999999999991112");
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(2) .help-block"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".form-group:nth-child(2) .valid-feedback"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".form-group:nth-child(2) > .col-sm-9")).click();
    driver.findElement(By.name("month")).click();
    {
      WebElement dropdown = driver.findElement(By.name("month"));
      dropdown.findElement(By.xpath("//option[. = '11']")).click();
    }
    driver.findElement(By.name("year")).click();
    {
      WebElement dropdown = driver.findElement(By.name("year"));
      dropdown.findElement(By.xpath("//option[. = '2022']")).click();
    }
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).click();
    driver.findElement(By.cssSelector(".col-sm-9:nth-child(4) > .form-control")).sendKeys("565");
    driver.findElement(By.name("cognomecarta")).click();
    driver.findElement(By.name("cognomecarta")).sendKeys("444");
    driver.findElement(By.cssSelector(".row-group:nth-child(4) > .hidden-form")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".col-sm-9:nth-child(3) > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testAggiungiPagamentoNomeNonCorrCognomeCorrNumeroCartaCorrNonPresenteDBCVCCorrDataScadenzaCorr() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill > path")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .grid__item > .btn")).click();
    driver.findElement(By.name("nomecarta")).click();
    driver.findElement(By.name("nomecarta")).sendKeys("3444");
    driver.findElement(By.cssSelector(".row-group:nth-child(4) .form-group:nth-child(1)")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".row-group:nth-child(4) .form-group:nth-child(1) > .col-sm-9:nth-child(2) > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }
}
