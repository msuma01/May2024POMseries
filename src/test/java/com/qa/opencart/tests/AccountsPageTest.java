package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accsetup() {
		accPage= login.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	public void AccountsPageTitleTest() {
		String acttitle= accPage.getAccountsPageTitle();
		Assert.assertEquals(acttitle,AppConstants.Accounts_PAGE_TITLE);
		
	}
	
	@Test
	public void isLogoutLinkExistTest() {
	Assert.assertTrue(accPage.isLogoutlinkExist());
	
	}
	
	@Test
	public void accPageHeadersCountTest() {
		Assert.assertEquals(accPage.getTotalAccountsPageHeaderCount(),AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
		
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actualHeadersList=accPage.getAccPageHeaders();
		Assert.assertEquals(actualHeadersList,AppConstants.EXPECTED_ACC_PAGE_HEADER_LIST);
	}
	
	@DataProvider
	public Object[][] getsearchKey() {
		return  new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"samsung",2}
		};
	}
	
	@Test(dataProvider="getsearchKey")
	public void searchCountTest(String searchKey, int searchCount) {
		resPage=accPage.dosearch(searchKey);
	   Assert.assertEquals(resPage.getSearchResultsCount(), searchCount);
	}
	
	
	
	@DataProvider
	public Object[][] getsearchData() {
		return  new Object[][] {
			{"macbook","MacBook Air"},
			{"macbook","MacBook Pro"},
			{"imac", "iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider="getsearchData")
	public void searchTest(String searchkey,String productName) {
		resPage=accPage.dosearch(searchkey);
		ProductPage=resPage.selectProduct(productName);
		Assert.assertEquals(ProductPage.getProductHeader(),productName);
	}
	

}
