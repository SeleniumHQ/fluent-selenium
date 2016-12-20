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
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.*;

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
            elems.add(new FluentWebElement(delegate, new Internal.WebElementHolder(null, aResult, null), ctx, monitor, booleanInsteadOfNotFoundException));
        }
        return new FluentWebElements(delegate, elems, ctx, monitor, booleanInsteadOfNotFoundException);
    }

    public FluentWebElements click() {
        Context ctx = Context.singular(context, "click");
        executeAndWrapReThrowIfNeeded(new Click(), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    private <T> T executeAndWrapReThrowIfNeeded(Execution<T> execution, Context ctx) {
        return executeAndWrapReThrowIfNeeded(execution, null, ctx, true);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public FluentWebElements clearField() {
        Context ctx = Context.singular(context, "clearField");
        executeAndWrapReThrowIfNeeded(new Clear(), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    public FluentWebElements submit() {
        Context ctx = Context.singular(context, "submit");
        executeAndWrapReThrowIfNeeded(new Submit(), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    // These are as they would be in the WebElement API

    public FluentWebElements sendKeys(final CharSequence... keysToSend) {
        Context ctx = Context.singular(context, "sendKeys", charSeqArrayAsHumanString(keysToSend));
        executeAndWrapReThrowIfNeeded(new SendKeys(keysToSend), ctx);
        return makeFluentWebElements(this, ctx, monitor);
    }

    public boolean isSelected() {
        Context ctx = Context.singular(context, "isSelected");
        return executeAndWrapReThrowIfNeeded(new IsSelected(), ctx);
    }

    public boolean isEnabled() {
        Context ctx = Context.singular(context, "isEnabled");
        return executeAndWrapReThrowIfNeeded(new IsEnabled(), ctx);
    }

    public boolean isDisplayed() {
        Context ctx = Context.singular(context, "isDisplayed");
        return executeAndWrapReThrowIfNeeded(new IsDisplayed(), ctx);
    }

    public TestableString getText() {
        Context ctx = Context.singular(context, "getText");
        return new TestableString(new GetText(), ctx, monitor).within(getPeriod());
    }

    public TestableString getText(TestableString.Concatenator concator) {
        Context ctx = Context.singular(context, "getText");
        return new TestableString(new GetText(concator), ctx, monitor).within(getPeriod());
    }

    public TestableString getText(TestableString.StringChanger... stringChangers) {
        Context ctx = Context.singular(context, "getText");
        return new TestableString(new GetText(stringChangers), ctx, monitor).within(getPeriod());
    }

    public TestableString getText(TestableString.Concatenator concator, TestableString.StringChanger... stringChangers) {
        Context ctx = Context.singular(context, "getText");
        return new TestableString(new GetText(concator, stringChangers), ctx, monitor).within(getPeriod());
    }

    public <K, V> Map<K,V> map(FluentWebElementMap<K,V> mapper) {
        int ix = -1;
        for (FluentWebElement next : currentElements) {
            mapper.map(next, ++ix);
        }
        return mapper;
    }

    public FluentWebElements each(FluentWebElementVistor visitor) {
        int ix = -1;
        for (FluentWebElement next : currentElements) {
            visitor.visit(next, ++ix);
        }
        return this;
    }

    @Override
    protected final WebElement findElement(By by, Context ctx, SearchContext searchContext) {
        return null;
    }

    @Override
    protected final List<WebElement> findElements(By by, Context ctx) {
        return null;
    }

    @Override
    protected final WebElement actualFindElement(By by, Context ctx, SearchContext searchContext) {
        return null;
    }

    @Override
    protected final List<WebElement> actualFindElements(By by, Context ctx) {
        return null;
    }

    public FluentWebElements filter(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "filter", null, matcher);
        return makeFluentWebElements(executeAndWrapReThrowIfNeeded(new FilterMatches(matcher), ctx), ctx, monitor);
    }

    public FluentWebElement first(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "first", null, matcher);
        return executeAndWrapReThrowIfNeeded(new MatchesFirst(matcher), ctx);
    }

    public FluentWebElement last(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "last", null, matcher);
        return executeAndWrapReThrowIfNeeded(new MatchesLast(matcher), ctx);
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
        try {
            return currentElements.get(i);
        } catch (IndexOutOfBoundsException e) {
            throw new FluentExecutionStopped("Element index " + i + " not in collection of " + currentElements.size() + " elements", e);
        }
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
        try {
            return currentElements.listIterator(i);
        } catch (IndexOutOfBoundsException e) {
            throw new FluentExecutionStopped("Element index " + i + " not in collection of " + currentElements.size() + " elements", e);
        }
    }

    public List<FluentWebElement> subList(int i, int i1) {
        try {
            return currentElements.subList(i, i1);
        } catch (IndexOutOfBoundsException e) {
            throw new FluentExecutionStopped("Element index " + i + ", or element index " + i1 + " not in collection of " + currentElements.size() + " elements", e);
        }
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
            int ix = -1;
            for (FluentWebElement fwe : FluentWebElements.this) {
                ix++;
                if (matcher.matches(fwe, ix)) {
                    result = fwe;
                    if (shouldBreak()) {
                        break;
                    }
                }
            }
            if (result == null) {
                throw new Internal.NothingMatches();
            } else {
                return result;
            }
        }

        protected boolean shouldBreak() {
            return true;
        }
    }

    private class MatchesLast extends MatchesFirst {

        public MatchesLast(FluentMatcher matcher) {
            super(matcher);
        }

        @Override
        protected boolean shouldBreak() {
            return false;
        }
    }

    private class FilterMatches extends Execution<List<FluentWebElement>> {
        private final FluentMatcher matcher;

        public FilterMatches(FluentMatcher matcher) {
            this.matcher = matcher;
        }

        public List<FluentWebElement> execute() {
            List<FluentWebElement> results = new ArrayList<FluentWebElement>();
            int ix = -1;
            for (FluentWebElement fwe : FluentWebElements.this) {
                ix++;
                if (matcher.matches(fwe, ix)) {
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
        private TestableString.Concatenator concatenator = new TestableString.DefaultConcatenator();
        private TestableString.StringChanger[] stringChangers = new TestableString.StringChanger[] { new TestableString.NoopStringChanger() };

        public GetText() {
        }

        public GetText(TestableString.Concatenator concatenator) {
            this.concatenator = concatenator;
        }

        public GetText(TestableString.StringChanger... stringChangers) {
            this.stringChangers = stringChangers;
        }

        public GetText(TestableString.Concatenator concatenator, TestableString.StringChanger... stringChangers) {
            this.stringChangers = stringChangers;
            this.concatenator = concatenator;
        }

        public String execute() {
            concatenator.start("");
            for (FluentWebElement fwe : FluentWebElements.this) {
                concatenator.concat(fwe.getText(stringChangers));
            }
            return concatenator.toString();
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
