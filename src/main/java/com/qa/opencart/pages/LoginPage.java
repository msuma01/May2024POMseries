package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

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
	
	@Step("getting login page title value")
	
	//3.Public Actions/Methods
	public String getLoginPageTitle() {
		
		String title=eleutil.waitForTitleContainsAndReturn(AppConstants.Login_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("login page title:"+title);
		return title;
	}
	
	@Step("getting login page url value")
	public String getLoginPageURL() {
		
		String url=	eleutil.waitForURLContainsAndReturn(AppConstants.Login_PAGE_FRACTION_URL,AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		System.out.println("login page title:"+url);
		return url;
	}
	
	
	@Step("checking isForgotPwdLink exist on the login page....")	
	public boolean isforgotpwdLinkExist() {
		return	eleutil.IsElementDisplayed(forgotPwdLink);
	}
	
	@Step("checking logo exist on the login page....")
	public boolean islogoExist() {
		return	eleutil.IsElementDisplayed(logo);
	}
	
	@Step("login with username : {0} and password: {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("credentials are: Username" +userName +"Password"+  pwd);
		
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
