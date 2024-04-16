package Bluebrix.QA.Platform.DriverFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

public class DriverFactory {
	private WebDriver driver;
	private Properties prop;
	private OptionsManager optionManager;
	public static ThreadLocal<WebDriver> tld=new ThreadLocal<WebDriver>();

	
	public WebDriver initBrowser(Properties prop) {
		optionManager=new OptionsManager(prop);
		String browsername=prop.getProperty("browser");
		System.out.println("The browser name is :"+ browsername);
		switch (browsername.toLowerCase().trim()) {
		case "chrome":
//			driver=new ChromeDriver(optionManager.getChromeOptions());
			tld.set(new ChromeDriver(optionManager.getChromeOptions()));
			break;
		case "edge":
//			driver=new EdgeDriver(optionManager.getEdgeOptions());
			tld.set(new EdgeDriver(optionManager.getEdgeOptions()));
			break;
		case "firefox":
//			driver=new FirefoxDriver(optionManager.getFirefoxOptions());
			tld.set(new FirefoxDriver(optionManager.getFirefoxOptions()));
			break;
		default:
			System.out.println("please pass the right browser"+browsername);
//			Exception.
			break;
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return tld.get();
	}
	
	
	public Properties initProperties() {
		prop=new Properties();
		FileInputStream ip=null;
		String envName=System.getProperty("env");
		try {
if	(envName==null) {
	System.out.println("your env name is null hence running test case in QA env");
			ip=new FileInputStream("./src/test/resources/Config/config.properties");}
else {switch (envName.toLowerCase().trim()) {
case "qa":
	ip=new FileInputStream("./src/test/resources/Config/config_qa.properties");
	break;
case "dev":
	ip=new FileInputStream("./src/test/resources/Config/config_dev.properties");
	break;
case "staging":
	ip=new FileInputStream("./src/test/resources/Config/config_staging.properties");
	break;

default:
	System.out.println("please pass the right env name");
	break;
	
}}}
		catch(FileNotFoundException e) {
			System.out.println("invalid file path");
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	public static String getScreenshot(String methodName) {
		File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		File destination=new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
	
}
