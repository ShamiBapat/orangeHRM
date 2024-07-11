
package StepDefinitions;

import Hooks.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import pages.LoginPage;
import utilities.configReader;

import java.util.Properties;
import java.util.ResourceBundle;

public class LoginPageSteps{

    private WebDriver driver;
    String url;
    String invalidUrl;

    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    public static ResourceBundle config =ResourceBundle.getBundle("config");
    configReader cp;
    Properties prop;

    @Given("User launches the browser")
    public void user_launches_the_browser() {

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
    public void user_gives_invalid_link() {
        invalidUrl = config.getString("invalidURL");
//        try {
//            DriverFactory.getDriver().get(invalidUrl);
//        } catch (WebDriverException e) {
//            System.out.println("invalid url provided");
//           e.printStackTrace();
//        }
    }
    @Then("user lands on invalid link")
    public void user_receives_page_not_found_error() {
       Boolean flag = loginPage.invalidURLCheck("https://www.abcd.com");
        System.out.println(flag);
       Assert.assertTrue(flag);
        //Assert.assertTrue(loginPage.isReloadButtonVisible());
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



}