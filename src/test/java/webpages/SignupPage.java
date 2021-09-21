package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SignupPage {
  private WebDriver driver;
  private int waitTime = 2;

  //Page URL
  private static String PAGE_URL="http://localhost:3000/signup";

  //Locators

  @FindBy(xpath = "//*[@id=\"app\"]/section/div[1]/form/input[1]")
  private WebElement emailInput;

  @FindBy(xpath = "//*[@id=\"app\"]/section/div[1]/form/input[2]")
  private WebElement passwordInput;

  @FindBy(xpath = "//*[@id=\"app\"]/section/div[2]/div/input")
  private WebElement checkbox;

  @FindBy(className = "signup-button")
  private WebElement signupButton;

  @FindBy(id = "errorMessage")
  private WebElement errorMessage;

  @FindBy(id = "loginMessage")
  private WebElement loginMessage;

  //Constructor
  public SignupPage(WebDriver driver){
    this.driver=driver;
    driver.get(PAGE_URL);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

  public void signup(String email, String password){
    emailInput.click();
    emailInput.sendKeys(email);
    passwordInput.click();
    passwordInput.sendKeys(password);
    signupButton.click();
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

  public void returnToSignupPage() { driver.navigate().to(PAGE_URL); }

  public void checkCheckbox() {
    checkbox.click();
  }

  public boolean isCheckboxChecked() { return checkbox.getAttribute("value").equals("true"); }

}
