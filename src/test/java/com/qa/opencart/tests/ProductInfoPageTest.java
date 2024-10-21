package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	@BeforeClass
	public void ProductInfoSetup() {
		accPage=login.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductHeader() {
		return new Object[][] {
			{"MacBook","MacBook Pro"},
			{"MacBook","MacBook Air"},
			{"imac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"canon","Canon EOS 5D"}
			
		};
	}
	
	@Test(dataProvider="getProductHeader")
	public void ProductHeaderTest(String searchKey, String productName) {
		resPage=accPage.dosearch(searchKey);
		ProductPage= resPage.selectProduct(productName);
		Assert.assertEquals(ProductPage.getProductHeader(),productName);
	}
	
	
	
	@DataProvider
	public Object[][] getProductInfo() {
		return new Object[][] {
			{"MacBook","MacBook","Brand", "Apple","Product Code","Product 16","Reward Points","600","Availability","In Stock","Price","$602.00" ,"TaxPrice","$500.00"},
			{"MacBook","MacBook Air","Brand", "Apple","Product Code","Product 17","Reward Points","700","Availability","Out Of Stock","Price","$1,202.00" ,"TaxPrice","$1,000.00"},
			{"MacBook","MacBook Pro","Brand", "Apple","Product Code","Product 18","Reward Points","800","Availability","In Stock","Price","$2,000.00" ,"TaxPrice","$2,000.00"},
			//{"iPhone","iPhone","Brand", "Apple","Product Code"," product 11","Reward Points","200","Availability"," 2-3 Days","Price","$98.00" ,"TaxPrice"," $80.00"}
			
		};
	}
	@Test(dataProvider="getProductInfo")
	public void ProductInfoTest(String searchKey, String productName, String Brand, String BrandName, String ProductCode, String code,
			 String Reward, String RewardPoints, String Availability, String instockornot, String Price,String PriceValue, String Tax, String TaxValue) {
		resPage=accPage.dosearch(searchKey);
		ProductPage= resPage.selectProduct(productName);
		Map<String, String> actualProductData = ProductPage.getProductData();
		softAssert.assertEquals(actualProductData.get(Brand),BrandName );
		softAssert.assertEquals(actualProductData.get(ProductCode),code );
		softAssert.assertEquals(actualProductData.get(Reward),RewardPoints);
		softAssert.assertEquals(actualProductData.get(Availability),instockornot );
		softAssert.assertEquals(actualProductData.get(Price),PriceValue );
		softAssert.assertEquals(actualProductData.get(Tax),TaxValue );
		softAssert.assertAll();
	}
	
//	softAssert.assertEquals(actualProductData.get("Brand"),"Apple" );
//	softAssert.assertEquals(actualProductData.get("Product Code"),"Product 17" );
//	softAssert.assertEquals(actualProductData.get("Reward Points"),"700" );
//	softAssert.assertEquals(actualProductData.get("Availability"),"Out Of Stock" );
//	softAssert.assertEquals(actualProductData.get("Price"),"$1,202.00" );
//	softAssert.assertEquals(actualProductData.get("TaxPrice"),"$1,000.00" );
//	softAssert.assertAll();
	
	@DataProvider
	public Object[][]  getProductImagesCount() {
		return new Object[][] {
			{"MacBook","MacBook Air",4},
			{"Samsung","Samsung Galaxy Tab 10.1",7},
			{"imac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"canon","Canon EOS 5D",3}
			
		};
		
	}
	
	
	@Test(dataProvider="getProductImagesCount")
	public void ProductImagesCountTest(String productsearch, String Productselection, int count) {
		resPage=accPage.dosearch(productsearch);
		ProductPage= resPage.selectProduct(Productselection);
		Assert.assertEquals(ProductPage.getProductImagesCount(), count);
		
		
	}

}
