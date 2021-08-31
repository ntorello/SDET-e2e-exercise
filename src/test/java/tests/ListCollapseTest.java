package tests;

import webpages.HomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class ListCollapseTest {
  WebDriver driver;

  @Before
  public void setup(){
    //Set up ChromeDriver
    System.setProperty("webdriver.chrome.driver","C:/Users/ntorello/Code/chromedriver_win32/chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void collapseList() {
    //Create object of HomePage Class
    HomePage home = new HomePage(driver);

    //Add item 1
    home.addTodo("ONE");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if item was added
    Assert.assertTrue(home.isItemAdded("ONE"));

    //Add item 2
    home.addTodo("TWO");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Add item 3
    home.addTodo("THREE");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check list size
    Integer size = home.getListSize();

    //Remove item 1
    home.removeTodo();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check that list size is smaller
    Assert.assertTrue(size > home.getListSize());

    //Check that order is preserved
    Assert.assertTrue(home.isOrderRetained("TWO","THREE"));


    //Cleanup
    home.removeTodo();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    home.removeTodo();

  }

  @After
  public void close(){
    driver.close();
  }
}