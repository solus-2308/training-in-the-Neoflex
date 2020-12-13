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
    private String username;

    public MailBox(WebDriver driver, String login, String password) {
        this.driver = driver;
        username = login;
        loginAs(login, password);
    }

    private void loginAs(String login, String password){
        driver.manage().window().maximize();
        driver.get("https://mail.ru");
        getWebElement(By.name("login")).sendKeys(login + Keys.ENTER);
        getWebElement(By.name("password")).sendKeys(password + Keys.ENTER);
    }

    WebElement getWebElement(By by){
        WebElement dynamicElement = (new WebDriverWait(driver, 30))
            .until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by);
    }

    void openWriter(){
        getWebElement(By.cssSelector("[data-title-shortcut='N']")).click();
    }

    void closeWriter(){
        getWebElement(By.cssSelector("button[tabindex='700']")).click();
    }

    void fillFieldsOfMessage(Message message){
        getWebElement(By.cssSelector(".container--ItIg4 [type='text']")).sendKeys(message.getWhom());
        getWebElement(By.cssSelector("input[name='Subject']")).sendKeys(message.getSubject());
        getWebElement(By.xpath("//div[@role='textbox']/div[2]")).sendKeys(message.getBody());
    }

    void saveAsDraft(){
        getWebElement(By.cssSelector("[data-title-shortcut='Ctrl\\+S']")).click();
    }

    void checkMessageAsDraft(Message message){
        getWebElement(By.xpath("//div[@id='sideBarContent']//nav/a[@href='/drafts/']")).click();
        getWebElement(By.cssSelector("a[href^='/drafts/'][tabindex]")).click();
        String checkFieldWhom = getWebElement(By.cssSelector("[class='text--1tHKB']")).getText();
        String checkFieldSubject = getWebElement(By.cssSelector("input[name='Subject']")).getAttribute("value");
        String checkFieldBody = getWebElement(By.cssSelector("div[role='textbox'] > div > div > div > div > div")).getText();
        checkData(new String[]{checkFieldWhom, checkFieldSubject, checkFieldBody}, message.getAllData());
    }

    void sendMessage(){
        getWebElement(By.cssSelector("[data-title-shortcut='Ctrl\\+Enter']")).click();
        getWebElement(By.cssSelector("[tabindex='1000']")).click();       
    }

    void checkSentMessage(Message message){
        getWebElement(By.xpath("//div[@id='sideBarContent']//nav/a[@href='/sent/']")).click();
        getWebElement(By.cssSelector("a[href^='/sent/'][tabindex]")).click();
        String checkFieldWhom = getWebElement(By.cssSelector("[class^='letter-contact']")).getAttribute("title");
        String checkFieldSubject = getWebElement(By.cssSelector("[class*='thread__subject_pony-mode']")).getText();
        // When sending a message to yourself, appear the inscription "Self: "
        if(username.equals(message.getWhom())){
            checkFieldSubject = checkFieldSubject.substring(6);
        }   
        String checkFieldBody = getWebElement(By.cssSelector("div[class^='js-helper']>div>div>div>div")).getText(); 
        checkData(new String[]{checkFieldWhom, checkFieldSubject, checkFieldBody}, message.getAllData());
    }

    private void checkData(String[] arr1, String[] arr2){
        for(int i = 0; i < arr1.length; i++){
            Assert.assertEquals(arr1[i], arr2[i]);
        }
    }

    void exit(){
        getWebElement(By.id("PH_logoutLink")).click();
    }
}