package com.midscene.web.utils;

import java.util.Objects;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

@UtilityClass
public class ElementActions {

  public final Function<WebDriver, Boolean> isPageLoaded = (WebDriver driver) ->
      Objects.equals(((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete");

  public final Function<WebDriver, Boolean> isJQueryCompleted = (WebDriver driver) ->
      Objects.equals(((JavascriptExecutor) driver).executeScript("return jQuery.active"), "0");
}
