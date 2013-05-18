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

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FluentWebElements extends BaseFluentWebElement implements List<FluentWebElement> {

    private final List<FluentWebElement> currentElements;

    public FluentWebElements(WebDriver delegate, List<FluentWebElement> currentElements, Context context) {
        super(delegate, context);
        this.currentElements = currentElements;
    }

    public FluentWebElements click() {
        Context ctx = Context.singular(context, "click");
        decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                for (FluentWebElement webElement : FluentWebElements.this) {
                    webElement.click();
                }
                return true;
            }
        }, ctx);
        return makeFluentWebElements(this, ctx);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public FluentWebElements clearField() {
        Context ctx = Context.singular(context, "clearField");
        decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                for (FluentWebElement webElement : FluentWebElements.this) {
                    webElement.getWebElement().clear();
                }
                return true;
            }
        }, ctx);
        return makeFluentWebElements(this, ctx);
    }

    public FluentWebElements submit() {
        Context ctx = Context.singular(context, "submit");
        decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                for (FluentWebElement webElement : FluentWebElements.this) {
                    webElement.submit();
                }
                return true;
            }
        }, ctx);
        return makeFluentWebElements(this, ctx);
    }

    // These are as they would be in the WebElement API

    public FluentWebElements sendKeys(final CharSequence... keysToSend) {
        Context ctx = Context.singular(context, "sendKeys", charSeqArrayAsHumanString(keysToSend));
        decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                for (FluentWebElement webElement : FluentWebElements.this) {
                    webElement.sendKeys(keysToSend);
                }
                return true;
            }
        }, ctx);
        return makeFluentWebElements(this, ctx);
    }

    public boolean isSelected() {
        Context ctx = Context.singular(context, "isSelected");
        boolean areSelected = decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                boolean areSelected = true;
                for (FluentWebElement webElement : FluentWebElements.this) {
                    areSelected = areSelected & webElement.isSelected();
                }
                return areSelected;
            }
        }, ctx);
        return areSelected;
    }

    public boolean isEnabled() {
        Context ctx = Context.singular(context, "isEnabled");
        return decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                boolean areSelected = true;
                for (FluentWebElement webElement : FluentWebElements.this) {
                    areSelected = areSelected & webElement.isEnabled();
                }
                return areSelected;
            }
        }, ctx);
    }

    public boolean isDisplayed() {
        Context ctx = Context.singular(context, "isDisplayed");
        return decorateExecution(new Execution<Boolean>() {
            public Boolean execute() {
                boolean areSelected = true;
                for (FluentWebElement webElement : FluentWebElements.this) {
                    areSelected = areSelected & webElement.isDisplayed();
                }
                return areSelected;
            }
        }, ctx);
    }

    public TestableString getText() {
        Context ctx = Context.singular(context, "getText");
        Execution<String> execution = new Execution<String>() {
            public String execute() {
                String text = "";
                for (FluentWebElement webElement : FluentWebElements.this) {
                    text = text + webElement.getText();
                }
                return text;
            }
        };
        return new TestableString(execution, ctx).within(getPeriod());
    }

    @Override
    protected final WebElement findIt(By by) {
        throw meaningless("findIt('" + by + "')");
    }

    @Override
    protected final List<WebElement> findThem(By by) {
        throw meaningless("findThem('" + by + "')");
    }

    @Override
    protected final WebElement actualFindIt(By by) {
        throw meaningless("findIt('" + by + "')");
    }

    @Override
    protected final List<WebElement> actualFindThem(By by) {
        throw meaningless("findThem('" + by + "')");
    }

    @Override
    public final Point getLocation() {
        throw meaningless("getLocation()");
    }

    private UnsupportedOperationException meaningless(final String invocation) {
        return new UnsupportedOperationException(invocation + " has no meaning for multiple elements");
    }

    @Override
    public final TestableString getCssValue(String cssName) {
        throw meaningless("getCssValue('"+cssName+"')");
    }

    @Override
    public final TestableString getAttribute(String attrName) {
        throw meaningless("getAttribute('"+attrName+"')");
    }

    @Override
    public final TestableString getTagName() {
        throw meaningless("getTagName()");
    }

    @Override
    public final Dimension getSize() {
        throw meaningless("getSize()");
    }

    public FluentWebElements within(Period p) {
        throw meaningless("within("+p+")");
    }

    public FluentWebElements filter(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "filter", null, matcher);
        final List<FluentWebElement> subset = decorateExecution(new Execution<List<FluentWebElement>>() {
            public List<FluentWebElement> execute() {
                List<FluentWebElement> results = new ArrayList<FluentWebElement>();
                for (FluentWebElement webElement : FluentWebElements.this) {
                    if (matcher.matches(webElement.getWebElement())) {
                        results.add(webElement);
                    }
                }
                if (results.size() == 0) {
                    throw new NothingMatches();
                }
                return results;
            }
        }, ctx);
        return makeFluentWebElements(subset, ctx);
    }

    public FluentWebElement first(final FluentMatcher matcher) {
        Context ctx = Context.singular(context, "first", null, matcher);
        FluentWebElement first = decorateExecution(new Execution<FluentWebElement>() {
            public FluentWebElement execute() {
                FluentWebElement result = null;
                for (FluentWebElement webElement : FluentWebElements.this) {
                    if (matcher.matches(webElement.getWebElement())) {
                        result = webElement;
                        break;
                    }
                }
                if (result == null) {
                    throw new NothingMatches();
                } else {
                    return result;
                }
            }
        }, ctx);

        return first;
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



    // From BaseWebDriver


    private UnsupportedOperationException notSupported() {
        return new UnsupportedOperationException("not supported for FluentWebElements");
    }

    @Override
    public final TestableString title() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement span() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement span(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements spans() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements spans(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement div() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement div(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements divs() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements divs(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement button() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement button(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements buttons() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements buttons(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement link() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement link(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements links() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements links(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement input() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement input(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements inputs() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements inputs(By by) {
        throw notSupported();
    }

    @Override
    public final FluentSelect select() {
        throw notSupported();
    }

    @Override
    public final FluentSelect select(By by) {
        throw notSupported();
    }

    @Override
    public final FluentSelects selects() {
        throw notSupported();
    }

    @Override
    public final FluentSelects selects(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h1() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h1(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h1s() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h1s(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h2() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h2(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h2s() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h2s(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h3() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h3s() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h3(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h3s(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h4() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h4s() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement h4(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements h4s(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement p() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements ps() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement p(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements ps(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement img() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements imgs() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement img(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements imgs(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement table() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements tables() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement table(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements tables(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement fieldset() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements fieldsets() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement fieldset(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements fieldsets(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement legend() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements legends() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement legend(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements legends(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement tr() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements trs() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement tr(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements trs(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement td() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements tds() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement td(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements tds(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement th() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements ths() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement th(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements ths(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement ul() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements uls() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement ul(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements uls(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement ol() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements ols() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement ol(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements ols(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement form() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements forms() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement form(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements forms(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement textarea() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements textareas() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement textarea(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements textareas(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement option() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements options() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement option(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements options(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement li() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement li(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements lis() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements lis(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElement map() {
        throw notSupported();
    }

    @Override
    public final FluentWebElements maps() {
        throw notSupported();
    }

    @Override
    public final FluentWebElement map(By by) {
        throw notSupported();
    }

    @Override
    public final FluentWebElements maps(By by) {
        throw notSupported();
    }

    @Override
    public final TestableString url() {
        throw notSupported();
    }
}
