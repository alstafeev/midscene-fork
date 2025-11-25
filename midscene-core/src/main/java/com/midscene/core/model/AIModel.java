package com.midscene.core.model;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import java.util.List;

public interface AIModel {

  ChatResponse chat(List<ChatMessage> messages);
}
