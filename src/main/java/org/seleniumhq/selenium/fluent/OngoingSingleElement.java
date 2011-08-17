package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class OngoingSingleElement extends OngoingFluentWebDriver {

    private final WebElement currentElement;

    public OngoingSingleElement(WebDriver delegate, WebElement currentElement, String context) {
        super(delegate, context);
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
        String ctx = context + ".click()";
        try {
            currentElement.click();
            return getOngoingSingleElement(currentElement, ctx);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public OngoingFluentWebDriver clearField() {
        String ctx = context + ".clearField()";
        try {
            currentElement.clear();
            return getOngoingSingleElement(currentElement, ctx);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }


    public OngoingFluentWebDriver submit() {
        String ctx = context + ".submit()";
        try {
            currentElement.submit();
            return getOngoingSingleElement(currentElement, ctx);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    // These are as they would be in the WebElement API

    public OngoingFluentWebDriver sendKeys(CharSequence... keysToSend) {
        String ctx = context + ".sendKeys(" + charSeqArrayAsHumanString(keysToSend) + ")";
        try {
            currentElement.sendKeys(keysToSend);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);

        }
        return getOngoingSingleElement(currentElement, ctx);
    }


    public String getTagName() {
        String ctx = context + ".getTagName()";
        try {
            return currentElement.getTagName();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public boolean isSelected() {
        String ctx = context + ".isSelected()";
        try {
            return currentElement.isSelected();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public boolean isEnabled() {
        String ctx = context + ".isEnabled()";
        try {
            return currentElement.isEnabled();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public boolean isDisplayed() {
        String ctx = context + ".isDisplayed()";
        try {
            return currentElement.isDisplayed();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public Point getLocation() {
        String ctx = context + ".getLocation()";
        try {
            return currentElement.getLocation();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public Dimension getSize() {
        String ctx = context + ".getSize()";
        try {
            return currentElement.getSize();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public String getCssValue(String cssName) {
        String ctx = context + ".getCssValue("+cssName+")";
        try {
            return currentElement.getCssValue(cssName);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public String  getAttribute(String attr) {
        String ctx = context + ".getAttribute("+attr+")";
        try {
            return currentElement.getAttribute(attr);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

    public String getText() {
        String ctx = context + ".getText()";
        try {
            return currentElement.getText();
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
    }

}
