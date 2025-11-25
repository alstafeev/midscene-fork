package com.midscene.core.pojo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BySelectorType {
  BY_XPATH("by_xpath"),
  BY_CSS("by_css");

  private final String value;
}
