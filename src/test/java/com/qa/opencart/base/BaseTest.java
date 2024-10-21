package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

import io.qameta.allure.Step;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage login;
	protected AccountsPage accPage;
	protected ResultsPage resPage;
	protected ProductInfoPage ProductPage;
	protected RegisterPage RegPage;
	
	protected SoftAssert softAssert;
	
	@Step("setup with Browser:{0} and init the properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setup(@Optional("chrome")String browserName) {
		df= new DriverFactory();
		prop=df.initProp();
		
		//check if browser param is coming from testNg.xml
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
		}
		driver=df.initDriver(prop);
		login= new LoginPage(driver);
		softAssert = new SoftAssert();
	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
