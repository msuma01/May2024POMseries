package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.AccountsPage;

public class LoginPageTest extends BaseTest{

	@Test
	public void loginPageTitleTest() {
		String acttitle=login.getLoginPageTitle();
		Assert.assertEquals(acttitle,AppConstants.Login_PAGE_TITLE);
		
	}
	@Test
	public void loginPageURLTest() {
		String acturl=login.getLoginPageURL();
		Assert.assertTrue(acturl.contains(AppConstants.Login_PAGE_FRACTION_URL));
	}
	
	@Test
	public void forgotpwdLinkTest() {
		
		Assert.assertTrue(login.isforgotpwdLinkExist());
		
	}
	@Test
	public void logoExistTest() {
		
		Assert.assertTrue(login.islogoExist());
	}
	
	@Test(priority=Integer.MAX_VALUE)
	public void loginTest() {
		accPage=login.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.Accounts_PAGE_TITLE);
		
	}
	
}
