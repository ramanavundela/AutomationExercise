package autexeTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import autexe.Signup;

public class SignupTest {

	@BeforeSuite
	public void browserlaunch() {
		Signup.launchBrowser();
	}

	@Test(priority = 1)
	public void verifyHomePageVisible() {

		// Signup su = new Signup();

		String actualTitl = Signup.getHomePageTitle();

		// Verify home page title
		Assert.assertEquals(actualTitl, "Automation Exercise", "Home page title mismatch!");
		if (actualTitl.equals("Automation Exercise"))
			System.out.println("Homepage title matched");
		else
			System.out.println("Homepage title mismatch");

		WebElement e = Signup.getHomeElement();
		// Verify webelement is displayed
		Assert.assertTrue(e.isDisplayed(), "Home page logo not visible!");
		if (e.isDisplayed())
			System.out.println("Homepage logo visible");
		else
			System.out.println("Homepage logo not visbible");

		Signup.clickSignuplogin();
		Signup.enterNameEmail();
	}

	@Test(priority = 2)
	public static void enterAccountInformation() {

		String actualValue1 = Signup.enterAccountInfo();

		/*
		 * for (char c : actualValue1.toCharArray()) { System.out.println((int)c +
		 * " -> " + c); }
		 */
		System.out.println("DEBUG — Value in disabled textbox: " + actualValue1);

		
		
		/*
		 * String cleanedActual = actualValue1.replaceAll("[^\\p{Print}]", "").trim();
		 * System.out.println("Cleaned actual value: '" + cleanedActual + "'");
		 * System.out.println("Cleaned length: " + cleanedActual.length());
		 */

		Assert.assertEquals(actualValue1, "ramanavundela14@gmail.com", "Value in disabled textbox mismatch!");
		System.out.println("Value in email  mathces with the entered value at the time of signup");

		Signup.dob();

		Signup su = new Signup(Signup.driver);

		Assert.assertTrue(su.isCheckboxSelected(Signup.newsletter), "Newsletter checkbox not selected!");
		Assert.assertTrue(su.isCheckboxSelecteda(Signup.partners), "Offers checkbox not selected!");

		Signup.addressDetails();
		Signup.accountcreated();
	
		
		Assert.assertTrue(su.isUserLoggedIn("Ramana"), "Login verification failed!");
		su.clickDeleteAccount();
	    Assert.assertTrue(su.verifyAccountDeleted(), " Account deletion failed!");

	}

	@AfterSuite
	public void tearDown() {
		if (Signup.driver != null) {
			//Signup.driver.quit();
		}
	}

}
