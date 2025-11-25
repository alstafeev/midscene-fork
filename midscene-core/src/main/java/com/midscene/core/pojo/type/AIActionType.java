package com.midscene.core.pojo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AIActionType {
  CLICK("click"),
  TYPE_TEXT("type_text"),
  SCROLL_DOWN("scroll_down"),
  SCROLL_UP("scroll_up"),
  HOVER("hover");

  private final String value;
}
