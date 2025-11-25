package com.midscene.core.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.midscene.core.pojo.planning.ActionsItem;
import com.midscene.core.pojo.planning.PlanningResponse;
import com.midscene.core.service.PageDriver;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrchestratorTest {

  @Mock
  private PageDriver driver;

  @Mock
  private Planner planner;

  @Mock
  private Executor executor;

  private Orchestrator orchestrator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    orchestrator = new Orchestrator(driver, planner, executor);
  }

  @Test
  void testExecuteSuccess() {
    // Mock driver responses
    when(driver.getScreenshotBase64()).thenReturn("base64image");
    when(driver.getPageSource()).thenReturn("<html></html>");

    // Mock Planner response
    PlanningResponse plan = new PlanningResponse();
    ActionsItem action = new ActionsItem();
    plan.setActions(Collections.singletonList(action));
    when(planner.plan(any(), any(), any(), any())).thenReturn(plan);

    // Execute
    orchestrator.execute("Click button");

    // Verify
    verify(executor, times(1)).execute(action);
  }

  @Test
  void testExecuteRetry() {
    // Mock driver responses
    when(driver.getScreenshotBase64()).thenReturn("base64image");
    when(driver.getPageSource()).thenReturn("<html></html>");

    // Mock Planner response: First attempt fails (throws exception), second
    // succeeds
    PlanningResponse plan = new PlanningResponse();
    ActionsItem action = new ActionsItem();
    plan.setActions(Collections.singletonList(action));

    when(planner.plan(any(), any(), any(), any()))
        .thenThrow(new RuntimeException("Planning failed"))
        .thenReturn(plan);

    // Execute
    orchestrator.execute("Click button");

    // Verify
    verify(planner, times(2)).plan(any(), any(), any(), any());
    verify(executor, times(1)).execute(action);
  }

  @Test
  void testExecuteFailure() {
    // Mock driver responses
    when(driver.getScreenshotBase64()).thenReturn("base64image");
    when(driver.getPageSource()).thenReturn("<html></html>");

    // Mock Planner response: Always fails
    when(planner.plan(any(), any(), any(), any()))
        .thenThrow(new RuntimeException("Planning failed"));

    // Execute and expect exception
    RuntimeException exception = assertThrows(RuntimeException.class, () -> orchestrator.execute("Click button"));

    assertEquals("Failed to complete instruction: Click button", exception.getMessage());
    verify(planner, times(3)).plan(any(), any(), any(), any());
    verify(executor, never()).execute(any());
  }

  @Test
  void testQuery() {
    // Mock driver
    when(driver.getScreenshotBase64()).thenReturn("base64image");

    // Mock Planner
    when(planner.query(any(), any())).thenReturn("Answer");

    // Execute
    String result = orchestrator.query("What is this?");

    // Verify
    assertEquals("Answer", result);
    verify(planner).query("What is this?", "base64image");
  }
}
