package com.midscene.web.driver;

import com.midscene.core.pojo.planning.Locate;
import com.midscene.core.pojo.type.BySelectorType;
import com.midscene.core.service.PageDriver;
import com.midscene.core.utils.WaitingUtils;
import com.midscene.web.utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SeleniumDriver implements PageDriver {

  private final WebDriver driver;

  public SeleniumDriver(WebDriver driver) {
    this.driver = driver;
  }

  @Override
  public String getUrl() {
    waitUntilPageLoaded();

    return driver.getCurrentUrl();
  }

  @Override
  public String getScreenshotBase64() {
    waitUntilPageLoaded();

    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
  }

  @Override
  public String getPageSource() {
    waitUntilPageLoaded();

    return driver.getPageSource();
  }

  @Override
  public void click(Locate locate) {
    waitUntilPageLoaded();

    new Actions(driver)
        .moveByOffset(locate.getX(), locate.getY())
        .click()
        .perform();
  }

  @Override
  public void click(BySelectorType selectorType, String elementSelector) {
    waitUntilPageLoaded();

    getWebElementBySelector(selectorType, elementSelector).click();
  }

  @Override
  public void type(Locate locate, String text) {
    waitUntilPageLoaded();

    new Actions(driver)
        .moveByOffset(locate.getX(), locate.getY())
        .click()
        .sendKeys(text)
        .perform();
  }

  @Override
  public void type(BySelectorType selectorType, String elementSelector, String text) {
    waitUntilPageLoaded();

    getWebElementBySelector(selectorType, elementSelector).sendKeys(text);
  }

  @Override
  public void scrollDown(Locate locate) {
    waitUntilPageLoaded();

    new Actions(driver)
        .scrollByAmount(locate.getX(), locate.getY())
        .perform();
  }

  @Override
  public void scrollDown(BySelectorType selectorType, String elementSelector) {
    waitUntilPageLoaded();

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
        getWebElementBySelector(selectorType, elementSelector));
  }

  @Override
  public void scrollUp(Locate locate) {
    waitUntilPageLoaded();

    new Actions(driver)
        .scrollByAmount(locate.getX(), locate.getY())
        .perform();
  }

  @Override
  public void scrollUp(BySelectorType selectorType, String elementSelector) {
    waitUntilPageLoaded();

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
        getWebElementBySelector(selectorType, elementSelector));
  }

  @Override
  public void hover(Locate locate) {
    waitUntilPageLoaded();
    new Actions(driver)
        .moveByOffset(locate.getX(), locate.getY())
        .perform();
  }

  @Override
  public void hover(BySelectorType selectorType, String elementSelector) {
    waitUntilPageLoaded();

    String javaScript = "var evObj = document.createEvent('MouseEvents');" +
        "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
        "arguments[0].dispatchEvent(evObj);";

    ((JavascriptExecutor) driver).executeScript(javaScript, getWebElementBySelector(selectorType, elementSelector));
  }

  public void waitUntilPageLoaded() {
    WaitingUtils.waitUntilWithoutException(2, 2000, () -> ElementActions.isPageLoaded.apply(driver),
        "Wait until page loaded");
  }

  public WebElement getWebElementBySelector(BySelectorType selectorType, String elementSelector) {
    return switch (selectorType) {
      case BY_XPATH -> driver.findElement(By.xpath(elementSelector));
      case BY_CSS -> driver.findElement(By.cssSelector(elementSelector));
    };
  }
}
