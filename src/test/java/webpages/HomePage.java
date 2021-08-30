package webpages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
  private WebDriver driver;

  //Page URL
  private static String PAGE_URL="http://localhost:3000";

  //Locators

  @FindBy(how = How.CLASS_NAME, using = "new-todo")
  private WebElement todoInput;

  @FindBy(xpath = "/html/body/section/section/div/section")
  private WebElement todoList;

  //Constructor
  public HomePage(WebDriver driver){
    this.driver=driver;
    driver.get(PAGE_URL);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

  public void addTodo(String item){

    todoInput.click();
    todoInput.sendKeys(item);
    todoInput.sendKeys(Keys.RETURN);

  }

  public boolean isItemAdded(String item) {
    return todoList.getText().toString().contains(item);
  }
}
