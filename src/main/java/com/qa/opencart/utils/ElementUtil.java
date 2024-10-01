package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.FrameworkExcpetion;

public class ElementUtil {
	private WebDriver driver;
	private Actions act;

	/*
	 * This is a constructor used to intialize the global variable
	 * 
	 */
	public ElementUtil(WebDriver driver) {
		this.driver = driver;	
		act = new Actions(driver);
	}

	/*
	 * This method is used to click any elect no return
	 * 
	 * @parameter-> locator
	 */

	public void doClick(By locator) {
		getElement(locator).click();
	}
	/*
	 * This method is used to input the text into the textbox no return
	 * 
	 * @parameters value and locator
	 */

	public void doclick(By locator, int timeout) {
		WaitForElementVisibile(locator, timeout).click();
	}

	public void dosendkeys(By locator, String value, int timeout) {
		WaitForElementVisibile(locator, timeout).sendKeys(value);
	}

	public void doSendKeys(By locator, String value) {

		getElement(locator).sendKeys(value);

	}

	/*
	 * This method is used to input the text in the form of character sequencei.e,
	 * Address no return
	 * 
	 * @parameters-> locator and character sequence
	 */

	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}

	/*
	 * This method is used to create web element retun WebElement
	 * 
	 * @para-> locator
	 */

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public void doActionsSendkeyswithPause(By locator, String name, long pause) {

		WebElement ele = getElement(locator);

		char ch[] = name.toCharArray();

		for (char c : ch) {
			act.sendKeys(ele, String.valueOf(c)).pause(pause).perform();
		}

	}

	/*
	 * This method is used to get text from the specific locator returns null or
	 * element Text
	 * 
	 * @param==> locator
	 */
	public String getElementText(By locator) {
		String eleText = getElement(locator).getText();
		if (eleText != null) {
			return eleText;
		} else {
			System.out.println("Element Text is null : " + eleText);
			return null;
		}

	}

	public void ElementDisabled() {

	}

	/*
	 * This method is used to present if any element is displayed returns "True" or
	 * False"
	 * 
	 * @param==>locator
	 */

	public boolean IsElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed :" + locator);
			return false;
		}
	}

	/*
	 * This method is used to verify if element is enable returns boolean (True or
	 * False)
	 * 
	 * @param===> locator
	 */

	public boolean IsElementEnabled(By locator) {
		return getElement(locator).isEnabled();

	}

	/*
	 * This method is used to Select(Checkbox) no return
	 * 
	 * @param==> locator
	 */

	public void ElementSelectDeselect(By locator) {
		getElement(locator).click();
	}

	/*
	 * This method is used to print the Element list on the page no return type
	 * 
	 * @param==> locator
	 */
	public void printElementList(By locator) {
		List<String> list = getElementsTextList(locator);
		for (String e : list) {
			System.out.println(e);
		}

	}

	/*
	 * This method is used to create list of WebElements returns List of WebElements
	 * 
	 * @param==> locator having multiple elements
	 */

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);

	}

	/*
	 * This method is used to get the ElementsCount returns locators count
	 * 
	 * @param===> locator
	 */

	public int getElementsCount(By locator) {
		return getElements(locator).size();

	}

	/*
	 * This method is used to get ElementsTExtList retun textlist of all elements
	 * 
	 * @param===>locator
	 */

	public List<String> getElementsTextList(By locator) {
		List<WebElement> EleList = getElements(locator);
		List<String> EleTextList = new ArrayList<String>();
		for (WebElement e : EleList) {
			String AllText = e.getText();
			if (AllText.length() != 0) {
				EleTextList.add(AllText);
			}
		}
		return EleTextList;
	}

	/*
	 * This method is used to Search for the given sendkeys based on the expected
	 * matchvalue (#Google Search field and Suggestions) returns boolean (True or
	 * False)
	 * 
	 * @param==> searchField, suggestions, searchkey and ,matching value
	 */

	public boolean doSearch(By searchField, By suggestions, String searchkey, String matchvalue)
			throws InterruptedException {
		boolean flag = false;

		doSendKeys(searchField, searchkey);
		Thread.sleep(2000);
		List<WebElement> sugglist = getElements(suggestions);
		int totalSuggestions = sugglist.size();
		System.out.println("Total number of Suggestion===" + sugglist.size());

		if (totalSuggestions == 0) {
			System.out.println("No suggestions found......");
			throw new FrameworkExcpetion("******** NO SUGGESTIONS FOUND************");
		}

		for (WebElement e : sugglist) {
			String allsuggestions = e.getText();
			System.out.println(allsuggestions);
			if (allsuggestions.contains(matchvalue)) {
				e.click();
				flag = true;
				break;
			}

		}
		if (flag) {
			System.out.println(matchvalue + ":is found ");
			return true;
		} else {
			System.out.println(matchvalue + ": is not found in the suggestions");
			return false;
		}
	}

	/*
	 * This method is used to verify the element isnot displayed on the page returns
	 * boolean (True or False)
	 * 
	 * @param===> locator
	 */

	public boolean IsElementNotPresent(By locator) {
		if (getElementsCount(locator) == 0) {
			return true;
		}
		return false;
	}
	/*
	 * This method is used to verify multiple elements present on the webpage
	 * returns boolean (True or False)
	 * 
	 * @param===> locator
	 */

	public boolean IsElementPresentMultipleTimes(By locator) {
		if (getElementsCount(locator) >= 0) {
			return true;
		}
		return false;
	}

	/*
	 * This method is used for validating particular Element ( multiple Elements
	 * count) must or must not be present on the page returns boolean (True or
	 * False)
	 * 
	 * @param->locator and expectedElement Count
	 */
	public boolean IsElementPresent(By locator, int expectedElementCount) {
		if (getElementsCount(locator) == expectedElementCount) {

			return true;
		} else {

			return false;
		}
	}
	/*
	 * This method is used for validating particular Element must or must not be
	 * present on the page returns boolean (True or False)
	 * 
	 * @param->locator
	 */

	public boolean IsElementPresent(By locator) {
		if (getElementsCount(locator) == 1) {
			System.out.println("Logo is present on the page");
			return true;
		} else {
			System.out.println("Logo is not present on the page");
			return false;
		}

	}

	// *********************Select Drop Down Utils***********************//
	/*
	 * This method is used to select the dropdown value by using the VisibleText in
	 * the dropdown no return value
	 * 
	 * @param====> locator, visibleText
	 */
	private Select getSelect(By locator) {
		return new Select(getElement(locator));
	}

	public void selectDropdownByVisibleText(By locator, String visibletext) {
		// WebElement sele = getElement(locator);
		// Select se = new Select(sele);
		getSelect(locator).selectByVisibleText(visibletext);
		// se.selectByVisibleText(visibletext);
	}

	/*
	 * This method is used to select the dropdown value by using the index in the
	 * dropdown no return value
	 * 
	 * @param====> locator, index
	 */

	public void selectDropdownByIndex(By locator, int index) {
//		WebElement sele = getElement(locator);
//		Select se = new Select(sele);
//		se.selectByIndex(index);
		getSelect(locator).selectByIndex(index);
	}

	/*
	 * This method is used to select the dropdown value by using the 'Value'
	 * attribute in the DOM no return value
	 * 
	 * @param====> locator, 'Value' attribute in the DOM
	 */

	public void selectDropdownByValue(By locator, String Value) {
//		WebElement sele = getElement(locator);
//		Select se = new Select(sele);
//		se.selectByValue(Value);
		getSelect(locator).selectByValue(Value);
	}

	/*
	 * DONOT Use THis way it will be more complex and in the case of user point of
	 * view why should he need to enter all the different if he chooses only select
	 * by value
	 */
//	public void SelectDropDown(By locator, String selecttype, String text, int index, String Value) {
//		
//		Select sel = new Select(getElement(locator));
//		switch (selecttype.toLowerCase()) {
//		case "visible text":
//			sel.selectByVisibleText(text);
//			break;
//		case "index":
//			sel.selectByIndex(index);
//			break;
//		case "value":
//			sel.selectByValue(Value);
//			break;
//
//		default:
//			break;
//		}
//	}

	/*
	 * this method is used to count the number of elements in the dropdown of select
	 * tag return type is 'int'
	 * 
	 * @param===> locator
	 */

	public int getDropdownOptionsCount(By locator) {
		return getSelect(locator).getOptions().size();
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		// Select sel = new Select(getElement(locator));
		List<WebElement> listOptions = getSelect(locator).getOptions();
		System.out.println(listOptions.size());

		List<String> Alloptionstext = new ArrayList<String>();
		for (WebElement e : listOptions) {
			String alltext = e.getText();
			Alloptionstext.add(alltext);

		}
		return Alloptionstext;

	}

	/*
	 * selecting the value using select class
	 */
	public void getDropDownValueUsingSelect(By locator, String value) {
		// Select sel = new Select(getElement(locator));
		List<WebElement> listOptions = getSelect(locator).getOptions();
		SelectDropDown(listOptions, value);
	}
	/*
	 * selecting the value without using select class
	 */

	public void selectDropdownValue(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		// List<String> textlist = new ArrayList<String>();
		SelectDropDown(optionsList, value);
	}

	// Encapsulation:
	private void SelectDropDown(List<WebElement> optionsList, String value) {

		System.out.println("Total number of options" + optionsList.size());

		for (WebElement e : optionsList) {
			String st = e.getText();
			System.out.println(st);
			// textlist.add(st);
			if (st.equals(value)) {
				e.click();
				break;
			}
		}

	}

	// **************************************ActionUtil**********************************************//

	/**
	 * This method is used to click on element using Actions class
	 * 
	 * @param locator
	 */
	public void doActionsClick(By locator) {

		act.click(getElement(locator));
	}

	/**
	 * 
	 * 
	 */

	public void doContextXlixk(By locator, String text) {
		act.contextClick(getElement(locator)).perform();
		doClick(locator);

	}

	/**
	 * This method is used to enter value in text field using actions class
	 * 
	 * @param locator
	 * @param value
	 */

	public void doActionsSendkeys(By locator, String value) {

		act.sendKeys(getElement(locator), value).perform();
		;
	}

	/**
	 * mousehover two levels using by locator
	 */
	public void ParentChildMenu(By ParentMenu, By ChildMenu) throws InterruptedException {

		act.moveToElement(getElement(ParentMenu)).perform();
		Thread.sleep(2000);
		doClick(ChildMenu);

	}

	/*
	 * mousehover two levels
	 */
	public void ParentChildMenu(String ParentMenu, String ChildMenu) throws InterruptedException {

		act.moveToElement(getElement(By.xpath("//*[text()='" + ParentMenu + "']"))).perform();
		Thread.sleep(2000);
		getElement(By.xpath("//*[text()='" + ChildMenu + "'][1]")).click();
		doClick(By.xpath("//*[text()='" + ChildMenu + "'][1]"));
	}

	public void ParentChildMenu(By level1, By level2, By level3, By level4) throws InterruptedException {

		Thread.sleep(2000);
		doClick(level1);
		Thread.sleep(2000);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(2000);
		doClick(level4);
	}

	// *************************************Wait
	// Utils**********************************************//

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does notnecessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param TimeOut
	 * @return WebElement
	 */

	public WebElement WaitForElementPresence(By locator, int TimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page as well. Visibility means that the element is not
	 * only displayed but also has a height and width that isgreater than 0. Default
	 * polling/Interval time = 500milliseconds
	 * 
	 * @param locator
	 * @param TimeOut
	 * @return WebElement
	 */
	public WebElement WaitForElementVisibile(By locator, int TimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement WaitForElementVisibile(By locator, int TimeOut, int intervaltime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut), Duration.ofSeconds(intervaltime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
	/**
	 * wait for Element visible on the page with fluent wait features
	 * @param locator
	 * @param TimeOut
	 * @param pollingtime
	 * @return
	 */
	public WebElement WaitForElementVisibileWithFluentFeatures(By locator, int TimeOut, int pollingtime) {
		  Wait<WebDriver> wait= new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(TimeOut))
					.pollingEvery(Duration.ofSeconds(pollingtime))
					.ignoring(NoSuchElementException.class)
					.withMessage("=========Element no Found==========")
					.ignoring(StaleElementReferenceException.class)
			.ignoring(ElementNotInteractableException.class)
			.withMessage("=====element is not found======" +locator);
			
			return	wait.until(ExpectedConditions.visibilityOfElementLocated(locator));			

		  
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param TimeOut
	 */

	public void WaitForElementAndClick(By locator, int TimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible.
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param Timeout
	 * @return
	 */

	public List<WebElement> WaitForElementsVisible(By locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param Timeout
	 * @return List<WebElement> 
	 */
	
	public List<WebElement> WaitForElementsPresence(By locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	
	//****************get title and page URL wwith Waits***********************/
	public  String getPageTitleIs(String ExpectedTitle, int timeout) {
		if(waitForTitleIs(ExpectedTitle, timeout)) {
			return driver.getTitle();
		}else {
			return "-1";
		}
	}
	
	public  String getPageTitleContains(String FractionTitle, int timeout) {
		if(waitForTitleContains(FractionTitle, timeout)) {
			return driver.getTitle();
		}else {
			return "-1";
		}
	}

	public  boolean waitForTitleIs(String ExpectedTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		boolean flag = false;

		try {
			return wait.until(ExpectedConditions.titleIs(ExpectedTitle));

		} catch (TimeoutException e) {
			System.out.println("Title is not matched");
		}
		return flag;
	}
	
	public  boolean waitForTitleContains(String FractionTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		boolean flag = false;

		try {
			return wait.until(ExpectedConditions.titleContains(FractionTitle));

		} catch (TimeoutException e) {
			System.out.println("Title is not matched");
		}
		return flag;
	}
	/**
	 * Both Contains and waiting
	 * @param fractionTitle
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleContainsAndReturn(String fractionTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("title is not matched");
			return "-1";
		}
	}

	public  boolean waitForURLContains(String FractionURL, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		boolean flag = false;

		try {
			return wait.until(ExpectedConditions.urlContains(FractionURL));

		} catch (TimeoutException e) {
			System.out.println("URL is not matched");
		}
		return flag;
	}
	
	public  String getPageURLContains(String FractionURL, int timeout) {
		if(waitForURLContains(FractionURL, timeout)) {
			return driver.getCurrentUrl();
		}else {
			return "-1";
		}
	}
	
	/**
	 * Both Contains and waiting
	 * @param fractionTitle
	 * @param timeOut
	 * @return
	 */
	public String waitForURLContainsAndReturn(String fractionURL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURL));// true
			return driver.getCurrentUrl();
		} catch (TimeoutException e) {
			System.out.println("URL is not matched");
			return "-1";
		}

	}
	
	
	public  Alert waitForAlertAndSwitch(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	
		public  Alert waitForAlertUsingFluentWaitAndSwitch(By locator, int timeout) {
			 Wait<WebDriver> wait= new FluentWait<WebDriver>(driver)
					 .withTimeout(Duration.ofSeconds(timeout))
						.ignoring(NoAlertPresentException.class)
						.withMessage("=========JS Alert is not Present=========="+locator);
			 
			 return wait.until(ExpectedConditions.alertIsPresent());
		
	}
		
		
	public  String getAlertText(int timeout) {
		return waitForAlertAndSwitch(timeout).getText();
	}
	public  void acceptAlert(int timeout) {
		waitForAlertAndSwitch(timeout).accept();
	}
	public  void dismissAlert(int timeout) {
		waitForAlertAndSwitch(timeout).dismiss();
	}
	public  void enterValueOnAlert(int timeout,String value ) {
		 waitForAlertAndSwitch(timeout).sendKeys(value);
	}

	//**********************wait for frames*********************************
		public void waitForFrameUsingLocatorAndSwitchToIt(By frameLocator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}

		public void waitForFrameUsingLocatorAndSwitchToIt(int frameIndex, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}

		public void waitForFrameUsingLocatorAndSwitchToIt(String idOrName, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
		}

		public void waitForFrameUsingLocatorAndSwitchToIt(WebElement frameElement, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}

		//************** wait for window/tab***************//
		public boolean waitForNewWindowOrTab(int expectedNumberOfWindows, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
					return true;
				}
			} catch (TimeoutException e) {
				System.out.println("number of windows are not matched....");
			}

			return false;
		}
	

}
