package materialesistema;
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
public class VisualizzazioneSegnalazioniArchiviateTest {
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
  public void testVisualizzazioneSegnalazioneArchiviateNotNull() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("UsersManager");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Users1");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".col:nth-child(2) .card-title")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".list-item"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testVisualizzazioneSegnalazioniArchiviateNull() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("UsersManager");
    driver.findElement(By.id("inputPassword")).sendKeys("Users1");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".col:nth-child(2) .card-title")).click();
    assertThat(driver.findElement(By.cssSelector(".card-subtitle")).getText(), is("Nessuna segnalazione presente."));
  }
}
