package com.midscene.core.config;

public enum ModelProvider {
  OPENAI("gpt-4o"),
  GEMINI("gemini-3-pro-preview");

  private final String modelName;

  ModelProvider(String modelName) {
    this.modelName = modelName;
  }

  public String getModelName() {
    return modelName;
  }
}
