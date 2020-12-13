package neoflex.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

class MailBox {

    private WebDriver driver;

    public MailBox(WebDriver driver, String login, String password) {
        this.driver = driver;
        loginAs(login, password);
    }

    private void loginAs(String login, String password){
        driver.manage().window().maximize();
        driver.get("https://mail.ru");
        getWebElement(By.name("login")).sendKeys(login + Keys.ENTER);
        getWebElement(By.name("password")).sendKeys(password + Keys.ENTER);
    }

    WebElement getWebElement(By by){
        WebElement dynamicElement = (new WebDriverWait(driver, 25))
            .until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by);
    }

    void openWriter(){
        getWebElement(By.cssSelector("a[title='Написать письмо']")).click();
    }

    void closeWriter(){
        getWebElement(By.cssSelector("button[title='Закрыть']")).click();
    }

    void fillFieldsOfMessage(Message message){
        getWebElement(By.cssSelector(".container--ItIg4 [type='text']")).sendKeys(message.getWhom());
        getWebElement(By.cssSelector("input[name='Subject']")).sendKeys(message.getSubject());
        getWebElement(By.xpath("//div[@role='textbox']/div[2]")).sendKeys(message.getBody());
    }

    void saveAsDraft(){
        getWebElement(By.cssSelector("span[title='Сохранить']")).click();
    }

    void checkMessage(Message message){
        getWebElement(By.linkText("Черновики")).click();
        getWebElement(By.cssSelector(".dataset__items > a:nth-of-type(1)")).click();
        String checkFieldWhom = getWebElement(By.cssSelector("[class='text--1tHKB']")).getText();
        String checkFieldSubject = getWebElement(By.cssSelector("input[name='Subject']")).getAttribute("value");
        String checkFieldBody = getWebElement(By.cssSelector("div[role='textbox'] > div > div > div > div > div")).getText();
        Assert.assertEquals(checkFieldWhom, message.getWhom());
        Assert.assertEquals(checkFieldSubject, message.getSubject());
        Assert.assertEquals(checkFieldBody, message.getBody());
    }

    void exit(){
        getWebElement(By.id("PH_logoutLink")).click();
    }
}