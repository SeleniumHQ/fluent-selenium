/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class OngoingSingleElement extends OngoingFluentWebDriver {

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
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
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
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }


    public OngoingFluentWebDriver submit() {
        String ctx = context + ".submit()";
        try {
            currentElement.submit();
            return getOngoingSingleElement(currentElement, ctx);
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    // These are as they would be in the WebElement API

    public OngoingFluentWebDriver sendKeys(CharSequence... keysToSend) {
        String ctx = context + ".sendKeys(" + charSeqArrayAsHumanString(keysToSend) + ")";
        try {
            currentElement.sendKeys(keysToSend);
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
        return getOngoingSingleElement(currentElement, ctx);
    }


    public String getTagName() {
        String ctx = context + ".getTagName()";
        try {
            return currentElement.getTagName();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public boolean isSelected() {
        String ctx = context + ".isSelected()";
        try {
            return currentElement.isSelected();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public boolean isEnabled() {
        String ctx = context + ".isEnabled()";
        try {
            return currentElement.isEnabled();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public boolean isDisplayed() {
        String ctx = context + ".isDisplayed()";
        try {
            return currentElement.isDisplayed();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public Point getLocation() {
        String ctx = context + ".getLocation()";
        try {
            return currentElement.getLocation();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public Dimension getSize() {
        String ctx = context + ".getSize()";
        try {
            return currentElement.getSize();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public String getCssValue(String cssName) {
        String ctx = context + ".getCssValue("+cssName+")";
        try {
            return currentElement.getCssValue(cssName);
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public String  getAttribute(String attr) {
        String ctx = context + ".getAttribute("+attr+")";
        try {
            return currentElement.getAttribute(attr);
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public String getText() {
        String ctx = context + ".getText()";
        try {
            return currentElement.getText();
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

}
