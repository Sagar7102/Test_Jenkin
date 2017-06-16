package core;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Base {
	WebDriver driver;
	//AppiumDriver driver;
	String BaseUrl = "Http://www.olx.in"; 
	@Before
	public void setup() throws Exception{
		driver = initializeChromeDriver(driver);	
		System.out.println("site name: "+ BaseUrl);
		driver.get(BaseUrl);
	}
	WebDriver initializeChromeDriver(WebDriver driver){
		System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		prefs.put("profile.default_content_settings.popups", 0);
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--disable-extensions");
	    options.addArguments("--test-type");       
	    options.addArguments("start-maximized"); 
	    options.addArguments("disable-popup-blocking");
	    options.setExperimentalOption("prefs", prefs);
	    driver = new ChromeDriver(options);
	    return driver;		
	}
	@After
	public void tearDown() throws Exception {
		 driver.close();
		 driver.quit();
	}
}

