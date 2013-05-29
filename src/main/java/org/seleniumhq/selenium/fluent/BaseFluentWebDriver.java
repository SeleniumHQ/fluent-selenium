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
import org.openqa.selenium.StaleElementReferenceException;
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

    protected BaseFluentWebDriver(WebDriver delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
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

    protected BaseFluentWebElement newFluentWebElement(WebDriver delegate, WebElement result, Context context) {
        return new FluentWebElement(delegate, result, context);
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

    protected abstract FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context);

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
        FluentExecutionStopped rv = null;
        if (e instanceof StaleElementReferenceException) {
            rv =  new FluentExecutionStopped.BecauseOfStaleElement(replacePkgNames(e) + ctx, e);
        } else if (e instanceof NothingMatches) {
            rv = new FluentExecutionStopped.BecauseNothingMatchesInFilter(replacePkgNames(e) + ctx);
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
        long start = System.currentTimeMillis();
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
        long endMillis = getPeriod().getEndMillis(System.currentTimeMillis());
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
        long endMillis = getPeriod().getEndMillis(System.currentTimeMillis());
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

    private class CurrentUrl implements Execution<String> {
        public String execute() {
            return delegate.getCurrentUrl();
        }
    }

    private class GetTitle implements Execution<String> {
        public String execute() {
            return delegate.getTitle();
        }
    }
}
