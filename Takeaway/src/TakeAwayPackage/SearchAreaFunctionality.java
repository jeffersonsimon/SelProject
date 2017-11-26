package TakeAwayPackage;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

public class SearchAreaFunctionality {
	
	WebDriver driver;

	@DataProvider
	 public Object[][] PositiveScenario() {
	    return new Object[][] {
	      new Object[] {"SearchTextPositive","Wolfsburg Hbf"}
	      
	    }; 
		  
	  }
	@DataProvider
	 public Object[][] NegativeScenario() {
	    return new Object[][] {
	      new Object[] {"SearchTextNegative","38440"}
	    }; 
		  
	  }
	
	//This Test checks if the SearchText box exists and if it is enabled	  
@Test(priority = 1, enabled = true)
public void CheckExistenceofSearchTextbox() 
{
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
}
	  
//The Search TextBox is given correct inputs(e.g. street name) and checked if the restaurants are displayed	  
  @Test(priority = 2, enabled = true, dataProvider = "PositiveScenario") 
  public void SearchFunctionalityPositiveScenario(String s,String SearchTextPositive ) throws InterruptedException {
	  

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
	  driver.findElement(Locators.SearchString).sendKeys(SearchTextPositive);
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
//The Search TextBox is given incorrect inputs(e.g. special characters) and checked if the expected error message is displayed
  @Test(priority = 3, enabled = true, dataProvider = "NegativeScenario") 
  public void SearchFunctionalityNegativeScenario(String s,String SearchTextNegative) throws InterruptedException
  {
	  driver.findElement(Locators.Logo).click();
	  driver.findElement(Locators.SearchString).click();
	  Thread.sleep(3000);
	  driver.findElement(Locators.SearchString).clear();
	  driver.findElement(Locators.SearchString).sendKeys(SearchTextNegative);
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
	  // The ArrayLists related to HTML Reporting are Initialized
	  Reporter.initialize(); 
	  //The Path for webdriver gecko driver is mentioned for using Firefox Driver
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
	  //The final HTML report is created 
	  Reporter.writeResults();
  }

}
