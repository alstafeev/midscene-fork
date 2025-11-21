package com.midscene.core.model;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import java.util.List;

public class GeminiModel implements AIModel {

  private final ChatModel model;

  public GeminiModel(String apiKey, String modelName) {
    this.model = GoogleAiGeminiChatModel.builder()
        .apiKey(apiKey)
        .modelName(modelName)
        .build();
  }

  @Override
  public String chat(List<ChatMessage> messages) {
    return model.chat(messages).aiMessage().text();
  }
}
