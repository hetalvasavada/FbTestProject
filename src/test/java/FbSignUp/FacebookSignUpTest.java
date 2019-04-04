package FbSignUp;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;

public class FacebookSignUpTest extends BaseTest {

	@Test()
	public void signUpFacebook() {

		CommonKeywords.sendKeys("firstname_XPATH", "firstname_VALUE");
		CommonKeywords.sendKeys("surname_XPATH", "surname_VALUE");
		CommonKeywords.sendKeys("email_XPATH", "email_VALUE");
		CommonKeywords.sendKeys("reemail_XPATH", "reemail_VALUE");
		CommonKeywords.sendKeys("password_XPATH", "password_VALUE");
		CommonKeywords.selectValueFromDropdown("daydropdown_XPATH", "date_VALUE");
		CommonKeywords.selectValueFromDropdown("monthdropdown_XPATH", "month_VALUE");
		CommonKeywords.selectValueFromDropdown("yeardropdown_XPATH", "year_VALUE");
		CommonKeywords.clickElement("femail_XPATH");
		CommonKeywords.clickElement("signupbtn_XPATH");
		Assert.assertTrue(CommonKeywords.isElementPresent("updatecontactinfo_XPATH"),
				"Update Contact Info Button not present!");
		Assert.assertTrue(CommonKeywords.isElementPresent("profileName_XPATH"), "Profile Button not present!");

	}

}
