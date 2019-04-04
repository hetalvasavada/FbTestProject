package Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import FbSignUp.CommonKeywords;
import FbSignUp.FacebookSignUpTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public static WebDriver driver;
	public static Properties fb = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream signupFile;
	public static FileInputStream configFile;
	public static Logger log = Logger.getLogger(FacebookSignUpTest.class);
	public static CommonKeywords cmnkeywords = new CommonKeywords();

	@BeforeSuite

	/**
	 * Configures and loads all property files. Calls openBrowserAndEnterUrl method.
	 */

	public void setUp() {

		try {
			if (driver == null) {
				PropertyConfigurator.configure(
						System.getProperty("user.dir") + "\\src\\test\\resources\\Properties\\log4j.properties");
				try {
					signupFile = new FileInputStream(
							System.getProperty("user.dir") + "\\src\\test\\resources\\Properties\\signup.properties");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				try {
					fb.load(signupFile);
					log.info("Signup property file loaded!");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					configFile = new FileInputStream(
							System.getProperty("user.dir") + "\\src\\test\\resources\\Properties\\config.properties");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					config.load(configFile);
					log.info("Config file loaded!");
				} catch (IOException e) {
					e.printStackTrace();
				}

				CommonKeywords.openBrowserAndEnterUrl(config.getProperty("browser"), config.getProperty("url"));
			}
		} catch (Throwable t) {
			log.error("Setup method failed in before suite!");
			Assert.fail("Setup method failed in before suite!");
		}

	}

	@AfterSuite
	public void tearDown() {
		try {
			driver.quit();
		} catch (Throwable t) {
			log.error("Teardown method failed in after suite!");
			Assert.fail("Teardown method failed in after suite!");
		}
	}
}
