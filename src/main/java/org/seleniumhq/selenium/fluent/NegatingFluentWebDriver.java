package org.seleniumhq.selenium.fluent;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        final T successfullyAbsent = null;
        while (!durationHasElapsed(startedAt)) {
            try {
                super.decorateExecution(execution, ctx);
            } catch (FluentExecutionStopped executionStopped) {
                final boolean elementGone = executionStopped.getCause() instanceof ElementNotFoundException;

                if (elementGone) {
                    return successfullyAbsent;
                }
            }
        }
        throw decorateAssertionError(ctx, new AssertionError("Element never disappeared"));
    }

    protected Boolean durationHasElapsed(Long startMillis) {
        return duration.getEndMillis(startMillis) <= System.currentTimeMillis();
    }

    @Override
    protected FluentWebElement newFluentWebElement(WebDriver delegate, WebElement result, Context context) {
        return new DisappearedFluentWebElement(delegate, context);
    }
}
