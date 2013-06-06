package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.internal.Context;

import java.util.List;

public abstract class BaseFluentWebElements extends BaseFluentWebElement implements List<FluentWebElement> {
    protected BaseFluentWebElements(WebDriver delegate, Context context) {
        super(delegate, context);
    }
}
