package StepDefinitions;

import Hooks.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LeavePage;
import pages.LoginPage;

import java.util.ResourceBundle;

public class LeavePageSteps{
    String url;
    private LeavePage lp = new LeavePage(DriverFactory.getDriver());
    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    public static ResourceBundle config =ResourceBundle.getBundle("config");

    @Given("user navigates to Leave menu")
    public void user_navigates_to_leave_menu(){
        lp.clickLeaveLink();
    }
    @When("user clicks apply leave button")
    public void user_clicks_apply_leave_button() {
        lp.clickApplyTop();
    }
    @Then("{string} screen can be viewed")
    public void screenCanBeViewed(String url) {
        String expectedUrl =loginPage.getURL();
        System.out.println(url);
        System.out.println("Expected = "+expectedUrl);
        Assert.assertTrue(expectedUrl.contains(url));
    }

    @Given("admin is logged in and on apply leave page")
    public void adminIsLoggedInAndOnApplyLeavePage() {
        lp.clickLeaveLink();
        lp.clickApplyTop();
    }
    @When("user selects leave type from the leave screen")
    public void user_selects_leave_type_from_the_leave_screen() throws InterruptedException {
        lp.selectLeaveType();
    }
    @Then("leave balance can be viewed")
    public void leave_balance_can_be_viewed() {
        String balance = lp.availableLeave();
        if(balance!=null){
            System.out.println("Leave balance is "+balance);
        }
        else
            System.out.println("Leave balance not visible");
    }
    @When("user selects from and to dates on the leave screen")
    public void user_selects_from_and_to_dates_on_the_leave_screen() throws InterruptedException {

    }
    @When("user enters as {string} in comments section")
    public void user_enters_as_in_comments_section(String string)
    {
        lp.enterComments(string);
    }
    @Then("Success message can be viewed by the user")
    public void success_message_can_be_viewed_by_the_user() {

    }

    @And("user clicks Apply button")
    public void userClicksApplyButton() {
    }

    @When("user selects {string} as from date on the leave screen")
    public void userSelectsAsFromDateOnTheLeaveScreen(String fromDate) throws InterruptedException {
        lp.selectDatePicker(fromDate);
    }

    @And("user selects {string} as to date on the leave screen")
    public void userSelectsAsToDateOnTheLeaveScreen(String toDate) throws InterruptedException {
        lp.selectDatePicker(toDate);
    }
}
