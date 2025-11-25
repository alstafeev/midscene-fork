package com.midscene.visualizer;

import com.midscene.core.context.Context;
import com.midscene.core.context.ContextEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class HtmlGenerator {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      .withZone(ZoneId.systemDefault());

  public static String generate(Context context) {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>");
    html.append("<html>");
    html.append("<head>");
    html.append("<title>Midscene Execution Report</title>");
    html.append("<style>");
    html.append("body { font-family: sans-serif; margin: 20px; }");
    html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
    html.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
    html.append("th { background-color: #f2f2f2; }");
    html.append(".screenshot { max-width: 200px; max-height: 200px; cursor: pointer; }");
    html.append("</style>");
    html.append("</head>");
    html.append("<body>");
    html.append("<h1>Midscene Execution Report</h1>");

    html.append("<table>");
    html.append(
        "<thead><tr><th>Timestamp</th><th>Type</th><th>Description</th><th>Data</th><th>Screenshot</th></tr></thead>");
    html.append("<tbody>");

    for (ContextEvent event : context.getEvents()) {
      html.append("<tr>");
      html.append("<td>").append(FORMATTER.format(Instant.ofEpochMilli(event.getTimestamp()))).append("</td>");
      html.append("<td>").append(event.getType()).append("</td>");
      html.append("<td>").append(event.getDescription()).append("</td>");
      html.append("<td><pre>").append(escapeHtml(event.getData())).append("</pre></td>");

      if (event.getScreenshotBase64() != null) {
        html.append("<td><img class='screenshot' src='data:image/png;base64,")
            .append(event.getScreenshotBase64()).append("' /></td>");
      } else {
        html.append("<td></td>");
      }

      html.append("</tr>");
    }

    html.append("</tbody>");
    html.append("</table>");
    html.append("</body>");
    html.append("</html>");

    return html.toString();
  }

  private static String escapeHtml(String text) {
    if (text == null) {
      return "";
    }
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;");
  }
}
