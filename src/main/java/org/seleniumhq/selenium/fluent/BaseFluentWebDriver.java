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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.seleniumhq.selenium.fluent.FluentBy.composite;

public abstract class BaseFluentWebDriver {

    protected final WebDriver delegate;
    protected final String context;


    static ThreadLocal<WaitContext> waiting = new ThreadLocal<WaitContext>();

    static class WaitContext {
        private final Period period;
        public WaitContext(Period period) {
            this.period = period;
        }
    }

    public BaseFluentWebDriver(WebDriver delegate, String context) {
        this.delegate = delegate;
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    protected final <T> T makeFluentWebElement(WebDriver delegate, WebElement result, String context, Constructor<?> constructor) {
        try {
            return (T) constructor.newInstance(delegate, result, context);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public FluentWebElement span() {
        return single(tagName("span"), "span", FluentWebElement.class);
    }

    public FluentWebElement span(By by) {
        return single(by, "span", FluentWebElement.class);
    }

    public FluentWebElements spans() {
        return multiple(tagName("span"), "span");
    }

    public FluentWebElements spans(By by) {
        return multiple(by, "span");
    }

    public FluentWebElement div() {
        return single(tagName("div"), "div", FluentWebElement.class);
    }

    public FluentWebElement div(By by) {
        return single(by, "div", FluentWebElement.class);
    }

    public FluentWebElements divs() {
        return multiple(tagName("div"), "div");
    }

    public FluentWebElements divs(By by) {
        return multiple(by, "div");
    }

    public FluentWebElement button() {
        return single(tagName("button"), "button", FluentWebElement.class);
    }

    public FluentWebElement button(By by) {
        return single(by, "button", FluentWebElement.class);
    }

    public FluentWebElements buttons() {
        return multiple(tagName("button"), "button");
    }

    public FluentWebElements buttons(By by) {
        return multiple(by, "button");
    }

    public FluentWebElement link() {
        return single(tagName("a"), "a", FluentWebElement.class);
    }

    public FluentWebElement link(By by) {
        return single(by, "a", FluentWebElement.class);
    }

    public FluentWebElements links() {
        return multiple(tagName("a"), "a");
    }

    public FluentWebElements links(By by) {
        return multiple(by, "a");
    }

    public FluentWebElement input() {
        return single(tagName("input"), "input", FluentWebElement.class);
    }

    public FluentWebElement input(By by) {
        return single(by, "input", FluentWebElement.class);
    }

    public FluentWebElements inputs() {
        return multiple(tagName("input"), "input");
    }

    public FluentWebElements inputs(By by) {
        return multiple(by, "input");
    }

    public FluentSelect select() {
        return single(tagName("select"), "select", FluentSelect.class);
    }

    public FluentSelect select(By by) {
        return single(by, "select", FluentSelect.class);
    }

    public FluentWebElements selects() {
        return multiple(tagName("select"), "select");
    }

    public FluentWebElements selects(By by) {
        return multiple(by, "select");
    }

    public FluentWebElement h1() {
        return single(tagName("h1"), "h1", FluentWebElement.class);
    }

    public FluentWebElement h1(By by) {
        return single(by, "h1", FluentWebElement.class);

    }

    public FluentWebElements h1s() {
        return multiple(tagName("h1"), "h1");
    }

    public FluentWebElements h1s(By by) {
        return multiple(by, "h1");
    }

    public FluentWebElement h2() {
        return single(tagName("h2"), "h2", FluentWebElement.class);
    }

    public FluentWebElement h2(By by) {
        return single(by, "h2", FluentWebElement.class);
    }

    public FluentWebElements h2s() {
        return multiple(tagName("h2"), "h2");
    }

    public FluentWebElements h2s(By by) {
        return multiple(by, "h2");
    }

    public FluentWebElement h3() {
        return single(tagName("h3"), "h3", FluentWebElement.class);
    }

    public FluentWebElements h3s() {
        return multiple(tagName("h3"), "h3");
    }

    public FluentWebElement h3(By by) {
        return single(by, "h3", FluentWebElement.class);
    }

    public FluentWebElements h3s(By by) {
        return multiple(by, "h3");
    }

    public FluentWebElement h4(){
        return single(tagName("h4"), "h4", FluentWebElement.class);
    }

    public FluentWebElements h4s() {
        return multiple(tagName("h4"), "h4");
    }

    public FluentWebElement h4(By by) {
        return single(by, "h4", FluentWebElement.class);
    }

    public FluentWebElements h4s(By by) {
        return multiple(by, "h4");
    }

    public FluentWebElement p() {
        return single(tagName("p"), "p", FluentWebElement.class);
    }

    public FluentWebElements ps() {
        return multiple(tagName("p"), "p");
    }

    public FluentWebElement p(By by) {
        return single(by, "p", FluentWebElement.class);
    }

    public FluentWebElements ps(By by) {
        return multiple(by, "p");
    }

    public FluentWebElement img() {
        return single(tagName("img"), "img", FluentWebElement.class);
    }

    public FluentWebElements imgs() {
        return multiple(tagName("img"), "img");
    }

    public FluentWebElement img(By by) {
        return single(by, "img", FluentWebElement.class);
    }

    public FluentWebElements imgs(By by) {
        return multiple(by, "img");
    }

    public FluentWebElement table() {
        return single(tagName("table"), "table", FluentWebElement.class);
    }

    public FluentWebElements tables() {
        return multiple(tagName("table"), "table");
    }

    public FluentWebElement table(By by) {
        return single(by, "table", FluentWebElement.class);
    }

    public FluentWebElements tables(By by) {
        return multiple(by, "table");
    }

    public FluentWebElement tr() {
        return single(tagName("tr"), "tr", FluentWebElement.class);
    }

    public FluentWebElements trs() {
        return multiple(tagName("tr"), "tr");
    }

    public FluentWebElement tr(By by) {
        return single(by, "tr", FluentWebElement.class);
    }

    public FluentWebElements trs(By by) {
        return multiple(by, "tr");
    }

    public FluentWebElement td() {
        return single(tagName("td"), "td", FluentWebElement.class);
    }

    public FluentWebElements tds() {
        return multiple(tagName("td"), "td");
    }

    public FluentWebElement td(By by) {
        return single(by, "td", FluentWebElement.class);
    }

    public FluentWebElements tds(By by) {
        return multiple(by, "td");
    }

    public FluentWebElement th() {
        return single(tagName("th"), "th", FluentWebElement.class);
    }

    public FluentWebElements ths() {
        return multiple(tagName("th"), "th");
    }

    public FluentWebElement th(By by) {
        return single(by, "th", FluentWebElement.class);
    }

    public FluentWebElements ths(By by) {
        return multiple(by, "th");
    }

    public FluentWebElement ul() {
        return single(tagName("ul"), "ul", FluentWebElement.class);
    }

    public FluentWebElements uls() {
        return multiple(tagName("ul"), "ul");
    }

    public FluentWebElement ul(By by) {
        return single(by, "ul", FluentWebElement.class);
    }

    public FluentWebElements uls(By by) {
        return multiple(by, "ul");
    }

    public FluentWebElement ol() {
        return single(tagName("ol"), "ol", FluentWebElement.class);
    }

    public FluentWebElements ols() {
        return multiple(tagName("ol"), "ol");
    }

    public FluentWebElement ol(By by) {
        return single(by, "ol", FluentWebElement.class);
    }

    public FluentWebElements ols(By by) {
        return multiple(by, "ol");
    }

    public FluentWebElement form() {
        return single(tagName("form"), "form", FluentWebElement.class);
    }

    public FluentWebElements forms() {
        return multiple(tagName("form"), "form");
    }

    public FluentWebElement form(By by) {
        return single(by, "form", FluentWebElement.class);
    }

    public FluentWebElements forms(By by) {
        return multiple(by, "form");
    }

    public FluentWebElement textarea() {
        return single(tagName("textarea"), "textarea", FluentWebElement.class);
    }

    public FluentWebElements textareas() {
        return multiple(tagName("textarea"), "textarea");
    }

    public FluentWebElement textarea(By by) {
        return single(by, "textarea", FluentWebElement.class);
    }

    public FluentWebElements textareas(By by) {
        return multiple(by, "textarea");
    }

    public FluentWebElement option() {
        return single(tagName("option"), "option", FluentWebElement.class);
    }

    public FluentWebElements options() {
        return multiple(tagName("option"), "option");
    }

    public FluentWebElement option(By by) {
        return single(by, "option", FluentWebElement.class);
    }

    public FluentWebElements options(By by) {
        return multiple(by, "option");
    }

    public FluentWebElement li() {
        return single(tagName("li"), "li", FluentWebElement.class);
    }

    public FluentWebElement li(By by) {
        return single(by, "li", FluentWebElement.class);
    }

    public FluentWebElements lis() {
        return multiple(tagName("li"), "li");
    }

    public FluentWebElements lis(By by) {
        return multiple(by, "li");
    }

    public TestableString url() {
        Execution<String> execution = new Execution<String>() {
            public String execute() {
                return delegate.getCurrentUrl();
            }
        };
        String ctx = context + ".url()";
        return new TestableString(getPeriod(), execution, ctx);
    }

    protected Period getPeriod() {
        return null;
    }

    public TestableString title() {
        Execution<String> execution = new Execution<String>() {
            public String execute() {
                return delegate.getTitle();
            }
        };
        String ctx = context + ".title()";
        return new TestableString(getPeriod(), execution, ctx);
    }

    protected abstract <T> T getFluentWebElement(WebElement result, String context, Class<T> webElementClass);

    protected abstract FluentWebElements getFluentWebElements(List<WebElement> results, String context);

    protected final By fixupBy(By by, String tagName) {
        if (by.getClass().getName().equals("org.openqa.selenium.By$ByXPath")) {
            by = xpath(".//" + tagName + "[" + by.toString().substring(by.toString().indexOf(":") + 1).trim() + "]");
        }
        if (by.getClass().getName().equals("org.openqa.selenium.By$ByClassName")) {
            by = composite(tagName(tagName), by);
        }
        return by;
    }

    protected final void assertTagIs(String is, String shouldBe) {
        if (!is.equals(shouldBe)) {
            throw new AssertionError("tag was incorrect, should have been " + shouldBe + " but was " + is);
        }
    }

    protected abstract WebElement findIt(By by);

    protected abstract List<WebElement> findThem(By by);

    private <T> T single(final By by, final String tagName, Class<T> resultingClass) {
        final By by2 = fixupBy(by, tagName);
        String ctx = contextualize(by.toString(), tagName);
        final WebElement result;
        try {
            changeTimeout();
            result = decorateExecution(new Execution<WebElement>() {
                public WebElement execute() {
                    WebElement it = findIt(by2);
                    assertTagIs(it.getTagName(), tagName);
                    return it;
                }
            }, ctx);
        } finally {
            resetTimeout();
        }
        return getFluentWebElement(result, ctx, resultingClass);
    }

    private String contextualize(String by, String tagName) {
        if (by.equals("By.tagName: " + tagName)) {
            by = "";
        }
        return context + "." + tagName + "(" + by + ")";
    }

    private FluentWebElements multiple(By by, final String tagName) {
        final By by2 = fixupBy(by, tagName);
        String ctx = context + "." + tagName + "s(" + by + ")";

        final List<WebElement> result = decorateExecution(new Execution<List<WebElement>>() {
            public List<WebElement> execute() {
                List<WebElement> results = findThem(by2);
                for (WebElement webElement : results) {
                    assertTagIs(webElement.getTagName(), tagName);
                }
                return results;
            }
        }, ctx);
        return getFluentWebElements(result, ctx);
    }

    protected static RuntimeException decorateRuntimeException(String ctx, RuntimeException e) {
        return new FluentExecutionStopped("RuntimeException during invocation of: " + ctx, e);
    }
    protected static RuntimeException decorateAssertionError(String ctx, AssertionError e) {
        return  new FluentExecutionStopped("AssertionError during invocation of: " + ctx, e);
    }

    protected <T> T decorateExecution(Execution<T> execution, String ctx) {
        try {
            return execution.execute();
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    protected void changeTimeout() {
    }

    protected void resetTimeout() {
    }

    public abstract BaseFluentWebDriver within(Period p);


}
