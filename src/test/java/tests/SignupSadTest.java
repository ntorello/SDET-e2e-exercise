package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import webpages.SignupPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;

public class SignupSadTest {
  WebDriver driver;

  @Before
  public void setup(){
    RestAssured.baseURI = "http://localhost:3000";
    //Set up ChromeDriver
    System.setProperty("webdriver.chrome.driver","C:/Users/ntorello/Code/chromedriver_win32/chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

  }

  @Test
  public void sadSignup() {
    //Create object of page class
    SignupPage signup = new SignupPage(driver);

    //Signup
    signup.signup("mymail", "mypass");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if login message displays
    Assert.assertTrue(signup.isLoginMessageVisible());

    //Check if we are on the home page
    Assert.assertTrue((signup.didPageChange()));

    //Return to signup page
    signup.returnToSignupPage();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Bad Signup
    signup.signup("mymail", "mypass");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if error message displays
    Assert.assertTrue(signup.isErrorMessageVisible());

    //Check if we are still on the signup page
    Assert.assertFalse((signup.didPageChange()));

  }

  @After
  public void close(){
    Response response = given()
        .header("Content-type", "application/json")
        .and()
        .body("{}")
        .when()
        .post("/reset")
        .then()
        .extract().response();
    driver.close();
  }
}