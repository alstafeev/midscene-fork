package com.midscene.core.model;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.util.List;

public class OpenAIModel implements AIModel {

  private final ChatModel model;

  public OpenAIModel(String apiKey, String modelName) {
    this.model = OpenAiChatModel.builder()
        .apiKey(apiKey)
        .modelName(modelName)
        .build();
  }

  @Override
  public String chat(List<ChatMessage> messages) {
    return model.chat(messages).aiMessage().text();
  }
}
