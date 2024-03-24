package Bluebrix.QA.Platform.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Bluebrix.QA.Platform.BaseTest.BaseTest;

public class DashBoardTest extends BaseTest {

	@BeforeClass
	public void dashboardPageSetup() {
		dashboard = logpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] CreateAppData() {
		return new Object[][] {
				{ "Mobile", "Mob Test", "short desc mob", "Mumbai", "Lithuanian", "Patient app",
						"./src/test/resources/TestData/UploadFiles/Mobapp.png" },
				{ "Web", "WebAPP", "short desc", "Mumbai", "English (Standard)", "Patient app",
						"./src/test/resources/TestData/UploadFiles/Webapp.png" } };
	}

	@Test(dataProvider="CreateAppData")
	public void appCreationTest(String appTemp, String appName, String shortDesc, String deployRegion,
			 String defaultLang,String appType,String filePath) throws InterruptedException {
		String[] languages = { "Armenian", "Lithuanian","English (Standard)","Chinese"};
		Assert.assertTrue(dashboard.doCreateApp(appTemp,appName,shortDesc,deployRegion,defaultLang,appType,filePath,languages));
	
	}

}
