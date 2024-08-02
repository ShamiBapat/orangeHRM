package pages;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private Actions action;

    //constructor
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        this.action = new Actions(driver);
    }

    //locators or Object Repository
    private By userName = By.name("username");
    private By passwordLocator = By.name("password");
    private By loginButton = By.xpath("//button[normalize-space()='Login']");
    private By logoImg = By.xpath("//div[@class='orangehrm-login-logo']");
    private By forgotPwdLink = By.linkText("Forgot your password? ");
    private By pageNotFoundError = By.xpath("//span[@jsselect='heading']");
    private By reloadButtonPageError = By.id("reload-button");
    private By invalidLoginMsg = By.xpath("//div[@class='oxd-alert-content oxd-alert-content--error']");

    // methods/actions

    public String currentURL(){
        return driver.getCurrentUrl();
    }
    public void enterUserName(String username){
        WebElement uname = driver.findElement(userName);
        uname.sendKeys(username);
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
        Thread.sleep(1000);
        WebElement logo = driver.findElement(logoImg);
        // Get the element's X coordinate
        int xCoord = logo.getLocation().getX();
        // Get the viewport width
        Dimension dim = driver.manage().window().getSize();
        int viewportWidth = dim.getWidth();
        // Determine if the element is on the right side
        boolean isElementOnRightSide = xCoord > (viewportWidth / 2);
        if (isElementOnRightSide) {
            System.out.println("The element is positioned on the right side of the webpage.");
            logoLocation = true;
        } else {
            System.out.println("The element is not positioned on the right side of the webpage.");
            logoLocation = false;
        }
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
            URL url = new URL("https://www.abcd.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

         //   huc = (HttpURLConnection)(new URL(link).openConnection());
         //   System.out.println("connecting..");
         //   huc.connect();
            System.out.println(connection.getResponseCode());

            if(connection.getResponseCode()>=400){
                System.out.println("Invalid link");
                flag = true;
            }
            else {
                System.out.println("valid url");
                flag =false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("flag value "+flag);
        return flag;
    }

    public void checkInvalidURL(String link) throws IOException {
        String invalidUrl = link;
        URL url = new URL(invalidUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();

        int statusCode = connection.getResponseCode();
        if (statusCode ==200) {

            System.out.println("the valid URL is: " + url + "-" + statusCode);
        }
        if (statusCode == HttpURLConnection.HTTP_NOT_FOUND ) {

            System.out.println("The invalid URL is: " + url + " #statusCode: " + statusCode);

        }
        // System.out.println("Status Code: " + statusCode);

        connection.disconnect();

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
    public void mouseClickLogin(){
        WebElement loginBtn = driver.findElement(loginButton);
        action.click(loginBtn).perform();
    }

    public void keyboardPressLogin(){
        WebElement loginBtn = driver.findElement(loginButton);
        loginBtn.sendKeys(Keys.RETURN);
    }

    public String getErrorMsg(){
        return driver.findElement(invalidLoginMsg).getText();
    }
}
