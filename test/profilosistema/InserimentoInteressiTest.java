package profilosistema;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InserimentoInteressiTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","test/materialesistema/chromedriver");
        //System.setProperty("webdriver.chrome.driver","test/profilosistema/chromedriver.exe");
        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testInserimentoInteressiAccessNumberErrato() {
        driver.get("http://localhost:8080/SocialNotes/Verifica?username=ggallocca1&mail=luigi.allocca12345@live.it&accessNumber=9");
        Assert.assertEquals("http://localhost:8080/SocialNotes/homepage.jsp", driver.getCurrentUrl());
    }

}
