package extentListenersUtility;

import autexe.Signup;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;




public class ExtentManager {

	private static ExtentReports extent;
	public static String fileName;
	
	

	    public static ExtentReports createInstance()
	    {
	    	// Generate timestamp-based unique report name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	        String reportPath = System.getProperty("user.dir") + "/extentReport/Extent_" + timeStamp + ".html";

	        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
	        spark.config().setDocumentTitle("Automation Test Report");
	        spark.config().setReportName("Automation Results");
	        spark.config().setTheme(Theme.STANDARD);

	        extent = new ExtentReports();
	        extent.attachReporter(spark);
	        return extent;
	    }

	
	    
		public static void captureScreenshot() throws IOException {
			

			
			Date d = new Date();
			fileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";

			
			
		File screeshot = ((TakesScreenshot)  Signup.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screeshot, new File(".//reports//"+fileName));
		}
		
		

		public static void captureElementScreenshot(WebElement element) throws IOException {
			
			Date d = new Date();
			String fileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";

			
			
			File screeshot = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screeshot, new File(".//screenshot//"+"Element_"+fileName));
		}

	 


	}
