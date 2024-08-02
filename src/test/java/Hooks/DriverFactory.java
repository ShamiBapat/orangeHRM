package Hooks;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public WebDriver driver;
    Properties prop;

    //Thread local is used for parallel execution.
    // this method initializes the driver according to the browser provided
    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver initDriver(String browser){

        System.out.println("Browser is"+browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driverThreadLocal.set(new ChromeDriver());
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driverThreadLocal.set(new EdgeDriver());
                break;
            default:
                System.out.println(browser + " Browser not found");
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }
    // when more than one threads are running, all threads will call getdriver(). to manage this, synchronized keyword is used
    public static synchronized WebDriver getDriver(){

        return driverThreadLocal.get();
    }
}
