# Midscene Java

**Midscene Java** is an AI-powered automation SDK that allows you to control web browsers using natural language instructions. It integrates with standard Selenium WebDriver (and Playwright) to serve as an intelligent agent layer on top of your existing test automation framework.

## Features

*   **Natural Language Control**: "Search for 'Headphones' and click the first result."
*   **Multimodal Understanding**: Uses screenshots to understand the page context.
*   **Smart Planning**: Automatically plans, executes, and retries actions.
*   **Framework Agnostic**: Works alongside your existing Selenium or Playwright tests.
*   **Flexible Configuration**: Supports OpenAI (GPT-4o) and Google Gemini models.

## Installation

### 1. Build Locally
Currently, Midscene Java is available as a source build. Clone this repository and install it to your local Maven repository:

```bash
git clone https://github.com/midscene/midscene-java.git
cd midscene-java
mvn clean install
```

### 2. Add Dependency
Add the `midscene-web` dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.midscene</groupId>
    <artifactId>midscene-web</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Usage Example

Here is how to use Midscene in a standard Selenium test.

### Prerequisites
*   Java 17+
*   Maven
*   `OPENAI_API_KEY` or `GEMINI_API_KEY` environment variable set.

### Code Snippet

```java
import com.midscene.core.agent.Agent;
import com.midscene.core.config.MidsceneConfig;
import com.midscene.core.config.ModelProvider;
import com.midscene.web.driver.SeleniumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MidsceneExample {
    public static void main(String[] args) {
        // 1. Setup Selenium WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Optional
        WebDriver webDriver = new ChromeDriver(options);
        
        try {
            // 2. Navigate to a page
            webDriver.get("https://www.ebay.com");

            // 3. Configure Midscene
            MidsceneConfig config = MidsceneConfig.builder()
                .provider(ModelProvider.OPENAI)
                .apiKey(System.getenv("OPENAI_API_KEY")) // Or hardcode your key
                .modelName("gpt-4o")
                .build();

            // 4. Create the Agent
            // Wrap your WebDriver in a Midscene SeleniumDriver
            SeleniumDriver driverAdapter = new SeleniumDriver(webDriver);
            Agent agent = Agent.create(config, driverAdapter);

            // 5. Interact using Natural Language
            // Perform actions
            agent.aiAction("Search for 'Gaming Keyboard'");
            agent.aiAction("Click on the first search result");

            // Query information
            String price = agent.aiQuery("What is the price of the item?");
            System.out.println("Item Price: " + price);

        } finally {
            webDriver.quit();
        }
    }
}
```

## Configuration

You can configure the agent using `MidsceneConfig`:

```java
MidsceneConfig config = MidsceneConfig.builder()
    .provider(ModelProvider.GEMINI)      // Choose OPENAI or GEMINI
    .apiKey("your-api-key")              // Set API Key
    .modelName("gemini-1.5-pro")         // Specific model version
    .timeoutMs(60000)                    // Timeout in milliseconds
    .build();
```

## Architecture

*   **`midscene-core`**: The brain of the agent. Contains the `Planner`, `Executor`, and `Orchestrator`. It is pure Java and has no dependency on Selenium/Playwright.
*   **`midscene-web`**: Adapters for browser automation tools. Currently supports `SeleniumDriver` and `PlaywrightDriver`.

## Requirements

*   JDK 21 (Recommended) or JDK 17+
*   Maven 3.8+
