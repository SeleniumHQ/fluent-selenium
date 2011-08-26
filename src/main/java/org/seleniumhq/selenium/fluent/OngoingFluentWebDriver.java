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

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class OngoingFluentWebDriver extends FluentCore {

    public OngoingFluentWebDriver(WebDriver delegate, String context) {
        super(delegate, context);
    }

    @Override
    protected FluentWebElement getOngoingSingleElement(WebElement result, String context) {
        return new FluentWebElement(super.delegate, result, context);
    }

    @Override
    protected FluentWebElements getOngoingMultipleElements(List<WebElement> results, String context) {
        return new FluentWebElements(super.delegate, results, context);
    }

    protected String charSeqArrayAsHumanString(CharSequence[] keysToSend) {
        String keys = "";
        for (CharSequence charSequence : keysToSend) {
            keys = keys + ", '" + charSequence + "'";
        }
        return keys.substring(2);
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
