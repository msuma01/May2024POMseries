package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
//import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.exceptions.FrameworkExcpetion;

import io.qameta.allure.Step;

//import io.qameta.allure.Step;





public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager options;
	public static String isHighlight;
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal <WebDriver>();
	/**
	 * This method is used to initilase the driver on the basis of browsername
	 * @param browserName
	 * @return WebDriver
	 */
	
	//@Step("Initializing the driver with Properties: {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName=prop.getProperty("browser");
		isHighlight=prop.getProperty("highlight");
		
		options= new OptionsManager(prop);
		
		System.out.println("Browser name is :" + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				init_remoteDriver("chrome");
			}
			else {
				//run tc in local
				//driver = new FirefoxDriver(options.getChromeOptions());
				tldriver.set( new ChromeDriver(options.getChromeOptions()));
			}
			
			break;
			
		case "firefox":
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				init_remoteDriver("firefox");
			}
			else {
				//run tc in local
				//driver = new FirefoxDriver(options.getFirefoxOptions());
				tldriver.set( new FirefoxDriver(options.getFirefoxOptions()));
			}
			
			break;
			
		case "edge":
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				init_remoteDriver("edge");
			}
			else {
				//run tc in local
				//driver = new FirefoxDriver(options.getEdgeOptions());
				tldriver.set( new EdgeDriver(options.getEdgeOptions()));
			}
			break;
			
			
		default:
			System.out.println(AppError.INVALID_BROWSER_MSG+browserName);
			throw new BrowserException(AppError.INVALID_BROWSER_MSG+browserName);
			
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	private void init_remoteDriver (String BrowserName) {
		System.out.println("Running tests on grid: "+BrowserName);
		try {
		switch (BrowserName.toLowerCase().trim()) {
		case "chrome":
			tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), options.getChromeOptions()));
			break;
			
		case "firefox":
			tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), options.getFirefoxOptions()));
			break;
		case "edge":
			tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), options.getEdgeOptions()));
			break;

		default:
			System.out.println("Please pass the right remote Browser"+BrowserName);
			throw new BrowserException(AppError.INVALID_BROWSER_MSG);
			
	
		}
		}catch (MalformedURLException e) {
		
		e.printStackTrace();
	}
	}

	/**
	 * This method is returning the driver with threadlocal
	 * @return tldriver
	 */
	
	public static WebDriver getDriver() {
		return tldriver.get();
	}

	/**
	 * This method is used to initialise the Properties from Config File
	 * @return
	 */
	//mvn clean install -Denv="qa"
	public Properties initProp() {
		prop= new Properties();
		FileInputStream ip ;
		String envName=System.getProperty("env");
		System.out.println("Running on the evn:"+envName);
		
		try {
		if(envName==null) {
			System.out.println("env is null..... hence running tests on qa environment");
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}else
		{
			switch (envName.toLowerCase().trim()) {
			case "qa" : ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev" : ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
			break;
			case "stg" : ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
			break;
			case "uat" : ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
			break;
			case "prod" : ip = new FileInputStream("./src/test/resources/config/config.properties");
			break;



			default: 
				System.out.println("Please pass the right Environment name:"+envName);
				throw new FrameworkExcpetion("INVALID ENVIRONMENT NAME");
				
			}
			
		}
			prop.load(ip);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}
		return prop;
}
	/**
	 * take screenshot 
	 */
	
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);//temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	

}
