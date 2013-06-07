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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FluentWebElements extends Internal.BaseFluentWebElements {

    private final List<FluentWebElement> currentElements;

    protected FluentWebElements(WebDriver delegate, List<FluentWebElement> currentElements, Context context) {
        super(delegate, context);
        this.currentElements = currentElements;
    }

    protected Internal.BaseFluentWebElements newFluentWebElements(MultipleResult multiple) {
        List<WebElement> result = multiple.getResult();
        Context ctx = multiple.getCtx();
        List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
        for (WebElement aResult : result) {
            elems.add(new FluentWebElement(delegate, aResult, ctx));
        }
        return new FluentWebElements(delegate, elems, ctx);
    }

    public FluentWebElements click() {
        Context ctx = Context.singular(context, "click");
        decorateExecution(new Click(), ctx);
        return makeFluentWebElements(this, ctx);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public FluentWebElements clearField() {
        Context ctx = Context.singular(context, "clearField");
        decorateExecution(new Clear(), ctx);
        return makeFluentWebElements(this, ctx);
    }

    public FluentWebElements submit() {
        Context ctx = Context.singular(context, "submit");
        decorateExecution(new Submit(), ctx);
        return makeFluentWebElements(this, ctx);
    }

    // These are as they would be in the WebElement API

    public FluentWebElements sendKeys(final CharSequence... keysToSend) {
        Context ctx = Context.singular(context, "sendKeys", charSeqArrayAsHumanString(keysToSend));
        decorateExecution(new SendKeys(keysToSend), ctx);
        return makeFluentWebElements(this, ctx);
    }

    public boolean isSelected() {
        Context ctx = Context.singular(context, "isSelected");
        return decorateExecution(new IsSelected(), ctx);
    }

    public boolean isEnabled() {
        Context ctx = Context.singular(context, "isEnabled");
        return decorateExecution(new IsEnabled(), ctx);
    }

    public boolean isDisplayed() {
        Context ctx = Context.singular(context, "isDisplayed");
        return decorateExecution(new IsDisplayed(), ctx);
    }

    public TestableString getText() {
        Context ctx = Context.singular(context, "getText");
        return new TestableString(new GetText(), ctx).within(getPeriod());
    }

    @Override
    protected final WebElement findIt(By by) {
        return null;
    }

    @Override
    protected final List<WebElement> findThem(By by) {
        return null;
    }

    @Override
    protected final WebElement actualFindIt(By by) {
        return null;
    }

    @Override
    protected final List<WebElement> actualFindThem(By by) {
        return null;
    }

    public FluentWebElements filter(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "filter", null, matcher);
        return makeFluentWebElements(decorateExecution(new FilterMatches(matcher), ctx), ctx);
    }

    public FluentWebElement first(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "first", null, matcher);
        return decorateExecution(new MatchesFirst(matcher), ctx);
    }


    // From java.util.List

    public void clear() {
        currentElements.clear();
    }

    public int size() {
        return currentElements.size();
    }

    public boolean isEmpty() {
        return currentElements.isEmpty();
    }

    public boolean contains(Object o) {
        return currentElements.contains(o);
    }

    public Iterator<FluentWebElement> iterator() {
        return currentElements.iterator();
    }

    public Object[] toArray() {
        return currentElements.toArray();
    }

    public <FluentWebElement> FluentWebElement[] toArray(FluentWebElement[] ts) {
        return currentElements.toArray(ts);
    }

    public boolean add(FluentWebElement webElement) {
        return currentElements.add(webElement);
    }

    public boolean remove(Object o) {
        return currentElements.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return currentElements.containsAll(objects);
    }

    public boolean addAll(Collection<? extends FluentWebElement> webElements) {
        return currentElements.addAll(webElements);
    }

    public boolean addAll(int i, Collection<? extends FluentWebElement> webElements) {
        return currentElements.addAll(webElements);
    }

    public boolean removeAll(Collection<?> objects) {
        return currentElements.removeAll(objects);
    }

    public boolean retainAll(Collection<?> objects) {
        return currentElements.retainAll(objects);
    }

    public FluentWebElement get(int i) {
        return currentElements.get(i);
    }

    public FluentWebElement set(int i, FluentWebElement webElement) {
        return currentElements.set(i, webElement);
    }

    public void add(int i, FluentWebElement webElement) {
        currentElements.add(i,webElement);
    }

    public FluentWebElement remove(int i) {
        return currentElements.remove(i);
    }

    public int indexOf(Object o) {
        return currentElements.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return currentElements.lastIndexOf(o);
    }

    public ListIterator<FluentWebElement> listIterator() {
        return currentElements.listIterator();
    }

    public ListIterator<FluentWebElement> listIterator(int i) {
        return currentElements.listIterator(i);
    }

    public List<FluentWebElement> subList(int i, int i1) {
        return currentElements.subList(i, i1);
    }

    private class Clear implements Execution<Boolean> {
        public Boolean execute() {
            for (FluentWebElement webElement : FluentWebElements.this) {
                webElement.getWebElement().clear();
            }
            return true;
        }
    }

    private class Click implements Execution<Boolean> {
        public Boolean execute() {
            for (FluentWebElement webElement : FluentWebElements.this) {
                webElement.click();
            }
            return true;
        }
    }

    private class MatchesFirst implements Execution<FluentWebElement> {
        private final FluentMatcher matcher;

        public MatchesFirst(FluentMatcher matcher) {
            this.matcher = matcher;
        }

        public FluentWebElement execute() {
            FluentWebElement result = null;
            for (FluentWebElement webElement : FluentWebElements.this) {
                if (matcher.matches(webElement.getWebElement())) {
                    result = webElement;
                    break;
                }
            }
            if (result == null) {
                throw new Internal.NothingMatches();
            } else {
                return result;
            }
        }
    }

    private class FilterMatches implements Execution<List<FluentWebElement>> {
        private final FluentMatcher matcher;

        public FilterMatches(FluentMatcher matcher) {
            this.matcher = matcher;
        }

        public List<FluentWebElement> execute() {
            List<FluentWebElement> results = new ArrayList<FluentWebElement>();
            for (FluentWebElement webElement : FluentWebElements.this) {
                if (matcher.matches(webElement.getWebElement())) {
                    results.add(webElement);
                }
            }
            if (results.size() == 0) {
                throw new Internal.NothingMatches();
            }
            return results;
        }
    }

    private class GetText implements Execution<String> {
        public String execute() {
            String text = "";
            for (FluentWebElement webElement : FluentWebElements.this) {
                text = text + webElement.getText();
            }
            return text;
        }
    }

    private class IsDisplayed implements Execution<Boolean> {
        public Boolean execute() {
            boolean areDisplayed = true;
            for (FluentWebElement webElement : FluentWebElements.this) {
                areDisplayed = areDisplayed & webElement.isDisplayed().getValue();
            }
            return areDisplayed;
        }
    }

    private class IsEnabled implements Execution<Boolean> {
        public Boolean execute() {
            boolean areEnabled = true;
            for (FluentWebElement webElement : FluentWebElements.this) {
                areEnabled = areEnabled & webElement.isEnabled().getValue();
            }
            return areEnabled;
        }
    }

    private class IsSelected implements Execution<Boolean> {
        public Boolean execute() {
            boolean areSelected = true;
            for (FluentWebElement webElement : FluentWebElements.this) {
                areSelected = areSelected & webElement.isSelected().getValue();
            }
            return areSelected;
        }
    }

    private class SendKeys implements Execution<Boolean> {
        private final CharSequence[] keysToSend;

        public SendKeys(CharSequence... keysToSend) {
            this.keysToSend = keysToSend;
        }

        public Boolean execute() {
            for (FluentWebElement webElement : FluentWebElements.this) {
                webElement.sendKeys(keysToSend);
            }
            return true;
        }
    }

    private class Submit implements Execution<Boolean> {
        public Boolean execute() {
            for (FluentWebElement webElement : FluentWebElements.this) {
                webElement.submit();
            }
            return true;
        }
    }
}
