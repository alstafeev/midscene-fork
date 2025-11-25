package com.midscene.core.agent;

import com.midscene.core.pojo.planning.ActionsItem;
import com.midscene.core.service.PageDriver;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Executor {

  private final PageDriver driver;

  public Executor(PageDriver driver) {
    this.driver = driver;
  }

  public void execute(ActionsItem action) {
    log.info("Executing action: {}", action.getType());

    switch (action.getType()) {
      case CLICK -> {
        if (Objects.nonNull(action.getSelectorType()) && Objects.nonNull(action.getElementSelector())) {
          driver.click(action.getSelectorType(), action.getElementSelector());
        }
        if (Objects.nonNull(action.getLocate())) {
          driver.click(action.getLocate());
        }
      }
      case TYPE_TEXT -> {
        if (Objects.nonNull(action.getSelectorType()) && Objects.nonNull(action.getElementSelector())) {
          driver.type(action.getSelectorType(), action.getElementSelector(), action.getText());
        }
        if (Objects.nonNull(action.getLocate()) && Objects.nonNull(action.getText())) {
          driver.type(action.getLocate(), action.getText());
        }
      }
      case SCROLL_DOWN -> {
        if (Objects.nonNull(action.getSelectorType()) && Objects.nonNull(action.getElementSelector())) {
          driver.scrollDown(action.getSelectorType(), action.getElementSelector());
        }
        if (Objects.nonNull(action.getLocate())) {
          driver.scrollDown(action.getLocate());
        }
      }
      case SCROLL_UP -> {
        if (Objects.nonNull(action.getSelectorType()) && Objects.nonNull(action.getElementSelector())) {
          driver.scrollUp(action.getSelectorType(), action.getElementSelector());
        }
        if (Objects.nonNull(action.getLocate())) {
          driver.scrollUp(action.getLocate());
        }
      }
      case HOVER -> {
        if (Objects.nonNull(action.getSelectorType()) && Objects.nonNull(action.getElementSelector())) {
          driver.hover(action.getSelectorType(), action.getElementSelector());
        }
        if (Objects.nonNull(action.getLocate())) {
          driver.hover(action.getLocate());
        }
      }
    }
  }
}
