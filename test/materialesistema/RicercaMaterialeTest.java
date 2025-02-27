package materialesistema;
// Generated by Selenium IDE
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

public class RicercaMaterialeTest {
	private WebDriver driver;
	private Map<String, Object> vars;
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
	public void testRicercaMaterialeByCorsoNoRating() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.id("ricerca")).click();
		driver.findElement(By.id("ricerca")).sendKeys("analisi");
		driver.findElement(By.cssSelector(".fas")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".col-xs-12:nth-child(4)"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByCorsoNotPresent() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.id("ricerca")).click();
		driver.findElement(By.id("ricerca")).sendKeys("glottologia");
		driver.findElement(By.cssSelector(".btn")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".card"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByCorsoRating() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.id("ricerca")).click();
		driver.findElement(By.id("ricerca")).sendKeys("programmazone");
		driver.findElement(By.id("ricerca")).sendKeys(Keys.DOWN);
		driver.findElement(By.id("ricerca")).sendKeys("programmazione");
		driver.findElement(By.id("ricerca")).sendKeys(Keys.ENTER);
		driver.findElement(By.cssSelector("label:nth-child(4)")).click();
		driver.findElement(By.cssSelector(".btn-default")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".col-xs-12"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByCorsoVuoto() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.cssSelector(".btn")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".card"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByNameNoRating() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.id("ricerca")).click();
		driver.findElement(By.id("ricerca")).sendKeys("derivate");
		driver.findElement(By.cssSelector(".btn")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".col-xs-12:nth-child(4)"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByNameNotPresent() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.id("ricerca")).click();
		driver.findElement(By.id("ricerca")).sendKeys("k");
		driver.findElement(By.cssSelector(".btn")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".card"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByNameRating() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.id("ricerca")).click();
		driver.findElement(By.id("ricerca")).sendKeys("prog");
		driver.findElement(By.cssSelector(".btn")).click();
		driver.findElement(By.cssSelector("label:nth-child(6)")).click();
		driver.findElement(By.cssSelector(".btn-default")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".col-xs-12:nth-child(4)"));
			assert(elements.size() > 0);
		}
	}
	@Test
	public void testRicercaMaterialeByNameVuoto() {
		driver.get("http://localhost:8080/SocialNotes/");
		driver.manage().window().setSize(new Dimension(1848, 913));
		driver.findElement(By.cssSelector(".btn")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector(".card"));
			assert(elements.size() > 0);
		}
	}
}
