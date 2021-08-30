package webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
  private WebDriver driver;
  private int waitTime = 2;

  //Page URL
  private static String PAGE_URL="http://localhost:3000";

  //Locators

  @FindBy(className = "new-todo")
  private WebElement todoInput;

  @FindBy(className = "todo-list")
  private WebElement todoList;

  @FindBy(className = "todo")
  private WebElement todoItem;

  @FindBy(className = "destroy")
  private WebElement deleteButton;

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
    todoInput.clear();

  }

  public boolean isItemAdded(String item) {
    return todoItem.getText().toString().contains(item);
  }

  public void removeTodo() {
    Actions actions = new Actions(driver);
    actions.moveToElement(todoItem).build().perform();
    WebDriverWait wait = new WebDriverWait(driver, waitTime);
    wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
    deleteButton.click();
  }

  public boolean isItemRemoved(String item) {
    return !todoList.getText().toString().contains(item);
  }

}
