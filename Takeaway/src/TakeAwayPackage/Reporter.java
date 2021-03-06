package TakeAwayPackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Reporter {
	
	private static List<Result> details;
	private static List<String> ScreenshotPathlist;
	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	 //Mention the path of the Template HTML report path here
	private static final String templatePath = "C:\\Selenium\\report_template.html";
	static String ScreenshotPath;
	static int intScreenshotCounter=0;
	static boolean ScreenshotFlag=false;
	public Reporter() {
	}
	/*
	 * Function name: initialize
	 * Purpose: To initialize the required ArrayLists for HTML report creation
	 * Parameters: No parameters
	 */
	public static void initialize() {
		details = new ArrayList<Result>();
		ScreenshotPathlist= new ArrayList<String>();
	}
	/*
	 * Function name: report
	 * Purpose: To compare two values and send the result to the HTML report and in case of failure take a screenshot
	 * Parameters: 1st Parameter- The Actual Value from the application
	 * 				2nd Parameter- The Expected Value
	 * 				3rd Parameter- pass the Webdriver object
	 */
	public static void report(String actualValue,String expectedValue, WebDriver driver) {
		if(actualValue.equals(expectedValue)) {
			Result r = new Result("<font color=\"green\">Pass</font>","Actual value '" + actualValue + "' matches expected value");
			details.add(r);
		} else {
			Reporter.captureScreenShot(driver);			
			Result r = new Result("<font color=\"red\">Fail</font>","Actual value '" + actualValue + "' does not match expected value '" + expectedValue + "'");
			details.add(r);
		}
	}
	/*
	 * Function name: ReportWithScreenshot
	 * Purpose: To send a custom message to the HTML report with a screenshot
	 * Parameters: 1st Parameter- Status, mentioned Pass, Fail, Done or Warning
	 * 				2nd Parameter- Description of the Status, Elaborate more on the Status
	 * 				3rd Parameter- pass the Webdriver object
	 */
	public static void ReportWithScreenshot(String Status,String InformationText,WebDriver driver) {
		Reporter.captureScreenShot(driver);
		if(Status.contains("Done"))
		{
		Result r = new Result("<font color=\"blue\">Done</font>",InformationText);
		details.add(r);
		}
		if(Status.contains("Warning"))
		{
		Result r = new Result("<font color=\"yellow\">Warning</font>",InformationText);
		details.add(r);
		}
		if(Status.contains("Pass"))
		{
		Result r = new Result("<font color=\"green\">Pass</font>",InformationText);
		details.add(r);
		}
		if(Status.contains("Fail"))
		{
		Result r = new Result("<font color=\"red\">Fail</font>",InformationText);
		details.add(r);
		}	
		
	}
	
	/*
	 * Function name: report
	 * Purpose: To add a custom message to the HTML report without any screenshot and only with Status Done
	 * Parameters: Description of the Status, Elaborate more on the Status
	 * 				
	 */
	public static void report(String InformationText) {
		
			Result r = new Result("<font color=\"blue\">Done</font>",InformationText);
			details.add(r);
		
	}
	/*
	 * Function name: ReportStatus
	 * Purpose: To send a custom message to the HTML report without a screenshot
	 * Parameters: 1st Parameter- Status, mentioned Pass, Fail, Done or Warning
	 * 				2nd Parameter- Description of the Status, Elaborate more on the Status
	 */
	public static void ReportStatus(String Status,String InformationText) {
		if(Status.contains("Done"))
		{
		Result r = new Result("<font color=\"blue\">Done</font>",InformationText);
		details.add(r);
		}
		if(Status.contains("Warning"))
		{
		Result r = new Result("<font color=\"yellow\">Warning</font>",InformationText);
		details.add(r);
		}
		if(Status.contains("Pass"))
		{
		Result r = new Result("<font color=\"green\">Pass</font>",InformationText);
		details.add(r);
		}
		if(Status.contains("Fail"))
		{
		Result r = new Result("<font color=\"red\">Fail</font>",InformationText);
		details.add(r);
		}
		
	
}
	/*
	 * Function name: showResults
	 * Purpose: This is used to know the contents of the details ArrayList
	 * Parameters: No Parameters
	 */
	public static void showResults() {
		for (int i = 0;i < details.size();i++) {
			System.out.println("Result " + Integer.toString(i) + ": " + details.get(i).getResult());
		}
	}
	/*
	 * Function name: writeResults
	 * Purpose: This is used to create the final HTML report. This function is called towards the end of the Test
	 * Parameters: No Parameters
	 */
	public static void writeResults() {
		try {
			String reportIn = new String(Files.readAllBytes(Paths.get(templatePath)));
			for (int i = 0; i < details.size();i++) {
				if(details.get(i).getResult().contains("Fail")||details.get(i).getResult().contains("Done!"))
				{
					reportIn = reportIn.replaceFirst(resultPlaceholder,"<tr><td>" + Integer.toString(i+1) + "</td><td>" + details.get(i).getResult() + "</td><td>" + details.get(i).getResultText() + "</td><td><a href='file:///"+ScreenshotPathlist.get(intScreenshotCounter)+"'>Click here</a></td></tr>" + resultPlaceholder);
					intScreenshotCounter=intScreenshotCounter+1;
					
				}
				else
				{
				reportIn = reportIn.replaceFirst(resultPlaceholder,"<tr><td>" + Integer.toString(i+1) + "</td><td>" + details.get(i).getResult() + "</td><td>" + details.get(i).getResultText() + "</td><td></td></tr>" + resultPlaceholder);
				}
			}
			
			String currentDate = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss").format(new Date());
			String reportPath = "C:\\Selenium\\report_" + currentDate + ".html";
			Files.write(Paths.get(reportPath),reportIn.getBytes(),StandardOpenOption.CREATE);
			
		} catch (Exception e) {
			System.out.println("Error when writing report file:\n" + e.toString());
		}
	}
	/*
	 * Function name: captureScreenShot
	 * Purpose: This is used to create the final HTML report. This function is called towards the end of the Test
	 * Parameters: pass the Webdriver object
	 */
	public static void captureScreenShot(WebDriver ldriver){
		
		// Take screenshot and store as a file format             
		 File src=((TakesScreenshot)ldriver).getScreenshotAs(OutputType.FILE);           
		try {
		// now copy the  screenshot to desired location using copyFile method
			ScreenshotPath="C:/Selenium/"+System.currentTimeMillis()+".png";
			ScreenshotPathlist.add(ScreenshotPath);
		FileUtils.copyFile(src, new File(ScreenshotPath)); 
		} 
		catch (IOException e)
		 
		{
		  System.out.println(e.getMessage()) ;
		 }
		  }
	/*
	 * Function name: waitForLoad
	 * Purpose: When this function is used, it will wait for the page to be fully loaded
	 * Parameters: pass the Webdriver object
	 */
	public static void waitForLoad(WebDriver driver) {
	    new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
	            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
		 
		}
