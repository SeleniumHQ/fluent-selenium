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
import org.seleniumhq.selenium.fluent.internal.NegatingFluentWebDriver;
import org.seleniumhq.selenium.fluent.internal.RetryingFluentWebDriver;

import java.util.ArrayList;
import java.util.List;

public class FluentWebDriver extends Internal.BaseFluentWebDriver {

    public FluentWebDriver(WebDriver delegate) {
        this(delegate, new Monitor.NULL(), null, false);
    }

    protected FluentWebDriver(WebDriver delegate, Context context) {
        this(delegate, new Monitor.NULL(), context, false);
    }

    public FluentWebDriver(WebDriver webDriver, Monitor monitor) {
        this(webDriver, monitor, null, false);
    }

    protected FluentWebDriver(WebDriver webDriver, Monitor monitor, Context context, boolean booleanInsteadOfNoSuchElement) {
        super(webDriver, context, monitor, booleanInsteadOfNoSuchElement);
    }

    @Override
    protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor) {
        return new FluentWebElements(super.delegate, results, context, monitor, booleanInsteadOfNotFoundException);
    }

    protected WebElement findElement(By by, Context ctx, SearchContext searchContext) {
        return actualFindElement(by, ctx, searchContext);
    }

    @Override
    protected List<WebElement> findElements(By by, Context ctx) {
        return actualFindElements(by, ctx);
    }

    protected final WebElement actualFindElement(By by, Context ctx, SearchContext searchContext) {
        beforeFindElement(by);
        return searchContext.findElement(by);
    }

    protected final List<WebElement> actualFindElements(By by, Context ctx) {
        beforeFindElement(by);
        return delegate.findElements(by);
    }

    public FluentWebDriver within(final Period period) {
        return new RetryingFluentWebDriver(delegate, period, Context.singular(context, "within", null, period), monitor);
    }

    public NegatingFluentWebDriver without(Period period) {
        return new NegatingFluentWebDriver(delegate, period, Context.singular(context, "without", null, period), monitor);
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
    public FluentSelect select() {
        return super.select();
    }

    @Override
    public FluentSelect select(By by) {
        return super.select(by);
    }

    @Override
    public FluentSelects selects() {
        return super.selects();
    }

    @Override
    public FluentSelects selects(By by) {
        return super.selects(by);
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
    public FluentWebElements h3s() {
        return (FluentWebElements) super.h3s();
    }

    @Override
    public FluentWebElement h3(By by) {
        return (FluentWebElement) super.h3(by);
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
    public FluentWebElements h4s() {
        return (FluentWebElements) super.h4s();
    }

    @Override
    public FluentWebElement h4(By by) {
        return (FluentWebElement) super.h4(by);
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
    public FluentWebElements h5s() {
        return (FluentWebElements) super.h5s();
    }

    @Override
    public FluentWebElement h5(By by) {
        return (FluentWebElement) super.h5(by);
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
    public FluentWebElements h6s() {
        return (FluentWebElements) super.h6s();
    }

    @Override
    public FluentWebElement h6(By by) {
        return (FluentWebElement) super.h6(by);
    }

    @Override
    public FluentWebElements h6s(By by) {
        return (FluentWebElements) super.h6s(by);
    }

    @Override
    public FluentWebElement p() {
        return (FluentWebElement) super.p();
    }

    @Override
    public FluentWebElements ps() {
        return (FluentWebElements) super.ps();
    }
    @Override
    public FluentWebElement nav() {
        return (FluentWebElement) super.nav();
    }

    @Override
    public FluentWebElements navs() {
        return (FluentWebElements) super.navs();
    }

    @Override
    public FluentWebElement nav(By by) {
        return (FluentWebElement) super.nav(by);
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
    public FluentWebElements tbodys() {
        return (FluentWebElements) super.tbodys();
    }

    @Override
    public FluentWebElement tbody(By by) {
        return (FluentWebElement) super.tbody(by);
    }

    @Override
    public FluentWebElements tbodys(By by) {
        return (FluentWebElements) super.tbodys(by);
    }

    @Override
    public FluentWebElement p(By by) {
        return (FluentWebElement) super.p(by);
    }
    @Override
    public FluentWebElement figure() {
        return (FluentWebElement) super.figure();
    }
    @Override
    public FluentWebElement abbr() {
        return (FluentWebElement) super.abbr();
    }
    @Override
    public FluentWebElement blockquote() {
        return (FluentWebElement) super.blockquote();
    }
    @Override
    public FluentWebElement label() {
        return (FluentWebElement) super.label();
    }

    @Override
    public FluentWebElements labels() {
        return (FluentWebElements) super.labels();
    }

    @Override
    public FluentWebElement label(By by) {
        return (FluentWebElement) super.label(by);
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
    public FluentWebElements objects() {
        return (FluentWebElements) super.objects();
    }

    @Override
    public FluentWebElement object(By by) {
        return (FluentWebElement) super.object(by);
    }

    @Override
    public FluentWebElements objects(By by) {
        return (FluentWebElements) super.objects(by);
    }

    @Override
    public FluentWebElements blockquotes() {
        return (FluentWebElements) super.blockquotes();
    }

    @Override
    public FluentWebElement blockquote(By by) {
        return (FluentWebElement) super.blockquote(by);
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
    public FluentWebElements areas() {
        return (FluentWebElements) super.areas();
    }

    @Override
    public FluentWebElement area(By by) {
        return (FluentWebElement) super.area(by);
    }

    @Override
    public FluentWebElements areas(By by) {
        return (FluentWebElements) super.areas(by);
    }

    @Override
    public FluentWebElement body() {
        return (FluentWebElement) super.body();
    }

    @Override
    public FluentWebElements abbrs() {
        return (FluentWebElements) super.abbrs();
    }

    @Override
    public FluentWebElement abbr(By by) {
        return (FluentWebElement) super.abbr(by);
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
    public FluentWebElements addresses() {
        return (FluentWebElements) super.addresses();
    }

    @Override
    public FluentWebElement address(By by) {
        return (FluentWebElement) super.address(by);
    }

    @Override
    public FluentWebElements addresses(By by) {
        return (FluentWebElements) super.addresses(by);
    }

    @Override
    public FluentWebElements figures() {
        return (FluentWebElements) super.figures();
    }

    @Override
    public FluentWebElement figure(By by) {
        return (FluentWebElement) super.figure(by);
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
    public FluentWebElements acronyms() {
        return (FluentWebElements) super.acronyms();
    }

    @Override
    public FluentWebElement acronym(By by) {
        return (FluentWebElement) super.acronym(by);
    }

    @Override
    public FluentWebElements acronyms(By by) {
        return (FluentWebElements) super.acronyms(by);
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
    public FluentWebElement header() {
        return (FluentWebElement) super.header();
    }

    @Override
    public FluentWebElements headers() {
        return (FluentWebElements) super.headers();
    }

    @Override
    public FluentWebElement header(By by) {
        return (FluentWebElement) super.header(by);
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
    public FluentWebElements footers() {
        return (FluentWebElements) super.footers();
    }

    @Override
    public FluentWebElement footer(By by) {
        return (FluentWebElement) super.footer(by);
    }

    @Override
    public FluentWebElements footers(By by) {
        return (FluentWebElements) super.footers(by);
    }

    @Override
    public FluentWebElements bs() {
        return (FluentWebElements) super.bs();
    }

    @Override
    public FluentWebElement b(By by) {
        return (FluentWebElement) super.b(by);
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
    public FluentWebElements pres() {
        return (FluentWebElements) super.pres();
    }

    @Override
    public FluentWebElement pre(By by) {
        return (FluentWebElement) super.pre(by);
    }

    @Override
    public FluentWebElements pres(By by) {
        return (FluentWebElements) super.pres(by);
    }

    @Override
    public FluentWebElement img() {
        return (FluentWebElement) super.img();
    }

    @Override
    public FluentWebElements imgs() {
        return (FluentWebElements) super.imgs();
    }

    @Override
    public FluentWebElement img(By by) {
        return (FluentWebElement) super.img(by);
    }

    @Override
    public FluentWebElements imgs(By by) {
        return (FluentWebElements) super.imgs(by);
    }

    @Override
    public FluentWebElement table() {
        return (FluentWebElement) super.table();
    }

    @Override
    public FluentWebElements tables() {
        return (FluentWebElements) super.tables();
    }

    @Override
    public FluentWebElement table(By by) {
        return (FluentWebElement) super.table(by);
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
    public FluentWebElements fieldsets() {
        return (FluentWebElements) super.fieldsets();
    }

    @Override
    public FluentWebElement fieldset(By by) {
        return (FluentWebElement) super.fieldset(by);
    }

    @Override
    public FluentWebElements fieldsets(By by) {
        return (FluentWebElements) super.fieldsets(by);
    }

    @Override
    public FluentWebElement legend() {
        return (FluentWebElement) super.legend();
    }

    @Override
    public FluentWebElements legends() {
        return (FluentWebElements) super.legends();
    }

    @Override
    public FluentWebElement legend(By by) {
        return (FluentWebElement) super.legend(by);
    }

    @Override
    public FluentWebElements legends(By by) {
        return (FluentWebElements) super.legends(by);
    }

    @Override
    public FluentWebElement tr() {
        return (FluentWebElement) super.tr();
    }

    @Override
    public FluentWebElements trs() {
        return (FluentWebElements) super.trs();
    }

    @Override
    public FluentWebElement tr(By by) {
        return (FluentWebElement) super.tr(by);
    }

    @Override
    public FluentWebElements trs(By by) {
        return (FluentWebElements) super.trs(by);
    }

    @Override
    public FluentWebElement td() {
        return (FluentWebElement) super.td();
    }

    @Override
    public FluentWebElements tds() {
        return (FluentWebElements) super.tds();
    }

    @Override
    public FluentWebElement td(By by) {
        return (FluentWebElement) super.td(by);
    }

    @Override
    public FluentWebElements tds(By by) {
        return (FluentWebElements) super.tds(by);
    }

    @Override
    public FluentWebElement th() {
        return (FluentWebElement) super.th();
    }

    @Override
    public FluentWebElements ths() {
        return (FluentWebElements) super.ths();
    }

    @Override
    public FluentWebElement th(By by) {
        return (FluentWebElement) super.th(by);
    }

    @Override
    public FluentWebElements ths(By by) {
        return (FluentWebElements) super.ths(by);
    }

    @Override
    public FluentWebElement ul() {
        return (FluentWebElement) super.ul();
    }

    @Override
    public FluentWebElements uls() {
        return (FluentWebElements) super.uls();
    }

    @Override
    public FluentWebElement ul(By by) {
        return (FluentWebElement) super.ul(by);
    }

    @Override
    public FluentWebElements uls(By by) {
        return (FluentWebElements) super.uls(by);
    }

    @Override
    public FluentWebElement ol() {
        return (FluentWebElement) super.ol();
    }

    @Override
    public FluentWebElements ols() {
        return (FluentWebElements) super.ols();
    }

    @Override
    public FluentWebElement ol(By by) {
        return (FluentWebElement) super.ol(by);
    }

    @Override
    public FluentWebElements ols(By by) {
        return (FluentWebElements) super.ols(by);
    }

    @Override
    public FluentWebElement form() {
        return (FluentWebElement) super.form();
    }

    @Override
    public FluentWebElements forms() {
        return (FluentWebElements) super.forms();
    }

    @Override
    public FluentWebElement form(By by) {
        return (FluentWebElement) super.form(by);
    }

    @Override
    public FluentWebElements forms(By by) {
        return (FluentWebElements) super.forms(by);
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
    public FluentWebElement map() {
        return (FluentWebElement) super.map();
    }

    @Override
    public FluentWebElements maps() {
        return (FluentWebElements) super.maps();
    }

    @Override
    public FluentWebElement map(By by) {
        return (FluentWebElement) super.map(by);
    }

    @Override
    public FluentWebElements maps(By by) {
        return (FluentWebElements) super.maps(by);
    }

    @Override
    public TestableString url() {
        return super.url();
    }

    @Override
    public TestableString title() {
        return super.title();
    }


    public BooleanResultsAdapter hasMissing() {
        return new BooleanResultsAdapter(super.delegate, super.monitor, super.context)
                .invert(true);
    }

    public BooleanResultsAdapter has() {
        return new BooleanResultsAdapter(super.delegate, super.monitor, super.context)
                .invert(false);
    }

    public static class BooleanResultsAdapter {

        private Internal.BaseFluentWebDriver bfwd;
        private boolean invert;

        protected BooleanResultsAdapter(WebDriver webDriver, Monitor monitor, Context context) {
            bfwd = new FluentWebDriver(webDriver, monitor, context, true);
        }

        public BooleanResultsAdapter(WebDriver webDriver, Internal.WebElementHolder elementHolder, Monitor monitor, Context context) {
            bfwd = new FluentWebElement(webDriver, elementHolder, context, monitor, true);
        }

        public BooleanResultsAdapter invert(boolean b) {
            this.invert = b;
            return this;
        }

        public boolean element() {
            return returnBool(bfwd.element());
        }

        public boolean element(By by) {
            return returnBool(bfwd.element(by));
        }

        public boolean span() {
            return returnBool(bfwd.span());
        }

        public boolean span(By by) {
            return returnBool(bfwd.span(by));
        }

        public boolean div() {
            return returnBool(bfwd.div());
        }

        public boolean div(By by) {
            return returnBool(bfwd.div(by));
        }

        public boolean button() {
            return returnBool(bfwd.button());
        }

        public boolean button(By by) {
            return returnBool(bfwd.button(by));
        }

        public boolean link() {
            return returnBool(bfwd.link());
        }

        public boolean link(By by) {
            return returnBool(bfwd.link(by));
        }

        public boolean input() {
            return returnBool(bfwd.input());
        }

        public boolean input(By by) {
            return returnBool(bfwd.input(by));
        }

        public boolean select() {
            return returnBool(bfwd.select());
        }

        public boolean select(By by) {
            return returnBool(bfwd.select(by));
        }

        public boolean h1() {
            return returnBool(bfwd.h1());
        }

        public boolean h1(By by) {
            return returnBool(bfwd.h1(by));
        }

        public boolean h2() {
            return returnBool(bfwd.h2());
        }

        public boolean h2(By by) {
            return returnBool(bfwd.h2(by));
        }

        public boolean h3() {
            return returnBool(bfwd.h3());
        }

        public boolean h3(By by) {
            return returnBool(bfwd.h3(by));
        }

        public boolean h4() {
            return returnBool(bfwd.h4());
        }

        public boolean h4(By by) {
            return returnBool(bfwd.h4(by));
        }


        public boolean p() {
            return returnBool(bfwd.p());
        }


        public boolean p(By by) {
            return returnBool(bfwd.p(by));
        }

        public boolean b() {
            return returnBool(bfwd.b());
        }


        public boolean b(By by) {
            return returnBool(bfwd.b(by));
        }

        public boolean pre() {
            return returnBool(bfwd.pre());
        }


        public boolean pre(By by) {
            return returnBool(bfwd.pre(by));
        }

        public boolean header() {
            return returnBool(bfwd.header());
        }


        public boolean header(By by) {
            return returnBool(bfwd.header(by));
        }

        public boolean footer() {
            return returnBool(bfwd.footer());
        }


        public boolean footer(By by) {
            return returnBool(bfwd.footer(by));
        }

        public boolean figure() {
            return returnBool(bfwd.figure());
        }


        public boolean figure(By by) {
            return returnBool(bfwd.figure(by));
        }

        public boolean acronym() {
            return returnBool(bfwd.acronym());
        }


        public boolean acronym(By by) {
            return returnBool(bfwd.acronym(by));
        }

        public boolean abbr() {
            return returnBool(bfwd.abbr());
        }


        public boolean abbr(By by) {
            return returnBool(bfwd.abbr(by));
        }

        public boolean address() {
            return returnBool(bfwd.address());
        }


        public boolean address(By by) {
            return returnBool(bfwd.address(by));
        }

        public boolean blockquote() {
            return returnBool(bfwd.blockquote());
        }


        public boolean blockquote(By by) {
            return returnBool(bfwd.blockquote(by));
        }

        public boolean area() {
            return returnBool(bfwd.area());
        }


        public boolean area(By by) {
            return returnBool(bfwd.area(by));
        }

        public boolean label() {
            return returnBool(bfwd.label());
        }


        public boolean label(By by) {
            return returnBool(bfwd.label(by));
        }

        public boolean object() {
            return returnBool(bfwd.object());
        }


        public boolean object(By by) {
            return returnBool(bfwd.object(by));
        }

        public boolean nav() {
            return returnBool(bfwd.nav());
        }


        public boolean nav(By by) {
            return returnBool(bfwd.nav(by));
        }

        public boolean tbody() {
            return returnBool(bfwd.tbody());
        }


        public boolean tbody(By by) {
            return returnBool(bfwd.tbody(by));
        }

        public boolean img() {
            return returnBool(bfwd.img());
        }

        public boolean img(By by) {
            return returnBool(bfwd.img(by));
        }

        public boolean table() {
            return returnBool(bfwd.table());
        }

        public boolean table(By by) {
            return returnBool(bfwd.table(by));
        }

        public boolean fieldset() {
            return returnBool(bfwd.fieldset());
        }

        public boolean fieldset(By by) {
            return returnBool(bfwd.fieldset(by));
        }

        public boolean legend() {
            return returnBool(bfwd.legend());
        }

        public boolean legend(By by) {
            return returnBool(bfwd.legend(by));
        }

        public boolean tr() {
            return returnBool(bfwd.tr());
        }

        public boolean tr(By by) {
            return returnBool(bfwd.tr(by));
        }

        public boolean td() {
            return returnBool(bfwd.td());
        }

        public boolean td(By by) {
            return returnBool(bfwd.td(by));
        }

        public boolean th() {
            return returnBool(bfwd.th());
        }

        public boolean th(By by) {
            return returnBool(bfwd.th(by));
        }

        public boolean ul() {
            return returnBool(bfwd.ul());
        }

        public boolean ul(By by) {
            return returnBool(bfwd.ul(by));
        }

        public boolean ol() {
            return returnBool(bfwd.ol());
        }

        public boolean ol(By by) {
            return returnBool(bfwd.ol(by));
        }

        public boolean form() {
            return returnBool(bfwd.form());
        }

        public boolean form(By by) {
            return returnBool(bfwd.form(by));
        }

        public boolean textarea() {
            return returnBool(bfwd.textarea());
        }

        public boolean textarea(By by) {
            return returnBool(bfwd.textarea(by));
        }

        public boolean option() {
            return returnBool(bfwd.option());
        }

        public boolean option(By by) {
            return returnBool(bfwd.option(by));
        }

        public boolean li() {
            return returnBool(bfwd.li());
        }

        public boolean li(By by) {
            return returnBool(bfwd.li(by));
        }

        public boolean map() {
            return returnBool(bfwd.map());
        }

        public boolean map(By by) {
            return returnBool(bfwd.map(by));
        }

        private boolean returnBool(Internal.BaseFluentWebElement bfwe) {
            boolean found = ((Internal.FoundOrNotFound) bfwe.getWebElement()).isFound();
            if (invert) {
                return !found;
            } else {
                return found;
            }
        }

    }
}
