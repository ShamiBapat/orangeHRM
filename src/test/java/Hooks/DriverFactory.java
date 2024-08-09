package Hooks;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

//DriverFactory class initializes and manages WebDriver instances correctly:
public class DriverFactory {

    private static DriverFactory instance = null;


    private DriverFactory(){
        // Private constructor to prevent instantiation
    }
    //Thread local is used for parallel execution.
    // this method initializes the driver according to the browser provided
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static DriverFactory getInstance(){
        if(instance==null){
            synchronized (WebDriverManager.class){
                if(instance==null){
                    instance =new DriverFactory();
                }
            }
        }
        return instance;
    }

    public WebDriver initDriver(String browser){
        if (browser == null) {
            throw new IllegalArgumentException("Browser parameter cannot be null.");
        }
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
        if(driverThreadLocal.get()==null){
            throw new IllegalStateException("Driver not initialized. Call initDriver() first.");
        }
        return driverThreadLocal.get();
    }
    public static void quitBrowser(){
        if(driverThreadLocal.get()==null){
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
