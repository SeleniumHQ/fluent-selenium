package org.seleniumhq.selenium.fluent;

import org.seleniumhq.selenium.fluent.recording.RetryStrategy;

public class RetryingFluentWebDriverExecutor implements FluentWebDriverExecutor {
    private final int millisToSleep;
    private final RetryStrategy[] retryStrategies;

    public RetryingFluentWebDriverExecutor(int millisToSleep, RetryStrategy... retryStrategies) {
        this.millisToSleep = millisToSleep;
        this.retryStrategies = retryStrategies;
    }
    
    public Object playback(FluentWebDriver driver, FluentWebDriverExecution execution) {
        long start = System.currentTimeMillis();
        int retries = 0;
        RuntimeException lastRE = null;
        AssertionError lastAE = null;
        boolean goAgain = true;
        while (goAgain) {
            lastAE = null;
            lastRE = null;
            goAgain = false;
            try {
                return execution.execute(driver);
            } catch (FluentExecutionStopped e) {
                lastRE = e;
                goAgain = retry(e, start, retries);
            } catch (RuntimeException e) {
                lastRE = e;
                goAgain = retry(e, start, retries);
            } catch (AssertionError e) {
                lastAE = e;
                goAgain = retry(e, start, retries);
            }
            if (goAgain) {
                try {
                    Thread.sleep(millisToSleep);
                } catch (InterruptedException e) {
                }
                retries++;
            }
        }
        if (lastRE != null) {
            if (lastRE instanceof FluentExecutionStopped && retries > 0) {
                ((FluentExecutionStopped) lastRE).setRetries(retries).setDuration(System.currentTimeMillis() - start);
            }
            throw lastRE;
        } else {
            throw lastAE;
        }
        
        
    }

    private boolean retry(Throwable e, long start, int retries) {
        boolean shouldRetry = retryStrategies.length > 0;
        for (RetryStrategy retryStrategy : retryStrategies) {
            shouldRetry = shouldRetry & retryStrategy.shouldRetry(e, start, retries);
        }
        return shouldRetry;
    }


}
