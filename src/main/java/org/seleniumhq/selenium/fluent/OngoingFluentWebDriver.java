package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class OngoingFluentWebDriver extends FluentCore {

    public OngoingFluentWebDriver(WebDriver delegate) {
        super(delegate);
    }

    @Override
    protected OngoingSingleElement getOngoingSingleElement(WebElement result) {
        return new OngoingSingleElement(super.delegate, result);
    }

    @Override
    protected OngoingMultipleElements getOngoingMultipleElement(List<WebElement> results) {
        return new OngoingMultipleElements(super.delegate, results);
    }

    // All these have peer equivalents in the WebElement interface
    // ===========================================================

    // These though, don't return void as they do in WebElement

    public abstract OngoingFluentWebDriver click();

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public abstract OngoingFluentWebDriver clearField();

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
