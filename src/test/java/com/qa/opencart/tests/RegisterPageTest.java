package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regSetup() {
		RegPage=login.navigateToRegisterPage();
	}
//	@Test
//	public void getRegPageTitleTest() {
//		String Actual=RegPage.getTitlePage();
//		Assert.assertEquals(Actual, AppConstants.REGISTER_PAGE_TITLE);
//	}
	
	
	public String getRandomEmail() {
		return "uiautomation"+ System.currentTimeMillis()+"@gmail.com";
	}
	
	@DataProvider
	public Object[][]  registerAccount() {
		return new Object[][] {
			{"Neetu","Singh","080384089538745","jk@345","yes"},
			{"Reetu","Sin","08038408955","ljk@345","yes"},
			{"Hari","Ghani","84089538745","gjk@345","yes"},
			{"Neu","ghini","0384089538745","djk@345","yes"},
			{"Neha","Niaha","08038408745","sjk@345","yes"}
		};
	}
	
	@DataProvider
	public Object[][]  getregdata() {
		return ExcelUtil.getTestData(AppConstants.REG_SHEET_NAME);
			
	}
	
	@Test(dataProvider="getregdata")
	public void userRegisterTest(String Firstname, String Lastname,String Mobile, String Password,String subscribe) {
		RegPage.userRegisteration(Firstname, Lastname, getRandomEmail(),Mobile,Password, subscribe);
		
		
		
	
		
		
	}

}
