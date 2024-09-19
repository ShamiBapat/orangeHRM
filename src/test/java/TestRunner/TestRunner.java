package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;


@CucumberOptions(
		features= {"src/test/resources/Features"},
		glue= {"StepDefinitions","Hooks"},
		tags = "@ExcelData",

		plugin= {"pretty","html:target/cucumber-reports/reports.html",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
				"json:target/cucumber-reports/cucumber.json",
				"junit:target/cucumber-reports/cucumber.xml",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/rerun.txt"},
				publish=true

)

public class TestRunner extends AbstractTestNGCucumberTests{

	@BeforeClass(alwaysRun = true)
	@Parameters({"browser"})
	public void setUp(String browser, ITestContext context) {
		// Set the browser parameter as a system property
		System.setProperty("browser", browser);
		System.out.println("Browser passed to TestRunner: " + browser);
	}

	// With this setup, TestNG will run each scenario in parallel using the specified number of threads.
	// Ensure the testng.xml file is properly configured to pass the browser parameter and run tests in parallel.

//		@Override
		@DataProvider(parallel = true)
		public Object[][] scenarios(){
			return super.scenarios();
        }

}