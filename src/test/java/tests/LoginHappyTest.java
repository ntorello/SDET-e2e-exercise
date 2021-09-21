package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import webpages.SignupPage;
import webpages.LoginPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;

public class LoginHappyTest {
  WebDriver driver1;
  WebDriver driver2;

  @Before
  public void setup(){
    RestAssured.baseURI = "http://localhost:3000";
    //Set up ChromeDriver
    System.setProperty("webdriver.chrome.driver","C:/Users/ntorello/Code/chromedriver_win32/chromedriver.exe");
    driver1 = new ChromeDriver();
    driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver2 = new ChromeDriver();
    driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

  }

  @Test
  public void happyLogin() {
    //Create objects of page classes
    SignupPage signup = new SignupPage(driver1);
    LoginPage login = new LoginPage(driver2);

    //Signup
    signup.signup("mymail", "mypass");
    driver1.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if login message displays
    Assert.assertTrue(signup.isLoginMessageVisible());

    //Check if we are on the home page
    Assert.assertTrue((signup.didPageChange()));

    //Login
    login.login("mymail", "mypass");
    driver2.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if login message displays
    Assert.assertTrue(login.isLoginMessageVisible());

    //Check if we are on the home page
    Assert.assertTrue((login.didPageChange()));

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
    driver1.close();
    driver2.close();
  }
}