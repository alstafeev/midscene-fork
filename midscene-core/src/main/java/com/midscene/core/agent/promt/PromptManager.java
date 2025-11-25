package com.midscene.core.agent.promt;

public class PromptManager {

  public static String constructQueryPrompt(String question) {
    return String.format(
        "You are an AI agent. User question: %s. " +
            "Answer the question briefly based on the screenshot provided.",
        question);
  }

  public static String constructPlanningPrompt(String instruction) {
    return String.format(
        "You are an AI agent controlling a web browser. You have page screenshot and page source attached to this message. "
            +
            "You will need to create a plan with list of actions to complete user instructions in web browser." +
            "Instructions will be executed by webdriver - you need to prepare clear and understandable instructions that can only be interpreted in one way. "
            +
            "Always try to find XPATH or CSS selector or element with which you need to interact. If this is not possible, specify the coordinates for interacting with the page. "
            +
            "User instruction: %s. " +
            "Return a JSON object with a list of actions. " +
            "Available actions: CLICK, TYPE_TEXT, SCROLL_DOWN, SCROLL_UP, HOVER - should be one of those strictly. " +
            "elementSelector - CSS or XPATH selector. " +
            "selectorType - Should be `BY_XPATH` or `BY_CSS` strictly. " +
            "CLICK(locate(x, y), elementSelector, selectorType), " +
            "TYPE_TEXT(locate(x, y), elementSelector, selectorType, text), " +
            "SCROLL_DOWN(locate(x, y), elementSelector, selectorType), " +
            "SCROLL_UP(locate(x, y), elementSelector, selectorType), " +
            "HOVER(locate(x, y), elementSelector, selectorType). " +
            "Example Format: {\"actions\":[{\"type\":\"CLICK\",\"locate\":{\"x\":110,\"y\":220},\"elementSelector\":\"//button\",\"selectorType\":\"BY_XPATH\"},{\"type\":\"TYPE_TEXT\",\"locate\":{\"x\":110,\"y\":220},\"elementSelector\":\"//button\",\"selectorType\":\"BY_XPATH\",\"text\":\"text to type\"},{\"type\":\"SCROLL_DOWN\",\"locate\":{\"x\":110,\"y\":220},\"elementSelector\":\"//button\",\"selectorType\":\"BY_XPATH\"},{\"type\":\"SCROLL_UP\",\"locate\":{\"x\":110,\"y\":220},\"elementSelector\":\"//button\",\"selectorType\":\"BY_XPATH\"},{\"type\":\"HOVER\",\"locate\":{\"x\":110,\"y\":220},\"elementSelector\":\"//button\",\"selectorType\":\"BY_XPATH\"}]}",
        instruction);
  }

  public static String constructRetryPrompt(String instruction) {
    return String.format("Previous attempt failed. Please try again with this new screenshot and new page source. " +
        "User instruction: %s. ", instruction);
  }
}
