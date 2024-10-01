package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	/**
	 * This method is used to initilase the driver on the basis of browsername
	 * @param browserName
	 * @return WebDriver
	 */
	public WebDriver initDriver(Properties prop) {
		String browserName=prop.getProperty("browser");
		
		System.out.println("Browser name is :" + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			driver= new ChromeDriver();
			break;
			
		case "firefox":
			driver = new FirefoxDriver();
			break;
			
		case "Edge":
			driver = new EdgeDriver();
			break;

		default:
			System.out.println(AppError.INVALID_BROWSER_MSG+browserName);
			throw new BrowserException(AppError.INVALID_BROWSER_MSG+browserName);
			
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		return driver;
	}
	/**
	 * This method is used to initialise the Properties from Config File
	 * @return
	 */
	public Properties initProp() {
		prop= new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

}
