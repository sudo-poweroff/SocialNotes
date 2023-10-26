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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.sql.*;
import java.util.*;
public class VisualizzaMaterialeDaApprovareTest {
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
  public void testVisualizzazioneMaterialeDaApprovareNotNull() {
    Connection connection=null;
    String url = "jdbc:mysql://localhost:3306/socialnotes?serverTimezone=UTC";
    String username = "SocialNotes";
    String password = "SocialNotes2023";
    try {
      connection = DriverManager.getConnection(url, username, password);
      String sql = "INSERT INTO Materiale (DataCaricamento,Keywords,Costo,Descrizione,Hidden,CodiceCorso,Username,nomeFile) VALUES ('2021-01-03', '', -1, 'test', true, 46, 'simo', 'appuntiGPS_48.pdf')";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);  // Esegui l'inserimento
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("Notesmanager");
    driver.findElement(By.id("inputPassword")).sendKeys("Notes1");
    driver.findElement(By.id("inputPassword")).sendKeys(Keys.ENTER);
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".form-row"));
      assert(elements.size() > 0);
    }
    try {
      String sql="DELETE FROM Materiale WHERE DataCaricamento='2021-01-03' AND Keywords='' AND Costo=-1 AND  Descrizione='test' AND Hidden=true AND CodiceCorso=46 AND Username='simo' AND nomeFile='appuntiGPS_48.pdf';";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.executeUpdate();
      connection.close();
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  @Test
  public void testVisualizzazioneMaterialeDaApprovareNull() {
    driver.get("http://127.0.0.1:8080/SocialNotes/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("inputEmail")).sendKeys("Notesmanager");
    driver.findElement(By.id("inputPassword")).sendKeys("Notes1");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.cssSelector(".card-subtitle")).click();
    assertThat(driver.findElement(By.cssSelector(".card-subtitle")).getText(), is("Nessun materiale da approvare."));
  }


}
