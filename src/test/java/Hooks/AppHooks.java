package Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

//AppHooks class initializes and quits the driver properly
public class AppHooks {

   private WebDriver driver; // Instance variable instead of static//
    // public static final ResourceBundle config =ResourceBundle.getBundle("config");

    @Before
//    @Parameters("browser")
    public void setup(){
        // Retrieve the browser parameter from the system properties set in the TestNG suite(xml file)
        String browser = System.getProperty("browser");
        System.out.println("Browser in hooks: "+browser);
        driver = DriverFactory.getInstance().initDriver(browser);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @After(order=0)
    public void tearDown(){
        DriverFactory.quitBrowser();
    }

    @After(order=1)
    public void TakeFailedScreenshots(Scenario scenario){
        if(scenario.isFailed()){
            //take screenshot of only failed scenarios
            String screenShotName=scenario.getName().replaceAll("","_");
            //Bytes should be the choice as file option cannot be accessed when project is run on jenkins.
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            //below code will attach the screenshots as images in reports
            scenario.attach(sourcePath,"image/png",screenShotName);
        }
    }

    }
