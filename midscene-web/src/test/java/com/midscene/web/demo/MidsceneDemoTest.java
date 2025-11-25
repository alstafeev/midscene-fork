package com.midscene.web.demo;

import com.midscene.core.agent.Agent;
import com.midscene.core.config.MidsceneConfig;
import com.midscene.core.config.ModelProvider;
import com.midscene.web.driver.SeleniumDriver;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Log4j2
public class MidsceneDemoTest {

  private WebDriver driver;

  @BeforeEach
  @SneakyThrows
  void initDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");

    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
  }

  @Test
  public void localGeminiTest() {
    driver.get("https://midscenejs.com/");

    MidsceneConfig config = MidsceneConfig.builder()
        .provider(ModelProvider.GEMINI)
        .apiKey("API_KEY")
        .modelName("gemini-2.5-pro")
        .build();

    SeleniumDriver driverAdapter = new SeleniumDriver(driver);
    Agent agent = Agent.create(config, driverAdapter);

    agent.aiAction("Search for 'MCP server' button in the left sidebar of this site and click it.");
  }

  @AfterEach
  void shutDownDriver() {
    driver.quit();
  }
}
