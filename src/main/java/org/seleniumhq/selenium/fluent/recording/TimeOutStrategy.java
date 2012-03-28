package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.Period;

public class TimeOutStrategy implements RetryStrategy {

    private Period timeout;

    public TimeOutStrategy(Period timeout) {
        this.timeout = timeout;
    }

    public boolean shouldRetry(Throwable t, long started, int retriesSoFar) {
        return (timeout.getEndMillis(started) - System.currentTimeMillis()) > 0L;
    }
}
