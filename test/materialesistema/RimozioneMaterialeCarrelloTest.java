package materialesistema;
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
public class RimozioneMaterialeCarrelloTest {
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
  public void testRimozioneMaterialeNonPresenteNelCarrello() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("pipp8");
    driver.findElement(By.id("inputPassword")).sendKeys("Pipp8");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".fa-cart-arrow-down > path")).click();
    assertThat(driver.findElement(By.cssSelector("strong")).getText(), is("0"));
  }
  @Test
  public void testRimozioneMaterialePresenteNelCarrello() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1920, 1040));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("pipp8");
    driver.findElement(By.id("inputPassword")).sendKeys("Pipp8");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("ricerca")).sendKeys("prog");
    driver.findElement(By.cssSelector(".fas")).click();
    driver.findElement(By.cssSelector(".col-xs-12:nth-child(12) .col-sm-9")).click();
    driver.findElement(By.cssSelector(".col-xs-12:nth-child(12) h4")).click();
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    driver.findElement(By.id("ricerca")).click();
    driver.findElement(By.id("ricerca")).sendKeys("prog");
    driver.findElement(By.cssSelector(".btn-dark")).click();
    driver.findElement(By.cssSelector(".col-xs-12:nth-child(14) h4")).click();
    driver.findElement(By.id("ricerca")).click();
    driver.findElement(By.id("ricerca")).sendKeys("prog");
    driver.findElement(By.cssSelector(".btn-dark")).click();
    driver.findElement(By.cssSelector(".col-xs-12:nth-child(10) h4")).click();
    driver.findElement(By.linkText("Aggiungi al carello")).click();
    driver.findElement(By.cssSelector(".fa-cart-arrow-down > path")).click();
    driver.findElement(By.linkText("X")).click();
    assertThat(driver.findElement(By.cssSelector("strong")).getText(), is("13"));
  }
}
