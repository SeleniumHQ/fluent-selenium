package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OngoingSingleElement extends OngoingFluentWebDriver {

    private final WebElement currentElement;

    public OngoingSingleElement(WebDriver delegate, WebElement currentElement) {
        super(delegate);
        this.currentElement = currentElement;
    }

    protected WebElement findIt(By by) {
        return currentElement.findElement(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return currentElement.findElements(by);
    }


    public OngoingFluentWebDriver click() {
        currentElement.click();
        return getSingleOngoingFluentWebDriver(currentElement);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public OngoingFluentWebDriver clearField() {
        currentElement.clear();
        return getSingleOngoingFluentWebDriver(currentElement);
    }


    public OngoingFluentWebDriver submit() {
        currentElement.submit();
        return getSingleOngoingFluentWebDriver(currentElement);
    }

    // These are as they would be in the WebElement API

    public OngoingFluentWebDriver sendKeys(CharSequence... keysToSend) {
        currentElement.sendKeys(keysToSend);
        return getSingleOngoingFluentWebDriver(currentElement);
    }

    public String getTagName() {
        return currentElement.getTagName();
    }

    public boolean isSelected() {
        return currentElement.isSelected();
    }

    public boolean isEnabled() {
        return currentElement.isEnabled();
    }

    public boolean isDisplayed() {
        return currentElement.isDisplayed();
    }

    public Point getLocation() {
        return currentElement.getLocation();
    }

    public Dimension getSize() {
        return currentElement.getSize();
    }

    public String getCssValue(String cssName) {
        return currentElement.getCssValue(cssName);
    }

    public String  getAttribute(String attr) {
        return currentElement.getAttribute(attr);
    }

    public String getText() {
        return currentElement.getText();
    }

}
