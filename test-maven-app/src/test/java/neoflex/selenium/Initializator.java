package neoflex.selenium;

import java.util.concurrent.TimeUnit; //delete
import org.testng.annotations.*;
import neoflex.ConfigReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Initializator {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger();

    @BeforeSuite
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
        WebDriverManager.operadriver().setup();
    }

    @BeforeTest
    public void setupTest() {
        try{
            ConfigReader conf = new ConfigReader();
            driver = getDriver(conf.getValue("browser"));
        }
        catch(Exception e){
            logger.error("Exception: " + e);
        }
    }

    private WebDriver getDriver(String browser){
        switch(browser){
            case "firefox":
                return new FirefoxDriver();
            case "opera":
                return new OperaDriver();
            case "edge":
                return new EdgeDriver();
            case "chrome":
                return new ChromeDriver();
        }
        return new EdgeDriver();
    }

    @AfterTest
    public void correctlyQuit() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name="getData")
    private Object[][] getData(){
        Object[][] data = {{"vip.jusper@mail.ru", "super-secret-password", "vip.jusper@mail.ru", "Automation QA", "Тут появиться текст"}};
        return data;
    }

    @Test(dataProvider="getData")
    public void draftTest(String login, String password, String whom, String subject, String body) {
        MailBox mail = new MailBox(driver, login, password);
        mail.openWriter();
        Message message = new Message(whom, subject, body);
        mail.fillFieldsOfMessage(message);
        mail.saveAsDraft();
        mail.closeWriter();
        mail.checkMessage(message);
        mail.closeWriter();
        mail.exit();
    }

}