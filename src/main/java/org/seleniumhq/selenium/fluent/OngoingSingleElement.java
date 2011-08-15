package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OngoingSingleElement extends OngoingFluentWebDriver {
    public OngoingSingleElement(WebDriver delegate, List<WebElement> currentElements) {
        super(delegate, currentElements);
    }

    public OngoingFluentWebDriver click() {
        get(0).click();
        return getSingleOngoingFluentWebDriver();
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public OngoingFluentWebDriver clearField() {
        get(0).clear();
        return getSingleOngoingFluentWebDriver();
    }


    public OngoingFluentWebDriver submit() {
        get(0).submit();
        return getSingleOngoingFluentWebDriver();
    }

    // These are as they would be in the WebElement API

    public OngoingFluentWebDriver sendKeys(CharSequence... keysToSend) {
        get(0).sendKeys(keysToSend);
        return getSingleOngoingFluentWebDriver();
    }

    public String getTagName() {
        return get(0).getTagName();
    }

    public boolean isSelected() {
        return get(0).isSelected();
    }

    public boolean isEnabled() {
        return get(0).isEnabled();
    }

    public boolean isDisplayed() {
        return get(0).isDisplayed();
    }

    public Point getLocation() {
        return get(0).getLocation();
    }

    public Dimension getSize() {
        return get(0).getSize();
    }

    public String getCssValue(String cssName) {
        return get(0).getCssValue(cssName);
    }

    public String  getAttribute(String attr) {
        return get(0).getAttribute(attr);
    }

    public String getText() {
        return get(0).getText();
    }

}
