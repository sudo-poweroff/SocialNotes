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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.util.*;
public class ModificaImmagineTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver.exe");
    //System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver.exe");
    driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
    js = (JavascriptExecutor) driver;
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testModificaImmagineImgNonValida() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Califano03");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".editable-main-row .grid__item > .btn")).click();
    driver.findElement(By.cssSelector("p:nth-child(1)")).click();
    driver.findElement(By.id("picture")).sendKeys("C:\\fakepath\\Lezione03_06_2020.pdf");
    assertThat(driver.switchTo().alert().getText(), is("Tipo del file non valido"));
  }
  @Test
  public void testModificaImmagineImgValida() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("califano03");
    driver.findElement(By.id("inputPassword")).sendKeys("Califano03");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.cssSelector(".bi-person-lines-fill")).click();
    driver.findElement(By.linkText("Modifica profilo")).click();
    driver.findElement(By.cssSelector(".editable-main-row .grid__item > .btn")).click();
    driver.findElement(By.cssSelector("p:nth-child(1)")).click();
    driver.findElement(By.id("picture")).sendKeys("C:\\fakepath\\unisa.png");
    driver.findElement(By.cssSelector(".btn--primary")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".alert"));
      assert(elements.size() > 0);
    }
  }
}
