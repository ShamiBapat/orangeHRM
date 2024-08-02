package Hooks;

import com.beust.jcommander.Parameter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import utilities.configReader;

import java.io.File;
import java.util.Properties;

public class AppHooks {

   DriverFactory driverFactory;
   WebDriver driver;
   configReader cp;
   Properties prop;
   LoginPage login;

    @Before(order =0)
    public void readProperty(){
       cp = new configReader();
       prop = cp.init_prop();
    }
    //@Parameters({"browserName"})
    @Before(order=1)
    public void launchBrowser(){
        String browserName = prop.getProperty("browser");
        driverFactory=new DriverFactory();
        driver = driverFactory.initDriver(browserName);
    }

    @After(order=0)
    public void tearDown(){
        driver.quit();
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
