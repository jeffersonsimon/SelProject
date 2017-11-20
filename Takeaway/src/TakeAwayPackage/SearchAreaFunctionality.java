package TakeAwayPackage;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class SearchAreaFunctionality {
  @Test
  public void f() {
	  
	  System.out.println("Test");
	  
  }
  @BeforeTest
  public void beforeTest() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver_win32\\chromedriver.exe");
	  WebDriver driver=new ChromeDriver();
	  driver.get("https://www.lieferando.de/");
	  Assert.assertTrue(true, "The website is opened");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("AfterTest");
  }

}
