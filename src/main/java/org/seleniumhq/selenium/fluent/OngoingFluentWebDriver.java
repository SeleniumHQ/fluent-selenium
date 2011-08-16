package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class OngoingFluentWebDriver extends FluentCore {

    public OngoingFluentWebDriver(WebDriver delegate, List<WebElement> currentElements) {
        super(delegate);
        addAll(currentElements);
    }

    @Override
    protected OngoingSingleElement getSingleOngoingFluentWebDriver() {
        return new OngoingSingleElement(super.delegate, this);
    }

    @Override
    protected OngoingMultipleElements getMultipleOngoingFluentWebDriver() {
        return new OngoingMultipleElements(super.delegate, this);
    }

    protected WebElement findIt(By by) {
        WebElement result = get(0).findElement(by);
        clear();
        add(result);
        return result;
    }

    @Override
    protected List<WebElement> findThem(By by) {
        List<WebElement> results = get(0).findElements(by);
        clear();
        addAll(results);
        return results;
    }


    // All these have peer equivalents in the WebElement interface
    // ===========================================================

    // These though, don't return void as they do in WebElement

    public abstract OngoingFluentWebDriver click();

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public abstract OngoingFluentWebDriver clearField();

    /**
     * Clear of Array that is the list of current WebElements
     *
     * Not to be confused with clearField() that maps to the WebElement.clear() method.
     */
    public final void clear() {
        super.clear();
    }

    public abstract OngoingFluentWebDriver submit();

    public abstract OngoingFluentWebDriver sendKeys(CharSequence... keysToSend);

    // These are as they would be in the WebElement API

    public abstract Point getLocation();

    public abstract String getCssValue(String cssName);

    public abstract String getAttribute(String attrName);

    public abstract String getTagName();

    public abstract Dimension getSize();

    public abstract boolean isSelected();

    public abstract boolean isEnabled();

    public abstract boolean isDisplayed();

    public abstract String getText();


}
