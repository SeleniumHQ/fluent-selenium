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

import java.util.List;

public abstract class BaseFluentWebElement extends BaseFluentWebDriver {

    public BaseFluentWebElement(WebDriver delegate, Context context) {
        super(delegate, context);
    }

    @Override
    protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context) {
        return new FluentWebElements(super.delegate, results, context);
    }

    protected String charSeqArrayAsHumanString(CharSequence[] keysToSend) {
        String keys = "";
        for (CharSequence charSequence : keysToSend) {
            keys = keys + ", " + charSequence;
        }
        return keys.substring(2);  // delete comma-space prefix
    }


    // All these have peer equivalents in the WebElement interface
    // ===========================================================

    // These are as they would be in the WebElement API

    public abstract Point getLocation();

    public abstract TestableString cssValue(String cssName);

    public abstract TestableString attribute(String attrName);

    public abstract TestableString getTagName();

    public abstract Dimension getSize();

    public abstract boolean isSelected();

    public abstract boolean isEnabled();

    public abstract boolean isDisplayed();

    public abstract TestableString getText();

}
