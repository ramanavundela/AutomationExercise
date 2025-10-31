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
	        if (extent == null) {
	            Date d = new Date();
	            fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	            // ✅ Create a folder for reports inside workspace
	            String reportDir = System.getProperty("user.dir") + "/extentReport/";
	            new File(reportDir).mkdirs();

	            String fullReportPath = reportDir + fileName;
	        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fullReportPath);
	       
	        
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle(fileName);
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName(fileName);
	        
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        extent.setSystemInfo("Automation Tester", "Ramana");
	        extent.setSystemInfo("Organization", "Taxilla");
	        extent.setSystemInfo("Build no", "Taxilla Sprint-1a");
	        
	        }
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
