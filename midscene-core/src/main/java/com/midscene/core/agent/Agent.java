package com.midscene.core.agent;

import com.midscene.core.config.MidsceneConfig;
import com.midscene.core.config.ModelProvider;
import com.midscene.core.model.AIModel;
import com.midscene.core.model.GeminiModel;
import com.midscene.core.model.OpenAIModel;
import com.midscene.core.service.PageDriver;
import java.util.concurrent.CompletableFuture;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Agent {

  private final Orchestrator orchestrator;

  public Agent(PageDriver driver, AIModel aiModel) {
    this.orchestrator = new Orchestrator(driver, aiModel);
  }

  public static Agent create(MidsceneConfig config, PageDriver driver) {
    AIModel model = switch (config.getProvider()) {
      case ModelProvider.OPENAI -> new OpenAIModel(config.getApiKey(), config.getModelName());
      case ModelProvider.GEMINI -> new GeminiModel(config.getApiKey(), config.getModelName());
    };

    return new Agent(driver, model);
  }

  public void aiAction(String instruction) {
    orchestrator.execute(instruction);
  }

  public String aiQuery(String question) {
    return orchestrator.query(question);
  }

  public CompletableFuture<Void> aiActionAsync(String instruction) {
    return CompletableFuture.runAsync(() -> aiAction(instruction));
  }
}
