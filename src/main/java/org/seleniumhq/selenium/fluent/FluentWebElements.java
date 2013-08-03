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

    protected FluentWebElements(WebDriver delegate, List<FluentWebElement> currentElements, Context context, Monitor monitor, boolean booleanInsteadOfNoSuchElement) {
        super(delegate, context, monitor, booleanInsteadOfNoSuchElement);
        this.currentElements = currentElements;
    }

    protected Internal.BaseFluentWebElements newFluentWebElements(MultipleResult multiple) {
        List<WebElement> result = multiple.getResult();
        Context ctx = multiple.getCtx();
        List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
        for (WebElement aResult : result) {
            elems.add(new FluentWebElement(delegate, new WebElementHolder(null, aResult, null), ctx, monitor, booleanInsteadOfNoSuchElement));
        }
        return new FluentWebElements(delegate, elems, ctx, monitor, booleanInsteadOfNoSuchElement);
    }

    public FluentWebElements click() {
        Context ctx = Context.singular(context, "click");
        decorateExecution(new Click(), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public FluentWebElements clearField() {
        Context ctx = Context.singular(context, "clearField");
        decorateExecution(new Clear(), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    public FluentWebElements submit() {
        Context ctx = Context.singular(context, "submit");
        decorateExecution(new Submit(), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    // These are as they would be in the WebElement API

    public FluentWebElements sendKeys(final CharSequence... keysToSend) {
        Context ctx = Context.singular(context, "sendKeys", charSeqArrayAsHumanString(keysToSend));
        decorateExecution(new SendKeys(keysToSend), ctx);
        return makeFluentWebElements(this, ctx, monitor);
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
    protected final WebElement findIt(By by, Context ctx) {
        return null;
    }

    @Override
    protected final List<WebElement> findThem(By by, Context ctx) {
        return null;
    }

    @Override
    protected final WebElement actualFindIt(By by, Context ctx) {
        return null;
    }

    @Override
    protected final List<WebElement> actualFindThem(By by, Context ctx) {
        return null;
    }

    public FluentWebElements filter(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "filter", null, matcher);
        return makeFluentWebElements(decorateExecution(new FilterMatches(matcher), ctx), ctx, monitor);
    }

    public FluentWebElement first(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "first", null, matcher);
        return decorateExecution(new MatchesFirst(matcher), ctx);
    }


    // From java.util.List

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

    public boolean containsAll(Collection<?> objects) {
        return currentElements.containsAll(objects);
    }

    public boolean retainAll(Collection<?> objects) {
        return currentElements.retainAll(objects);
    }

    public FluentWebElement get(int i) {
        return currentElements.get(i);
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

    private class Clear extends Execution<Boolean> {
        public Boolean execute() {
            for (FluentWebElement fwe : FluentWebElements.this) {
                fwe.clearField();
            }
            return true;
        }
    }

    private class Click extends Execution<Boolean> {
        public Boolean execute() {
            for (FluentWebElement fwe : FluentWebElements.this) {
                fwe.click();
            }
            return true;
        }
    }

    private class MatchesFirst extends Execution<FluentWebElement> {
        private final FluentMatcher matcher;

        public MatchesFirst(FluentMatcher matcher) {
            this.matcher = matcher;
        }

        public FluentWebElement execute() {
            FluentWebElement result = null;
            for (FluentWebElement fwe : FluentWebElements.this) {
                if (matcher.matches(fwe.getWebElement())) {
                    result = fwe;
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

    private class FilterMatches extends Execution<List<FluentWebElement>> {
        private final FluentMatcher matcher;

        public FilterMatches(FluentMatcher matcher) {
            this.matcher = matcher;
        }

        public List<FluentWebElement> execute() {
            List<FluentWebElement> results = new ArrayList<FluentWebElement>();
            for (FluentWebElement fwe : FluentWebElements.this) {
                if (matcher.matches(fwe.getWebElement())) {
                    results.add(fwe);
                }
            }
            if (results.size() == 0) {
                throw new Internal.NothingMatches();
            }
            return results;
        }
    }

    private class GetText extends Execution<String> {
        public String execute() {
            String text = "";
            for (FluentWebElement fwe : FluentWebElements.this) {
                text = text + fwe.getText();
            }
            return text;
        }
    }

    private class IsDisplayed extends Execution<Boolean> {
        public Boolean execute() {
            boolean areDisplayed = true;
            for (FluentWebElement fwe : FluentWebElements.this) {
                areDisplayed = areDisplayed & fwe.isDisplayed().value();
            }
            return areDisplayed;
        }
    }

    private class IsEnabled extends Execution<Boolean> {
        public Boolean execute() {
            boolean areEnabled = true;
            for (FluentWebElement fwe : FluentWebElements.this) {
                areEnabled = areEnabled & fwe.isEnabled().value();
            }
            return areEnabled;
        }
    }

    private class IsSelected extends Execution<Boolean> {
        public Boolean execute() {
            boolean areSelected = true;
            for (FluentWebElement fwe : FluentWebElements.this) {
                areSelected = areSelected & fwe.isSelected().value();
            }
            return areSelected;
        }
    }

    private class SendKeys extends Execution<Boolean> {
        private final CharSequence[] keysToSend;

        public SendKeys(CharSequence... keysToSend) {
            this.keysToSend = keysToSend;
        }

        public Boolean execute() {
            for (FluentWebElement fwe : FluentWebElements.this) {
                fwe.sendKeys(keysToSend);
            }
            return true;
        }
    }

    private class Submit extends Execution<Boolean> {
        public Boolean execute() {
            for (FluentWebElement fwe : FluentWebElements.this) {
                fwe.submit();
            }
            return true;
        }
    }
}
