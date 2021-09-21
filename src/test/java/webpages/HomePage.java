package webpages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
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

  @FindBy(className = "toggle")
  private WebElement checkbox;

  @FindBy(css = ".todo-list li.completed label")
  private WebElement checkedTodoItem;

  @FindAll({
      @FindBy(className = "todo")
  })
  private List<WebElement> todoItems;

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

  public void checkTodo() {
    checkbox.click();
  }

  public boolean isItemChecked() {
    String color = checkedTodoItem.getCssValue("color");
    System.out.println(color);
    String textDecoration = checkedTodoItem.getCssValue("text-decoration");
    System.out.println(textDecoration);
    if ((color.equals("rgba(81, 81, 81, 1)") || color.equals("rgba(77, 77, 77, 1)") || color.equals("rgba(87, 87, 87, 1)"))
        && textDecoration.contains("line-through")) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isItemClickable() {
    boolean noError = true;
    Actions actions = new Actions(driver);
    actions.doubleClick(todoItem).build().perform();
    WebDriverWait wait = new WebDriverWait(driver, waitTime);
    try {
      todoItem.findElement(By.className("todo-edit"));
    } catch (Exception e) {
      noError = false;
    }
    return noError;
  }

  public Integer getListSize() {
    Dimension size = todoList.getSize();
    return size.height;
  }

  public boolean isOrderRetained(String item1, String item2) {
    return todoItems.get(0).getText().toString().contains(item1) &&
        todoItems.get(1).getText().toString().contains(item2);
  }

}
