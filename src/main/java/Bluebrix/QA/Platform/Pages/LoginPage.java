package Bluebrix.QA.Platform.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Bluebrix.QA.Platform.Constants.AppConstants;
import Bluebrix.QA.Platform.Utils.EleUtil;

public class LoginPage {
	private WebDriver driver;
	private EleUtil eleutil;
	private By Logo = By.cssSelector(".m-auto");
	private By SigninBtn = By.cssSelector(".btn-primary");
	private By userName = By.cssSelector("#username");
	private By passWord = By.cssSelector("#password");
	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new EleUtil(driver);
	}

	public boolean isLogoVisible() {
		return eleutil.waitforVisibleofEle(Logo, AppConstants.SHORT_WAIT).isDisplayed();

	}

	public boolean isSigninVisible() {
		return driver.findElement(SigninBtn).isDisplayed();
	}

	public String getSigninText() {
		String SigninText = eleutil.getTextWait(SigninBtn, AppConstants.SHORT_WAIT);
		return SigninText;
	}

	public String getURL() {
		String URL = driver.getCurrentUrl();
		return URL;
	}

	public String getTitle() {
		String Title = driver.getTitle();
		return Title;

	}

	public DashBoardPage doLogin(String username, String pwd) {
		eleutil.sendwithWait(userName, username, AppConstants.SHORT_WAIT);
		eleutil.sendText(passWord, pwd);
		eleutil.clickwithWait(SigninBtn,AppConstants.SHORT_WAIT);
		return new DashBoardPage(driver);
		}

}
