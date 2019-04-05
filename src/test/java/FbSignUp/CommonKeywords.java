package FbSignUp;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.github.javafaker.Faker;

import Base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonKeywords extends BaseTest {

	public static String filename;

	/**
	 * Open browser and enter test URL.
	 * 
	 * @param browserName This is browser name in which automation should run.It
	 *                    Should be updated accordingly in config.properties file.
	 * @param testUrl     This is testUrl in which automation should run.It should
	 *                    be updated accordingly in config.properties file.
	 */

	public static void openBrowserAndEnterUrl(String browserName, String testUrl) {
		if (browserName.equals("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equals("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
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
	 * Generates random test data and passes to data provider
	 * @return testdata returns two dimensional array object of an test data
	 */

	public static Object[][] generateRandomTestdata() {

		int lengthTestdata = 5;
		String noOfTestdata = config.getProperty("testData");
		int noOfFields=driver.findElements(By.xpath(fb.getProperty("noOfElementsSignUp_XPATH"))).size();
		boolean useLetters = true;
		boolean useNumbers = false;

		Object[][] testdata = new Object[Integer.parseInt(noOfTestdata)][noOfFields-1];
		for (int i = 0; i < Integer.parseInt(noOfTestdata); i++) {
			String generatedString = RandomStringUtils.random(lengthTestdata, useLetters, useNumbers);
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			int randomDate = (int) (Math.random() * 27 + 1);
			int randomMonth = (int) (Math.random() * 11 + 1);
			int randomYear = ThreadLocalRandom.current().nextInt(1905, 2020);
			String reverse = "";
			for (int j = 0; j < noOfFields-1; j++) {
				if (j == 0)
					generatedString = firstName;
				if (j == 1)
					generatedString = lastName;
				if (j == 2)
					generatedString = generatedString.concat(firstName + "@gmail.com");
				if (j == 3)
					generatedString = testdata[i][j - 1].toString();
				if (j == 4) {
					for (int k = generatedString.length() - 1; k >= 0; k--) {
						reverse = reverse + generatedString.charAt(k);
					}
					generatedString = reverse;
				}
				if (j == 5)
					generatedString = Integer.toString(randomDate);
				if (j == 6)
					generatedString = Integer.toString(randomMonth);
				if (j == 7)
					generatedString = Integer.toString(randomYear);

				testdata[i][j] = generatedString.toLowerCase();
			}
		}
		return testdata;
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
				driver.findElement(By.xpath(fb.getProperty(key))).clear();
				driver.findElement(By.xpath(fb.getProperty(key))).sendKeys(value);
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.xpath(fb.getProperty(key))).clear();
				driver.findElement(By.cssSelector(fb.getProperty(key))).sendKeys(value);
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.xpath(fb.getProperty(key))).clear();
				driver.findElement(By.id(fb.getProperty(key))).sendKeys(value);
			}
			log.info("Typing in an element: " + key + "Value: " + value);
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
	 * Selects value from dropdown on Webpage.
	 * 
	 * @param key           Dropdown name in which value needs to
	 *                      select.XPATH/CSS/ID of a key should be added in
	 *                      signup.properties file
	 * @param dropDownValue Value of dropdown which needs to select.
	 * 
	 */

	public static void selectValueFromDropdown(String key, String dropDownValue) {
		try {
			WebElement dateDropDown = driver.findElement(By.xpath(fb.getProperty(key)));
			Select select = new Select(dateDropDown);
			select.selectByValue(dropDownValue);
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