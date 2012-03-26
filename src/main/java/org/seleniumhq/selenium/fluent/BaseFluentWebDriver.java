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
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.seleniumhq.selenium.fluent.FluentBy.composite;

public abstract class BaseFluentWebDriver implements FluentWebDriver {

    private static final RuntimeException AN_EXCEPTION = new RuntimeException();

    protected final WebDriver delegate;
    protected final Context context;

    public BaseFluentWebDriver(WebDriver delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    public FluentWebElement span() {
        SingleResult single = single(tagName("span"), "span");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement span(By by) {
        SingleResult single = single(by, "span");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements spans() {
        MultipleResult multiple = multiple(tagName("span"), "span");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    private FluentWebElements newFluentWebElements(List<WebElement> result, Context ctx) {
        List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
        for (WebElement aResult : result) {
            elems.add(new FluentWebElement(delegate, aResult, ctx));
        }
        return new FluentWebElements(delegate, elems, ctx);
    }

    private FluentSelects newFluentSelects(List<WebElement> result, Context ctx) {
        List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
        for (WebElement aResult : result) {
            elems.add(new FluentSelect(delegate, aResult, ctx));
        }
        return new FluentSelects(delegate, elems, ctx);
    }

    public FluentWebElements spans(By by) {
        MultipleResult multiple = multiple(by, "span");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement div() {
        SingleResult single = single(tagName("div"), "div");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement div(By by) {
        SingleResult single = single(by, "div");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements divs() {
        MultipleResult multiple = multiple(tagName("div"), "div");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements divs(By by) {
        MultipleResult multiple = multiple(by, "div");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement button() {
        SingleResult single = single(tagName("button"), "button");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement button(By by) {
        SingleResult single = single(by, "button");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements buttons() {
        MultipleResult multiple = multiple(tagName("button"), "button");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements buttons(By by) {
        MultipleResult multiple = multiple(by, "button");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement link() {
        SingleResult single = single(tagName("a"), "a");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement link(By by) {
        SingleResult single = single(by, "a");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements links() {
        MultipleResult multiple = multiple(tagName("a"), "a");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements links(By by) {
        MultipleResult multiple = multiple(by, "a");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement input() {
        SingleResult single = single(tagName("input"), "input");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement input(By by) {
        SingleResult single = single(by, "input");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements inputs() {
        MultipleResult multiple = multiple(tagName("input"), "input");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements inputs(By by) {
        MultipleResult multiple = multiple(by, "input");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentSelect select() {
        SingleResult single = single(tagName("select"), "select");
        return new FluentSelect(delegate, single.getResult(), single.getCtx());
    }

    public FluentSelect select(By by) {
        SingleResult single = single(by, "select");
        return new FluentSelect(delegate, single.getResult(), single.getCtx());
    }

    public FluentSelects selects() {
        MultipleResult multiple = multiple(tagName("select"), "select");
        return newFluentSelects(multiple.getResult(), multiple.getCtx());
    }

    public FluentSelects selects(By by) {
        MultipleResult multiple = multiple(by, "select");
        return newFluentSelects(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement h1() {
        SingleResult single = single(tagName("h1"), "h1");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement h1(By by) {
        SingleResult single = single(by, "h1");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements h1s() {
        MultipleResult multiple = multiple(tagName("h1"), "h1");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements h1s(By by) {
        MultipleResult multiple = multiple(by, "h1");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement h2() {
        SingleResult single = single(tagName("h2"), "h2");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement h2(By by) {
        SingleResult single = single(by, "h2");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements h2s() {
        MultipleResult multiple = multiple(tagName("h2"), "h2");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements h2s(By by) {
        MultipleResult multiple = multiple(by, "h2");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement h3() {
        SingleResult single = single(tagName("h3"), "h3");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements h3s() {
        MultipleResult multiple = multiple(tagName("h3"), "h3");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement h3(By by) {
        SingleResult single = single(by, "h3");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements h3s(By by) {
        MultipleResult multiple = multiple(by, "h3");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement h4(){
        SingleResult single = single(tagName("h4"), "h4");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements h4s() {
        MultipleResult multiple = multiple(tagName("h4"), "h4");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement h4(By by) {
        SingleResult single = single(by, "h4");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements h4s(By by) {
        MultipleResult multiple = multiple(by, "h4");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement p() {
        SingleResult single = single(tagName("p"), "p");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements ps() {
        MultipleResult multiple = multiple(tagName("p"), "p");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement p(By by) {
        SingleResult single = single(by, "p");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements ps(By by) {
        MultipleResult multiple = multiple(by, "p");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement img() {
        SingleResult single = single(tagName("img"), "img");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements imgs() {
        MultipleResult multiple = multiple(tagName("img"), "img");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement img(By by) {
        SingleResult single = single(by, "img");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements imgs(By by) {
        MultipleResult multiple = multiple(by, "img");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement table() {
        SingleResult single = single(tagName("table"), "table");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements tables() {
        MultipleResult multiple = multiple(tagName("table"), "table");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement table(By by) {
        SingleResult single = single(by, "table");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements tables(By by) {
        MultipleResult multiple = multiple(by, "table");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement fieldset() {
        SingleResult single = single(tagName("fieldset"), "fieldset");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements fieldsets() {
        MultipleResult multiple = multiple(tagName("fieldset"), "fieldset");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement fieldset(By by) {
        SingleResult single = single(by, "fieldset");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements fieldsets(By by) {
        MultipleResult multiple = multiple(by, "fieldset");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement legend() {
        SingleResult single = single(tagName("legend"), "legend");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements legends() {
        MultipleResult multiple = multiple(tagName("legend"), "legend");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement legend(By by) {
        SingleResult single = single(by, "legend");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements legends(By by) {
        MultipleResult multiple = multiple(by, "legend");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement tr() {
        SingleResult single = single(tagName("tr"), "tr");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements trs() {
        MultipleResult multiple = multiple(tagName("tr"), "tr");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement tr(By by) {
        SingleResult single = single(by, "tr");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements trs(By by) {
        MultipleResult multiple = multiple(by, "tr");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement td() {
        SingleResult single = single(tagName("td"), "td");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements tds() {
        MultipleResult multiple = multiple(tagName("td"), "td");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement td(By by) {
        SingleResult single = single(by, "td");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements tds(By by) {
        MultipleResult multiple = multiple(by, "td");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement th() {
        SingleResult single = single(tagName("th"), "th");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements ths() {
        MultipleResult multiple = multiple(tagName("th"), "th");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement th(By by) {
        SingleResult single = single(by, "th");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements ths(By by) {
        MultipleResult multiple = multiple(by, "th");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement ul() {
        SingleResult single = single(tagName("ul"), "ul");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements uls() {
        MultipleResult multiple = multiple(tagName("ul"), "ul");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement ul(By by) {
        SingleResult single = single(by, "ul");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements uls(By by) {
        MultipleResult multiple = multiple(by, "ul");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement ol() {
        SingleResult single = single(tagName("ol"), "ol");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements ols() {
        MultipleResult multiple = multiple(tagName("ol"), "ol");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement ol(By by) {
        SingleResult single = single(by, "ol");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements ols(By by) {
        MultipleResult multiple = multiple(by, "ol");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement form() {
        SingleResult single = single(tagName("form"), "form");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements forms() {
        MultipleResult multiple = multiple(tagName("form"), "form");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement form(By by) {
        SingleResult single = single(by, "form");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements forms(By by) {
        MultipleResult multiple = multiple(by, "form");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement textarea() {
        SingleResult single = single(tagName("textarea"), "textarea");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements textareas() {
        MultipleResult multiple = multiple(tagName("textarea"), "textarea");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement textarea(By by) {
        SingleResult single = single(by, "textarea");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements textareas(By by) {
        MultipleResult multiple = multiple(by, "textarea");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement option() {
        SingleResult single = single(tagName("option"), "option");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements options() {
        MultipleResult multiple = multiple(tagName("option"), "option");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement option(By by) {
        SingleResult single = single(by, "option");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements options(By by) {
        MultipleResult multiple = multiple(by, "option");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement li() {
        SingleResult single = single(tagName("li"), "li");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElement li(By by) {
        SingleResult single = single(by, "li");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements lis() {
        MultipleResult multiple = multiple(tagName("li"), "li");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElements lis(By by) {
        MultipleResult multiple = multiple(by, "li");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement map() {
        SingleResult single = single(tagName("map"), "map");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements maps() {
        MultipleResult multiple = multiple(tagName("map"), "map");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public FluentWebElement map(By by) {
        SingleResult single = single(by, "map");
        return new FluentWebElement(delegate, single.getResult(), single.getCtx());
    }

    public FluentWebElements maps(By by) {
        MultipleResult multiple = multiple(by, "map");
        return newFluentWebElements(multiple.getResult(), multiple.getCtx());
    }

    public TestableString url() {
        Execution<String> execution = new Execution<String>() {
            public String execute() {
                return delegate.getCurrentUrl();
            }
        };
        Context ctx = Context.singular(context, "url");
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
        Context ctx = Context.singular(context, "title");
        return new TestableString(getPeriod(), execution, ctx);
    }

    protected abstract FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context);

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

    private SingleResult single(final By by, final String tagName) {
        final By by2 = fixupBy(by, tagName);
        Context ctx = contextualize(by, tagName);
        final WebElement result;
        try {
            changeTimeout();
            FindIt execution = new FindIt(by2, tagName);
            result = decorateExecution(execution, ctx);
        } finally {
            resetTimeout();
        }
        return new SingleResult(result, ctx);
    }
    
    public static class Context implements Iterable<ContextElem> {
        
        private List<ContextElem> elems = new ArrayList<ContextElem>();

        public Iterator<ContextElem> iterator() {
            return Collections.unmodifiableList(elems).iterator();
        }

        public static Context singular(Context previous, String tagName, By by) {
            return make(previous, tagName, by, null);
        }

        private static Context make(Context previous, String tagName, By by, Object arg) {
            Context ctx = new Context();
            if (previous != null) {
                ctx.elems.addAll(previous.elems);
            }
            ctx.elems.add(new ContextElem(tagName, by, arg));
            return ctx;
        }
        
        public static Context plural(Context previous, String tagName, By by) {
            return make(previous, tagName + "s", by, null);
        }

        public static Context singular(Context previous, String tagName) {
            return make(previous, tagName, null, null);
        }

        public static Context singular(Context context, String tagName, Object arg) {
            return make(context, tagName, null, arg);
        }

        public static Context singular(Context previous, String tagName, By by, Object arg) {
            return make(previous, tagName, by, arg);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("?");
            for (ContextElem elem : elems) {
                sb.append(elem.toString());
            }
            return sb.toString();
        }
    }
    
    public static class ContextElem {
        private final String tagName;
        private final By by;
        private final Object arg;

        public ContextElem(String tagName, By by, Object arg) {
            this.tagName = tagName;
            this.by = by;
            this.arg = arg;
        }

        @Override
        public String toString() {
            StringBuilder sb =
                    new StringBuilder(".")
                            .append(tagName);
            if (by == null && arg == null) {
                return sb.append("()").toString();
            }
            if (by != null) {
                return sb.append("(").append(by).append(")").toString();

            }
            String quote = "'";
            if (arg instanceof Number || arg instanceof Period || arg instanceof FluentMatcher) {
                quote = "";
            }
            return sb.append("(").append(quote).append(arg).append(quote).append(")").toString();
        }
    }
    
    public static class SingleResult {
        private final WebElement result;
        private final Context ctx;
        public SingleResult(WebElement result, Context ctx) {
            this.result = result;
            this.ctx = ctx;
        }

        public WebElement getResult() {
            return result;
        }

        public Context getCtx() {
            return ctx;
        }
    }

    private class FindIt implements Execution<WebElement> {
        private final By by2;
        private final String tagName;

        public FindIt(By by2, String tagName) {
            this.by2 = by2;
            this.tagName = tagName;
        }

        public WebElement execute() {
            WebElement it = findIt(by2);
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
            FindThem execution = new FindThem(by2, tagName);
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


    protected List<SingleResult> listOfSingleResults(List<WebElement> result, Context context) {
        List<SingleResult> fluents = new ArrayList<SingleResult>();
        for (WebElement aResult : result) {
            fluents.add(new SingleResult(aResult, context));
        }
        return fluents;
    }

    private class FindThem implements Execution<List<WebElement>> {
        private final By by2;
        private final String tagName;

        public FindThem(By by2, String tagName) {
            this.by2 = by2;
            this.tagName = tagName;
        }

        public List<WebElement> execute() {
            List<WebElement> results = findThem(by2);
            for (WebElement webElement : results) {
                assertTagIs(webElement.getTagName(), tagName);
            }
            return results;
        }
    }


    protected static RuntimeException decorateRuntimeException(Context ctx, RuntimeException e) {
        return new FluentExecutionStopped(e.getClass().getName().replace("java.lang.", "") + " during invocation of: " + ctx, e);
    }
    protected static RuntimeException decorateAssertionError(Context ctx, AssertionError e) {
        return  new FluentExecutionStopped(e.getClass().getName().replace("java.lang.", "") + " during invocation of: " + ctx, e);
    }

    protected <T> T decorateExecution(Execution<T> execution, Context ctx) {
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

    protected final WebElement retryingFindIt(By by) {
        long endMillis = getPeriod().getEndMillis();
        RuntimeException exceptionCausingRetry = new RuntimeException();
        WebElement it = null;
        while (exceptionCausingRetry != null && endMillis - System.currentTimeMillis() > 0) {
            try {
                it = actualFindIt(by);
                exceptionCausingRetry = null;
                return it;
            } catch (WebDriverException e) {
                exceptionCausingRetry = e;
            }
        }
        if (exceptionCausingRetry != null) {
            throw exceptionCausingRetry;
        }
        return it;
    }

    protected final List<WebElement> retryingFindThem(By by) {
        long endMillis = getPeriod().getEndMillis();
        RuntimeException exceptionCausingRetry = AN_EXCEPTION;
        List<WebElement> them = null;
        while (exceptionCausingRetry != null && endMillis - System.currentTimeMillis() > 0) {
            try {
                them = actualFindThem(by);
                exceptionCausingRetry = null;
                return them;
            } catch (WebDriverException e) {
                exceptionCausingRetry = e;
            }
        }
        if (exceptionCausingRetry != null) {
            throw exceptionCausingRetry;
        }
        return them;
    }

    protected abstract WebElement actualFindIt(By by);

    protected abstract List<WebElement> actualFindThem(By by);

}
