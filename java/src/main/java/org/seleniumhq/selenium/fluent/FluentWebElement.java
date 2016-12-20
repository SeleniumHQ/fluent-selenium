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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FluentWebElement extends Internal.BaseFluentWebElement {

    protected final Internal.WebElementHolder currentElement;

    protected FluentWebElement(WebDriver delegate, Internal.WebElementHolder currentElement, Context context, Monitor monitor, boolean booleanInsteadOfNoSuchElement) {
        super(delegate, context, monitor, booleanInsteadOfNoSuchElement);
        this.currentElement = currentElement;
    }

    @Override
    protected SearchContext getSearchContext() {
        return currentElement.getFound();
    }

    public WebElement getWebElement() {
        return currentElement.getFound();
    }

    @Override
    protected WebElement findElement(By by, Context ctx, SearchContext searchContext) {
        return actualFindElement(by, ctx, searchContext);
    }

    @Override
    protected List<WebElement> findElements(By by, Context ctx) {
        return actualFindElements(by, ctx);
    }

    @Override
    protected WebElement actualFindElement(By by, Context ctx, SearchContext searchContext) {
        beforeFindElement(by);
        return searchContext.findElement(by);
    }

    @Override
    protected List<WebElement> actualFindElements(By by, Context ctx) {
        beforeFindElement(by);
        return currentElement.getFound().findElements(by);
    }

    private <T> T executeAndWrapReThrowIfNeeded(Execution<T> execution, Context ctx, boolean expectedToBeThere) {
        return executeAndWrapReThrowIfNeeded(execution, currentElement, ctx, expectedToBeThere);
    }

    public FluentWebElement click() {
        Context ctx = Context.singular(context, "click");
        executeAndWrapReThrowIfNeeded(setCurrentElement(new Click()), ctx, true);
        return new FluentWebElement(delegate, currentElement, ctx, monitor, booleanInsteadOfNotFoundException);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public FluentWebElement clearField() {
        Context ctx = Context.singular(context, "clearField");
        executeAndWrapReThrowIfNeeded(setCurrentElement(new Clear()), ctx, true);
        return new FluentWebElement(delegate, currentElement, ctx, monitor, booleanInsteadOfNotFoundException);
    }


    public FluentWebElement submit() {
        executeAndWrapReThrowIfNeeded(new Submit(), Context.singular(context, "submit"), true);
        return new FluentWebElement(delegate, currentElement, context, monitor, booleanInsteadOfNotFoundException);
    }

    // These are as they would be in the WebElement API

    public FluentWebElement sendKeys(final CharSequence... keysToSend) {

        executeAndWrapReThrowIfNeeded(new SendKeys(keysToSend), Context.singular(context, "sendKeys", null, charSeqArrayAsHumanString(keysToSend)), true);
        return new FluentWebElement(delegate, currentElement, context, monitor, booleanInsteadOfNotFoundException);
    }

    public TestableString getTagName() {
        return new TestableString(setCurrentElement(new GetTagName()), Context.singular(context, "getTagName"), monitor);
    }

    public TestableValue<Boolean> isSelected() {
        Context isSelected = Context.singular(context, "isSelected");
        return new TestableValue<Boolean>(setCurrentElement(new IsSelected()), isSelected, monitor);
    }

    public TestableValue<Boolean> isEnabled() {
        Context isEnabled = Context.singular(context, "isEnabled");
        return new TestableValue<Boolean>(setCurrentElement(new IsEnabled()), isEnabled, monitor);
    }

    public TestableValue<Boolean> isDisplayed() {
        Context isDisplayed = Context.singular(context, "isDisplayed");
        return new TestableValue<Boolean>(setCurrentElement(new IsDisplayed()), isDisplayed, monitor);
    }

    public FluentWebElement ifInvisibleWaitUpTo(Period period) {
        Context ifInvisibleWaitUpTo = Context.singular(context, "ifInvisibleWaitUpTo", period);
        executeAndWrapReThrowIfNeeded(setCurrentElement(new IfInvisibleWait(period)), currentElement, ifInvisibleWaitUpTo, true);
        return new FluentWebElement(delegate, currentElement, ifInvisibleWaitUpTo, monitor, booleanInsteadOfNotFoundException);
    }

    public TestableValue<Point> getLocation() {
        final Context getLocation = Context.singular(context, "getLocation");
        return new TestableValue<Point>(setCurrentElement(new GetLocation()), getLocation, monitor);
    }

    private Execution setCurrentElement(Execution execution) {
        return execution.withWebElementHolder(currentElement);
    }

    public TestableValue<Dimension> getSize() {
        Context getSize = Context.singular(context, "getSize");
        return new TestableValue<Dimension>(setCurrentElement(new GetSize()), getSize, monitor);
    }

    public TestableString getCssValue(final String cssName) {
        return new TestableString(setCurrentElement(new GetCssValue(cssName)), Context.singular(context, "getCssValue", null, cssName), monitor).within(getPeriod());
    }

    public TestableString getAttribute(final String attr) {
        return new TestableString(setCurrentElement(new GetAttribute(attr)), Context.singular(context, "getAttribute", null, attr), monitor).within(getPeriod());
    }

    public TestableString getText() {
        return new TestableString(setCurrentElement(new GetText(new TestableString.NoopStringChanger())), Context.singular(context, "getText"), monitor);
    }

    public TestableString getText(TestableString.StringChanger... stringChangers) {
        return new TestableString(setCurrentElement(new GetText(stringChangers)), Context.singular(context, "getText"), monitor);
    }


    public FluentWebElement within(Period period) {
        return new RetryingFluentWebElement(delegate, currentElement, Context.singular(context, "within", null, period), period, monitor, booleanInsteadOfNotFoundException);
    }

    public NegatingFluentWebElement without(Period period) {
        return new NegatingFluentWebElement(delegate, currentElement, period, Context.singular(context, "without", null, period), monitor, booleanInsteadOfNotFoundException);
    }

    @Override
    public FluentWebElement element() {
        return (FluentWebElement) super.element();
    }

    @Override
    public FluentWebElement element(By by) {
        return (FluentWebElement) super.element(by);
    }

    @Override
    public FluentWebElements elements() {
        return (FluentWebElements) super.elements();
    }

    @Override
    public FluentWebElements elements(By by) {
        return (FluentWebElements) super.elements(by);
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
    public FluentWebElement h5() {
        return (FluentWebElement) super.h5();
    }

    @Override
    public FluentWebElement h5(By by) {
        return (FluentWebElement) super.h5(by);
    }

    @Override
    public FluentWebElements h5s() {
        return (FluentWebElements) super.h5s();
    }

    @Override
    public FluentWebElements h5s(By by) {
        return (FluentWebElements) super.h5s(by);
    }

    @Override
    public FluentWebElement h6() {
        return (FluentWebElement) super.h6();
    }

    @Override
    public FluentWebElement h6(By by) {
        return (FluentWebElement) super.h6(by);
    }

    @Override
    public FluentWebElements h6s() {
        return (FluentWebElements) super.h6s();
    }

    @Override
    public FluentWebElements h6s(By by) {
        return (FluentWebElements) super.h6s(by);
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
    public FluentWebElement ul() { return (FluentWebElement) super.ul(); }

    @Override
    public FluentWebElement ul(By by) { return (FluentWebElement) super.ul(by); }

    @Override
    public FluentWebElements uls() { return (FluentWebElements) super.uls(); }

    @Override
    public FluentWebElements uls(By by) { return (FluentWebElements) super.uls(by); }

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

    @Override
    public FluentWebElement b() {
        return (FluentWebElement) super.b();
    }

    @Override
    public FluentWebElement b(By by) {
        return (FluentWebElement) super.b(by);
    }

    @Override
    public FluentWebElements bs() {
        return (FluentWebElements) super.bs();
    }

    @Override
    public FluentWebElements bs(By by) {
        return (FluentWebElements) super.bs(by);
    }

    @Override
    public FluentWebElement pre() {
        return (FluentWebElement) super.pre();
    }

    @Override
    public FluentWebElement pre(By by) {
        return (FluentWebElement) super.pre(by);
    }

    @Override
    public FluentWebElements pres() {
        return (FluentWebElements) super.pres();
    }

    @Override
    public FluentWebElements pres(By by) {
        return (FluentWebElements) super.pres(by);
    }

    @Override
    public FluentWebElement header() {
        return (FluentWebElement) super.header();
    }

    @Override
    public FluentWebElement header(By by) {
        return (FluentWebElement) super.header(by);
    }

    @Override
    public FluentWebElements headers() {
        return (FluentWebElements) super.headers();
    }

    @Override
    public FluentWebElements headers(By by) {
        return (FluentWebElements) super.headers(by);
    }

    @Override
    public FluentWebElement footer() {
        return (FluentWebElement) super.footer();
    }

    @Override
    public FluentWebElement footer(By by) {
        return (FluentWebElement) super.footer(by);
    }

    @Override
    public FluentWebElements footers() {
        return (FluentWebElements) super.footers();
    }

    @Override
    public FluentWebElements footers(By by) {
        return (FluentWebElements) super.footers(by);
    }

    @Override
    public FluentWebElement figure() {
        return (FluentWebElement) super.figure();
    }

    @Override
    public FluentWebElement figure(By by) {
        return (FluentWebElement) super.figure(by);
    }

    @Override
    public FluentWebElements figures() {
        return (FluentWebElements) super.figures();
    }

    @Override
    public FluentWebElements figures(By by) {
        return (FluentWebElements) super.figures(by);
    }

    @Override
    public FluentWebElement acronym() {
        return (FluentWebElement) super.acronym();
    }

    @Override
    public FluentWebElement acronym(By by) {
        return (FluentWebElement) super.acronym(by);
    }

    @Override
    public FluentWebElements acronyms() {
        return (FluentWebElements) super.acronyms();
    }

    @Override
    public FluentWebElements acronyms(By by) {
        return (FluentWebElements) super.acronyms(by);
    }

    @Override
    public FluentWebElement abbr() {
        return (FluentWebElement) super.abbr();
    }

    @Override
    public FluentWebElement abbr(By by) {
        return (FluentWebElement) super.abbr(by);
    }

    @Override
    public FluentWebElements abbrs() {
        return (FluentWebElements) super.abbrs();
    }

    @Override
    public FluentWebElements abbrs(By by) {
        return (FluentWebElements) super.abbrs(by);
    }

    @Override
    public FluentWebElement address() {
        return (FluentWebElement) super.address();
    }

    @Override
    public FluentWebElement address(By by) {
        return (FluentWebElement) super.address(by);
    }

    @Override
    public FluentWebElements addresses() {
        return (FluentWebElements) super.addresses();
    }

    @Override
    public FluentWebElements addresses(By by) {
        return (FluentWebElements) super.addresses(by);
    }

    @Override
    public FluentWebElement blockquote() {
        return (FluentWebElement) super.blockquote();
    }

    @Override
    public FluentWebElement blockquote(By by) {
        return (FluentWebElement) super.blockquote(by);
    }

    @Override
    public FluentWebElements blockquotes() {
        return (FluentWebElements) super.blockquotes();
    }

    @Override
    public FluentWebElements blockquotes(By by) {
        return (FluentWebElements) super.blockquotes(by);
    }

    @Override
    public FluentWebElement area() {
        return (FluentWebElement) super.area();
    }

    @Override
    public FluentWebElement area(By by) {
        return (FluentWebElement) super.area(by);
    }

    @Override
    public FluentWebElements areas() {
        return (FluentWebElements) super.areas();
    }

    @Override
    public FluentWebElements areas(By by) {
        return (FluentWebElements) super.areas(by);
    }

    @Override
    public FluentWebElement label() {
        return (FluentWebElement) super.label();
    }

    @Override
    public FluentWebElement label(By by) {
        return (FluentWebElement) super.label(by);
    }

    @Override
    public FluentWebElements labels() {
        return (FluentWebElements) super.labels();
    }

    @Override
    public FluentWebElements labels(By by) {
        return (FluentWebElements) super.labels(by);
    }

    @Override
    public FluentWebElement object() {
        return (FluentWebElement) super.object();
    }

    @Override
    public FluentWebElement object(By by) {
        return (FluentWebElement) super.object(by);
    }

    @Override
    public FluentWebElements objects() {
        return (FluentWebElements) super.objects();
    }

    @Override
    public FluentWebElements objects(By by) {
        return (FluentWebElements) super.objects(by);
    }

    @Override
    public FluentWebElement nav() {
        return (FluentWebElement) super.nav();
    }

    @Override
    public FluentWebElement nav(By by) {
        return (FluentWebElement) super.nav(by);
    }

    @Override
    public FluentWebElements navs() {
        return (FluentWebElements) super.navs();
    }

    @Override
    public FluentWebElements navs(By by) {
        return (FluentWebElements) super.navs(by);
    }

    @Override
    public FluentWebElement tbody() {
        return (FluentWebElement) super.tbody();
    }

    @Override
    public FluentWebElement tbody(By by) {
        return (FluentWebElement) super.tbody(by);
    }

    @Override
    public FluentWebElements tbodys() {
        return (FluentWebElements) super.tbodys();
    }

    @Override
    public FluentWebElements tbodys(By by) {
        return (FluentWebElements) super.tbodys(by);
    }


    public FluentWebDriver.BooleanResultsAdapter hasMissing() {
        return new FluentWebDriver.BooleanResultsAdapter(super.delegate, this.currentElement, super.monitor, super.context)
                .invert(true);
    }

    public FluentWebDriver.BooleanResultsAdapter has() {
        return new FluentWebDriver.BooleanResultsAdapter(super.delegate, this.currentElement, super.monitor, super.context)
                .invert(false);
    }


    private class RetryingFluentWebElement extends FluentWebElement {

        private final Period period;

        public RetryingFluentWebElement(WebDriver webDriver, Internal.WebElementHolder currentElement, Context context, Period period, Monitor monitor, boolean booleanInsteadOfNoSuchElement) {
            super(webDriver, currentElement, context, monitor, booleanInsteadOfNoSuchElement);
            this.period = period;
        }

        @Override
        protected Period getPeriod() {
            return period;
        }

        @Override
        protected WebElement findElement(By by, Context ctx, SearchContext searchContext) {
            return retryingFindIt(by, searchContext);
        }

        @Override
        protected List<WebElement> findElements(By by, Context ctx) {
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
        private final Internal.BaseFluentWebElement delegate;
        private final Period duration;
        private final Long startedAt;

        protected NegatingFluentWebElement(WebDriver delegate, Internal.WebElementHolder currentElement, Period duration, Context context, final Monitor monitor, final boolean booleanInsteadOfNoSuchElement) {
            this.delegate = new FluentWebElement(delegate, currentElement, context, monitor, booleanInsteadOfNoSuchElement) {
                protected <T> T executeAndWrapReThrowIfNeeded(Execution<T> execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                    final T successfullyAbsent = null;
                    while (!durationHasElapsed(startedAt)) {
                        try {
                            // ignore the passed in boolean-----------↴-----------------------↗
                            super.executeAndWrapReThrowIfNeeded(execution, currentElement, ctx, false);
                        } catch (FluentExecutionStopped executionStopped) {
                            final boolean elementGone = executionStopped.getCause() instanceof NotFoundException;

                            if (elementGone) {
                                return successfullyAbsent;
                            }
                        }
                    }
                    throw monitor.exceptionDuringExecution(wrapAssertionError(ctx, new AssertionError("Element never disappeared after: " + (System.currentTimeMillis() - startedAt))), execution.getWebElement());
                }
            };
            this.duration = duration;
            startedAt = System.currentTimeMillis();
        }

        protected Boolean durationHasElapsed(Long startMillis) {
            return duration.getEndMillis(startMillis) <= System.currentTimeMillis();
        }

        public void element() {
            delegate.element();
        }

        public void element(By by) {
            delegate.element(by);
        }

        public void elements() {
            delegate.elements();
        }

        public void elements(By by) {
            delegate.elements(by);
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

        public void b() {
            delegate.b();
        }


        public void b(By by) {
            delegate.b(by);
        }

        public void pre() {
            delegate.pre();
        }


        public void pre(By by) {
            delegate.pre(by);
        }

        public void header() {
            delegate.header();
        }


        public void header(By by) {
            delegate.header(by);
        }

        public void footer() {
            delegate.footer();
        }


        public void footer(By by) {
            delegate.footer(by);
        }

        public void figure() {
            delegate.figure();
        }


        public void figure(By by) {
            delegate.figure(by);
        }

        public void acronym() {
            delegate.acronym();
        }


        public void acronym(By by) {
            delegate.acronym(by);
        }

        public void abbr() {
            delegate.abbr();
        }


        public void abbr(By by) {
            delegate.abbr(by);
        }

        public void address() {
            delegate.address();
        }


        public void address(By by) {
            delegate.address(by);
        }

        public void blockquote() {
            delegate.blockquote();
        }


        public void blockquote(By by) {
            delegate.blockquote(by);
        }

        public void area() {
            delegate.area();
        }


        public void area(By by) {
            delegate.area(by);
        }

        public void label() {
            delegate.label();
        }


        public void label(By by) {
            delegate.label(by);
        }

        public void object() {
            delegate.object();
        }


        public void object(By by) {
            delegate.object(by);
        }

        public void nav() {
            delegate.nav();
        }


        public void nav(By by) {
            delegate.nav(by);
        }

        public void tbody() {
            delegate.tbody();
        }


        public void tbody(By by) {
            delegate.tbody(by);
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

    private class Clear extends StaleElementRecoveringExecution<Boolean> {
        public Boolean execute() {
            currentElement.getFound().clear();
            return true;
        }
    }

    private class GetTagName extends StaleElementRecoveringExecution<String> {
        public String execute() {
            return currentElement.getFound().getTagName();
        }
    }

    private class Click extends StaleElementRecoveringExecution<Boolean> {
        public Boolean execute() {
            currentElement.getFound().click();
            return true;
        }
    }

    private abstract class StaleElementRecoveringExecution<T> extends Execution<T> {

        @Override
        public T doExecution() {
            try {
                return execute();
            } catch (StaleElementReferenceException orig) {
                // try to recover from StaleElementReferenceException just once
                try {
                    currentElement.reFindElement();
                    return execute();
                } catch (WebDriverException e) {
                    // might have been a grandparent that was stale
                    // - can't recover from that, and the original staleness
                    // is more important that this during-recovery exception
                    // whatever it is.
                    throw orig;
                }
            }
        }
    }

    private class GetAttribute extends StaleElementRecoveringExecution<String> {
        private final String attr;

        public GetAttribute(String attr) {
            this.attr = attr;
        }

        public String execute() {
            try {
                return currentElement.getFound().getAttribute(attr);
            } catch (StaleElementReferenceException orig) {
                try {
                    currentElement.reFindElement();
                    return currentElement.getFound().getAttribute(attr);
                } catch (StaleElementReferenceException e) {
                    throw orig;
                }
            }
        }
    }

    private class GetCssValue extends StaleElementRecoveringExecution<String> {
        private final String cssName;

        public GetCssValue(String cssName) {
            this.cssName = cssName;
        }

        public String execute() {
            return currentElement.getFound().getCssValue(cssName);
        }
    }

    private class GetText extends StaleElementRecoveringExecution<String> {
        private TestableString.StringChanger[] stringChangers;

        public GetText(TestableString.StringChanger... stringChangers) {

            this.stringChangers = stringChangers;
        }

        public String execute() {
            String text = currentElement.getFound().getText();
            for (TestableString.StringChanger stringChanger : stringChangers) {
                text = stringChanger.chg(text);
            }
            return text;
        }
    }

    private class GetSize extends StaleElementRecoveringExecution<Dimension> {
        public Dimension execute() {
            return currentElement.getFound().getSize();
        }
    }

    private class GetLocation extends StaleElementRecoveringExecution<Point> {
        public Point execute() {
            return currentElement.getFound().getLocation();
        }
    }

    private class IsDisplayed extends StaleElementRecoveringExecution<Boolean> {
        public Boolean execute() {
            return currentElement.getFound().isDisplayed();
        }
    }

    class IfInvisibleWait extends StaleElementRecoveringExecution<Boolean> {
        private Period period;

        IfInvisibleWait(Period period) {
            this.period = period;
        }

        public Boolean execute() {
            boolean visible = false;
            long endMillis = period.getEndMillis(System.currentTimeMillis());
            while (!visible && System.currentTimeMillis() < endMillis) {
                visible = currentElement.getFound().isDisplayed();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                }
            }
            return visible;
        }
    }

    private class IsEnabled extends StaleElementRecoveringExecution<Boolean> {
        public Boolean execute() {
            return currentElement.getFound().isEnabled();
        }
    }

    private class IsSelected extends StaleElementRecoveringExecution<Boolean> {
        public Boolean execute() {
            return currentElement.getFound().isSelected();
        }
    }

    private class SendKeys extends StaleElementRecoveringExecution<Boolean> {
        private final CharSequence[] keysToSend;

        public SendKeys(CharSequence... keysToSend) {
            this.keysToSend = keysToSend;
        }

        public Boolean execute() {
            currentElement.getFound().sendKeys(keysToSend);
            return true;
        }
    }

    private class Submit extends StaleElementRecoveringExecution<Boolean> {
        public Boolean execute() {
            currentElement.getFound().submit();
            return true;
        }
    }

}
