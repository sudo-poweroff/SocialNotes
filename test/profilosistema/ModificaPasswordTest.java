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
public class ModificaPasswordTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	 // System.setProperty("webdriver.chrome.driver","test/profilosistema/chromedriver.exe");
	  System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testModificaPasswodPassCorrettaFormatoCorrettoPassConfermaCorretta() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso33!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(3) .grid__item > .btn")).click();
    driver.findElement(By.name("current_password")).click();
    driver.findElement(By.name("current_password")).sendKeys("Alfonso33!");
    driver.findElement(By.id("pwd")).sendKeys("Alfonso32!");
    driver.findElement(By.id("newpwd")).sendKeys("Alfonso32!");
    {
      WebElement element = driver.findElement(By.cssSelector(".one-half:nth-child(4)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".submit:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".one-half:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testModificaPasswodPassCorrettaFormatoCorrettoPassConfermaNonCorretta() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(3) .grid__item > .btn")).click();
    driver.findElement(By.name("current_password")).click();
    driver.findElement(By.name("current_password")).sendKeys("Alfosno32!");
    driver.findElement(By.id("pwd")).click();
    driver.findElement(By.id("pwd")).sendKeys("alfonso22!");
    {
      WebElement element = driver.findElement(By.id("newpwd"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    driver.findElement(By.cssSelector(".row-group:nth-child(3) > .hidden-form")).click();
    driver.findElement(By.id("pwd")).sendKeys("Alfonso22!");
    driver.findElement(By.id("newpwd")).click();
    driver.findElement(By.id("newpwd")).sendKeys("Alfonso21!");
    driver.findElement(By.cssSelector(".row-group:nth-child(3) .one-third")).click();
    assertThat(driver.findElement(By.cssSelector(".input-group:nth-child(3) > .invalid-feedback")).getText(), is("Le password non corrispondono."));
  }
  @Test
  public void testModificaPasswodPassCorrettaFormatoNonCorretto() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill > path")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(3) .grid__item > .btn")).click();
    driver.findElement(By.name("current_password")).click();
    driver.findElement(By.name("current_password")).sendKeys("Alfonso32!");
    driver.findElement(By.id("pwd")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(3) > .hidden-form")).click();
    driver.findElement(By.id("pwd")).sendKeys("Alf");
    {
      WebElement element = driver.findElement(By.cssSelector(".row-group:nth-child(5) > .data-row"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".row-group:nth-child(5) > .grid > .one-half"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".row-group:nth-child(5) > .data-row")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".input-group:nth-child(2) > .invalid-feedback"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testModificaPasswodPassNonCorrettaFormatoCorrettoPassConfermaCorretta() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Alfonso32!");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill > path")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".row-group:nth-child(3) .grid__item > .btn")).click();
    driver.findElement(By.name("current_password")).click();
    driver.findElement(By.name("current_password")).sendKeys("alfonso44!");
    driver.findElement(By.id("pwd")).click();
    driver.findElement(By.id("pwd")).sendKeys("alfonso44!");
    {
      WebElement element = driver.findElement(By.id("newpwd"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".input-group:nth-child(2) > .invalid-feedback"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".grid__item > .input-group")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3)")).click();
    driver.findElement(By.id("newpwd")).click();
    driver.findElement(By.id("pwd")).sendKeys("Alfonso23!");
    driver.findElement(By.id("newpwd")).click();
    driver.findElement(By.id("newpwd")).sendKeys("Alfonso");
    driver.findElement(By.id("newpwd")).sendKeys("Alfonso23!");
    driver.findElement(By.cssSelector(".row-group:nth-child(3) .one-third")).click();
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
}
