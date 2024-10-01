package com.qa.opencart.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {
	private WebDriver driver;
	public ElementUtil eleutil;
	
	//1. private By locators: page objects
      private By username = By.id("input-email"); 
	  private By password = By.id("input-password");
	  private By loginBtn = By.xpath("//input[@type='submit']");
	  private By  forgotPwdLink = By.linkText("Forgotten Password");
	  private By registerLink = By.linkText("Register");
	  private By logo = By.cssSelector("img.img-responsive");
	
	//2.Public Page Constructor...
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	//3.Public Actions/Methods
	public String getLoginPageTitle() {
		
		String title=eleutil.waitForTitleContainsAndReturn(AppConstants.Login_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("login page title:"+title);
		return title;
	}
	public String getLoginPageURL() {
		
		String url=	eleutil.waitForURLContainsAndReturn(AppConstants.Login_PAGE_FRACTION_URL,AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		System.out.println("login page title:"+url);
		return url;
	}
	
	public boolean isforgotpwdLinkExist() {
		return	eleutil.IsElementDisplayed(forgotPwdLink);
	}
	
	public boolean islogoExist() {
		return	eleutil.IsElementDisplayed(logo);
	}
	
	public AccountsPage doLogin(String userName, String pwd) {
		
		eleutil.WaitForElementVisibile(username, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(userName);
		eleutil.doSendKeys(password, pwd);
		eleutil.doClick(loginBtn);
		
		return new AccountsPage(driver);
		
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleutil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
