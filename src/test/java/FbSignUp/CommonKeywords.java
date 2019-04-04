package FbSignUp;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonKeywords extends BaseTest {

	public static String filename;

	/**
	 * Opens browser and enters test URL. s@param browserName This is browser name
	 * in which automation should run.It Should be updated accordingly in
	 * config.properties file.
	 * 
	 * @param testUrl This is testUrl in which automation should run.It should be
	 *                updated accordingly in config.properties file.
	 */

	public static void openBrowserAndEnterUrl(String browserName, String testUrl) {
		if (browserName.equals("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equals("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		log.info(browserName + " Browser Launched!");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		driver.get(testUrl);
		log.info("Navigated to " + testUrl);
	}

	/**
	 * Sends value in textbox/element.
	 * 
	 * @param key   Textbox/Element name where value should be sent.XPATH/CSS/ID of
	 *              an element should be added in signup.properties file
	 * @param value Value which should be sent in textbox/element. Value of an
	 *              element should be added in signup.properties file
	 */

	public static void sendKeys(String key, String value) {
		try {
			if (key.endsWith("_XPATH")) {
				driver.findElement(By.xpath(fb.getProperty(key))).sendKeys(fb.getProperty(value));
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(fb.getProperty(key))).sendKeys(fb.getProperty(value));
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(fb.getProperty(key))).sendKeys(fb.getProperty(value));
			}
			log.info("Typing in an element: " + key + "Value: " + fb.getProperty(value));
		} catch (Throwable t) {
			log.error("Error while entering text in textbox " + key);
			Assert.fail("Error while entering text in textbox " + key);
		}

	}

	/**
	 * Clicks on element in Webpage.
	 * 
	 * @param element Element which should be clicked. XPATH/CSS/ID of an element
	 *                should be added in signup.properties file
	 */

	public static void clickElement(String element) {
		try {
			if (element.endsWith("_XPATH")) {
				driver.findElement(By.xpath(fb.getProperty(element))).click();
			} else if (element.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(fb.getProperty(element))).click();
			} else if (element.endsWith("_ID")) {
				driver.findElement(By.id(fb.getProperty(element))).click();
			}
			log.info("Clicking an element:" + element);
		} catch (Throwable t) {
			log.error("Error while clicking an element " + element);
			Assert.fail("Error while clicking an element " + element);
		}

	}

	/**
	 * Verifies that element is present on Webpage.
	 * 
	 * @param element Element name whose present needs to check on Webpage.
	 * @return True-if element is present on Webpage. False-if element is not
	 *         present on Webpage.
	 */

	public static boolean isElementPresent(String element) {

		try {

			if (element.endsWith("_XPATH")) {
				driver.findElement(By.xpath(fb.getProperty(element)));

			} else if (element.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(fb.getProperty(element)));

			} else if (element.endsWith("_ID")) {
				driver.findElement(By.id(fb.getProperty(element)));
			}
			log.info("An element: " + element + " is present on page!");
			return true;
		} catch (Throwable t) {
			log.error("Element " + element + " not present on the page!");
		
		}
		return false;

	}

	/**
	 * Selects value from dropdown pn Webpage.
	 * 
	 * @param key           Dropdown name in which value needs to
	 *                      select.XPATH/CSS/ID of a key should be added in
	 *                      signup.properties file
	 * @param dropDownValue Value of dropdown which needs to select.XPATH/CSS/ID of
	 *                      a dropDownValue should be added in signup.properties
	 *                      file
	 * 
	 */

	public static void selectValueFromDropdown(String key, String dropDownValue) {
		try {
			if (key.endsWith("_XPATH")) {
				WebElement dateDropDown = driver.findElement(By.xpath(fb.getProperty(key)));
				Select select = new Select(dateDropDown);
				select.selectByValue(fb.getProperty(dropDownValue));

			} else if (key.endsWith("_CSS")) {
				WebElement dateDropDown = driver.findElement(By.cssSelector(fb.getProperty(key)));
				Select select = new Select(dateDropDown);
				select.selectByValue(fb.getProperty(dropDownValue));

			} else if (key.endsWith("_ID")) {
				WebElement dateDropDown = driver.findElement(By.id(fb.getProperty(key)));
				Select select = new Select(dateDropDown);
				select.selectByValue(fb.getProperty(dropDownValue));

			}
			log.info(dropDownValue + " is selected in " + key + "dropdown!");
		} catch (Throwable t) {
			log.error("User is not able to select value " + dropDownValue + " from dropdown " + key);
			Assert.fail("User is not able to select value " + dropDownValue + " from dropdown " + key);

		}
	}

	/**
	 * Waits for an element on Webpage.
	 * 
	 * @param element An Element for which needs to wait on Webpage.
	 * 
	 */

	public static void waitForElement(String element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("explicit.wait")));
			System.out.println(fb.getProperty(element));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fb.getProperty(element))));
		} catch (Throwable t) {
			log.error("Wait for an element failed! " + fb.getProperty(element));
			Assert.fail("Wait for an element failed! " + fb.getProperty(element));

		}
	}

	/**
	 * Captures screenshot
	 * 
	 * @param element An Element for which needs to wait on Webpage.
	 * 
	 */

	public static void captureScreenshot() {
		try {

			Date d = new Date();
			filename = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot,
					new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + filename));
		} catch (Throwable t) {
			log.error("Screenshot capture failed! ");
			Assert.fail("Screenshot capture failed! ");

		}
	}

}
