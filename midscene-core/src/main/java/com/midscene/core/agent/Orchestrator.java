package com.midscene.core.agent;

import com.midscene.core.model.AIModel;
import com.midscene.core.pojo.planning.ActionsItem;
import com.midscene.core.pojo.planning.PlanningResponse;
import com.midscene.core.service.PageDriver;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Orchestrator {

  private final PageDriver driver;
  private final Planner planner;
  private final Executor executor;

  public Orchestrator(PageDriver driver, AIModel aiModel) {
    this.driver = driver;
    this.planner = new Planner(aiModel);
    this.executor = new Executor(driver);
  }

  public String query(String question) {
    log.info("Querying: {}", question);
    String screenshotBase64 = driver.getScreenshotBase64();
    return planner.query(question, screenshotBase64);
  }

  public void execute(String instruction) {
    log.info("Executing instruction: {}", instruction);

    List<ChatMessage> history = new ArrayList<>();
    int maxRetries = 3;
    boolean finished = false;

    for (int i = 0; i < maxRetries && !finished; i++) {
      try {
        String screenshotBase64 = driver.getScreenshotBase64();
        String pageSource = driver.getPageSource();

        PlanningResponse plan = planner.plan(instruction, screenshotBase64, pageSource, history);

        if (Objects.nonNull(plan.getActions()) && !plan.getActions().isEmpty()) {
          for (ActionsItem action : plan.getActions()) {
            executor.execute(action);
          }
          finished = true;
        } else {
          throw new RuntimeException("No actions returned by AI.");
        }

      } catch (Exception e) {
        log.error("Failed to execute plan (Attempt {}) {}", i + 1, e.getMessage());
        history.add(UserMessage.from("Error executing plan: " + e.getMessage()));
      }
    }

    if (!finished) {
      log.error("Failed to complete instruction after {} attempts", maxRetries);
      throw new RuntimeException("Failed to complete instruction: " + instruction);
    }
  }
}
