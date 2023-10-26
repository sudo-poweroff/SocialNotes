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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
public class VisualizzazioneSegnalazioniTest {
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
  public void testVisualizzazioneSegnalazioneNull() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("UsersManager");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Users1");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".col:nth-child(1) .card-title")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".card"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void testVisualizzazioneSegnalazioneNotNull() {
    Connection connection=null;
    String url = "jdbc:mysql://localhost:3306/socialnotes?serverTimezone=UTC";
    String username = "SocialNotes";
    String password = "SocialNotes2023";
    try {
      connection = DriverManager.getConnection(url, username, password);
      String sql = "INSERT INTO Segnalazione (Stato,Motivo,Username) VALUES (0,'test segnalazione','simo')";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);  // Esegui l'inserimento
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("UsersManager");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("Users1");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".col:nth-child(1) .card-title")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("sortable"));
      assert(elements.size() > 0);
    }
    try {
      String sql="DELETE FROM Segnalazione WHERE Stato=0 AND Motivo='test segnalazione' AND Username='simo'";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.executeUpdate();
      connection.close();
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
