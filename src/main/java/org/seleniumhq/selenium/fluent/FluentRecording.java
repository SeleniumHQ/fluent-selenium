package org.seleniumhq.selenium.fluent;

import org.seleniumhq.selenium.fluent.recording.OnFluentSomething;
import org.seleniumhq.selenium.fluent.recording.RetryStrategy;

import java.util.List;

public class FluentRecording {

    private final List<OnFluentSomething> onFluentSomethings;
    private final RetryStrategy[] retryStrategies;

    public FluentRecording(List<OnFluentSomething> onFluentSomethings, RetryStrategy... retryStrategies) {
        this.onFluentSomethings = onFluentSomethings;
        this.retryStrategies = retryStrategies;
    }

    public Object playback(FluentWebDriver driver) {
        long start = System.currentTimeMillis();
        int retries = 0;
        RuntimeException lastRE = null;
        boolean goAgain = true;
        while (goAgain) {
            goAgain = false;
            try {
                Object last = driver;
                for (OnFluentSomething o : onFluentSomethings) {
                    last = o.dispatch(last);
                }
                return last;
            } catch (FluentExecutionStopped e) {
                lastRE = e;
                goAgain = retry(e, start, retries);
            } catch (RuntimeException e) {
                lastRE = e;
                goAgain = retry(e, start, retries);
            }
        }
        throw lastRE;
    }

    private boolean retry(Throwable e, long start, int retries) {
        boolean shouldRetry = retryStrategies.length > 0;
        for (RetryStrategy retryStrategy : retryStrategies) {
            shouldRetry = shouldRetry & retryStrategy.shouldRetry(e, start, retries);
        }
        return shouldRetry;
    }

}
