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
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FluentWebDriver extends Internal.BaseFluentWebDriver {

    public FluentWebDriver(WebDriver delegate) {
        super(delegate, null);
    }

    protected FluentWebDriver(WebDriver delegate, Context context) {
        super(delegate, context);
    }

    @Override
    protected Internal.FluentWebElements makeFluentWebElements(List<Internal.FluentWebElement> results, Context context) {
        return new Internal.FluentWebElements(super.delegate, results, context);
    }

    protected WebElement findIt(By by) {
        return actualFindIt(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return actualFindThem(by);
    }

    protected final WebElement actualFindIt(By by) {
        return delegate.findElement(by);
    }

    protected final List<WebElement> actualFindThem(By by) {
        return delegate.findElements(by);
    }

    public FluentWebDriver within(final Period period) {
        return new RetryingFluentWebDriver(delegate, period, Context.singular(context, "within", null, period));
    }

    public NegatingFluentWebDriver without(Period period) {
        return new NegatingFluentWebDriver(delegate, period, Context.singular(context, "without", null, period));
    }

    protected Internal.BaseFluentWebElements newFluentWebElements(MultipleResult multiple) {
        List<WebElement> result = multiple.getResult();
        Context ctx = multiple.getCtx();
        List<Internal.FluentWebElement> elems = new ArrayList<Internal.FluentWebElement>();
        for (WebElement aResult : result) {
            elems.add(new Internal.FluentWebElement(delegate, aResult, ctx));
        }
        return new Internal.FluentWebElements(delegate, elems, ctx);
    }

    private class RetryingFluentWebDriver extends FluentWebDriver {

        private final Period period;

        public RetryingFluentWebDriver(WebDriver webDriver, Period period, Context context) {
            super(webDriver, context);
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


    public static class NegatingFluentWebDriver {
        private final Internal.BaseFluentWebDriver delegate;
        private final Period duration;
        private final Long startedAt;

        protected NegatingFluentWebDriver(WebDriver delegate, Period duration, Context context) {
            this.delegate = new FluentWebDriver(delegate, context) {
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

    @Override
    public Internal.FluentWebElement span() {
        return (Internal.FluentWebElement) super.span();
    }

    @Override
    public Internal.FluentWebElement span(By by) {
        return (Internal.FluentWebElement) super.span(by);
    }

    @Override
    public Internal.FluentWebElements spans() {
        return (Internal.FluentWebElements) super.spans();
    }

    @Override
    public Internal.FluentWebElements spans(By by) {
        return (Internal.FluentWebElements) super.spans(by);
    }

    @Override
    public Internal.FluentWebElement div() {
        return (Internal.FluentWebElement) super.div();
    }

    @Override
    public Internal.FluentWebElement div(By by) {
        return (Internal.FluentWebElement) super.div(by);
    }

    @Override
    public Internal.FluentWebElements divs() {
        return (Internal.FluentWebElements) super.divs();
    }

    @Override
    public Internal.FluentWebElements divs(By by) {
        return (Internal.FluentWebElements) super.divs(by);
    }

    @Override
    public Internal.FluentWebElement button() {
        return (Internal.FluentWebElement) super.button();
    }

    @Override
    public Internal.FluentWebElement button(By by) {
        return (Internal.FluentWebElement) super.button(by);
    }

    @Override
    public Internal.FluentWebElements buttons() {
        return (Internal.FluentWebElements) super.buttons();
    }

    @Override
    public Internal.FluentWebElements buttons(By by) {
        return (Internal.FluentWebElements) super.buttons(by);
    }

    @Override
    public Internal.FluentWebElement link() {
        return (Internal.FluentWebElement) super.link();
    }

    @Override
    public Internal.FluentWebElement link(By by) {
        return (Internal.FluentWebElement) super.link(by);
    }

    @Override
    public Internal.FluentWebElements links() {
        return (Internal.FluentWebElements) super.links();
    }

    @Override
    public Internal.FluentWebElements links(By by) {
        return (Internal.FluentWebElements) super.links(by);
    }

    @Override
    public Internal.FluentWebElement input() {
        return (Internal.FluentWebElement) super.input();
    }

    @Override
    public Internal.FluentWebElement input(By by) {
        return (Internal.FluentWebElement) super.input(by);
    }

    @Override
    public Internal.FluentWebElements inputs() {
        return (Internal.FluentWebElements) super.inputs();
    }

    @Override
    public Internal.FluentWebElements inputs(By by) {
        return (Internal.FluentWebElements) super.inputs(by);
    }

    @Override
    public Internal.FluentSelect select() {
        return super.select();
    }

    @Override
    public Internal.FluentSelect select(By by) {
        return super.select(by);
    }

    @Override
    public Internal.FluentSelects selects() {
        return super.selects();
    }

    @Override
    public Internal.FluentSelects selects(By by) {
        return super.selects(by);
    }

    @Override
    public Internal.FluentWebElement h1() {
        return (Internal.FluentWebElement) super.h1();
    }

    @Override
    public Internal.FluentWebElement h1(By by) {
        return (Internal.FluentWebElement) super.h1(by);
    }

    @Override
    public Internal.FluentWebElements h1s() {
        return (Internal.FluentWebElements) super.h1s();
    }

    @Override
    public Internal.FluentWebElements h1s(By by) {
        return (Internal.FluentWebElements) super.h1s(by);
    }

    @Override
    public Internal.FluentWebElement h2() {
        return (Internal.FluentWebElement) super.h2();
    }

    @Override
    public Internal.FluentWebElement h2(By by) {
        return (Internal.FluentWebElement) super.h2(by);
    }

    @Override
    public Internal.FluentWebElements h2s() {
        return (Internal.FluentWebElements) super.h2s();
    }

    @Override
    public Internal.FluentWebElements h2s(By by) {
        return (Internal.FluentWebElements) super.h2s(by);
    }

    @Override
    public Internal.FluentWebElement h3() {
        return (Internal.FluentWebElement) super.h3();
    }

    @Override
    public Internal.FluentWebElements h3s() {
        return (Internal.FluentWebElements) super.h3s();
    }

    @Override
    public Internal.FluentWebElement h3(By by) {
        return (Internal.FluentWebElement) super.h3(by);
    }

    @Override
    public Internal.FluentWebElements h3s(By by) {
        return (Internal.FluentWebElements) super.h3s(by);
    }

    @Override
    public Internal.FluentWebElement h4() {
        return (Internal.FluentWebElement) super.h4();
    }

    @Override
    public Internal.FluentWebElements h4s() {
        return (Internal.FluentWebElements) super.h4s();
    }

    @Override
    public Internal.FluentWebElement h4(By by) {
        return (Internal.FluentWebElement) super.h4(by);
    }

    @Override
    public Internal.FluentWebElements h4s(By by) {
        return (Internal.FluentWebElements) super.h4s(by);
    }

    @Override
    public Internal.FluentWebElement p() {
        return (Internal.FluentWebElement) super.p();
    }

    @Override
    public Internal.FluentWebElements ps() {
        return (Internal.FluentWebElements) super.ps();
    }

    @Override
    public Internal.FluentWebElement p(By by) {
        return (Internal.FluentWebElement) super.p(by);
    }

    @Override
    public Internal.FluentWebElements ps(By by) {
        return (Internal.FluentWebElements) super.ps(by);
    }

    @Override
    public Internal.FluentWebElement img() {
        return (Internal.FluentWebElement) super.img();
    }

    @Override
    public Internal.FluentWebElements imgs() {
        return (Internal.FluentWebElements) super.imgs();
    }

    @Override
    public Internal.FluentWebElement img(By by) {
        return (Internal.FluentWebElement) super.img(by);
    }

    @Override
    public Internal.FluentWebElements imgs(By by) {
        return (Internal.FluentWebElements) super.imgs(by);
    }

    @Override
    public Internal.FluentWebElement table() {
        return (Internal.FluentWebElement) super.table();
    }

    @Override
    public Internal.FluentWebElements tables() {
        return (Internal.FluentWebElements) super.tables();
    }

    @Override
    public Internal.FluentWebElement table(By by) {
        return (Internal.FluentWebElement) super.table(by);
    }

    @Override
    public Internal.FluentWebElements tables(By by) {
        return (Internal.FluentWebElements) super.tables(by);
    }

    @Override
    public Internal.FluentWebElement fieldset() {
        return (Internal.FluentWebElement) super.fieldset();
    }

    @Override
    public Internal.FluentWebElements fieldsets() {
        return (Internal.FluentWebElements) super.fieldsets();
    }

    @Override
    public Internal.FluentWebElement fieldset(By by) {
        return (Internal.FluentWebElement) super.fieldset(by);
    }

    @Override
    public Internal.FluentWebElements fieldsets(By by) {
        return (Internal.FluentWebElements) super.fieldsets(by);
    }

    @Override
    public Internal.FluentWebElement legend() {
        return (Internal.FluentWebElement) super.legend();
    }

    @Override
    public Internal.FluentWebElements legends() {
        return (Internal.FluentWebElements) super.legends();
    }

    @Override
    public Internal.FluentWebElement legend(By by) {
        return (Internal.FluentWebElement) super.legend(by);
    }

    @Override
    public Internal.FluentWebElements legends(By by) {
        return (Internal.FluentWebElements) super.legends(by);
    }

    @Override
    public Internal.FluentWebElement tr() {
        return (Internal.FluentWebElement) super.tr();
    }

    @Override
    public Internal.FluentWebElements trs() {
        return (Internal.FluentWebElements) super.trs();
    }

    @Override
    public Internal.FluentWebElement tr(By by) {
        return (Internal.FluentWebElement) super.tr(by);
    }

    @Override
    public Internal.FluentWebElements trs(By by) {
        return (Internal.FluentWebElements) super.trs(by);
    }

    @Override
    public Internal.FluentWebElement td() {
        return (Internal.FluentWebElement) super.td();
    }

    @Override
    public Internal.FluentWebElements tds() {
        return (Internal.FluentWebElements) super.tds();
    }

    @Override
    public Internal.FluentWebElement td(By by) {
        return (Internal.FluentWebElement) super.td(by);
    }

    @Override
    public Internal.FluentWebElements tds(By by) {
        return (Internal.FluentWebElements) super.tds(by);
    }

    @Override
    public Internal.FluentWebElement th() {
        return (Internal.FluentWebElement) super.th();
    }

    @Override
    public Internal.FluentWebElements ths() {
        return (Internal.FluentWebElements) super.ths();
    }

    @Override
    public Internal.FluentWebElement th(By by) {
        return (Internal.FluentWebElement) super.th(by);
    }

    @Override
    public Internal.FluentWebElements ths(By by) {
        return (Internal.FluentWebElements) super.ths(by);
    }

    @Override
    public Internal.FluentWebElement ul() {
        return (Internal.FluentWebElement) super.ul();
    }

    @Override
    public Internal.FluentWebElements uls() {
        return (Internal.FluentWebElements) super.uls();
    }

    @Override
    public Internal.FluentWebElement ul(By by) {
        return (Internal.FluentWebElement) super.ul(by);
    }

    @Override
    public Internal.FluentWebElements uls(By by) {
        return (Internal.FluentWebElements) super.uls(by);
    }

    @Override
    public Internal.FluentWebElement ol() {
        return (Internal.FluentWebElement) super.ol();
    }

    @Override
    public Internal.FluentWebElements ols() {
        return (Internal.FluentWebElements) super.ols();
    }

    @Override
    public Internal.FluentWebElement ol(By by) {
        return (Internal.FluentWebElement) super.ol(by);
    }

    @Override
    public Internal.FluentWebElements ols(By by) {
        return (Internal.FluentWebElements) super.ols(by);
    }

    @Override
    public Internal.FluentWebElement form() {
        return (Internal.FluentWebElement) super.form();
    }

    @Override
    public Internal.FluentWebElements forms() {
        return (Internal.FluentWebElements) super.forms();
    }

    @Override
    public Internal.FluentWebElement form(By by) {
        return (Internal.FluentWebElement) super.form(by);
    }

    @Override
    public Internal.FluentWebElements forms(By by) {
        return (Internal.FluentWebElements) super.forms(by);
    }

    @Override
    public Internal.FluentWebElement textarea() {
        return (Internal.FluentWebElement) super.textarea();
    }

    @Override
    public Internal.FluentWebElements textareas() {
        return (Internal.FluentWebElements) super.textareas();
    }

    @Override
    public Internal.FluentWebElement textarea(By by) {
        return (Internal.FluentWebElement) super.textarea(by);
    }

    @Override
    public Internal.FluentWebElements textareas(By by) {
        return (Internal.FluentWebElements) super.textareas(by);
    }

    @Override
    public Internal.FluentWebElement option() {
        return (Internal.FluentWebElement) super.option();
    }

    @Override
    public Internal.FluentWebElements options() {
        return (Internal.FluentWebElements) super.options();
    }

    @Override
    public Internal.FluentWebElement option(By by) {
        return (Internal.FluentWebElement) super.option(by);
    }

    @Override
    public Internal.FluentWebElements options(By by) {
        return (Internal.FluentWebElements) super.options(by);
    }

    @Override
    public Internal.FluentWebElement li() {
        return (Internal.FluentWebElement) super.li();
    }

    @Override
    public Internal.FluentWebElement li(By by) {
        return (Internal.FluentWebElement) super.li(by);
    }

    @Override
    public Internal.FluentWebElements lis() {
        return (Internal.FluentWebElements) super.lis();
    }

    @Override
    public Internal.FluentWebElements lis(By by) {
        return (Internal.FluentWebElements) super.lis(by);
    }

    @Override
    public Internal.FluentWebElement map() {
        return (Internal.FluentWebElement) super.map();
    }

    @Override
    public Internal.FluentWebElements maps() {
        return (Internal.FluentWebElements) super.maps();
    }

    @Override
    public Internal.FluentWebElement map(By by) {
        return (Internal.FluentWebElement) super.map(by);
    }

    @Override
    public Internal.FluentWebElements maps(By by) {
        return (Internal.FluentWebElements) super.maps(by);
    }

    @Override
    public TestableString url() {
        return super.url();
    }

    @Override
    public TestableString title() {
        return super.title();
    }
}
