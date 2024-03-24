package Bluebrix.QA.Platform.BaseTest;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import Bluebrix.QA.Platform.DriverFactory.DriverFactory;
import Bluebrix.QA.Platform.Pages.DashBoardPage;
import Bluebrix.QA.Platform.Pages.LoginPage;

public class BaseTest {
	DriverFactory df;
	protected WebDriver driver;
	protected Properties prop;
	protected DashBoardPage dashboard;
	protected LoginPage logpage;
	protected SoftAssert softassert;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df=new DriverFactory();
		prop=df.initProperties();
		if (browserName!=null) {
			System.out.println("the browser name passed: "+browserName);
			prop.setProperty("browser", browserName);
		}
		driver=df.initBrowser(prop);
		logpage=new LoginPage(driver);
		softassert=new SoftAssert();
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
