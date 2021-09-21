package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {
  private WebDriver driver;
  private int waitTime = 2;

  //Page URL
  private static String PAGE_URL="http://localhost:3000/login";

  //Locators

  @FindBy(xpath = "//*[@id=\"app\"]/section/div/form/input[1]")
  private WebElement emailInput;

  @FindBy(xpath = "//*[@id=\"app\"]/section/div/form/input[2]")
  private WebElement passwordInput;

  @FindBy(className = "login-button")
  private WebElement loginButton;

  @FindBy(id = "errorMessage")
  private WebElement errorMessage;

  @FindBy(id = "loginMessage")
  private WebElement loginMessage;

  //Constructor
  public LoginPage(WebDriver driver){
    this.driver=driver;
    driver.get(PAGE_URL);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

  public void login(String email, String password){
    emailInput.click();
    emailInput.sendKeys(email);
    passwordInput.click();
    passwordInput.sendKeys(password);
    loginButton.click();
  }

  public boolean isErrorMessageVisible() {
    return !errorMessage.getAttribute("style").equals("display: none;");
  }

  public boolean isLoginMessageVisible() {
    return !loginMessage.getAttribute("style").equals("display: none;");
  }

  public boolean didPageChange() {
    return driver.getCurrentUrl().equals("http://localhost:3000/");
  }

}
