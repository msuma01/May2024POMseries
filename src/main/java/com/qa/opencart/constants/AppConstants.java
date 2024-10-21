package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {
	
	
	public static final int DEFAULT_SHORT_TIME_OUT =5;
	public static final int DEFAULT_MEDIUM_TIME_OUT =10;
	public static final int DEFAULT_LONG_TIME_OUT = 20;
	
	public static final String Login_PAGE_TITLE="Account Login";
	
	public static final String Accounts_PAGE_TITLE= "My Account";
	public static final String REGISTER_PAGE_TITLE= "Register Account";
	
	public static final int ACCOUNTS_PAGE_HEADERS_COUNT= 4;
	
	public static final String USER_REGISTER_SUCCESS_MESSG= "Your Account Has Been Created!";
	
	public static final String Login_PAGE_FRACTION_URL= "route=account/login";
	

	public static	List<String> EXPECTED_ACC_PAGE_HEADER_LIST = List.of("My Account","My Orders","My Affiliate Account", "Newsletter");
	
	//*********************Sheet names **********************//
	
	
	public static final String REG_SHEET_NAME = "register";
	public static final String SEARCH_SHEET_NAME= "search";
	
}
