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
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.seleniumhq.selenium.fluent.FluentBy.composite;
import static org.seleniumhq.selenium.fluent.Internal.BaseFluentWebDriver.decorateAssertionError;
import static org.seleniumhq.selenium.fluent.Internal.BaseFluentWebDriver.decorateRuntimeException;

public class Internal {

    public abstract static class BaseFluentWebDriver {


        protected final WebDriver delegate;
        protected final Context context;
        protected final Monitor monitor;

        protected BaseFluentWebDriver(WebDriver delegate, Context context, Monitor monitor) {
            this.delegate = delegate;
            this.context = context;
            this.monitor = monitor;
        }

        protected SearchContext getSearchContext() {
            return delegate;
        }

        protected BaseFluentWebElement span() {
            SingleResult single = single(tagName("span"), "span");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement span(By by) {
            SingleResult single = single(by, "span");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements spans() {
            return newFluentWebElements(multiple(tagName("span"), "span"));
        }

        protected BaseFluentWebElements newFluentWebElements(MultipleResult multiple) {
            List<WebElement> result = multiple.getResult();
            Context ctx = multiple.getCtx();
            List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
            for (WebElement aResult : result) {
                elems.add(new FluentWebElement(delegate, new WebElementHolder(null, aResult, null), ctx, monitor));
            }
            return new FluentWebElements(delegate, elems, ctx, monitor);
        }

        private FluentSelects newFluentSelects(List<WebElement> result, Context ctx) {
            List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
            for (WebElement aResult : result) {
                elems.add(new FluentSelect(delegate, aResult, ctx, monitor));
            }
            return new FluentSelects(delegate, elems, ctx, monitor);
        }

        protected BaseFluentWebElements spans(By by) {
            return newFluentWebElements(multiple(by, "span"));
        }

        protected BaseFluentWebElement div() {
            SingleResult single = single(tagName("div"), "div");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement div(By by) {
            SingleResult single = single(by, "div");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements divs() {
            return newFluentWebElements(multiple(tagName("div"), "div"));
        }

        protected BaseFluentWebElements divs(By by) {
            return newFluentWebElements(multiple(by, "div"));
        }

        protected BaseFluentWebElement button() {
            SingleResult single = single(tagName("button"), "button");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement button(By by) {
            SingleResult single = single(by, "button");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements buttons() {
            return newFluentWebElements(multiple(tagName("button"), "button"));
        }

        protected BaseFluentWebElements buttons(By by) {
            return newFluentWebElements(multiple(by, "button"));
        }

        protected BaseFluentWebElement link() {
            SingleResult single = single(tagName("a"), "a");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement link(By by) {
            SingleResult single = single(by, "a");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements links() {
            return newFluentWebElements(multiple(tagName("a"), "a"));
        }

        protected BaseFluentWebElements links(By by) {
            return newFluentWebElements(multiple(by, "a"));
        }

        protected BaseFluentWebElement input() {
            SingleResult single = single(tagName("input"), "input");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement input(By by) {
            SingleResult single = single(by, "input");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements inputs() {
            return newFluentWebElements(multiple(tagName("input"), "input"));
        }

        protected BaseFluentWebElements inputs(By by) {
            return newFluentWebElements(multiple(by, "input"));
        }

        public FluentSelect select() {
            SingleResult single = single(tagName("select"), "select");
            return new FluentSelect(delegate, single.getResult().getFound(), single.getCtx(), monitor);
        }

        public FluentSelect select(By by) {
            SingleResult single = single(by, "select");
            return new FluentSelect(delegate, single.getResult().getFound(), single.getCtx(), monitor);
        }

        public FluentSelects selects() {
            MultipleResult multiple = multiple(tagName("select"), "select");
            return newFluentSelects(multiple.getResult(), multiple.getCtx());
        }

        public FluentSelects selects(By by) {
            MultipleResult multiple = multiple(by, "select");
            return newFluentSelects(multiple.getResult(), multiple.getCtx());
        }

        protected BaseFluentWebElement h1() {
            SingleResult single = single(tagName("h1"), "h1");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement h1(By by) {
            SingleResult single = single(by, "h1");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h1s() {
            return newFluentWebElements(multiple(tagName("h1"), "h1"));
        }

        protected BaseFluentWebElements h1s(By by) {
            return newFluentWebElements(multiple(by, "h1"));
        }

        protected BaseFluentWebElement h2() {
            SingleResult single = single(tagName("h2"), "h2");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement h2(By by) {
            SingleResult single = single(by, "h2");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h2s() {
            return newFluentWebElements(multiple(tagName("h2"), "h2"));
        }

        protected BaseFluentWebElements h2s(By by) {
            return newFluentWebElements(multiple(by, "h2"));
        }

        protected BaseFluentWebElement h3() {
            SingleResult single = single(tagName("h3"), "h3");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h3s() {
            return newFluentWebElements(multiple(tagName("h3"), "h3"));
        }

        protected BaseFluentWebElement h3(By by) {
            SingleResult single = single(by, "h3");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h3s(By by) {
            return newFluentWebElements(multiple(by, "h3"));
        }

        protected BaseFluentWebElement h4(){
            SingleResult single = single(tagName("h4"), "h4");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h4s() {
            return newFluentWebElements(multiple(tagName("h4"), "h4"));
        }

        protected BaseFluentWebElement h4(By by) {
            SingleResult single = single(by, "h4");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h4s(By by) {
            return newFluentWebElements(multiple(by, "h4"));
        }

        protected BaseFluentWebElement p() {
            SingleResult single = single(tagName("p"), "p");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ps() {
            return newFluentWebElements(multiple(tagName("p"), "p"));
        }

        protected BaseFluentWebElement p(By by) {
            SingleResult single = single(by, "p");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ps(By by) {
            return newFluentWebElements(multiple(by, "p"));
        }

        protected BaseFluentWebElement img() {
            SingleResult single = single(tagName("img"), "img");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements imgs() {
            return newFluentWebElements(multiple(tagName("img"), "img"));
        }

        protected BaseFluentWebElement img(By by) {
            SingleResult single = single(by, "img");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements imgs(By by) {
            return newFluentWebElements(multiple(by, "img"));
        }

        protected BaseFluentWebElement table() {
            SingleResult single = single(tagName("table"), "table");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements tables() {
            return newFluentWebElements(multiple(tagName("table"), "table"));
        }

        protected BaseFluentWebElement table(By by) {
            SingleResult single = single(by, "table");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements tables(By by) {
            return newFluentWebElements(multiple(by, "table"));
        }

        protected BaseFluentWebElement fieldset() {
            SingleResult single = single(tagName("fieldset"), "fieldset");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements fieldsets() {
            return newFluentWebElements(multiple(tagName("fieldset"), "fieldset"));
        }

        protected BaseFluentWebElement fieldset(By by) {
            SingleResult single = single(by, "fieldset");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements fieldsets(By by) {
            return newFluentWebElements(multiple(by, "fieldset"));
        }

        protected BaseFluentWebElement legend() {
            SingleResult single = single(tagName("legend"), "legend");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements legends() {
            return newFluentWebElements(multiple(tagName("legend"), "legend"));
        }

        protected BaseFluentWebElement legend(By by) {
            SingleResult single = single(by, "legend");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements legends(By by) {
            return newFluentWebElements(multiple(by, "legend"));
        }

        protected BaseFluentWebElement tr() {
            SingleResult single = single(tagName("tr"), "tr");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements trs() {
            return newFluentWebElements(multiple(tagName("tr"), "tr"));
        }

        protected BaseFluentWebElement tr(By by) {
            SingleResult single = single(by, "tr");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements trs(By by) {
            return newFluentWebElements(multiple(by, "tr"));
        }

        protected BaseFluentWebElement td() {
            SingleResult single = single(tagName("td"), "td");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements tds() {
            return newFluentWebElements(multiple(tagName("td"), "td"));
        }

        protected BaseFluentWebElement td(By by) {
            SingleResult single = single(by, "td");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements tds(By by) {
            return newFluentWebElements(multiple(by, "td"));
        }

        protected BaseFluentWebElement th() {
            SingleResult single = single(tagName("th"), "th");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ths() {
            return newFluentWebElements(multiple(tagName("th"), "th"));
        }

        protected BaseFluentWebElement th(By by) {
            SingleResult single = single(by, "th");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ths(By by) {
            return newFluentWebElements(multiple(by, "th"));
        }

        protected BaseFluentWebElement ul() {
            SingleResult single = single(tagName("ul"), "ul");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements uls() {
            return newFluentWebElements(multiple(tagName("ul"), "ul"));
        }

        protected BaseFluentWebElement ul(By by) {
            SingleResult single = single(by, "ul");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements uls(By by) {
            return newFluentWebElements(multiple(by, "ul"));
        }

        protected BaseFluentWebElement ol() {
            SingleResult single = single(tagName("ol"), "ol");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ols() {
            return newFluentWebElements(multiple(tagName("ol"), "ol"));
        }

        protected BaseFluentWebElement ol(By by) {
            SingleResult single = single(by, "ol");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ols(By by) {
            return newFluentWebElements(multiple(by, "ol"));
        }

        protected BaseFluentWebElement form() {
            SingleResult single = single(tagName("form"), "form");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements forms() {
            return newFluentWebElements(multiple(tagName("form"), "form"));
        }

        protected BaseFluentWebElement form(By by) {
            SingleResult single = single(by, "form");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements forms(By by) {
            return newFluentWebElements(multiple(by, "form"));
        }

        protected BaseFluentWebElement textarea() {
            SingleResult single = single(tagName("textarea"), "textarea");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements textareas() {
            return newFluentWebElements(multiple(tagName("textarea"), "textarea"));
        }

        protected BaseFluentWebElement textarea(By by) {
            SingleResult single = single(by, "textarea");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements textareas(By by) {
            return newFluentWebElements(multiple(by, "textarea"));
        }

        protected BaseFluentWebElement option() {
            SingleResult single = single(tagName("option"), "option");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements options() {
            return newFluentWebElements(multiple(tagName("option"), "option"));
        }

        protected BaseFluentWebElement option(By by) {
            SingleResult single = single(by, "option");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements options(By by) {
            return newFluentWebElements(multiple(by, "option"));
        }

        protected BaseFluentWebElement li() {
            SingleResult single = single(tagName("li"), "li");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement li(By by) {
            SingleResult single = single(by, "li");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements lis() {
            return newFluentWebElements(multiple(tagName("li"), "li"));
        }

        protected BaseFluentWebElements lis(By by) {
            return newFluentWebElements(multiple(by, "li"));
        }

        protected BaseFluentWebElement map() {
            SingleResult single = single(tagName("map"), "map");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements maps() {
            return newFluentWebElements(multiple(tagName("map"), "map"));
        }

        protected BaseFluentWebElement map(By by) {
            SingleResult single = single(by, "map");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements maps(By by) {
            return newFluentWebElements(multiple(by, "map"));
        }

        protected BaseFluentWebElement newFluentWebElement(WebDriver delegate, WebElementHolder result, Context context) {
            return new FluentWebElement(delegate, result, context, monitor);
        }

        public TestableString url() {
            Execution<String> execution = new CurrentUrl();
            Context ctx = Context.singular(context, "url");
            return new TestableString(execution, ctx).within(getPeriod());
        }

        protected Period getPeriod() {
            return null;
        }

        public TestableString title() {
            Execution<String> execution = new GetTitle();
            Context ctx = Context.singular(context, "title");
            return new TestableString(execution, ctx).within(getPeriod());
        }

        protected abstract FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor);

        protected final By fixupBy(By by, String tagName) {
            if (by.getClass().getName().equals("org.openqa.selenium.By$ByXPath")) {
                by = xpath(".//" + tagName + "[" + by.toString().substring(by.toString().indexOf(":") + 1).trim() + "]");
            }
            if (by.getClass().getName().equals("org.openqa.selenium.By$ByClassName")) {
                by = composite(new By.ByTagName(tagName), (By.ByClassName) by);
            }
            return by;
        }

        protected final void assertTagIs(String is, String shouldBe) {
            if (!is.equals(shouldBe)) {
                throw new AssertionError("tag was incorrect, should have been " + shouldBe + " but was " + is);
            }
        }

        protected abstract WebElement findIt(By by, Context ctx);

        protected abstract List<WebElement> findThem(By by, Context ctx);

        private SingleResult single(final By by, final String tagName) {
            final By by2 = fixupBy(by, tagName);
            Context ctx = contextualize(by, tagName);
            final WebElementHolder result;
            try {
                changeTimeout();
                FindIt execution = new FindIt(by2, tagName, ctx);
                WebElement found = decorateExecution(execution, ctx);
                result = new WebElementHolder(getSearchContext(), found, by2);
            } finally {
                resetTimeout();
            }
            return new SingleResult(result, ctx);
        }

        public static class WebElementHolder {
            private final SearchContext parent;
            protected WebElement foundElement;
            private final By locator;

            public WebElementHolder(SearchContext parent, WebElement found, By locator) {
                this.parent = parent;
                this.foundElement = found;
                this.locator = locator;
            }

            public WebElement getFound() {
                return foundElement;
            }

            public void reFindElement() {
                if (parent != null) {
                    foundElement = parent.findElement(locator);
                }
            }
        }

        public static class SingleResult {
            private final WebElementHolder result;
            private final Context ctx;
            public SingleResult(WebElementHolder result, Context ctx) {
                this.result = result;
                this.ctx = ctx;
            }

            public WebElementHolder getResult() {
                return result;
            }

            public Context getCtx() {
                return ctx;
            }
        }

        private class FindIt extends Execution<WebElement> {
            private final By by2;
            private final String tagName;
            private final Context ctx;

            public FindIt(By by2, String tagName, Context ctx) {
                this.by2 = by2;
                this.tagName = tagName;
                this.ctx = ctx;
            }

            public WebElement execute() {
                WebElement it = findIt(by2, ctx);
                assertTagIs(it.getTagName(), tagName);
                return it;
            }
        }

        private Context contextualize(By by, String tagName) {
            if (by.toString().equals("By.tagName: " + tagName)) {
                by = null;
            }
            return Context.singular(context, tagName, by);
        }

        private MultipleResult multiple(By by, final String tagName) {
            final By by2 = fixupBy(by, tagName);
            final List<WebElement> result;
            Context ctx = Context.plural(context, tagName, by);
            try {
                changeTimeout();
                FindThem execution = new FindThem(by2, tagName, ctx);
                result = decorateExecution(execution, ctx);
            } finally {
                resetTimeout();
            }
            return new MultipleResult(result, ctx);
        }

        public static class MultipleResult {
            private final List<WebElement> result;
            private final Context ctx;
            public MultipleResult(List<WebElement> result, Context ctx) {
                this.result = result;
                this.ctx = ctx;
            }

            public List<WebElement> getResult() {
                return result;
            }

            public Context getCtx() {
                return ctx;
            }
        }

        private class FindThem extends Execution<List<WebElement>> {
            private final By by2;
            private final String tagName;
            private final Context ctx;

            public FindThem(By by2, String tagName, Context ctx) {
                this.by2 = by2;
                this.tagName = tagName;
                this.ctx = ctx;
            }

            public List<WebElement> execute() {
                List<WebElement> results = findThem(by2, ctx);
                for (WebElement webElement : results) {
                    assertTagIs(webElement.getTagName(), tagName);
                }
                return results;
            }
        }

        protected static RuntimeException decorateRuntimeException(Context ctx, RuntimeException e) {
            FluentExecutionStopped rv = null;
            if (e instanceof StaleElementReferenceException) {
                rv =  new FluentExecutionStopped.BecauseOfStaleElement(replacePkgNames(e) + ctx, e);
            } else if (e instanceof NothingMatches) {
                rv = new FluentExecutionStopped.BecauseNothingMatchesInFilter("Nothing matched filter, during invocation of: " + ctx);
            } else {
                rv = new FluentExecutionStopped(replacePkgNames(e) + ctx, e);
            }
            return rv;
        }

        private static String replacePkgNames(Throwable e) {
            return e.getClass().getName()
                    .replace("java.lang.", "")
                    .replace("org.openqa.selenium.", "")
                    + " during invocation of: ";
        }

        protected static RuntimeException decorateAssertionError(Context ctx, AssertionError e) {
            FluentExecutionStopped rv = new FluentExecutionStopped(replacePkgNames(e) + ctx, e);
            return rv;
        }

        protected <T> T decorateExecution(Execution<T> execution, Context ctx) {

            Monitor.Timer timer = monitor.start(ctx.toString().substring(2));
            try {
                return execution.doExecution();
            } catch (UnsupportedOperationException e) {
                throw e;
            } catch (RuntimeException e) {
                throw decorateRuntimeException(ctx, e);
            } catch (AssertionError e) {
                throw decorateAssertionError(ctx, e);
            } finally {
                timer.end();
            }
        }

        protected void changeTimeout() {
        }

        protected void resetTimeout() {
        }

        protected final WebElement retryingFindIt(By by) {
            long endMillis = getPeriod().getEndMillis(System.currentTimeMillis());
            RuntimeException exceptionCausingRetry = null;
            boolean toRetry = true;
            WebElement it = null;
            while (toRetry && endMillis - System.currentTimeMillis() > 0) {
                try {
                    it = actualFindIt(by, context);
                    toRetry = false;
                    return it;
                } catch (WebDriverException e) {
                    exceptionCausingRetry = e;
                }
            }
            if (toRetry) {
                throw exceptionCausingRetry;
            }
            return it;
        }

        protected final List<WebElement> retryingFindThem(By by) {
            long endMillis = getPeriod().getEndMillis(System.currentTimeMillis());
            RuntimeException exceptionCausingRetry = null;
            boolean toRetry = true;
            List<WebElement> them = null;
            while (toRetry && endMillis - System.currentTimeMillis() > 0) {
                try {
                    them = actualFindThem(by, context);
                    toRetry = false;
                    return them;
                } catch (WebDriverException e) {
                    exceptionCausingRetry = e;
                }
            }
            if (toRetry) {
                throw exceptionCausingRetry;
            }
            return them;
        }

        protected abstract WebElement actualFindIt(By by, Context ctx);

        protected abstract List<WebElement> actualFindThem(By by, Context ctx);

        private class CurrentUrl extends Execution<String> {
            public String execute() {
                return delegate.getCurrentUrl();
            }
        }

        private class GetTitle extends Execution<String> {
            public String execute() {
                return delegate.getTitle();
            }
        }
    }

    public abstract static class BaseFluentWebElement extends BaseFluentWebDriver {

        public BaseFluentWebElement(WebDriver delegate, Context context, Monitor monitor) {
            super(delegate, context, monitor);
        }

        @Override
        protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor1) {
            return new FluentWebElements(super.delegate, results, context, monitor);
        }

        protected String charSeqArrayAsHumanString(CharSequence[] keysToSend) {
            String keys = "";
            for (CharSequence charSequence : keysToSend) {
                keys = keys + ", " + charSequence;
            }
            return keys.substring(2);  // delete comma-space prefix
        }


    }

    public abstract static class BaseFluentWebElements extends BaseFluentWebElement implements List<FluentWebElement> {
        protected BaseFluentWebElements(WebDriver delegate, Context context, Monitor monitor) {
            super(delegate, context, monitor);
        }

        public final FluentWebElement set(int i, FluentWebElement fwe) {
            throw new UnsupportedOperationException();
        }

        public final void add(int index, FluentWebElement element) {
            throw new UnsupportedOperationException();
        }

        public final FluentWebElement remove(int index) {
            throw new UnsupportedOperationException();
        }

        public final void clear() {
            throw new UnsupportedOperationException();
        }

        public final boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public final boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection<? extends FluentWebElement> c) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(int index, Collection<? extends FluentWebElement> c) {
            throw new UnsupportedOperationException();
        }

        public final boolean add(FluentWebElement fluentWebElement) {
            throw new UnsupportedOperationException();
        }
    }

    public abstract static class BaseTestableObject<T> {

        protected final Period within;
        protected final Execution<T> execution;
        protected final Context context;
        protected T is;


        public BaseTestableObject(Period within, Execution<T> execution, Context context) {
            this.within = within;
            this.execution = execution;
            this.context = context;
        }

        protected long calcEndMillis() {
            return within.getEndMillis(System.currentTimeMillis());
        }

        protected void validateWrapRethrow(Validation validation, Context ctx) {
            try {
                validation.validate(System.currentTimeMillis());
            } catch (UnsupportedOperationException e) {
                throw e;
            } catch (RuntimeException e) {
                throw decorateRuntimeException(ctx, e);
            } catch (AssertionError e) {
                throw decorateAssertionError(ctx, e);
            }
        }

        protected void assignValueIfNeeded() {
            if (is != null) {
                return;
            }
            is = execution.doExecution();
        }

        protected String durationIfNotZero(long start) {
            long duration = System.currentTimeMillis() - start;
            if (duration > 0 ) {
                return "(after " + duration + " ms)";
            } else {
                return "";
            }
        }

        public void baseShouldBe(final T shouldBe) {
            Context ctx = Context.singular(context, "shouldBe", null, shouldBe);

            validateWrapRethrow(new Internal.Validation() {
                @Override
                public void validate(long start) {
                    if (!shouldBe.equals(is)) {
                        if (within != null) {
                            boolean passed;
                            long endMillis = calcEndMillis();
                            do {
                                is = execution.doExecution();
                                passed = is != null && is.equals(shouldBe);
                            } while (System.currentTimeMillis() < endMillis && !passed);
                        } else {
                            assignValueIfNeeded();
                        }
                    }
                    assertThat(durationIfNotZero(start), (T) is, equalTo(shouldBe));
                }
            }, ctx);
        }

        public void baseShouldNotBe(final T shouldNotBe) {
            Context ctx = Context.singular(context, "shouldNotBe", null, shouldNotBe);
            validateWrapRethrow(new Internal.Validation() {
                @Override
                public void validate(long start) {
                    assignValueIfNeeded();
                    if (shouldNotBe.equals(is) && within != null) {
                        boolean passed;
                        long endMillis = calcEndMillis();
                        do {
                            is = execution.doExecution();
                            passed = is != null && !is.equals(shouldNotBe);
                        } while (System.currentTimeMillis() < endMillis && !passed);
                    }
                    assertThat(durationIfNotZero(start), (T) is, not(equalTo(shouldNotBe)));
                }
            }, ctx);

        }
    }

    public abstract static class Validation {
        public abstract void validate(long start);
    }

    public static class NothingMatches extends RuntimeException {
    }
}
