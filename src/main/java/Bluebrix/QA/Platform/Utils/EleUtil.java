package Bluebrix.QA.Platform.Utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Bluebrix.QA.Platform.Constants.AppConstants;
import Bluebrix.QA.Platform.FrameworkException.FrameworkException;

public class EleUtil {
	private WebDriver driver;
	private int size;

	public EleUtil(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public void doWindowMaximize() {
		driver.manage().window().maximize();
	}

	public void getWebsite(String URL) {
		driver.navigate().to(URL);
	}

	public void browserForward() {
		driver.navigate().forward();
	}

	public void browserBackward() {
		driver.navigate().back();
	}

	public void browserReferesh() {
		driver.navigate().refresh();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void clickElement(By locator) {
		getElement(locator).click();
	}

	public void clickWithAction(By locator, int time) {
		WebElement target = waitforVisibleofEle(locator, time);
		Actions act = new Actions(driver);
		act.moveToElement(target).click().build().perform();

	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getUrl() {
		return driver.getCurrentUrl();
	}

	public void sendText(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	public void dropCount(By locator) {
		int listSize = getElements(locator).size();
		System.out.println(listSize);
	}

	public void getDropListText(By locator) {
		List<WebElement> drpEleList = getElements(locator);
		dropCount(locator);
		for (WebElement e : drpEleList) {
			String drpListText = e.getText();
			System.out.println(drpListText);
		}
	}

	public boolean checkElePresent(By locator) {

		return driver.findElements(locator).size() > 0 ? true : false;
	}

	public boolean checkMultiElePresent(By locator) {

		return driver.findElements(locator).size() >= 1 ? true : false;
	}

	public boolean checkOneElePresent(By locator) {

		return driver.findElements(locator).size() == 1 ? true : false;
	}

	public Select selectClass(By locator) {
		Select sel = new Select(getElement(locator));
		return sel;

	}

	public void doSelectByIndex(By locator, int index) {

//	WebElement eleDropdwn = getElement(locator);
//	Select listDrp = new Select(eleDropdwn);
		selectClass(locator).selectByIndex(index);
	}

	public void doSelectByVisible(By locator, String value) {

//	WebElement eleDropdwn = getElement(locator);
//	Select listDrp = new Select(eleDropdwn);
		selectClass(locator).selectByVisibleText(value);
	}

	public void doSelectByValue(By locator, String value) {
//	WebElement eleDropdwn = getElement(locator);
//	Select listDrp = new Select(eleDropdwn);
		selectClass(locator).selectByValue(value);
	}

	public int getListCopunt(By locator) {
//	Select listDrp = selectClass(locator);
//	Select listDrp=new Select(getElement(locator));
		return selectClass(locator).getOptions().size();

	}

	public void getListAndSelectOne(By locator, String value) {
		Select listDrp = new Select(getElement(locator));
		List<WebElement> allDrp = listDrp.getOptions();
		System.out.println(allDrp.size());
		for (WebElement e : allDrp) {
			String drpList = e.getText().toString();
			System.out.println(drpList);
			if (drpList.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void dropDownSelectByValue(By locator, String value, int time) {
		List<WebElement> eleDrp = waitforVisibleofElements(locator, time);
		for (WebElement e : eleDrp) {
			String ID = e.getAttribute("id");
			By id = By.id(ID);
			WebElement webEle = waitforVisibleofEle(id, AppConstants.SHORT_WAIT);
			String text = webEle.getText();
			System.out.println(text);
//			 = e.getText();
			if (text.equals(value)) {
				webEle.click();
				break;
			}
		}
	}

	public void dropDownSelectOneByValue(By locator, String value, int time) {
		List<WebElement> eleDrp = waitforVisibleofElements(locator, time);
		for (WebElement e : eleDrp) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void dropDownSelectMidOfList(By locator, int time) {
		List<WebElement> eleDrp = waitforVisibleofElements(locator, time);
		int listSize = eleDrp.size();
		int listMiddle= listSize / 2;
		int i = 0;
		for (WebElement e : eleDrp) {
			String text = e.getText();
			System.out.println(text);
			if (i==listMiddle) {
				e.click();
				break;
			}
			i++;
		}
	}

	public void dropDownSelectByText(By locator, String value, int time) {
		List<WebElement> eleDrp = waitforVisibleofElements(locator, time);
		for (WebElement e : eleDrp) {
			String text = e.getText();
			System.out.println(text);
//			 = e.getText();
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	public void dropDownMultiSelect(By locator, int time, String... values) {
		List<WebElement> eleDrp = waitforPresenceofElements(locator, time);
		if (eleDrp.size() == 0 && values.length == 0) {
			System.out.println(
					"Total element list is:" + eleDrp.size() + "Total values in the string is list: " + values.length);
		}
		for (WebElement e : eleDrp) {
			for (String value : values) {

				String text = e.getText();
				if (text.equals(value)) {
					e.click();
					break;
				}
			}
		}
	}

	public void drpSelectByAction(By locator, String value) {
		List<WebElement> eleDrp = getElements(locator);
		System.out.println("****************" + eleDrp.size() + "****************");
		for (WebElement e : eleDrp) {
			String text = e.getText();
			System.out.println(text);
			Actions act = new Actions(driver);
			if (text.equals(value)) {
				act.moveToElement(e).click().perform();
				break;
			}
		}
	}

	public void dropdownSelectByvalueWithWait(By locator, String value, int timeOut) {
		List<WebElement> eleDrp = waitforPresenceofElements(locator, timeOut);
		System.out.println("****************" + eleDrp.size() + "****************");
		for (WebElement e : eleDrp) {
			String text = e.getText();
			System.out.println(text);
			Actions act = new Actions(driver);
			if (text.equals(value)) {
				act.moveToElement(e).click().perform();
//			break;
			}
		}
	}

	public boolean checkMultiple(By locator) {
//	Select sel = new Select(getElement(locator));
		return selectClass(locator).isMultiple() ? true : false;
	}

	/**
	 * it is to handle the multi selection with single multi and all selection for
	 * all selection pass "all"
	 * 
	 * @param locator
	 * @param values
	 */
	public void doMultiselect(By locator, String... values) {
//  String... values are the String dynamic array another format we can add or remove any value of string with this keyword.
		Select sel = new Select(getElement(locator));
		if (checkMultiple(locator)) {
			for (String value : values) {
				if (values[0].equalsIgnoreCase("all")) {
					List<WebElement> list = sel.getOptions();
					for (WebElement e : list) {
						String listText = e.getText();
						sel.selectByVisibleText(listText);
					}
				} else {
					selectClass(locator).selectByVisibleText(value);
				}
			}
		}
	}

//we can handle single dropdown selection and some limited the drop data we can pass and handle but select all by passing
//we cant pass all string with comma seperated is not possible.
//to check all
	public void doMultiselect(By locator, By optionLocator, String... values) {
		// String... values are the String dynamic array another format we can add or
		// remove any value of string with this keyword.
//		Select sel = new Select(getElement(locator));
		if (checkMultiple(locator)) {
			if (values[0].equalsIgnoreCase("all")) {
				List<WebElement> list = getElements(optionLocator);
				for (WebElement e : list) {
					e.click();
				}
			} else {
				for (String value : values) {
					selectClass(locator).selectByVisibleText(value);
				}
			}
		}
	}

	public WebElement retryElement(By locator, int retry, int intervalTime) {
		WebElement ele = null;
		int attempts = 0;
		while (attempts < retry) {
			try {
				ele = getElement(locator);
				System.out.println("ele is found" + locator);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found" + locator);
				try {
					Thread.sleep(intervalTime);// polling time

				} catch (InterruptedException e1) {
					e1.printStackTrace();// TODO: handle exception
				}
			}
			attempts++;
		}
		if (ele == null) {
			System.out.println("ele is not found tried with element for " + retry + "times with the timeout of 500 ms");
			throw new FrameworkException("no such ele");
		}
		return ele;
	}

	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState ==='complete'"))
				.toString();
		return Boolean.parseBoolean(flag);
	}

	public boolean isDomBodyLoaded(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		System.out.println("DOM body loaded");
		return true;

	}

	public WebElement waitforVisibleofEle(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> waitforVisibleofElements(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public void clickwithWait(By locator, int time) {
		waitforVisibleofEle(locator, time).click();
	}

	public String getTextWait(By locator, int time) {
		return waitforVisibleofEle(locator, time).getText();
	}

	public void sendwithWait(By locator, String value, int time) {
		waitforVisibleofEle(locator, time).sendKeys(value);
	}

	public WebElement waitforPresenceofEle(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public List<WebElement> waitforPresenceofElements(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public void doMultiselected(By locator, String... values) {
		// String... values are the String dynamic array another format we can add or
		// remove any value of string with this keyword.
		Select sel = new Select(getElement(locator));
		if (checkMultiple(locator)) {
			for (String value : values) {
				if (values[0].equalsIgnoreCase("all")) {
					List<WebElement> list = driver.findElements(By.xpath("//select/option"));
					for (WebElement e : list) {
						e.click();
						break;
//	we have also handled the all selection in multi selection
					}
					sel.selectByVisibleText(value);
				}
			}
		}
	}

	public void doRightClickAndSelect(By locator, By RightList, String selectText) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).build().perform();
		List<WebElement> list = getElements(RightList);
		for (WebElement e : list) {
			String text = e.getText();
			if (text.equals(selectText)) {
				e.click();
				break;
			}
		}
	}
}
