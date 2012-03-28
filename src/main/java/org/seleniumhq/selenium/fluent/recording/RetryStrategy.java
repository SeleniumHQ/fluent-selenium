package org.seleniumhq.selenium.fluent.recording;

public interface RetryStrategy {

    boolean shouldRetry(Throwable t, long started, int retriesSoFar);

}
