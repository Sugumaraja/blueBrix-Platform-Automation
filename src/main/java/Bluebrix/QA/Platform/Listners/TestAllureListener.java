package Bluebrix.QA.Platform.Listners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Bluebrix.QA.Platform.DriverFactory.DriverFactory;
import io.qameta.allure.Attachment;


public class TestAllureListener  implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	
	// Text attachments for Allure
	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		System.out.println("The method " + iTestContext.getName()+" Overall test started");
		//iTestContext.setAttribute("WebDriver", BasePage.getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("The method " + iTestContext.getName()+" Overall Test Finished");
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("The method " + getTestMethodName(iTestResult) + " Test Started");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("The method " + getTestMethodName(iTestResult) + " Test succeed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("The method " + getTestMethodName(iTestResult) + "Test failed");
		Object testClass = iTestResult.getInstance();
//		WebDriver driver = BaseTest.getDriver();
		// Allure ScreenShotRobot and SaveTestLog
		if (DriverFactory.getDriver() instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(DriverFactory.getDriver());
		}
		// Save a log on allure.
		saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");		
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
		
	}

}