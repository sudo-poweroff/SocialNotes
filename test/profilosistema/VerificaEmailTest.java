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

import java.util.List;

public class VerificaEmailTest {
    private WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","test/profilosistema/chromedriver.exe");
        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testVerificaEmailPrimaVerifica(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=rock1&mail=rocco.iul2000@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(1382, 736));
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Mail verificata!"));
        assertThat(driver.findElement(By.cssSelector("p")).getText(), is("Dopo aver effettuato il login potrai inserire le tue materie di interesse."));
        driver.close();
    }

    @Test
    public void testVerificaEmailModificata(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=Siuuummm&mail=cristiano@ronaldo.com&accessNumber=1");
        driver.manage().window().setSize(new Dimension(1147, 720));
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Mail verificata!"));
        {
            List<WebElement> elements = driver.findElements(By.tagName("p"));
            Assert.assertEquals(elements.size(),0);
        }
        driver.close();
    }

    @Test
    public void testVerificaEmailAccessNumberNO(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=rock1&mail=rocco.iul2000@gmail.com&accessNumber=3");
        driver.manage().window().setSize(new Dimension(1147, 720));
        assertThat(driver.getTitle(), is("SocialNotes"));
        driver.close();
    }

    @Test
    public void testVerificaEmailNonAssociata(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=rock1&mail=prova@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(777, 720));
        assertThat(driver.getTitle(), is("SocialNotes"));
        driver.close();
    }

    @Test
    public void testVerificaEmailNonInserita(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=rock1&mail=&accessNumber=0");
        driver.manage().window().setSize(new Dimension(777, 720));
        assertThat(driver.getTitle(), is("SocialNotes"));
        driver.close();
    }

    @Test
    public void testVerificaEmailGiaVerificata(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=acalifano&mail=acalifano123@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(777, 720));
        assertThat(driver.getTitle(), is("SocialNotes"));
        driver.close();
    }

    @Test
    public void testVerificaEmailUsernameNonEsistente(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=prova00&mail=abc123@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(777, 720));
        assertThat(driver.getTitle(), is("SocialNotes"));
        driver.close();
    }

    @Test
    public void testVerificaEmailUsernameEmpty(){
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=&mail=rocco.iul2000@gmail.com&accessNumber=0");
        driver.manage().window().setSize(new Dimension(777, 720));
        assertThat(driver.getTitle(), is("SocialNotes"));
        driver.close();
    }
}
