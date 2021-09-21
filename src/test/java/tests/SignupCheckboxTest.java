package tests;

import webpages.SignupPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class SignupCheckboxTest {
  WebDriver driver;

  @Before
  public void setup(){
    //Set up ChromeDriver
    System.setProperty("webdriver.chrome.driver","C:/Users/ntorello/Code/chromedriver_win32/chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void checkTodoItem() {
    //Create object of page class
    SignupPage signup = new SignupPage(driver);

    //Check item
    signup.checkCheckbox();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if item is checked
    Assert.assertTrue(signup.isCheckboxChecked());

  }

  @After
  public void close(){
    driver.close();
  }
}