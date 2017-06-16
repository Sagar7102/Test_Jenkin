package core;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Action {

	static int maxWaitingTime = 600;

	public static void waitForElementToBeClickable(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	public static void closePopup(WebDriver driver) throws Exception{
		if(checkElementAvailability(driver,By.cssSelector(".btn.no.span6"))){
				click(driver, By.cssSelector(".btn.no.span6"));
				waitTill(1000);
		}
	}
	public static boolean checkElementAvailability(WebDriver driver,By by){
		try {
			if (driver.findElement(by).isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	public static void waitFor(WebDriver driver, By by) throws Exception {
		log(driver, "Waiting for Element " + by.toString());
		WebDriverWait wdw = new WebDriverWait(driver, maxWaitingTime);
		wdw.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	 public static void waitForElement(WebDriver driver, By by){
	    log(driver, "Waiting for Element with id "+ by.toString());
	    for(int i=0; i<= 40; i++){
	    if(!verifyElementPresent(driver, by)){
	     waitTill(4000);//wait for 4 seconds
	    }
	     else{
	      break;
	     }
	     
	     }
	    }
	 
	public static void waitForOptionalElement(final WebDriver driver, final By by1,final By by2) throws Exception {
		log(driver, "Waiting for Element " + by1.toString()+" Or "+by2.toString());
		WebDriverWait wdw = new WebDriverWait(driver, maxWaitingTime);
	    ExpectedCondition<Boolean> conditionToCheck = new ExpectedCondition<Boolean>() {
	     	   public Boolean apply(WebDriver d) {
	    	      if (verifyElementPresent(driver, by1) || verifyElementPresent(driver, by2))
	    	        return true;
	    	     else
	    	          return false;
	    	    }
	    };
	    wdw.until(conditionToCheck);
	}
	public static void waitForNotVisible(WebDriver driver, By by) {
		log(driver, "Waiting for Element to disapper on site " + by);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public static void waitForVisible(WebDriver driver, By by) {
		log(driver, "Waiting for Element to be visible " + by);
		WebElement elm = getWebElement(driver, by);
		WebDriverWait wdw = new WebDriverWait(driver, maxWaitingTime);
		wdw.until(ExpectedConditions.visibilityOf(elm));
	}

	public static void scrollDown(WebDriver driver, String size) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + size + ")", "");
	}

	public static WebElement getWebElement(WebDriver driver, By by) {
		log(driver, "Looking for " + by);
		WebElement ele = driver.findElement(by);
		return ele;
	}

	public static void log(WebDriver driver, String message) {
		String pageURL = driver.getCurrentUrl();
		StackTraceElement caller = new Throwable().getStackTrace()[1];
		String callerInfo = caller.getClassName() + " " + caller.getMethodName() + " line " + caller.getLineNumber();
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.print("--->" + sdf.format(cal.getTime()) + " ");
		System.out.print("--->" + pageURL);
		System.out.println(callerInfo + " | " + message);
	}

	public static void logMessage(String message) {
		StackTraceElement caller = new Throwable().getStackTrace()[1];
		String callerInfo = caller.getClassName() + " " + caller.getMethodName() + " line " + caller.getLineNumber();
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.print("--->" + sdf.format(cal.getTime()) + " ");
		System.out.println(callerInfo + " | " + message);
	}

	public static List<WebElement> getWebElements(WebDriver driver, By by) {
		List<WebElement> lis = driver.findElements(by);
		return lis;
	}

	public static boolean verifyElementPresent(WebDriver driver, By by) {
		try {
			if (getWebElement(driver, by).isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			log(driver, "Element Not displayed by " + by);
			return false;
		}
	}

	public static boolean verifyElementPresentOnPageSource(WebDriver driver, By by) {
		try {
			getWebElement(driver, by);
			return true;
		} catch (Exception e) {
			log(driver, "Element Not foound by " + by);
			return false;
		}
	}

	public static boolean verifyTextPresent(WebDriver driver, By by, String text) {
		try {
			if (getWebElement(driver, by) == null) {
				return false;
			} else {
				if (getWebElement(driver, by).getText().contains(text)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			log(driver, "Element Not displayed by " + by);
			return false;
		}
	}

	public static boolean verifyElementIsEnabled(WebDriver driver, By by) {
		return getWebElement(driver, by).isEnabled();
	}

	public static void click(WebDriver driver, By by) throws Exception {
		try {
			getWebElement(driver, by).click();
		} catch (Exception e) {
			log(driver, "Element Not displayed" + by);
			throw e;
		}
		log(driver, "On Page" + driver.getCurrentUrl());
	}

	public static void click(WebDriver driver, By by, String info) throws Exception {
		click(driver, by);
	}

	public static String getText(WebDriver driver, By by) throws Exception {
		try {
			return getWebElement(driver, by).getText();
		} catch (Exception e) {
			log(driver, "Element Not displayed" + by);
			throw e;
		}
	}

	public static String getAttribute(WebDriver driver, By by, String attribute) throws Exception {
		try {
			return getWebElement(driver, by).getAttribute(attribute);
		} catch (Exception e) {
			log(driver, "Element Not displayed" + by);
			throw e;
		}
	}

	public String getCssValue(WebDriver driver, By by, String cssAttribute) throws Exception {
		String cssValue;
		try {
			cssValue = getWebElement(driver, by).getCssValue(cssAttribute);
		} catch (Exception e) {
			log(driver, "Element Not found" + by);
			throw e;
		}
		return cssValue;
	}

	public static void clear(WebDriver driver, By by) throws Exception {
		try {
			getWebElement(driver, by).clear();
		} catch (Exception e) {
			log(driver, "Element Not found" + by);
			throw e;
		}
	}

	public static void jSClick(WebDriver driver, By by) {
		WebElement ele = getWebElement(driver, by);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
	}

	public static void sendKeys(WebDriver driver, By by, String data) throws Exception {
		try {
			getWebElement(driver, by).sendKeys(data);
		} catch (Exception e) {
			log(driver, "Element Not found by " + by);
			throw e;
		}
	}

	public static void get(WebDriver driver, String url) {
		driver.get(url);
		waitTill();
	}

	public static void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
		waitTill();
	}

	public static void browserBack(WebDriver driver) {
		driver.navigate().back();
		waitTill();
	}

	public static void browserForward(WebDriver driver) {
		driver.navigate().forward();
		waitTill();
	}

	protected static void executeJavaScript(WebDriver driver, String javaScriptFunction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(javaScriptFunction);
	}

	public static void clickAlertOkButton(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public static String verifyTextInAlertBoxAndClickOnOkButton(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public static void clickAlertCancelButton(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public static boolean clickAlertCancelButton(WebDriver driver, String alertString) {
		Alert alert = driver.switchTo().alert();
		if (!alert.getText().contains(alertString)) {
			return false;
		}
		alert.dismiss();
		return true;
	}

	public static void clickCheckBox(WebDriver driver, By by) throws Exception {
		if (!getWebElement(driver, by).isSelected()) {
			click(driver, by);
		}
	}

	public static void doubleClick(WebDriver driver, By by) {
		// For FF browser.
		((JavascriptExecutor) driver).executeScript("var evt = document.createEvent('MouseEvents');"
				+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				+ "arguments[0].dispatchEvent(evt);", getWebElement(driver, by));
	}

	protected static void onMouseOver(WebDriver driver, By by) {
		Actions builder = new Actions(driver);
		WebElement menuRegistrar = getWebElement(driver, by);
		builder.moveToElement(menuRegistrar).build().perform();
	}

	public static void mouseOverJavascript(WebDriver driver, By by) {
		WebElement element = getWebElement(driver, by);
		String code = "var fireOnThis = arguments[0];" + "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent( 'mouseover', true, true );" + "fireOnThis.dispatchEvent(evObj);";
		((JavascriptExecutor) driver).executeScript(code, element);
	}

	public static void mouseHoverElement(WebDriver driver, WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	public static void selectFromDropDown(WebDriver driver, By by, String option) {
		Select droplist = new Select(getWebElement(driver, by));
		droplist.selectByVisibleText(option);
	}

	public static int getRandomIntwithinRange(int low, int high) {
		Random random = new Random();
		int randomNum = random.nextInt((high - low) + 1) + low;
		return randomNum;
	}

	public static void waitTill(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}

	public static void waitTill() {
		waitTill(3000);
	}

	public static void scrollToElement(WebDriver driver, By by) throws Exception {
		WebElement elm = driver.findElement(by);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elm);
	}
	 public static void pageDown(WebDriver driver, String value) {
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + value + ")", "");
		  }
	public static void hitEnter(WebDriver driver, By by) {
		log(driver, " Hit enter on " + by.toString());
		try {
			waitFor(driver, by);
			driver.findElement(by).sendKeys(Keys.RETURN);
		} catch (Exception e) {
		}
	}

	public static void setAttribute(WebDriver driver, By by, String attributeName, String attributeValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", getWebElement(driver, by),
				attributeName, attributeValue);
	}
	
	

	/*public static boolean verifyElementNotPresent(WebDriver driver, By by){
>>>>>>> c8b162017b778a2da1a6ecb9b2c8638eea96a97a
	       StackTraceElement caller = new Throwable().getStackTrace()[1];
	       String callerInfo = caller.getClassName()
	               + " " + caller.getMethodName()   
	               + " line " + caller.getLineNumber()  ;
	       //log(callerInfo,"verifying for element not to be present on page soruce"+by.toString());
	      try{
	      driver.findElement(by);
	      throw new Exception("Element founded");
	      }catch(Exception e){
	       return true;
	      }
	     }*/
	 public static boolean verifyElementNotPresent(WebDriver driver, By by){
	       try{
	       driver.findElement(by);
	       throw new Exception("Element founded");
	       }catch(Exception e){
	        return true;
	       }
	      }

}
