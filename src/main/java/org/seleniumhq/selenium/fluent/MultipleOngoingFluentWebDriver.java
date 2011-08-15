package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MultipleOngoingFluentWebDriver extends OngoingFluentWebDriver {
    public MultipleOngoingFluentWebDriver(WebDriver delegate, List<WebElement> currentElements) {
        super(delegate, currentElements);
    }

    public OngoingFluentWebDriver click() {
        for (WebElement webElement : this) {
            webElement.click();
        }
        return getMultipleOngoingFluentWebDriver();
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public OngoingFluentWebDriver clearField() {
        for (WebElement webElement : this) {
            webElement.clear();
        }
        return getMultipleOngoingFluentWebDriver();
    }

    public OngoingFluentWebDriver submit() {
        for (WebElement webElement : this) {
            webElement.submit();
        }
        return getMultipleOngoingFluentWebDriver();
    }

    // These are as they would be in the WebElement API

    public OngoingFluentWebDriver sendKeys(CharSequence... keysToSend) {
        for (WebElement webElement : this) {
            webElement.sendKeys(keysToSend);
        }
        return getMultipleOngoingFluentWebDriver();
    }

    public boolean isSelected() {
        boolean areSelected = true;
        for (WebElement webElement : this) {
            areSelected = areSelected & webElement.isSelected();
        }
        return areSelected;
    }

    public boolean isEnabled() {
        boolean areEnabled = true;
        for (WebElement webElement : this) {
            areEnabled = areEnabled & webElement.isEnabled();
        }
        return areEnabled;
    }

    public boolean isDisplayed() {
        boolean areDisplayed = true;
        for (WebElement webElement : this) {
            areDisplayed = areDisplayed & webElement.isDisplayed();
        }
        return areDisplayed;
    }

    public String getText() {
        String text = "";
        for (WebElement webElement : this) {
            text = text + webElement.getText();
        }
        return text;
    }


    @Override
    public Point getLocation() {
        throw new UnsupportedOperationException("getLocation() has no meaning for multiple elements");
    }

    @Override
    public String getCssValue(String cssName) {
        throw new UnsupportedOperationException("getCssValue() has no meaning for multiple elements");
    }

    @Override
    public String getAttribute(String attrName) {
        throw new UnsupportedOperationException("getAttribute() has no meaning for multiple elements");
    }

    @Override
    public String getTagName() {
        throw new UnsupportedOperationException("getTagName() has no meaning for multiple elements");
    }

    @Override
    public Dimension getSize() {
        throw new UnsupportedOperationException("getSize() has no meaning for multiple elements");
    }
}
