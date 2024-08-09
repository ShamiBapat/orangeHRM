
package StepDefinitions;

import Hooks.AppHooks;
import Hooks.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pages.ExcelReader;
import pages.LoginPage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoginPageSteps{

    private static final Logger log = LoggerFactory.getLogger(LoginPageSteps.class);
    private WebDriver driver;
    String url;
    String invalidUrl;

    private LoginPage loginPage;
    public static ResourceBundle config =ResourceBundle.getBundle("config");

    public LoginPageSteps () {
//        loginPage = new LoginPage(DriverFactory.getDriver());
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(driver);

//        this.driver = appHooks.getDriver();
//        this.loginPage = new LoginPage(driver);
    }


    @Given("User launches the browser")
    public void user_launches_the_browser() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }
    @When("User gives correct website link")
    public void user_gives_correct_website_link() {
        url = config.getString("validURL");
         DriverFactory.getDriver().get(url);
    }
    @Then("user is navigated to the home page of website")
    public void user_is_navigated_to_the_home_page_of_website() {
        Assert.assertEquals(loginPage.pageTitle(),"OrangeHRM");
    }
    @When("user gives invalid link")
    public void user_gives_invalid_link() throws IOException {
        loginPage.invalidURLCheck(config.getString("invalidURL"));
//        try {
//            DriverFactory.getDriver().get(invalidUrl);
//        } catch (WebDriverException e) {
//            System.out.println("invalid url provided");
//           e.printStackTrace();
//        }
    }
    @Then("user lands on invalid link")
    public void user_receives_page_not_found_error() throws IOException {
       boolean flag = loginPage.invalidURLCheck(config.getString("invalidURL"));
        System.out.println(flag);
        Assert.assertFalse(flag);
    }
    @Then("HTTP response >= {int}. Then the link is broken")
    public void http_response_then_the_link_is_broken(Integer int1) {
    loginPage.brokenLinksOnPage(config.getString("validURL"));
    }
    @Then("user should see correct spellings in all fields")
    public void user_should_see_correct_spellings_in_all_fields() {
            loginPage.spellCheckLoginPage();
    }
    @Then("user should see logo on the right side")
    public void user_should_see_logo_on_the_right_side() throws InterruptedException {
            Boolean locationFlag = loginPage.LogoAlignment();
            Assert.assertTrue(locationFlag);
    }
    @Then("user should see username in gray color")
    public void user_should_see_username_in_gray_color() throws InterruptedException {
        Assert.assertTrue(loginPage.textColor());
    }


    @Given("User enters invalid {string} and {string}")
    public void userEntersInvalidAnd(String uname, String pwd) {
        loginPage.enterUserName(uname);
        loginPage.enterPassword(pwd);
    }

    @When("User clicks login button using mouse")
    public void userClicksLoginButtonUsingMouse() {
        loginPage.mouseClickLogin();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("user gets error message as {string}")
    public void userGetsErrorMessageAs(String expectedMsg) {
        System.out.println("Expected="+expectedMsg);
        String actual = loginPage.getErrorMsg();
        System.out.println("Actual="+loginPage.getErrorMsg());
        Assert.assertEquals(actual,expectedMsg);
    }

    @When("user presses enter key on keyboard")
    public void userPressesEnterKeyOnKeyboard() {
        loginPage.keyboardPressLogin();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Given("user launches OrangeHRM")
    public void userLaunchesORangeHRM() throws InterruptedException {
        url = config.getString("validURL");
        DriverFactory.getDriver().get(url);
        Thread.sleep(5000);
    }

    @Then("user gets error message  {string} with unsuccessful login")
    public void userGetsErrorMessageWithUnsuccessfulLogin(String expectedMsg) {
        System.out.println("Expected="+expectedMsg);
        String actual = loginPage.getErrorMsg();
        System.out.println("Actual="+loginPage.getErrorMsg());
        Assert.assertEquals(actual,expectedMsg);
    }

    @Given("user enters credentials from {string} and {int}")
    public void user_enters_credentials_from_and(String sheetname, Integer rownumber) throws IOException {
       // String filePath = "./src/test/resources/TestData/AutomationTestData.xlsx";
       // System.out.println(filePath);
        ExcelReader reader = new ExcelReader();
        List<Map<String,String>> testData = reader.getTestDataFromSheet(sheetname);
        System.out.println("data from excel="+testData);
        String uName = testData.get(rownumber).get("username");
        System.out.println(uName);
        String pwd = testData.get(rownumber).get("password");
        System.out.println(pwd);
        loginPage.enterUserName(uName);
        loginPage.enterPassword(pwd);
    }

    @Then("user logs in successfully and lands on {string} url")
    public void userLogsInSuccessfullyAndLandsOn(String url) {
        String actual = loginPage.currentURL();
        System.out.println(url);
        System.out.println(actual);
        Assert.assertTrue(actual.contains(url));
    }

    @And("user enters {string} and {string}")
    public void userEntersAnd(String uname, String pwd) {
        loginPage.enterUserName(uname);
        loginPage.enterPassword(pwd);
    }
}