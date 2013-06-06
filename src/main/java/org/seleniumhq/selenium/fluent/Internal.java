package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;
import org.seleniumhq.selenium.fluent.internal.TestableString;
import org.seleniumhq.selenium.fluent.internal.WebElementValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.seleniumhq.selenium.fluent.FluentBy.composite;

public class Internal {

    public abstract static class BaseFluentWebDriver {

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

        public static RuntimeException decorateRuntimeException(Context ctx, RuntimeException e) {
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

        public static RuntimeException decorateAssertionError(Context ctx, AssertionError e) {
            FluentExecutionStopped rv = new FluentExecutionStopped(replacePkgNames(e) + ctx, e);
            return rv;
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

    public abstract static class BaseFluentWebElement extends BaseFluentWebDriver {

        public BaseFluentWebElement(WebDriver delegate, Context context) {
            super(delegate, context);
        }

        @Override
        protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context) {
            return new FluentWebElements(super.delegate, results, context);
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
        protected BaseFluentWebElements(WebDriver delegate, Context context) {
            super(delegate, context);
        }
    }

    public static class FluentWebElement extends BaseFluentWebElement {

        protected final WebElement currentElement;

        public FluentWebElement(WebDriver delegate, WebElement currentElement, Context context) {
            super(delegate, context);
            this.currentElement = currentElement;
        }

        protected WebElement getWebElement() {
            return currentElement;
        }

        @Override
        protected WebElement findIt(By by) {
            return actualFindIt(by);
        }

        @Override
        protected List<WebElement> findThem(By by) {
            return actualFindThem(by);
        }

        @Override
        protected WebElement actualFindIt(By by) {
            return currentElement.findElement(by);
        }

        @Override
        protected List<WebElement> actualFindThem(By by) {
            return currentElement.findElements(by);
        }

        public FluentWebElement click() {
            Context ctx = Context.singular(context, "click");
            decorateExecution(new Click(), ctx);
            return new FluentWebElement(delegate, currentElement, ctx);
        }

        /**
         *  Use this instead of clear() to clear an WebElement
         */

        public FluentWebElement clearField() {
            Context ctx = Context.singular(context, "clearField");
            decorateExecution(new Clear(), ctx);
            return new FluentWebElement(delegate, currentElement, ctx);
        }


        public FluentWebElement submit() {
            decorateExecution(new Submit(), Context.singular(context, "submit"));
            return new FluentWebElement(delegate, currentElement, context);
        }

        // These are as they would be in the WebElement API

        public FluentWebElement sendKeys(final CharSequence... keysToSend) {

            decorateExecution(new SendKeys(keysToSend), Context.singular(context, "sendKeys", null, charSeqArrayAsHumanString(keysToSend)));
            return new FluentWebElement(delegate, currentElement, context);
        }

        public TestableString getTagName() {
            return new TestableString(new GetTagName(), Context.singular(context, "getTagName"));
        }

        public WebElementValue<Boolean> isSelected() {
            Context isSelected = Context.singular(context, "isSelected");
            Boolean foo = decorateExecution(new IsSelected(), isSelected);
            return new WebElementValue<Boolean>(foo, isSelected);
        }

        public WebElementValue<Boolean> isEnabled() {
            Context isEnabled = Context.singular(context, "isEnabled");
            Boolean foo = decorateExecution(new IsEnabled(), isEnabled);
            return new WebElementValue<Boolean>(foo, isEnabled);
        }

        public WebElementValue<Boolean> isDisplayed() {
            Context isDisplayed = Context.singular(context, "isDisplayed");
            Boolean foo = decorateExecution(new IsDisplayed(), isDisplayed);
            return new WebElementValue<Boolean>(foo, isDisplayed);
        }

        public WebElementValue<Point> getLocation() {
            Context getLocation = Context.singular(context, "getLocation");
            Point foo = decorateExecution(new GetLocation(), getLocation);
            return new WebElementValue<Point>(foo, getLocation);
        }

        public WebElementValue<Dimension> getSize() {
            Context getSize = Context.singular(context, "getSize");
            Dimension foo = decorateExecution(new GetSize(), getSize);
            return new WebElementValue<Dimension>(foo, getSize);
        }

        public TestableString getCssValue(final String cssName) {
            return new TestableString(new GetCssValue(cssName), Context.singular(context, "getCssValue", null, cssName)).within(getPeriod());
        }

        public TestableString getAttribute(final String attr) {
            return new TestableString(new GetAttribute(attr), Context.singular(context, "getAttribute", null, attr)).within(getPeriod());
        }

        public TestableString getText() {
            return new TestableString(new GetText(), Context.singular(context, "getText")).within(getPeriod());
        }

        public FluentWebElement within(Period period) {
            return new RetryingFluentWebElement(delegate, currentElement, Context.singular(context, "within", null, period), period);
        }

        public NegatingFluentWebElement without(Period period) {
            return new NegatingFluentWebElement(delegate, currentElement, period, Context.singular(context, "without", null, period));
        }

        @Override
        public FluentWebElement span() {
            return (FluentWebElement) super.span();
        }

        @Override
        public FluentWebElement span(By by) {
            return (FluentWebElement) super.span(by);
        }

        @Override
        public FluentWebElements spans() {
            return (FluentWebElements) super.spans();
        }

        @Override
        public FluentWebElements spans(By by) {
            return (FluentWebElements) super.spans(by);
        }

        @Override
        public FluentWebElement div() {
            return (FluentWebElement) super.div();
        }

        @Override
        public FluentWebElement div(By by) {
            return (FluentWebElement) super.div(by);
        }

        @Override
        public FluentWebElements divs() {
            return (FluentWebElements) super.divs();
        }

        @Override
        public FluentWebElements divs(By by) {
            return (FluentWebElements) super.divs(by);
        }

        @Override
        public FluentWebElement button() {
            return (FluentWebElement) super.button();
        }

        @Override
        public FluentWebElement button(By by) {
            return (FluentWebElement) super.button(by);
        }

        @Override
        public FluentWebElements buttons() {
            return (FluentWebElements) super.buttons();
        }

        @Override
        public FluentWebElements buttons(By by) {
            return (FluentWebElements) super.buttons(by);
        }

        @Override
        public FluentWebElement link() {
            return (FluentWebElement) super.link();
        }

        @Override
        public FluentWebElement link(By by) {
            return (FluentWebElement) super.link(by);
        }

        @Override
        public FluentWebElements links() {
            return (FluentWebElements) super.links();
        }

        @Override
        public FluentWebElements links(By by) {
            return (FluentWebElements) super.links(by);
        }

        @Override
        public FluentWebElement input() {
            return (FluentWebElement) super.input();
        }

        @Override
        public FluentWebElement input(By by) {
            return (FluentWebElement) super.input(by);
        }

        @Override
        public FluentWebElements inputs() {
            return (FluentWebElements) super.inputs();
        }

        @Override
        public FluentWebElements inputs(By by) {
            return (FluentWebElements) super.inputs(by);
        }

        @Override
        public FluentWebElement option() {
            return (FluentWebElement) super.option();
        }

        @Override
        public FluentWebElements options() {
            return (FluentWebElements) super.options();
        }

        @Override
        public FluentWebElement option(By by) {
            return (FluentWebElement) super.option(by);
        }

        @Override
        public FluentWebElements options(By by) {
            return (FluentWebElements) super.options(by);
        }

        @Override
        public FluentWebElement textarea() {
            return (FluentWebElement) super.textarea();
        }

        @Override
        public FluentWebElements textareas() {
            return (FluentWebElements) super.textareas();
        }

        @Override
        public FluentWebElement textarea(By by) {
            return (FluentWebElement) super.textarea(by);
        }

        @Override
        public FluentWebElements textareas(By by) {
            return (FluentWebElements) super.textareas(by);
        }

        @Override
        public FluentWebElement h1() {
            return (FluentWebElement) super.h1();
        }

        @Override
        public FluentWebElement h1(By by) {
            return (FluentWebElement) super.h1(by);
        }

        @Override
        public FluentWebElements h1s() {
            return (FluentWebElements) super.h1s();
        }

        @Override
        public FluentWebElements h1s(By by) {
            return (FluentWebElements) super.h1s(by);
        }

        @Override
        public FluentWebElement h2() {
            return (FluentWebElement) super.h2();
        }

        @Override
        public FluentWebElement h2(By by) {
            return (FluentWebElement) super.h2(by);
        }

        @Override
        public FluentWebElements h2s() {
            return (FluentWebElements) super.h2s();
        }

        @Override
        public FluentWebElements h2s(By by) {
            return (FluentWebElements) super.h2s(by);
        }

        @Override
        public FluentWebElement h3() {
            return (FluentWebElement) super.h3();
        }

        @Override
        public FluentWebElement h3(By by) {
            return (FluentWebElement) super.h3(by);
        }

        @Override
        public FluentWebElements h3s() {
            return (FluentWebElements) super.h3s();
        }

        @Override
        public FluentWebElements h3s(By by) {
            return (FluentWebElements) super.h3s(by);
        }

        @Override
        public FluentWebElement h4() {
            return (FluentWebElement) super.h4();
        }

        @Override
        public FluentWebElement h4(By by) {
            return (FluentWebElement) super.h4(by);
        }

        @Override
        public FluentWebElements h4s() {
            return (FluentWebElements) super.h4s();
        }

        @Override
        public FluentWebElements h4s(By by) {
            return (FluentWebElements) super.h4s(by);
        }

        @Override
        public FluentWebElement td() {
            return (FluentWebElement) super.td();
        }

        @Override
        public FluentWebElement td(By by) {
            return (FluentWebElement) super.td(by);
        }

        @Override
        public FluentWebElements tds() {
            return (FluentWebElements) super.tds();
        }

        @Override
        public FluentWebElements tds(By by) {
            return (FluentWebElements) super.tds(by);
        }

        @Override
        public FluentWebElement form() {
            return (FluentWebElement) super.form();
        }

        @Override
        public FluentWebElement form(By by) {
            return (FluentWebElement) super.form(by);
        }

        @Override
        public FluentWebElements forms() {
            return (FluentWebElements) super.forms();
        }

        @Override
        public FluentWebElements forms(By by) {
            return (FluentWebElements) super.forms(by);
        }

        @Override
        public FluentWebElement table() {
            return (FluentWebElement) super.table();
        }

        @Override
        public FluentWebElement table(By by) {
            return (FluentWebElement) super.table(by);
        }

        @Override
        public FluentWebElements tables() {
            return (FluentWebElements) super.tables();
        }

        @Override
        public FluentWebElements tables(By by) {
            return (FluentWebElements) super.tables(by);
        }

        @Override
        public FluentWebElement fieldset() {
            return (FluentWebElement) super.fieldset();
        }

        @Override
        public FluentWebElement fieldset(By by) {
            return (FluentWebElement) super.fieldset(by);
        }

        @Override
        public FluentWebElements fieldsets() {
            return (FluentWebElements) super.fieldsets();
        }

        @Override
        public FluentWebElements fieldsets(By by) {
            return (FluentWebElements) super.fieldsets(by);
        }

        @Override
        public FluentWebElement tr() {
            return (FluentWebElement) super.tr();
        }

        @Override
        public FluentWebElement tr(By by) {
            return (FluentWebElement) super.tr(by);
        }

        @Override
        public FluentWebElements trs() {
            return (FluentWebElements) super.trs();
        }

        @Override
        public FluentWebElements trs(By by) {
            return (FluentWebElements) super.trs(by);
        }

        @Override
        public FluentWebElement img() {
            return (FluentWebElement) super.img();
        }

        @Override
        public FluentWebElement img(By by) {
            return (FluentWebElement) super.img(by);
        }

        @Override
        public FluentWebElements imgs() {
            return (FluentWebElements) super.imgs();
        }

        @Override
        public FluentWebElements imgs(By by) {
            return (FluentWebElements) super.imgs(by);
        }

        @Override
        public FluentWebElement legend() {
            return (FluentWebElement) super.legend();
        }

        @Override
        public FluentWebElement legend(By by) {
            return (FluentWebElement) super.legend(by);
        }

        @Override
        public FluentWebElements legends() {
            return (FluentWebElements) super.legends();
        }

        @Override
        public FluentWebElements legends(By by) {
            return (FluentWebElements) super.legends(by);
        }

        @Override
        public FluentWebElement th() {
            return (FluentWebElement) super.th();
        }

        @Override
        public FluentWebElement th(By by) {
            return (FluentWebElement) super.th(by);
        }

        @Override
        public FluentWebElements ths() {
            return (FluentWebElements) super.ths();
        }

        @Override
        public FluentWebElements ths(By by) {
            return (FluentWebElements) super.ths(by);
        }

        @Override
        public FluentWebElement map() {
            return (FluentWebElement) super.map();
        }

        @Override
        public FluentWebElement map(By by) {
            return (FluentWebElement) super.map(by);
        }

        @Override
        public FluentWebElements maps() {
            return (FluentWebElements) super.maps();
        }

        @Override
        public FluentWebElements maps(By by) {
            return (FluentWebElements) super.maps(by);
        }

        @Override
        public FluentWebElement ol() {
            return (FluentWebElement) super.ol();
        }

        @Override
        public FluentWebElement ol(By by) {
            return (FluentWebElement) super.ol(by);
        }

        @Override
        public FluentWebElements ols() {
            return (FluentWebElements) super.ols();
        }

        @Override
        public FluentWebElements ols(By by) {
            return (FluentWebElements) super.ols(by);
        }

        @Override
        public FluentWebElement li() {
            return (FluentWebElement) super.li();
        }

        @Override
        public FluentWebElement li(By by) {
            return (FluentWebElement) super.li(by);
        }

        @Override
        public FluentWebElements lis() {
            return (FluentWebElements) super.lis();
        }

        @Override
        public FluentWebElements lis(By by) {
            return (FluentWebElements) super.lis(by);
        }

        @Override
        public FluentWebElement p() {
            return (FluentWebElement) super.p();
        }

        @Override
        public FluentWebElement p(By by) {
            return (FluentWebElement) super.p(by);
        }

        @Override
        public FluentWebElements ps() {
            return (FluentWebElements) super.ps();
        }

        @Override
        public FluentWebElements ps(By by) {
            return (FluentWebElements) super.ps(by);
        }

        private class RetryingFluentWebElement extends FluentWebElement {

            private final Period period;

            public RetryingFluentWebElement(WebDriver webDriver, WebElement currentElement, Context context, Period period) {
                super(webDriver, currentElement, context);
                this.period = period;
            }

            @Override
            protected Period getPeriod() {
                return period;
            }

            @Override
            protected WebElement findIt(By by) {
                return retryingFindIt(by);
            }

            @Override
            protected List<WebElement> findThem(By by) {
                return retryingFindThem(by);
            }

            @Override
            protected void changeTimeout() {
                delegate.manage().timeouts().implicitlyWait(period.howLong(), period.timeUnit());
            }

            @Override
            protected void resetTimeout() {
                delegate.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            }

        }

        public static class NegatingFluentWebElement {
            private final BaseFluentWebElement delegate;
            private final Period duration;
            private final Long startedAt;

            protected NegatingFluentWebElement(WebDriver delegate, WebElement currentElement, Period duration, Context context) {
                this.delegate = new FluentWebElement(delegate, currentElement, context) {
                    protected <T> T decorateExecution(Execution<T> execution, Context ctx) {
                        final T successfullyAbsent = null;
                        while (!durationHasElapsed(startedAt)) {
                            try {
                                super.decorateExecution(execution, ctx);
                            } catch (FluentExecutionStopped executionStopped) {
                                final boolean elementGone = executionStopped.getCause() instanceof NotFoundException;

                                if (elementGone) {
                                    return successfullyAbsent;
                                }
                            }
                        }
                        throw decorateAssertionError(ctx, new AssertionError("Element never disappeared"));
                    }
                };
                this.duration = duration;
                startedAt = System.currentTimeMillis();
            }

            protected Boolean durationHasElapsed(Long startMillis) {
                return duration.getEndMillis(startMillis) <= System.currentTimeMillis();
            }

            public void span() {
                delegate.span();
            }

            public void span(By by) {
                delegate.span(by);
            }


            public void div() {
                delegate.div();
            }

            public void div(By by) {
                delegate.div(by);
            }

            public void button() {
                delegate.button();
            }

            public void button(By by) {
                delegate.button(by);
            }


            public void link() {
                delegate.link();
            }

            public void link(By by) {
                delegate.link(by);
            }

            public void input() {
                delegate.input();
            }

            public void input(By by) {
                delegate.input(by);
            }


            public void select() {
                delegate.select();
            }

            public void select(By by) {
                delegate.select(by);
            }

            public void h1() {
                delegate.h1();
            }

            public void h1(By by) {
                delegate.h1(by);
            }


            public void h2() {
                delegate.h2();
            }

            public void h2(By by) {
                delegate.h2(by);
            }

            public void h3() {
                delegate.h3();
            }

            public void h3(By by) {
                delegate.h3(by);
            }

            public void h4() {
                delegate.h4();
            }

            public void h4(By by) {
                delegate.h4(by);
            }


            public void p() {
                delegate.p();
            }


            public void p(By by) {
                delegate.p(by);
            }


            public void img() {
                delegate.img();
            }

            public void img(By by) {
                delegate.img(by);
            }


            public void table() {
                delegate.table();
            }

            public void table(By by) {
                delegate.table(by);
            }

            public void fieldset() {
                delegate.fieldset();
            }


            public void fieldset(By by) {
                delegate.fieldset(by);
            }


            public void legend() {
                delegate.legend();
            }


            public void legend(By by) {
                delegate.legend(by);
            }


            public void tr() {
                delegate.tr();
            }

            public void tr(By by) {
                delegate.tr(by);
            }


            public void td() {
                delegate.td();
            }


            public void td(By by) {
                delegate.td(by);
            }


            public void th() {
                delegate.th();
            }


            public void th(By by) {
                delegate.th(by);
            }

            public void ul() {
                delegate.ul();
            }


            public void ul(By by) {
                delegate.ul(by);
            }


            public void ol() {
                delegate.ol();
            }


            public void ol(By by) {
                delegate.ol(by);
            }

            public void form() {
                delegate.form();
            }


            public void form(By by) {
                delegate.form(by);
            }


            public void textarea() {
                delegate.textarea();
            }


            public void textarea(By by) {
                delegate.textarea(by);
            }


            public void option() {
                delegate.option();
            }


            public void option(By by) {
                delegate.option(by);
            }

            public void li() {
                delegate.li();
            }

            public void li(By by) {
                delegate.li(by);
            }


            public void map() {
                delegate.map();
            }


            public void map(By by) {
                delegate.map(by);
            }


        }

        private class Clear implements Execution<Boolean> {
            public Boolean execute() {
                currentElement.clear();
                return true;
            }
        }

        private class GetTagName implements Execution<String> {
            public String execute() {
                return currentElement.getTagName();
            }
        }

        private class Click implements Execution<Boolean> {
            public Boolean execute() {
                currentElement.click();
                return true;
            }
        }

        private class GetAttribute implements Execution<String> {
            private final String attr;

            public GetAttribute(String attr) {
                this.attr = attr;
            }

            public String execute() {
                return currentElement.getAttribute(attr);
            }
        }

        private class GetCssValue implements Execution<String> {
            private final String cssName;

            public GetCssValue(String cssName) {
                this.cssName = cssName;
            }

            public String execute() {
                return currentElement.getCssValue(cssName);
            }
        }

        private class GetText implements Execution<String> {
            public String execute() {
                return currentElement.getText();
            }
        }

        private class GetSize implements Execution<Dimension> {
            public Dimension execute() {
                return currentElement.getSize();
            }
        }

        private class GetLocation implements Execution<Point> {
            public Point execute() {
                return currentElement.getLocation();
            }
        }

        private class IsDisplayed implements Execution<Boolean> {
            public Boolean execute() {
                return currentElement.isDisplayed();
            }
        }

        private class IsEnabled implements Execution<Boolean> {
            public Boolean execute() {
                return currentElement.isEnabled();
            }
        }

        private class IsSelected implements Execution<Boolean> {
            public Boolean execute() {
                return currentElement.isSelected();
            }
        }

        private class SendKeys implements Execution<Boolean> {
            private final CharSequence[] keysToSend;

            public SendKeys(CharSequence... keysToSend) {
                this.keysToSend = keysToSend;
            }

            public Boolean execute() {
                currentElement.sendKeys(keysToSend);
                return true;
            }
        }

        private class Submit implements Execution<Boolean> {
            public Boolean execute() {
                currentElement.submit();
                return true;
            }
        }

    }

    public static class FluentWebElements extends BaseFluentWebElements {

        private final List<FluentWebElement> currentElements;

        public FluentWebElements(WebDriver delegate, List<FluentWebElement> currentElements, Context context) {
            super(delegate, context);
            this.currentElements = currentElements;
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
                    throw new NothingMatches();
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
                    throw new NothingMatches();
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

    public static class FluentSelect extends FluentWebElement {

        private Select currentSelect;

        public FluentSelect(WebDriver delegate, WebElement currentElement, Context context) {
            super(delegate, currentElement, context);
        }

        private FluentSelect(WebDriver delegate, Select currentSelect, WebElement currentElement, Context context) {
            super(delegate, currentElement, context);
            this.currentSelect = currentSelect;
        }

        /**
         * @return Whether this select element support selecting multiple options at the same time? This
         *         is done by checking the value of the "multiple" attribute.
         */
        public boolean isMultiple() {
            return decorateExecution(new IsMultiple(), Context.singular(context, "isMultiple"));
        }

        /**
         * @return All options belonging to this select tag
         */
        public List<WebElement> getOptions() {
            return decorateExecution(new GetOptions(), Context.singular(context, "getOptions"));
        }

        /**
         * @return All selected options belonging to this select tag
         */
        public List<WebElement> getAllSelectedOptions() {
            return decorateExecution(new GetAllSelectedOptions(), Context.singular(context, "getAllSelectedOptions"));
        }

        /**
         * @return The first selected option in this select tag (or the currently selected option in a
         *         normal select)
         */
        public WebElement getFirstSelectedOption() {
            return decorateExecution(new GetFirstSelectedOption(), Context.singular(context, "getFirstSelectedOption"));
        }

        /**
         * Select all options that display text matching the argument. That is, when given "Bar" this
         * would select an option like:
         * <p/>
         * &lt;option value="foo"&gt;Bar&lt;/option&gt;
         *
         * @param text The visible text to match against
         */
        public FluentSelect selectByVisibleText(final String text) {
            decorateExecution(new SelectByVisibleText(text), Context.singular(context, "selectByVisibleText", null, text));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        /**
         * Select the option at the given index. This is done by examing the "index" attribute of an
         * element, and not merely by counting.
         *
         * @param index The option at this index will be selected
         */
        public FluentSelect selectByIndex(final int index) {
            decorateExecution(new SelectByIndex(index), Context.singular(context, "selectByIndex", null, index));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        /**
         * Select all options that have a value matching the argument. That is, when given "foo" this
         * would select an option like:
         * <p/>
         * &lt;option value="foo"&gt;Bar&lt;/option&gt;
         *
         * @param value The value to match against
         */
        public FluentSelect selectByValue(final String value) {
            decorateExecution(new SelectByValue(value), Context.singular(context, "selectByValue", null, value));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        /**
         * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
         *
         * @throws UnsupportedOperationException If the SELECT does not support multiple selections
         */
        public FluentSelect deselectAll() {
            decorateExecution(new DeselectAll(), Context.singular(context, "deselectAll"));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        /**
         * Deselect all options that have a value matching the argument. That is, when given "foo" this
         * would deselect an option like:
         * <p/>
         * &lt;option value="foo"&gt;Bar&lt;/option&gt;
         *
         * @param value The value to match against
         */
        public FluentSelect deselectByValue(final String value) {
            decorateExecution(new DeselectByValue(value), Context.singular(context, "deselectByValue", null, value));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        /**
         * Deselect the option at the given index. This is done by examining the "index" attribute of an
         * element, and not merely by counting.
         *
         * @param index The option at this index will be deselected
         */
        public FluentSelect deselectByIndex(final int index) {
            decorateExecution(new DeselectByIndex(index), Context.singular(context, "deselectByIndex", null, index));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        /**
         * Deselect all options that display text matching the argument. That is, when given "Bar" this
         * would deselect an option like:
         * <p/>
         * &lt;option value="foo"&gt;Bar&lt;/option&gt;
         *
         * @param text The visible text to match against
         */
        public FluentSelect deselectByVisibleText(final String text) {
            decorateExecution(new DeselectByVisibleText(text), Context.singular(context, "deselectByVisibleText", null, text));
            return new FluentSelect(super.delegate, currentElement, this.context);
        }

        protected synchronized Select getSelect() {
            if (currentSelect == null) {
                currentSelect = new Select(currentElement);
            }
            return currentSelect;
        }

        public FluentSelect within(Period period) {
            return new RetryingFluentSelect(delegate, Context.singular(context, "within", null, period), currentSelect, currentElement, period);
        }

        private class RetryingFluentSelect extends FluentSelect {

            private final Period period;

            public RetryingFluentSelect(WebDriver webDriver, Context context, Select currentSelect, WebElement currentElement, Period period) {
                super(webDriver, currentSelect, currentElement, context);
                this.period = period;
            }

            @Override
            protected WebElement findIt(By by) {
                return retryingFindIt(by);
            }

            @Override
            protected List<WebElement> findThem(By by) {
                return retryingFindThem(by);
            }

            @Override
            protected Period getPeriod() {
                return period;
            }

            @Override
            protected void changeTimeout() {
                delegate.manage().timeouts().implicitlyWait(period.howLong(), period.timeUnit());
            }

            @Override
            protected void resetTimeout() {
                delegate.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            }

        }


        private class DeselectAll implements Execution<Boolean> {
            public Boolean execute() {
                getSelect().deselectAll();
                return true;
            }
        }

        private class SelectByValue implements Execution<Boolean> {
            private final String value;

            public SelectByValue(String value) {
                this.value = value;
            }

            public Boolean execute() {
                Select select = getSelect();
                select.selectByValue(value);
                return true;
            }
        }

        private class SelectByIndex implements Execution<Boolean> {
            private final int index;

            public SelectByIndex(int index) {
                this.index = index;
            }

            public Boolean execute() {
                getSelect().selectByIndex(index);
                return true;
            }
        }

        private class SelectByVisibleText implements Execution<Boolean> {
            private final String text;

            public SelectByVisibleText(String text) {
                this.text = text;
            }

            public Boolean execute() {
                getSelect().selectByVisibleText(text);
                return true;
            }
        }

        private class GetFirstSelectedOption implements Execution<WebElement> {
            public WebElement execute() {
                return getSelect().getFirstSelectedOption();
            }
        }

        private class GetAllSelectedOptions implements Execution<List<WebElement>> {
            public List<WebElement> execute() {
                return getSelect().getAllSelectedOptions();
            }
        }

        private class GetOptions implements Execution<List<WebElement>> {
            public List<WebElement> execute() {
                return getSelect().getOptions();
            }
        }

        private class IsMultiple implements Execution<Boolean> {
            public Boolean execute() {
                return getSelect().isMultiple();
            }
        }

        private class DeselectByValue implements Execution<Boolean> {
            private final String value;

            public DeselectByValue(String value) {
                this.value = value;
            }

            public Boolean execute() {
                getSelect().deselectByValue(value);
                return true;
            }
        }

        private class DeselectByIndex implements Execution<Boolean> {
            private final int index;

            public DeselectByIndex(int index) {
                this.index = index;
            }

            public Boolean execute() {
                getSelect().deselectByIndex(index);
                return true;
            }
        }

        private class DeselectByVisibleText implements Execution<Boolean> {
            private final String text;

            public DeselectByVisibleText(String text) {
                this.text = text;
            }

            public Boolean execute() {
                getSelect().deselectByVisibleText(text);
                return true;
            }
        }
    }

    public static class FluentSelects extends FluentWebElements {
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

    public static class NothingMatches extends RuntimeException {
    }
}
