package org.seleniumhq.selenium.fluent.recording;

public class NoRetryStrategy implements RetryStrategy {
    public boolean shouldRetry(Throwable t, long started, int retriesSoFar) {
        return false;
    }
}
