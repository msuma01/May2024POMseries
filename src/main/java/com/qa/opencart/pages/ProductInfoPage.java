package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
private WebDriver driver;
private ElementUtil eleutil;

private By productHeader = By.tagName("h1");
private By productMetaData = By.xpath("(//div[@id='content']//div//ul[@class='list-unstyled'])[1]/li");
private By productPriceData = By.xpath("(//div[@id='content']//div//ul[@class='list-unstyled'])[2]/li");
private By productimages = By.cssSelector("ul.thumbnails img");
private Map<String, String> productMap;

public ProductInfoPage(WebDriver driver) {
	this.driver = driver;
	eleutil = new ElementUtil(driver);
	
}

public String getProductHeader() {
	String productHeaderValue=eleutil.WaitForElementVisibile(productHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
	System.out.println(productHeaderValue);
	return productHeaderValue;
}


//Brand: Apple
//Product Code: Product 17
//Reward Points: 700
//Availability: Out Of Stock
private void getProductMetaData() {
	List<WebElement> metaList = eleutil.getElements(productMetaData);
	for(WebElement meta:metaList) {
		String ProductInfoList = meta.getText();
		String[] metaData = ProductInfoList.split(":");
		String metaKey = metaData[0].trim();
		String metaValue = metaData[1].trim();
		productMap.put(metaKey, metaValue);
	}
}

//$1,202.00
//Ex Tax: $1,000.00
	
	private void getProductPriceData() {
		List<WebElement> priceList = eleutil.getElements(productPriceData);
		String ProductPrice=priceList.get(0).getText().trim();
		String ExTaxPrice=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("Price",ProductPrice);
		productMap.put("TaxPrice", ExTaxPrice);
		}
	public Map<String, String> getProductData() {
		productMap = new HashMap<String,String>(); //no order
//		productMap = new LinkedHashMap<String,String>(); // to maintain the insertion order
//		productMap = new TreeMap<String,String>(); // to maintain the alphabetical order
		productMap.put("productHeader", getProductHeader());
		getProductMetaData();
		getProductPriceData();
		System.out.println("Product Info:"+productMap);
		return productMap;
	}
	public int getProductImagesCount() {
		int imagescount = eleutil.WaitForElementsPresence(productimages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Images Count====> "+imagescount);
		return imagescount;
	}
}

