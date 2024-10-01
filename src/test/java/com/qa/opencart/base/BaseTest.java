package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage login;
	protected AccountsPage accPage;
	protected ResultsPage resPage;
	protected ProductInfoPage ProductPage;
	protected RegisterPage RegPage;
	
	
	@BeforeTest
	public void setup() {
		df= new DriverFactory();
		prop=df.initProp();
		driver=df.initDriver(prop);
		login= new LoginPage(driver);
	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
