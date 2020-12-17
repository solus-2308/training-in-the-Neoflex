package neoflex.selenium;

import org.testng.annotations.*;
import neoflex.ConfigReader;
import neoflex.selenium.testSupportClasses.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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

    @BeforeMethod
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

    @AfterMethod
    public void correctlyQuit() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name="getData")
    private Object[][] getData(){
        Object[][] data = {{"login@mail.ru", "password", "anotherUser@mail.ru", "Subject", "Text"}};
        return data;
    }

    @Test(dataProvider="getData")
    public void draftTest(String login, String password, String whom, String subject, String body) {
        logger.info("Входим на почтовую страницу");
        MailBox mail = new MailBox(driver, login, password);
        mail.openWriter();
        Message message = new Message(whom, subject, body);
        logger.info("Заполняем поля письма");
        mail.fillFieldsOfMessage(message);
        mail.saveAsDraft();
        mail.closeWriter();
        logger.info("Проверяем правильность заполнения полей");
        mail.checkMessageAsDraft(message);
        mail.closeWriter();
        logger.info("Выходим");
        mail.exit();
    }

    @Test(dataProvider="getData")
    public void sendMessage(String login, String password, String whom, String subject, String body){
        logger.info("Входим на почтовую страницу");
        MailBox mail = new MailBox(driver, login, password);
        mail.openWriter();
        Message message = new Message(whom, subject, body);
        logger.info("Заполняем поля письма");
        mail.fillFieldsOfMessage(message);
        mail.sendMessage();
        logger.info("Проверяем поля отправленного письма на эквивалентность шаблону");
        mail.checkSentMessage(message);
        logger.info("Выходим");
        mail.exit();
    }

}