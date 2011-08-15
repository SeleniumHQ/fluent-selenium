package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class FluentWebDriverImpl extends FluentBase implements FluentWebDriver {

    public FluentWebDriverImpl(WebDriver delegate) {
        super(delegate);
    }

    @Override
    protected OngoingFluentWebDriver getSubsequentFluentWebDriver() {
        return new OngoingFluentWebDriver(super.delegate, this);
    }

    protected final WebElement findIt(By by) {
        WebElement result = delegate.findElement(by);
        clear();
        add(result);
        return result;
    }

    @Override
    protected List<WebElement> findThem(By by) {
        List<WebElement> results = delegate.findElements(by);
        clear();
        addAll(results);
        return results;
    }

}
