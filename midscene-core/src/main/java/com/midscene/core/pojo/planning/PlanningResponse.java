package com.midscene.core.pojo.planning;

import java.util.List;
import lombok.Data;

@Data
public class PlanningResponse {

  private List<ActionsItem> actions;
}