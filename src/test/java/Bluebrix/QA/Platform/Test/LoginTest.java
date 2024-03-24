package Bluebrix.QA.Platform.Test;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import Bluebrix.QA.Platform.BaseTest.BaseTest;
import Bluebrix.QA.Platform.Constants.AppConstants;

public class LoginTest extends BaseTest {
	@Test (priority=1)
	public void isLogoPresent() {
		assertTrue(logpage.isLogoVisible());
	}
	@Test (priority=2)
	public void isSigninPresent() {
		assertTrue(logpage.isSigninVisible());
	}
	@Test (priority=3)
	public void isSigninTextPresent() {
		String text=logpage.getSigninText();
		softassert.assertEquals(text,"Sign in");
		
	}
	@Test (priority=4)
	public void URLvalidation() {
		String URL=logpage.getURL(); 
		Assert.assertTrue(URL.contains(AppConstants.LOGIN_URL_FRACTION));
	}
	@Test (priority=5)
	public void isLoginSuccess() {
		dashboard=logpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(dashboard.getAppListHeader(),AppConstants.DASHBOARD_APPLIST_HEADER);
	}
}
