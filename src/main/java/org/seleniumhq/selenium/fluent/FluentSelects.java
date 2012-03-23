package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FluentSelects extends FluentWebElements {
    public FluentSelects(WebDriver delegate, List<FluentWebElement> currentElements, Context context) {
        super(delegate, currentElements, context);
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
    public FluentSelects within(Period p) {
        return (FluentSelects) super.within(p);
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
    public FluentSelect set(int i, FluentWebElement webElement) {
        return (FluentSelect) super.set(i, webElement);
    }


    @Override
    public FluentSelect remove(int i) {
        return (FluentSelect) super.remove(i);
    }

    @Override
    protected FluentSelects makeFluentWebElements(List<FluentWebElement> results, Context context) {
        return new FluentSelects(super.delegate, results, context);
    }
}
