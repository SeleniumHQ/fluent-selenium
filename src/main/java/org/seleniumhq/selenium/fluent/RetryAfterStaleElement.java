package org.seleniumhq.selenium.fluent;

public abstract class RetryAfterStaleElement {

    public abstract void toRetry();

    public void stopAfter(Period period) {
        boolean again = true;
        long start = System.currentTimeMillis();
        long endMillis = period.getEndMillis(start);
        FluentExecutionStopped.BecauseOfStaleElement ex = null;
        while (again && System.currentTimeMillis() < endMillis) {
            ex = null;
            try {
                toRetry();
                again = false;
            } catch (FluentExecutionStopped.BecauseOfStaleElement e) {
                ex = e;
            }
        }
        if (ex != null) {
            ex.setDuration(System.currentTimeMillis() - start);
            throw ex;
        }

    }
}