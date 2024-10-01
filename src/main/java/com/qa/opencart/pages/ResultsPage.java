package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	private WebDriver driver;
	private ElementUtil eleutil;
	
	private  By searchHeader = By.cssSelector("div#content h2");
	private By results = By.cssSelector("div.product-thumb");
	
	public ResultsPage(WebDriver driver) {
		this.driver= driver;
		eleutil = new ElementUtil(driver);
	}
	
	public String getSearchHeader() {
		String Header=eleutil.WaitForElementVisibile(searchHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println(Header);
		return Header;
		
	}
	public int getSearchResultsCount() {
		int resultcount= eleutil.WaitForElementsVisible(results, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Search Result Count is =====>>"+resultcount);
		return resultcount;
		
	}
	
	public  ProductInfoPage selectProduct(String productName) {
		System.out.println("Selecting Product:"+productName);
		eleutil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}

	
	
	
	
	
}
