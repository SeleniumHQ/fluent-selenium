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

import org.openqa.selenium.*;

import java.util.*;

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
        return new TestableString(getPeriod(), execution, ctx);
    }

    @Override
    protected WebElement findIt(By by) {
        throw meaningless("findIt('" + by + "')");
    }

    @Override
    protected List<WebElement> findThem(By by) {
        throw meaningless("findThem('" + by + "')");
    }

    @Override
    public Point getLocation() {
        throw meaningless("getLocation()");
    }

    private UnsupportedOperationException meaningless(final String invocation) {
        return new UnsupportedOperationException(invocation + " has no meaning for multiple elements");
    }

    @Override
    public TestableString getCssValue(String cssName) {
        throw meaningless("getCssValue('"+cssName+"')");
    }

    @Override
    public TestableString getAttribute(String attrName) {
        throw meaningless("getAttribute('"+attrName+"')");
    }

    @Override
    public TestableString getTagName() {
        throw meaningless("getTagName()");
    }

    @Override
    public Dimension getSize() {
        throw meaningless("getSize()");
    }

    @Override
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


}
