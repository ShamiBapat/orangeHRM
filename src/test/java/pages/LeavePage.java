package pages;

import Hooks.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class LeavePage{
    private WebDriver driver;
    private Actions action;

    //constructor
    public LeavePage(WebDriver driver)
    {
        this.driver = driver;
        this.action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // locators
    private By LeaveLink = By.xpath("//span[text()='Leave']");
    private By ApplyTop = By.linkText("Apply");
    private By LeaveType = By.xpath("//div[@class='oxd-select-text-input']");
    private By leaveTypeArrow = By.xpath("//div[@class='oxd-select-text--after']");
    private By FromDate = By.xpath("//div[@class='oxd-grid-4 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//input[1]");
    private By ToDate = By.xpath("//input[@placeholder='mm-dd-yyyy'])[2]");
    private By leaveBalance = By.cssSelector(".oxd-text.oxd-text--p.orangehrm-leave-balance-text");
    private By calender = By.className("oxd-date-input-calender");
    private By previousCalender = By.xpath("//div[@class='oxd-form-row']//button[1]");
    private By nextCalender = By.xpath("//i[@class='oxd-icon bi-chevron-right']");
    private By monthCalender = By.className("oxd-calendar-selector-month");
    private By yearCalender = By.className("oxd-calendar-selector-year");
    private By commentsLocator = By.tagName("textarea");


    // Methods/actions


    public void clickLeaveLink(){
        driver.findElement(LeaveLink).click();
    }

    public void clickApplyTop()
    {
        driver.findElement(ApplyTop).click();

    }
    public void selectLeaveType() throws InterruptedException {
        driver.findElement(leaveTypeArrow).click();
        WebElement dropdown = driver.findElement(LeaveType);
        WebElement option = driver.findElement(By.xpath("//div[@role='listbox"));
        dropdown.click();
        option.click();
        Thread.sleep(5000);
    }
     public String availableLeave()
     {
        return driver.findElement(leaveBalance).getText();

     }
    public void selectDatePicker(String expectedDate) throws InterruptedException {
        // Split the date into year, month, and day
        String[] dateParts = expectedDate.split("-");
        String year = dateParts[0];
        System.out.println("year="+year);
        String month = dateParts[1];
        System.out.println("month="+month);
        String day = dateParts[2];
        System.out.println("day="+day);

        String expectedMonth = "December";
        String expectedYear = "2025";
        String expectedDay = "25";

        driver.findElement(FromDate).click();
        String currentMonth = driver.findElement(monthCalender).getText();
        System.out.println("Current month ="+currentMonth);
        String currentYear = driver.findElement(yearCalender).getText();
        System.out.println("Current year="+currentYear);

        while(!(currentMonth.equalsIgnoreCase(month) && currentYear.equalsIgnoreCase(year))){
            driver.findElement(nextCalender).click();
            currentMonth = driver.findElement(monthCalender).getText();
            currentYear = driver.findElement(yearCalender).getText();
        }
        driver.findElement(By.xpath("//div[text()='25']")).click();
        Thread.sleep(5000);

        System.out.println("date selected= "+currentMonth+" "+currentYear);
    }
    public void enterComments(String comments){
        driver.findElement(commentsLocator).sendKeys(comments);
    }

}
