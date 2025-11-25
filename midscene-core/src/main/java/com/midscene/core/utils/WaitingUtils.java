package com.midscene.core.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.awaitility.Awaitility;

@UtilityClass
@Log4j2
public class WaitingUtils {

  public void waitUntil(long maxTimeout, long pollInterval, Callable<Boolean> action, String alias) {
    Awaitility
        .await(alias)
        .atMost(maxTimeout, TimeUnit.SECONDS)
        .ignoreExceptions()
        .pollInSameThread()
        .pollInterval(pollInterval, TimeUnit.MILLISECONDS)
        .until(action);
  }

  public <T> T waitUntilPredicate(long maxTimeout, long pollInterval, Callable<T> action,
      Predicate<? super T> predicate, String alias) {
    return Awaitility
        .await(alias)
        .atMost(maxTimeout, TimeUnit.SECONDS)
        .ignoreExceptions()
        .pollInSameThread()
        .pollInterval(pollInterval, TimeUnit.MILLISECONDS)
        .until(action, predicate);
  }

  public static void waitUntilWithoutException(long maxTimeout, long pollInterval, Callable<Boolean> action,
      String alias) {
    try {
      waitUntil(maxTimeout, pollInterval, action, alias);
    } catch (Exception | Error exception) {
      log.warn("Not completed after waiting {}", alias);
    }
  }
}
