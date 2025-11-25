package com.midscene.core.service;

import com.midscene.core.pojo.planning.Locate;
import com.midscene.core.pojo.type.BySelectorType;

public interface PageDriver {

  String getUrl();

  String getScreenshotBase64();

  String getPageSource();

  void click(Locate locate);

  void click(BySelectorType selectorType, String elementSelector);

  void type(Locate locate, String text);

  void type(BySelectorType selectorType, String elementSelector, String text);

  void scrollDown(Locate locate);

  void scrollDown(BySelectorType selectorType, String elementSelector);

  void scrollUp(Locate locate);

  void scrollUp(BySelectorType selectorType, String elementSelector);

  void hover(Locate locate);

  void hover(BySelectorType selectorType, String elementSelector);
}
