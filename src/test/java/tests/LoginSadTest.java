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

public class LoginSadTest {
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
  public void happyLogin() {
    //Create object of page class
    LoginPage login = new LoginPage(driver);

    //Login
    login.login("mail", "pass");
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    //Check if error message displays
    Assert.assertTrue(login.isErrorMessageVisible());

    //Check if we are still on the login page
    Assert.assertFalse((login.didPageChange()));

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