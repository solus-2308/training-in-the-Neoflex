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

    @FindBy(css = "input[tabindex='100']")
    private WebElement writerWhomField;
    @FindBy(css = "input[name='Subject']")
    private WebElement writerSubjectField;
    @FindBy(css = "[tabindex='505']")
    private WebElement writerBodyField;

    @FindBy(css = "[data-title-shortcut='Ctrl\\+S']")
    private WebElement saveAsDraftMessageButton;

    @FindBy(xpath = "//div[@id='sideBarContent']//nav/a[@href='/drafts/']")
    private WebElement openDraftsButton;
    @FindBy(css = "a[href^='/drafts/'][tabindex]")
    private WebElement openDraftMessageButton;

    @FindBy(css = "[class='text--1tHKB']")
    private WebElement checkDraftWhomField;
    @FindBy(css = "input[name='Subject']")
    private WebElement checkDraftSubjectField;
    @FindBy(css = "[class^='cl_']>div")
    private WebElement checkDraftBodyField;

    @FindBy(css = "[data-title-shortcut='Ctrl\\+Enter']")
    private WebElement sendMessageButton;
    @FindBy(css = "[tabindex='1000']")
    private WebElement closeNotificationButton;

    @FindBy(xpath = "//div[@id='sideBarContent']//nav/a[@href='/sent/']")
    private WebElement openSentsButton;
    @FindBy(css = "a[href^='/sent/'][tabindex]")
    private WebElement openSentMessageButton;

    @FindBy(css = "[class^='letter-contact']")
    private WebElement checkSentWhomField;
    @FindBy(css = "[class*='thread__subject_pony-mode']")
    private WebElement checkSentSubjectField;
    @FindBy(css = "[class^='cl_']>div")
    private WebElement checkSentBodyField;

    @FindBy(id = "PH_logoutLink")
    private WebElement exitButton;

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
        waitFor(writerSubjectField).sendKeys(message.getSubject());
        waitFor(writerBodyField).sendKeys(message.getBody());
    }

    void saveAsDraft(){
        waitFor(saveAsDraftMessageButton).click();
    }

    void checkMessageAsDraft(Message message){
        waitFor(openDraftsButton).click();
        waitFor(openDraftMessageButton).click();
        String checkFieldWhom = waitFor(checkDraftWhomField).getText();
        String checkFieldSubject = waitFor(checkDraftSubjectField).getAttribute("value");
        String checkFieldBody = waitFor(checkDraftBodyField).getText();
        checkData( checkFieldWhom, checkFieldSubject, checkFieldBody, message);
    }

    void sendMessage(){
        waitFor(sendMessageButton).click();
        waitFor(closeNotificationButton).click();       
    }

    void checkSentMessage(Message message){
        waitFor(openSentsButton).click();
        waitFor(openSentMessageButton).click();
        String checkFieldWhom = waitFor(checkSentWhomField).getAttribute("title");
        String checkFieldSubject = waitFor(checkSentSubjectField).getText();
        // When sending a message to yourself, appear the inscription "Self: "
        if(username.equals(message.getWhom())){
            checkFieldSubject = checkFieldSubject.substring(6);
        }   
        String checkFieldBody = waitFor(checkSentBodyField).getText(); 
        checkData( checkFieldWhom, checkFieldSubject, checkFieldBody, message);
    }

    private void checkData(String checkWhom, String checkSubject, String checkBody, Message message){
        Assert.assertEquals(checkWhom, message.getWhom());
        Assert.assertEquals(checkSubject, message.getSubject());
        Assert.assertEquals(checkBody, message.getBody());
    }

    void exit(){
        waitFor(exitButton).click();
    }
}