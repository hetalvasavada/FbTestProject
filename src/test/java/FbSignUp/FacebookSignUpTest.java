package FbSignUp;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseTest;

public class FacebookSignUpTest extends BaseTest {

	@DataProvider(name ="FacebookSignUp")
	 public Object[][] credentials() {

	      return CommonKeywords.generateRandomTestdata();
	 }

	@Test(dataProvider="FacebookSignUp")
	public void signUpFacebook(String firstname,String lastname,String email,String reemail,String password,String date,String month,String year) {
		CommonKeywords.sendKeys("firstname_XPATH", firstname);
		CommonKeywords.sendKeys("surname_XPATH", lastname);
		CommonKeywords.sendKeys("email_XPATH", email);
		CommonKeywords.sendKeys("reemail_XPATH", reemail);
		CommonKeywords.sendKeys("password_XPATH", password);
		CommonKeywords.selectValueFromDropdown("daydropdown_XPATH", date);
		CommonKeywords.selectValueFromDropdown("monthdropdown_XPATH", month);
		CommonKeywords.selectValueFromDropdown("yeardropdown_XPATH", year);
		CommonKeywords.clickElement("femail_XPATH");
		
		if(firstname.equalsIgnoreCase(" ")||lastname.equalsIgnoreCase(" ")||email.equalsIgnoreCase(" ")||reemail.equalsIgnoreCase(" ")||password.equalsIgnoreCase(" "))
		{
			CommonKeywords.clickElement("signupbtn_XPATH");
			Assert.assertTrue(CommonKeywords.isElementPresent("validationmessage_XPATH"),
					"Validation message is not shown!");
		}
		else {
			CommonKeywords.clickElement("signupbtn_XPATH");
		Assert.assertTrue((CommonKeywords.isElementPresent("updatecontactinfo_XPATH"))||(CommonKeywords.isElementPresent("continue_XPATH")),
				"Signup not successful!");
		}
	}

}
