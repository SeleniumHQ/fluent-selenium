package org.seleniumhq.selenium.fluent.recording;

import org.openqa.selenium.StaleElementReferenceException;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;

public class KeepGoingIfStaleStrategy implements RetryStrategy {

    public boolean shouldRetry(Throwable t, long started, int retriesSoFar) {
        return t != null && (t instanceof FluentExecutionStopped.BecauseOfStaleElement
                || t instanceof StaleElementReferenceException) ;
    }
}
