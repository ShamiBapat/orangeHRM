package pages;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LoginPage {
    private WebDriver driver;

    //constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //locators or Object Repository
    private By userName = By.name("username");
    private By passwordLocator = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By logoImg = By.xpath("//div[@class='orangehrm-login-logo']");
    private By forgotPwdLink = By.linkText("Forgot your password? ");
    private By pageNotFoundError = By.xpath("//span[@jsselect='heading']");
    private By reloadButtonPageError = By.id("reload-button");


    // methods/actions
    public void enterUserName(String username){
        WebElement uname = driver.findElement(userName);
        uname.sendKeys(username);
    }
    public boolean isReloadButtonVisible(){
        boolean flag = false;
        WebElement reload = driver.findElement(reloadButtonPageError);
        if(reload.isDisplayed()){
            return true;
        }
        return flag;
    }
    public void enterPassword(String password){
        WebElement pwd = driver.findElement(passwordLocator);
        pwd.sendKeys(password);
    }
    public void loginButtonClick() {

        driver.findElement(loginButton).click();
    }
    public void forgotPasswordLinkClick()
    {
        driver.findElement(forgotPwdLink).click();
    }
    public String getURL(){

        return driver.getCurrentUrl();
    }
    public boolean logoIsPresent(){

        return driver.findElement(logoImg).isDisplayed();
    }
    public String pageTitle(){

        return driver.getTitle();
    }

    public String getPageNotFoundErrorMsg(){
        return driver.findElement(pageNotFoundError).getText().trim();
    }
    public void spellCheckLoginPage(){
        WebElement allPage = driver.findElement(By.tagName("body"));
        String PageText = allPage.getText();
        JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());
        try {
            List<RuleMatch> errors = languageTool.check(PageText);
            if(errors.isEmpty()){
                System.out.println("No spelling errors on the page");
            }
            else{
                System.out.println("Please find the below spelling errors");
                for(RuleMatch eachError:errors){
                    System.out.println("Potential typo "+eachError.getSentence());
                    System.out.println("possible match to be replaced "+eachError.getSuggestedReplacements());
                    System.out.println("-------------------------------------------------------------");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean LogoAlignment() throws InterruptedException {
        boolean logoLocation = false;
        Thread.sleep(5000);
        WebElement logo = driver.findElement(logoImg);
//        System.out.println("Height = "+logo.getRect().getHeight()+"Width="+logo.getRect().getWidth());
        String Location = logo.getCssValue("text-align");
        System.out.println("Alignment = "+Location);
        return logoLocation;
    }
    public Boolean textColor() throws InterruptedException {
        Thread.sleep(5000);
        boolean flag = false;
        WebElement color = driver.findElement(userName);
        String color1 = color.getCssValue("color");
        String hexaValue = Color.fromString(color1).asHex();
        System.out.println(hexaValue);
        if(hexaValue.equalsIgnoreCase("#64728c")){
            return flag = true;
        }
        else{
        return flag; }
    }
    public boolean invalidURLCheck(String link){
        HttpURLConnection huc = null;
        boolean flag = false;
        try {
            huc = (HttpURLConnection)(new URL(link).openConnection());
            huc.connect();
            System.out.println(huc.getResponseCode());

            if(huc.getResponseCode()>=400){
                System.out.println("Invalid link");
                return false;
            }
            if(huc.getResponseCode()==200){
                System.out.println("valid url");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public void brokenLinksOnPage(String URL){
        HttpURLConnection huc = null;
        String homePage = URL;
        int respCode = 200;
        List<WebElement> Links = driver.findElements(By.tagName("a"));
        for(WebElement link:Links){
            String url = link.getAttribute("href");
            System.out.println(url);
            if(url==null || url.isEmpty()){
                System.out.println("Empty URL ");
                continue;
            }
            if(!url.startsWith(homePage)){
                System.out.println("URL is from different domain, skipping it");
            }
            try{
                huc = (HttpURLConnection)(new URL(url).openConnection());
                huc.connect();
                if(huc.getResponseCode()>=400){
                    System.out.println("Broken link");
                }
                else
                    System.out.println("valid url");

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
