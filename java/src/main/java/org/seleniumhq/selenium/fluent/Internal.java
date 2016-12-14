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

import org.openqa.selenium.*;
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
import static org.seleniumhq.selenium.fluent.Internal.BaseFluentWebDriver.wrapAssertionError;
import static org.seleniumhq.selenium.fluent.Internal.BaseFluentWebDriver.wrapRuntimeException;

public class Internal {

    public abstract static class BaseFluentWebDriver {

        protected static final String GENERIC_TAG = "*";

        protected final WebDriver delegate;
        protected final Context context;
        protected final Monitor monitor;
        protected final boolean booleanInsteadOfNotFoundException;

        protected BaseFluentWebDriver(WebDriver delegate, Context context, Monitor monitor, boolean booleanInsteadOfNotFoundException) {
            this.delegate = delegate;
            this.context = context;
            this.monitor = monitor;
            this.booleanInsteadOfNotFoundException = booleanInsteadOfNotFoundException;
        }

        protected void beforeFindElement(By by) {
            if (by instanceof FluentBy) {
                ((FluentBy) by).beforeFindElement(delegate);
            }
        }

        protected SearchContext getSearchContext() {
            return delegate;
        }

        protected BaseFluentWebElements newFluentWebElements(MultipleResult multiple) {
            List<WebElement> result = multiple.getResult();
            Context ctx = multiple.getCtx();
            List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
            for (WebElement aResult : result) {
                elems.add(new FluentWebElement(delegate, new WebElementHolder(null, aResult, null), ctx, monitor, booleanInsteadOfNotFoundException));
            }
            return new FluentWebElements(delegate, elems, ctx, monitor, booleanInsteadOfNotFoundException);
        }

        private FluentSelects newFluentSelects(List<WebElement> result, Context ctx) {
            List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
            for (WebElement aResult : result) {
                elems.add(new FluentSelect(delegate, aResult, ctx, monitor, booleanInsteadOfNotFoundException));
            }
            return new FluentSelects(delegate, elems, ctx, monitor, booleanInsteadOfNotFoundException);
        }

        protected BaseFluentWebElement element() {
            SingleResult single = single(tagName(GENERIC_TAG), GENERIC_TAG);
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElement element(By by) {
            SingleResult single = single(by, GENERIC_TAG);
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements elements() {
            return newFluentWebElements(multiple(tagName(GENERIC_TAG), GENERIC_TAG));
        }

        protected BaseFluentWebElements elements(By by) {
            return newFluentWebElements(multiple(by, GENERIC_TAG));
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
            return new FluentSelect(delegate, single.getResult().getFound(), single.getCtx(), monitor, booleanInsteadOfNotFoundException);
        }

        public FluentSelect select(By by) {
            SingleResult single = single(by, "select");
            return new FluentSelect(delegate, single.getResult().getFound(), single.getCtx(), monitor, booleanInsteadOfNotFoundException);
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

        protected BaseFluentWebElement h5(){
            SingleResult single = single(tagName("h5"), "h5");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h5s() {
            return newFluentWebElements(multiple(tagName("h5"), "h5"));
        }

        protected BaseFluentWebElement h5(By by) {
            SingleResult single = single(by, "h5");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h5s(By by) {
            return newFluentWebElements(multiple(by, "h5"));
        }

        protected BaseFluentWebElement h6(){
            SingleResult single = single(tagName("h6"), "h6");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h6s() {
            return newFluentWebElements(multiple(tagName("h6"), "h6"));
        }

        protected BaseFluentWebElement h6(By by) {
            SingleResult single = single(by, "h6");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements h6s(By by) {
            return newFluentWebElements(multiple(by, "h6"));
        }
        protected BaseFluentWebElement p() {
            SingleResult single = single(tagName("p"), "p");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements ps() {
            return newFluentWebElements(multiple(tagName("p"), "p"));
        }
        protected BaseFluentWebElement nav() {
            SingleResult single = single(tagName("nav"), "nav");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements navs() {
            return newFluentWebElements(multiple(tagName("nav"), "nav"));
        }

        protected BaseFluentWebElement nav(By by) {
            SingleResult single = single(by, "nav");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements navs(By by) {
            return newFluentWebElements(multiple(by, "nav"));
        }

        protected BaseFluentWebElement tbody() {
            SingleResult single = single(tagName("tbody"), "tbody");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements tbodys() {
            return newFluentWebElements(multiple(tagName("tbody"), "tbody"));
        }

        protected BaseFluentWebElement tbody(By by) {
            SingleResult single = single(by, "tbody");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements tbodys(By by) {
            return newFluentWebElements(multiple(by, "tbody"));
        }

        protected BaseFluentWebElement p(By by) {
            SingleResult single = single(by, "p");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }
        protected BaseFluentWebElement figure() {
            SingleResult single = single(tagName("figure"), "figure");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }
        protected BaseFluentWebElement abbr() {
            SingleResult single = single(tagName("abbr"), "abbr");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }
        protected BaseFluentWebElement blockquote() {
            SingleResult single = single(tagName("blockquote"), "blockquote");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }
        protected BaseFluentWebElement label() {
            SingleResult single = single(tagName("label"), "label");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements labels() {
            return newFluentWebElements(multiple(tagName("label"), "label"));
        }

        protected BaseFluentWebElement label(By by) {
            SingleResult single = single(by, "label");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements labels(By by) {
            return newFluentWebElements(multiple(by, "label"));
        }

        protected BaseFluentWebElement object() {
            SingleResult single = single(tagName("object"), "object");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements objects() {
            return newFluentWebElements(multiple(tagName("object"), "object"));
        }

        protected BaseFluentWebElement object(By by) {
            SingleResult single = single(by, "object");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements objects(By by) {
            return newFluentWebElements(multiple(by, "object"));
        }

        protected BaseFluentWebElements blockquotes() {
            return newFluentWebElements(multiple(tagName("blockquote"), "blockquote"));
        }

        protected BaseFluentWebElement blockquote(By by) {
            SingleResult single = single(by, "blockquote");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements blockquotes(By by) {
            return newFluentWebElements(multiple(by, "blockquote"));
        }

        protected BaseFluentWebElement area() {
            SingleResult single = single(tagName("area"), "area");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements areas() {
            return newFluentWebElements(multiple(tagName("area"), "area"));
        }

        protected BaseFluentWebElement area(By by) {
            SingleResult single = single(by, "area");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements areas(By by) {
            return newFluentWebElements(multiple(by, "area"));
        }

        protected BaseFluentWebElement body() {
            SingleResult single = single(tagName("body"), "body");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements abbrs() {
            return newFluentWebElements(multiple(tagName("abbr"), "abbr"));
        }

        protected BaseFluentWebElement abbr(By by) {
            SingleResult single = single(by, "abbr");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements abbrs(By by) {
            return newFluentWebElements(multiple(by, "abbr"));
        }

        protected BaseFluentWebElement address() {
            SingleResult single = single(tagName("address"), "address");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements addresses() {
            return newFluentWebElements(multiple(tagName("address"), "address"));
        }

        protected BaseFluentWebElement address(By by) {
            SingleResult single = single(by, "address");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements addresses(By by) {
            return newFluentWebElements(multiple(by, "address"));
        }

        protected BaseFluentWebElements figures() {
            return newFluentWebElements(multiple(tagName("figure"), "figure"));
        }

        protected BaseFluentWebElement figure(By by) {
            SingleResult single = single(by, "figure");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements figures(By by) {
            return newFluentWebElements(multiple(by, "figure"));
        }

        protected BaseFluentWebElement acronym() {
            SingleResult single = single(tagName("acronym"), "acronym");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements acronyms() {
            return newFluentWebElements(multiple(tagName("acronym"), "acronym"));
        }

        protected BaseFluentWebElement acronym(By by) {
            SingleResult single = single(by, "acronym");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements acronyms(By by) {
            return newFluentWebElements(multiple(by, "acronym"));
        }

        protected BaseFluentWebElements ps(By by) {
            return newFluentWebElements(multiple(by, "p"));
        }
        protected BaseFluentWebElement b() {
            SingleResult single = single(tagName("b"), "b");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }
        protected BaseFluentWebElement header() {
            SingleResult single = single(tagName("header"), "header");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements headers() {
            return newFluentWebElements(multiple(tagName("header"), "header"));
        }

        protected BaseFluentWebElement header(By by) {
            SingleResult single = single(by, "header");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements headers(By by) {
            return newFluentWebElements(multiple(by, "header"));
        }

        protected BaseFluentWebElement footer() {
            SingleResult single = single(tagName("footer"), "footer");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements footers() {
            return newFluentWebElements(multiple(tagName("footer"), "footer"));
        }

        protected BaseFluentWebElement footer(By by) {
            SingleResult single = single(by, "footer");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements footers(By by) {
            return newFluentWebElements(multiple(by, "footer"));
        }

        protected BaseFluentWebElements bs() {
            return newFluentWebElements(multiple(tagName("b"), "b"));
        }

        protected BaseFluentWebElement b(By by) {
            SingleResult single = single(by, "b");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements bs(By by) {
            return newFluentWebElements(multiple(by, "b"));
        }

        protected BaseFluentWebElement pre() {
            SingleResult single = single(tagName("pre"), "pre");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements pres() {
            return newFluentWebElements(multiple(tagName("pre"), "pre"));
        }

        protected BaseFluentWebElement pre(By by) {
            SingleResult single = single(by, "pre");
            return newFluentWebElement(delegate, single.getResult(), single.getCtx());
        }

        protected BaseFluentWebElements pres(By by) {
            return newFluentWebElements(multiple(by, "pre"));
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
            return new FluentWebElement(delegate, result, context, monitor, booleanInsteadOfNotFoundException);
        }

        public TestableString url() {
            Execution<String> execution = new CurrentUrl();
            Context ctx = Context.singular(context, "url");
            return new TestableString(execution, ctx, monitor).within(getPeriod());
        }

        protected Period getPeriod() {
            return null;
        }

        public TestableString title() {
            Execution<String> execution = new GetTitle();
            Context ctx = Context.singular(context, "title");
            return new TestableString(execution, ctx, monitor).within(getPeriod());
        }

        protected abstract FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor);

        protected final By fixupBy(By by, String tagName) {
            if (by.getClass().getName().equals("org.openqa.selenium.By$ByXPath")) {
                by = xpath(".//" + tagName + "[" + by.toString().substring(by.toString().indexOf(":") + 1).trim() + "]");
            } else if (by.getClass().getName().equals("org.openqa.selenium.By$ByClassName")) {
                by = composite(new By.ByTagName(tagName), (By.ByClassName) by);
            } else if (by instanceof FluentBy.NotByAttribute) {
                by = xpath(".//" + tagName + "[" + ((FluentBy.NotByAttribute) by).nameAndValue() + "]");
            }
            return by;
        }

        protected final void assertTagIs(String is, String shouldBe) {
            if (!is.equalsIgnoreCase(shouldBe)) {
                throw new AssertionError("tag was incorrect, should have been " + shouldBe + " but was " + is);
            }
        }

        protected abstract WebElement findElement(By by, Context ctx, SearchContext searchContext);

        protected abstract List<WebElement> findElements(By by, Context ctx);

        private SingleResult single(final By by, final String tagName) {
            final By by2 = fixupBy(by, tagName);
            Context ctx = contextualize(by2, tagName);
            final WebElementHolder result;
            try {
                changeTimeout();
                FindElement execution = new FindElement(by2, tagName, ctx, getSearchContext());
                WebElement found = executeAndWrapReThrowIfNeeded(execution, null, ctx, true);
                result = new WebElementHolder(getSearchContext(), found, by2);
            } finally {
                resetTimeout();
            }
            return new SingleResult(result, ctx);
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

        private class FindElement extends Execution<WebElement> {
            private final By by2;
            private final String tagName;
            private final Context ctx;
            private final SearchContext searchContext;

            public FindElement(By by2, String tagName, Context ctx, SearchContext searchContext) {
                this.by2 = by2;
                this.tagName = tagName;
                this.ctx = ctx;
                this.searchContext = searchContext;
            }

            public WebElement execute() {
                if (booleanInsteadOfNotFoundException) {
                    try {
                        findElement(by2, ctx, searchContext);
                        return new FoundOrNotFound(true);
                    } catch (NotFoundException e) {
                        return new FoundOrNotFound(false);
                    }
                } else {
                    WebElement it = findElement(by2, ctx, searchContext);
                    if (!GENERIC_TAG.equals(tagName)) {
                        assertTagIs(it.getTagName(), tagName);
                    }
                    return it;
                }
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
                FindElements execution = new FindElements(by2, tagName, ctx);
                result = executeAndWrapReThrowIfNeeded(execution, null, ctx, true);
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

        private class FindElements extends Execution<List<WebElement>> {
            private final By by2;
            private final String tagName;
            private final Context ctx;

            public FindElements(By by2, String tagName, Context ctx) {
                this.by2 = by2;
                this.tagName = tagName;
                this.ctx = ctx;
            }

            public List<WebElement> execute() {
                List<WebElement> results = findElements(by2, ctx);
                if (!GENERIC_TAG.equals(tagName)) {
                    for (WebElement webElement : results) {
                        assertTagIs(webElement.getTagName(), tagName);
                    }
                }
                return results;
            }
        }

        protected static FluentExecutionStopped wrapRuntimeException(Context ctx, RuntimeException e) {
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

        protected static FluentExecutionStopped wrapAssertionError(Context ctx, AssertionError e) {
            return new FluentExecutionStopped(replacePkgNames(e) + ctx, e);
        }

        protected <T> T executeAndWrapReThrowIfNeeded(Execution<T> execution, WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {

            execution.withWebElementHolder(currentElement);
            Monitor.Timer timer = monitor.start(ctx.toString().substring(2));
            boolean success = false;
            try {
                T t = execution.doExecution();
                success = true;
                return t;
            } catch (UnsupportedOperationException e) {
                throw e;
            } catch (RuntimeException e) {
                FluentExecutionStopped ex = wrapRuntimeException(ctx, e);
                if (expectedToBeThere) {
                    throw monitor.exceptionDuringExecution(ex, currentWebElement(execution));
                } else {
                    throw ex;
                }
            } catch (AssertionError e) {
                FluentExecutionStopped ex = wrapAssertionError(ctx, e);
                if (expectedToBeThere) {
                    throw monitor.exceptionDuringExecution(ex, currentWebElement(execution));
                } else {
                    throw ex;
                }
            } finally {
                timer.end(success);
            }
        }

        private <T> WebElement currentWebElement(Execution<T> execution) {
            WebElement webElement = execution.getWebElement();
            if (webElement == null && execution instanceof FindElement) {
                SearchContext searchContext = ((FindElement) execution).searchContext;
                if (searchContext instanceof WebElement) {
                    webElement = (WebElement) searchContext;
                }
            }
            return webElement;
        }

        protected void changeTimeout() {
        }

        protected void resetTimeout() {
        }

        protected final WebElement retryingFindIt(By by, SearchContext searchContext) {
            long endMillis = getPeriod().getEndMillis(System.currentTimeMillis());
            RuntimeException exceptionCausingRetry = null;
            boolean toRetry = true;
            WebElement it = null;
            while (toRetry && endMillis - System.currentTimeMillis() > 0) {
                try {
                    it = actualFindElement(by, context, searchContext);
                    toRetry = false;
                    return it;
                } catch (WebDriverException e) {
                    exceptionCausingRetry = e;
                }
            }
            throw exceptionCausingRetry;
        }

        protected final List<WebElement> retryingFindThem(By by) {
            long endMillis = getPeriod().getEndMillis(System.currentTimeMillis());
            RuntimeException exceptionCausingRetry = null;
            boolean toRetry = true;
            List<WebElement> them = null;
            while (toRetry && endMillis - System.currentTimeMillis() > 0) {
                try {
                    them = actualFindElements(by, context);
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

        protected abstract WebElement actualFindElement(By by, Context ctx, SearchContext searchContext);

        protected abstract List<WebElement> actualFindElements(By by, Context ctx);

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

    public abstract static class BaseFluentWebElement extends BaseFluentWebDriver {

        public BaseFluentWebElement(WebDriver delegate, Context context, Monitor monitor, boolean booleanInsteadOfNoSuchElement) {
            super(delegate, context, monitor, booleanInsteadOfNoSuchElement);
        }

        protected abstract WebElement getWebElement();

        @Override
        protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor1) {
            return new FluentWebElements(super.delegate, results, context, monitor, booleanInsteadOfNotFoundException);
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
        protected BaseFluentWebElements(WebDriver delegate, Context context, Monitor monitor, boolean booleanInsteadOfNoSuchElement) {
            super(delegate, context, monitor, booleanInsteadOfNoSuchElement);
        }

        @Override
        protected WebElement getWebElement() {
            throw new UnsupportedOperationException();
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
        protected final Monitor monitor;


        public BaseTestableObject(Period within, Execution<T> execution, Context context, Monitor monitor) {
            this.within = within;
            this.execution = execution;
            this.context = context;
            this.monitor = monitor;
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
                throw monitor.exceptionDuringExecution(wrapRuntimeException(ctx, e), execution.getWebElement());
            } catch (AssertionError e) {
                throw monitor.exceptionDuringExecution(wrapAssertionError(ctx, e), execution.getWebElement());
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
            validateWrapRethrow(new ShouldBeValidation<T>(shouldBe),
                    Context.singular(context, "shouldBe", null, shouldBe));
        }

        public void baseShouldNotBe(final T shouldNotBe) {
            validateWrapRethrow(new ShouldNotBeValidation<T>(shouldNotBe),
                    Context.singular(context, "shouldNotBe", null, shouldNotBe));
        }

        private class ShouldNotBeValidation<T> extends Validation {
            private final T shouldNotBe;

            public ShouldNotBeValidation(T shouldNotBe) {
                this.shouldNotBe = shouldNotBe;
            }

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
        }

        private class ShouldBeValidation<T> extends Validation {
            private final T shouldBe;

            public ShouldBeValidation(T shouldBe) {
                this.shouldBe = shouldBe;
            }

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
        }
    }

    public abstract static class Validation {
        public abstract void validate(long start);
    }

    public static class NothingMatches extends RuntimeException {
    }

    public static class FoundOrNotFound implements WebElement {

        private boolean found;

        public boolean isFound() {
            return found;
        }

        public FoundOrNotFound(boolean found) {
            this.found = found;
        }

        public void click() {
            throw new UnsupportedOperationException();
        }

        public void submit() {
            throw new UnsupportedOperationException();
        }

        public void sendKeys(CharSequence... keysToSend) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public String getTagName() {
            throw new UnsupportedOperationException();
        }

        public String getAttribute(String name) {
            throw new UnsupportedOperationException();
        }

        public boolean isSelected() {
            throw new UnsupportedOperationException();
        }

        public boolean isEnabled() {
            throw new UnsupportedOperationException();
        }

        public String getText() {
            throw new UnsupportedOperationException();
        }

        public List<WebElement> findElements(By by) {
            throw new UnsupportedOperationException();
        }

        public WebElement findElement(By by) {
            throw new UnsupportedOperationException();
        }

        public boolean isDisplayed() {
            throw new UnsupportedOperationException();
        }

        public Point getLocation() {
            throw new UnsupportedOperationException();
        }

        public Dimension getSize() {
            throw new UnsupportedOperationException();
        }

        public String getCssValue(String propertyName) {
            throw new UnsupportedOperationException();
        }

        public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException { throw new UnsupportedOperationException(); }

        public Rectangle getRect() {
            throw new UnsupportedOperationException();
        }
    }
}
