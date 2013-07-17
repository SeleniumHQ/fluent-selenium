package org.seleniumhq.selenium.fluent.internal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.Period;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RetryingFluentWebDriver extends FluentWebDriver {

    private final Period period;

    public RetryingFluentWebDriver(WebDriver webDriver, Period period, Context context) {
        super(webDriver, context);
        this.period = period;
    }

    @Override
    protected WebElement findIt(By by) {
        return retryingFindIt(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return retryingFindThem(by);
    }

    @Override
    protected Period getPeriod() {
        return period;
    }

    @Override
    protected void changeTimeout() {
        delegate.manage().timeouts().implicitlyWait(period.howLong(), period.timeUnit());

    }

    @Override
    protected void resetTimeout() {
        delegate.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

}
