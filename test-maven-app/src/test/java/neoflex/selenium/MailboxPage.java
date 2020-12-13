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

    public MailBox(WebDriver driver) {
        this.driver = driver;
    }

    WebElement getWebElement(By by){
        WebElement dynamicElement = (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by);
    }

    void openWriter(){
        driver.manage().window().maximize();
        getWebElement(By.cssSelector("a[title='Написать письмо'] > .compose-button__wrapper")).click();
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
        String checkField = getWebElement(By.cssSelector("[class='text--1tHKB']")).getText();
        Assert.assertEquals(checkField, message.getWhom());
        checkField = getWebElement(By.cssSelector("[class='text--1tHKB']")).getText();
    }


}