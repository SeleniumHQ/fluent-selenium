/*
Copyright 2011-2013 Software Freedom Conservancy

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

import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.internal.Context;

import java.util.List;

public class FluentSelects extends FluentWebElements {

    protected FluentSelects(WebDriver delegate, List<FluentWebElement> currentElements, Context context, Monitor monitor, boolean booleanInsteadOfNoSuchElement) {
        super(delegate, currentElements, context, monitor, booleanInsteadOfNoSuchElement);
    }

    @Override
    public FluentSelects click() {
        return (FluentSelects) super.click();
    }

    @Override
    public FluentSelects clearField() {
        return (FluentSelects) super.clearField();
    }

    @Override
    public FluentSelects submit() {
        return (FluentSelects) super.submit();
    }

    @Override
    public FluentSelects sendKeys(CharSequence... keysToSend) {
        return (FluentSelects) super.sendKeys(keysToSend);
    }

    @Override
    public FluentSelects filter(FluentMatcher matcher) {
        return (FluentSelects) super.filter(matcher);
    }

    @Override
    public FluentSelect first(FluentMatcher matcher) {
        return (FluentSelect) super.first(matcher);
    }

    @Override
    public FluentSelect get(int i) {
        return (FluentSelect) super.get(i);
    }

    @Override
    protected FluentSelects makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor) {
        return new FluentSelects(super.delegate, results, context, monitor, booleanInsteadOfNotFoundException);
    }
}
