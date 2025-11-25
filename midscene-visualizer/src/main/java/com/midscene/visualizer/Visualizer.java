package com.midscene.visualizer;

import com.midscene.core.context.Context;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Visualizer {

  public static void generateReport(Context context, Path outputPath) {
    log.info("Generating report to {}", outputPath);
    String html = HtmlGenerator.generate(context);
    try {
      Files.writeString(outputPath, html);
      log.info("Report generated successfully.");
    } catch (IOException e) {
      log.error("Failed to write report to {}", outputPath, e);
      throw new RuntimeException("Failed to generate report", e);
    }
  }
}
