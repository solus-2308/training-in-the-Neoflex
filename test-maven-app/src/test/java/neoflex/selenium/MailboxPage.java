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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

class MailBox {

    final private WebDriver driver;
    final private String username;

    @FindBy(name = "login")
    private WebElement loginField;
    
    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = "[data-title-shortcut='N']")
    private WebElement openWriterButton;

    @FindBy(css = "button[tabindex='700']")
    private WebElement closeWriterButton;

    @FindBy(css = ".container--ItIg4 [type='text']")
    private WebElement writerWhomField;

    public MailBox(WebDriver driver, String login, String password) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        username = login;
        loginAs(login, password);
    }

    private void loginAs(String login, String password){
        driver.manage().window().maximize();
        driver.get("https://mail.ru");
        waitFor(loginField).sendKeys(login + Keys.ENTER);
        waitFor(passwordField).sendKeys(password + Keys.ENTER);
    }

    WebElement getWebElement(By by){
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(by));
    }
    WebElement waitFor(WebElement elem){
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(elem));
    }

    void openWriter(){
        waitFor(openWriterButton).click();
    }

    void closeWriter(){
        waitFor(closeWriterButton).click();
    }

    void fillFieldsOfMessage(Message message){
        waitFor(writerWhomField).sendKeys(message.getWhom());
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
        checkData( checkFieldWhom, checkFieldSubject, checkFieldBody, message);
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
        checkData( checkFieldWhom, checkFieldSubject, checkFieldBody, message);
    }

    private void checkData(String checkWhom, String checkSubject, String checkBody, Message message){
        Assert.assertEquals(checkWhom, message.getWhom());
        Assert.assertEquals(checkSubject, message.getSubject());
        Assert.assertEquals(checkBody, message.getBody());
    }

    void exit(){
        getWebElement(By.id("PH_logoutLink")).click();
    }
}