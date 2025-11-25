package com.midscene.visualizer;

import com.midscene.core.context.Context;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class ManualVisualizerTest {

  @Test
  public void testGenerateReport() {
    Context context = new Context();
    context.logInstruction("Open Google");
    context.logAction("Navigating to https://google.com");
    context.logScreenshot(
        "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==");
    context.logInstruction("Search for 'Midscene'");
    context.logPlan("{\"actions\": [{\"type\": \"CLICK\", \"description\": \"Click search box\"}]}");
    context.logAction("Clicking search box");

    Path outputPath = Paths.get("target/report.html");
    Visualizer.generateReport(context, outputPath);
    System.out.println("Report generated at: " + outputPath.toAbsolutePath());
  }
}
