package tests;

import webpages.HomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class CheckItemTest {
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
    //Create object of HomePage Class
    HomePage home = new HomePage(driver);

    //Add item
    home.addTodo("Look at new todo item");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if item was added
    boolean isItemAdded = home.isItemAdded("Look at new todo item");

    //Check item
    home.checkTodo();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if item is checked
    boolean isItemChecked = home.isItemChecked();

    //Cleanup
    home.removeTodo();

    //Assert booleans
    Assert.assertTrue(isItemAdded);
    Assert.assertTrue(isItemChecked);

  }

  @After
  public void close(){
    driver.close();
  }
}