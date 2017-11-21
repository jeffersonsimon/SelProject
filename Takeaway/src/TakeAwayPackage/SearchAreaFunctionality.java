package TakeAwayPackage;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


import org.testng.annotations.BeforeTest;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class SearchAreaFunctionality {
	
	WebDriver driver;
	ExtentReports report;
	  ExtentTest test;
  @Test(priority = 1, enabled = true)
  public void SearchFunctionalityPositiveScenario() throws InterruptedException {
	  
	  if(driver.findElements(Locators.SearchString).size()!=0)
	  {
		  Reporter.ReportStatus("Pass","The Search Textbox is present");
	  }
	 else
	 {
		 Reporter.ReportWithScreenshot("Fail","The Search Textbox is not present",driver);
	 }
		
	  if(driver.findElement(Locators.SearchString).isEnabled())
	  {
		  Reporter.ReportStatus("Pass","The Search Textbox is Enabled");
	  }
	 else
	 {
		 Reporter.ReportWithScreenshot("Fail","The Search Textbox is not Enabled",driver);
	 }
	  driver.findElement(Locators.SearchString).click();
	  
	  if(driver.findElement(Locators.AutoComplete).isDisplayed())
	  {
		  Reporter.ReportStatus("Pass","The Auto Complete drop down is displayed");
	  }
	 else
	 {
		 Reporter.ReportWithScreenshot("Fail","The Auto Complete drop down is displayed",driver);
	 }
	  Thread.sleep(3000);
	  driver.findElement(Locators.SearchString).sendKeys("Wolfsburg Hbf");
	  Thread.sleep(3000);
	  
	  driver.findElement(Locators.Autosuggestions).click();
	  Reporter.waitForLoad(driver);
	  Thread.sleep(3000);
	  try
	  {
	  if(driver.findElement(Locators.ResturantGrid).isDisplayed())
	  {
		  
		  Reporter.ReportStatus("Pass","The list of resturants is displayed for the address entered");
	  }
	  else
	  {
		  Reporter.ReportWithScreenshot("Fail","The list of resturants is not displayed",driver);
	  }
	  }
	  catch(Exception e)
	  {
		  Reporter.ReportWithScreenshot("Fail","The list of resturants is not displayed",driver);
	  }
	  
  }
  
  @Test(priority = 2, enabled = true)
  public void SearchFunctionalityNegativeScenario() throws InterruptedException
  {
	  driver.findElement(Locators.Logo).click();
	  driver.findElement(Locators.SearchString).click();
	  Thread.sleep(3000);
	  driver.findElement(Locators.SearchString).clear();
	  driver.findElement(Locators.SearchString).sendKeys("38440");
	  Thread.sleep(3000);
	  driver.findElement(Locators.SearchString).click();
	  try
	  {
	  if(driver.findElement(By.xpath("//span[contains(text(), 'Bitte gib Deine Straﬂe und Hausnummer ein')]")).isDisplayed())
	  {
		  Reporter.ReportStatus("Pass","Expected Error message 'Bitte gib Deine Straﬂe und Hausnummer ein' is displayed");
	  }
	  else
	  {
		  Reporter.ReportWithScreenshot("Fail","Expected Error message 'Bitte gib Deine Straﬂe und Hausnummer ein' is not displayed",driver);
	  }
	  }
	  catch(Exception e)
	  {
		  Reporter.ReportWithScreenshot("Fail","Error message is not displayed",driver);
	  }
	  
  }
  
  
  @BeforeTest
  public void beforeTest() {
	  Reporter.initialize();
	  //System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver_win32\\chromedriver.exe");
	  //driver=new ChromeDriver();
	  System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver-v0.19.1-win64\\geckodriver.exe");
	  driver=new FirefoxDriver();
	  driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
	  driver.get("https://www.lieferando.de/");
	  driver.manage().window().maximize();
	  Reporter.ReportStatus("Done","https://www.lieferando.de/ link is opened");
  }

  @AfterTest
  public void afterTest() {
	  //driver.quit();
	  Reporter.writeResults();
  }

}
