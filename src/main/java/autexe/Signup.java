package autexe;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Signup {
	
		
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	@FindBy(xpath = "//input[@id='newsletter']")
	public static WebElement newsletter;
	
	@FindBy(xpath = "//input[@id='optin']")
    public static WebElement partners;
	
	@FindBy(xpath = "//input[@id='first_name']")
    public static WebElement fn;
	
	@FindBy(xpath = "//input[@id='last_name']")
    public static WebElement ln;
	
	@FindBy(xpath = "//input[@id='company']")
    public static WebElement com;
	
	@FindBy(xpath = "//input[@id='address1']")
    public static WebElement add1;
	
	@FindBy(xpath = "//input[@id='address2']")
    public static WebElement add2;
	
	@FindBy(xpath = "//select[@id='country']")
    public static WebElement cnt;
	
	@FindBy(xpath = "//input[@id='state']")
    public static WebElement st;
	
	@FindBy(xpath = "//input[@id='city']")
    public static WebElement ct;
	
	@FindBy(xpath = "//input[@id='zipcode']")
    public static WebElement zc;
	
	@FindBy(xpath = "//input[@id='mobile_number']")
    public static WebElement mn;
	
	@FindBy(xpath = "//button[@type='submit']")
	public static WebElement crt;
	
	@FindBy(xpath = "//a[text()='Continue']")
	public static WebElement cnue;
	
	@FindBy(xpath = "//a[text()='Continue']")
	public static WebElement cnue1;
	
	public Signup(WebDriver driver) {
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}

	public static void launchBrowser() {
		driver = new ChromeDriver();
		driver.get("https://automationexercise.com/");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}

	public static String getHomePageTitle() {
		String actualTitle = driver.getTitle();
		return actualTitle;

	}

	public static WebElement getHomeElement() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement e = driver.findElement(By.xpath("//a[normalize-space()='Home']"));
		return e;
	}

	public static WebElement clickSignuplogin() {

		String beforelogin = driver.getCurrentUrl();
		WebElement e1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Signup / Login']")));
		e1.click();
		String afterlogin = driver.getCurrentUrl();

		if (!beforelogin.equals(afterlogin)) {
			System.out.println("New user signup is visible");
		} else {
			System.out.println("Click failed — Home page is visible!");
		}

		return e1;
	}

	public static void enterNameEmail() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement na = driver.findElement(By.name("name"));
		na.sendKeys("Ramana");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//String uniqueEmail = "ramana" + System.currentTimeMillis() + "@gmail.com";
		driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("ramanavundela15@gmail.com");

		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement e2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Signup']")));
		e2.click();

		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[normalize-space()='Enter Account Information']")));
		 System.out.println("Enter Account Information page is Visible"); 
		 
		 

	}
	
	public static String enterAccountInfo()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement na1 = driver.findElement(By.xpath("//input[@id='id_gender1']"));
		na1.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement na2 = driver.findElement(By.name("name"));
		na2.clear();
		na2.sendKeys("Ramana");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement disabledBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='email']")));
		
		System.out.println("Email box displayed? " + disabledBox.isDisplayed());
	    System.out.println("Email box enabled? " + disabledBox.isEnabled());
		String actualValue = disabledBox.getAttribute("value");
		System.out.println("Actual value is" + actualValue);
		
		/*
		 * System.out.println("Expected: '" + "ramanavundela1@gmail.com" + "'");
		 * System.out.println("Actual: '" + actualValue + "'");
		 * System.out.println("Actual (with length): " + actualValue.length());
		 * System.out.println("Expected (with length): " +
		 * "ramanavundela@gmail.com".length());
		 */
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//input[@data-qa='password']")).sendKeys("Ramana$1");
				
		return actualValue;
	}
	
	public static void dob()
	{
		WebElement dayDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='days']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dayDropdown);
		//dayDropdown.click();
		Select days =  new Select(dayDropdown);
		days.selectByVisibleText("25");
		
		WebElement monthDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='months']")));
		monthDropdown.click();
		Select months =  new Select(monthDropdown);
		months.selectByVisibleText("August");
		
		WebElement yearDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='years']")));
		yearDropdown.click();
		Select year =  new Select(yearDropdown);
		year.selectByVisibleText("1991");
		}
	
	public boolean isCheckboxSelected(WebElement newsletter) {
		if(!newsletter.isSelected()) {
			newsletter.click();
		}
	    return newsletter.isDisplayed() && newsletter.isEnabled() && newsletter.isSelected();
	}
	
	public boolean isCheckboxSelecteda(WebElement partners) {
		if(!partners.isSelected()) {
			partners.click();
			}
	    return partners.isDisplayed() && partners.isEnabled() && partners.isSelected();
	}
	
	public static void addressDetails() {
		
		fn.sendKeys("ramana");
		ln.sendKeys("vundela");
		com.sendKeys("taxilla");
		add1.sendKeys("seshadri main street");
		add2.sendKeys("sholinganallur");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cnt);
		Select cntry =  new Select(cnt);
		cntry.selectByVisibleText("India");
		st.sendKeys("TN");
		ct.sendKeys("Chennai");
		zc.sendKeys("600119");
		mn.sendKeys("8547961235");
				
		
		
	}
	
	public static void accountcreated()
	{
       WebElement crt1 =  wait.until(ExpectedConditions.elementToBeClickable(crt));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", crt1);
       
		driver.findElement(By.xpath("//b[normalize-space()='Account Created!']"));
		System.out.println("Account created successfully");
		
		WebElement cnue1 =  wait.until(ExpectedConditions.elementToBeClickable(cnue));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", cnue1);

		
    }
	
	@FindBy(xpath = "//a[contains(text(),'Logged in as')]")
	WebElement loggedInAs;

	public boolean isUserLoggedIn(String username) {
try {
	    wait.until(ExpectedConditions.visibilityOf(loggedInAs));
	    String text = loggedInAs.getText();
	    System.out.println("Displayed Text: " + text);
	    return text.contains(username);
} catch (Exception e) {
    System.out.println("❌ Login verification failed: " + e.getClass().getSimpleName());
    return false;
}
	}
	
	 @FindBy(xpath = "//a[contains(text(),'Delete Account')]")
	    WebElement deleteAccountLink;

	    @FindBy(xpath = "//b[normalize-space()='Account Deleted!']")
	    WebElement deletedConfirmationText;

	    public void clickDeleteAccount() {
	       WebElement deleteacc =  wait.until(ExpectedConditions.elementToBeClickable(deleteAccountLink));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteacc);

	    }

	    public boolean verifyAccountDeleted() {
	    	
	    	try {
	        wait.until(ExpectedConditions.visibilityOf(deletedConfirmationText));
	        WebElement cnue2 = wait.until(ExpectedConditions.elementToBeClickable(cnue1));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", cnue2);

	        return deletedConfirmationText.getText().contains("ACCOUNT DELETED");
	    	} catch (Exception e) {
	    	    System.out.println("❌ Account deletion failed: " + e.getClass().getSimpleName());
	    	    return false;
	    	}
	    	
			

	    }
	

}
