package Bluebrix.QA.Platform.Pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Bluebrix.QA.Platform.Constants.AppConstants;
import Bluebrix.QA.Platform.Utils.EleUtil;

public class DashBoardPage {
	private WebDriver driver;
	private EleUtil eleutil;
	private By Header = By.xpath("(//h1[@class='text-base font-bold text-blue'])[1]");
	private By CreateBtn = By.cssSelector(".px-9");// xpath("//span[@class='btn-dragon px-9']");
	private By WebTemp = By.xpath(
			"(//div[@class='p-4 pt-3 w-full rounded bg-widgetColors-200 flex flex-col justify-center items-start'])[1]");
	private By MobileTemp = By.xpath(
			"(//div[@class='p-4 pt-3 w-full rounded bg-widgetColors-200 flex flex-col justify-center items-start'])[2]");

	private By AppName = By.name("appName");
	private By AppShortDesc = By.name("appShortDesc");

	private By AppLang = By.xpath("(//div[@class='react-select__input-container css-19bb58m'])[2]");
	private By DeployRegion = By
			.cssSelector("div.inline-block div[class='basic-multi-select  react-select-single css-b62m3t-container']");
	private By RegionsList = By.cssSelector(".react-select__menu-list>div");

	private By LangsList = By.cssSelector("div.react-select__menu-portal div[role='listbox'] div");

	private By NextBtn = By.xpath("//button[text()='Next']");
	private By DefaultLang = By.xpath("(//div[@class='react-select__input-container css-19bb58m'])[3]");
	private By DefaultLangList = By.cssSelector(".react-select__menu-portal div.react-select__option");
	private By AppType = By.xpath("(//div[@class='react-select__input-container css-19bb58m'])[4]");
	private By AppTypeList = By.cssSelector(".css-qr46ko div.react-select__option");
	private By fileupload = By.cssSelector("#app-logo-file");
	private By CreateAPPBtn = By.cssSelector("button[data-testid='footer-create-button']");

	private By DataSourceList = By.cssSelector(
			".data-source-wrapper div.app-list-container div[data-testid='choose-template-card-modal'] div h3");

	public DashBoardPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new EleUtil(this.driver);
	}

	public String getAppListHeader() {
		return eleutil.getTextWait(Header, AppConstants.MED_WAIT);
	}

	public boolean getCreateAppButtonStatus() {
		return eleutil.getElement(CreateBtn).isDisplayed();
	}

	public boolean doCreateApp(String appTemp, String appName, String shortDesc, String deployRegion,
			String defaultLang, String appType, String filePath, String[] languages) throws InterruptedException {
		Thread.sleep(AppConstants.SLEEP_WAIT_MED);
		eleutil.clickwithWait(CreateBtn, AppConstants.LONG_WAIT);

		if (appTemp.toLowerCase().equalsIgnoreCase("web")) {

			eleutil.clickwithWait(WebTemp, AppConstants.SHORT_WAIT);
		} else if (appTemp.toLowerCase().equalsIgnoreCase("mobile")) {
			eleutil.clickwithWait(MobileTemp, AppConstants.SHORT_WAIT);
		}
		Thread.sleep(AppConstants.SLEEP_WAIT_MED);
		eleutil.clickwithWait(NextBtn, AppConstants.SHORT_WAIT);
		createAppFormFilling(appName, shortDesc, deployRegion, defaultLang, filePath, languages);
		eleutil.clickElement(AppType);
		eleutil.dropDownSelectOneByValue(AppTypeList, appType, AppConstants.SHORT_WAIT);
		eleutil.sendText(fileupload, filePath);
		if (appTemp.equalsIgnoreCase("web")) {
			eleutil.clickElement(CreateAPPBtn);
			eleutil.dropDownSelectMidOfList(DataSourceList, AppConstants.SHORT_WAIT);
			System.out.println("it is a webapp");
		}
		eleutil.clickElement(CreateAPPBtn);
		Thread.sleep(AppConstants.SLEEP_WAIT_LONG);
		eleutil.browserBackward();

		return true;
	}

	public void createAppFormFilling(String appName, String shortDesc, String deployRegion, String defaultLang,
			String filePath, String[] languages) throws InterruptedException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String newAppName = appName + " " + dtf.format(now);
		eleutil.sendText(AppName, newAppName);
		eleutil.sendText(AppShortDesc, shortDesc);
		eleutil.waitforPresenceofEle(DeployRegion, AppConstants.SHORT_WAIT).click();
		eleutil.dropDownSelectByText(RegionsList, deployRegion, AppConstants.SHORT_WAIT);
		Thread.sleep(AppConstants.SLEEP_WAIT_SHORT);
		eleutil.clickwithWait(AppLang, AppConstants.SHORT_WAIT);
		eleutil.dropDownMultiSelect(LangsList, AppConstants.SHORT_WAIT, languages);
		eleutil.clickwithWait(AppLang, AppConstants.SHORT_WAIT);
		eleutil.clickwithWait(DefaultLang, AppConstants.SHORT_WAIT);
		eleutil.dropDownSelectOneByValue(DefaultLangList, defaultLang, AppConstants.SHORT_WAIT);

	}
}
