package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleutil;
	
	 private By logoutlink = By.linkText("Logout"); 
	 private By headers = By.cssSelector("div#content h2");
	 private By search = By.name("search");
	 private By searchIcon = By.cssSelector("button.btn-default");
	 
	 public AccountsPage(WebDriver driver) {
		 this.driver = driver;
		 eleutil = new ElementUtil(driver);
	 }
	 
	 public String getAccountsPageTitle() {
			
			String title=eleutil.waitForTitleContainsAndReturn(AppConstants.Accounts_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
			System.out.println("Accounts page title:"+title);
			return title;
		}
	 
	 public boolean isLogoutlinkExist() {
			return	eleutil.IsElementDisplayed(logoutlink);
		}
	 
	 public int getTotalAccountsPageHeaderCount() {
		 return eleutil.WaitForElementsVisible(headers, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	 }
	 
	 
	 public List<String> getAccPageHeaders() {
		List<WebElement> headersList = eleutil.WaitForElementsVisible(headers, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> headersValueList = new ArrayList <String>();
		for(WebElement e: headersList) {
			String alltext=e.getText();
			headersValueList.add(alltext);
		}
		return headersValueList;
	 }
	 
	 public ResultsPage dosearch(String searchkey) {
		 System.out.println("SearchKey===> "+searchkey);
		WebElement SearchEle= eleutil.WaitForElementVisibile(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		eleutil.doSendKeys(SearchEle, searchkey);
		 eleutil.doClick(searchIcon);
		 return new ResultsPage(driver);
	 }
}
