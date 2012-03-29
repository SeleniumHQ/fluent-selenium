package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.Period;

public class ShouldTimeOut implements RetryStrategy {

    private Period after;

    public ShouldTimeOut(Period after) {
        this.after = after;
    }

    public boolean shouldRetry(Throwable t, long started, int retriesSoFar) {
        return (after.getEndMillis(started) - System.currentTimeMillis()) > 0L;
    }
}
