package org.seleniumhq.selenium.fluent;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.WebDriver;

public class NegatingFluentWebDriver extends FluentWebDriverImpl {
    private final Period duration;
    private final Long startedAt;

    protected NegatingFluentWebDriver(WebDriver delegate, Period duration, Context context) {
        super(delegate, context);
        this.duration = duration;
        startedAt = System.currentTimeMillis();
    }

    @Override
    protected <T> T decorateExecution(Execution<T> execution, Context ctx) {
        final T neverFound = null;
        T found = null;
        while (!durationHasElapsed(startedAt)) {
            try {
                found = super.decorateExecution(execution, ctx);

            } catch (FluentExecutionStopped executionStopped) {
                if (!(executionStopped.getCause() instanceof ElementNotFoundException)) {
                    continue;
                } else {
                    return neverFound;
                }
            }
        }
        throw decorateAssertionError(ctx, new AssertionError("Element never disappeared"));
    }

    protected Boolean durationHasElapsed(Long startMillis) {
        return duration.getEndMillis(startMillis) <= System.currentTimeMillis();
    }
}
