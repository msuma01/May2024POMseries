package com.qa.opencart.factory;

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

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
//import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.exceptions.FrameworkExcpetion;

import io.qameta.allure.Step;

//import io.qameta.allure.Step;





public class DriverFactory {
	WebDriver driver;
	Properties prop;
	public static String isHighlight;
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal <WebDriver>();
	/**
	 * This method is used to initilase the driver on the basis of browsername
	 * @param browserName
	 * @return WebDriver
	 */
	
	@Step("Initializing the driver with Properties: {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName=prop.getProperty("browser");
		isHighlight=prop.getProperty("highlight");
		
		OptionsManager options = new OptionsManager(prop);
		
		System.out.println("Browser name is :" + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new FirefoxDriver(options.getFirefoxOptions());
			tldriver.set( new ChromeDriver(options.getChromeOptions()));
			break;
			
		case "firefox":
			//driver = new FirefoxDriver(options.getFirefoxOptions());
			tldriver.set( new FirefoxDriver(options.getFirefoxOptions()));
			break;
			
		case "edge":
			//driver = new EdgeDriver(options.getEdgeOptions());
			tldriver.set( new EdgeDriver(options.getEdgeOptions()));
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
