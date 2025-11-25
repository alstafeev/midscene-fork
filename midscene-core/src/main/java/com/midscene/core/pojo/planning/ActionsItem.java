package com.midscene.core.pojo.planning;

import com.midscene.core.pojo.type.AIActionType;
import com.midscene.core.pojo.type.BySelectorType;
import lombok.Data;

@Data
public class ActionsItem {

  private Locate locate;
  private String text;
  private AIActionType type;
  private String elementSelector;
  private BySelectorType selectorType;
}