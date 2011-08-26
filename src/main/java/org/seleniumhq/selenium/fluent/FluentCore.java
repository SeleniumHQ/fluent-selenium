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

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class FluentCore {

    protected final WebDriver delegate;
    protected final String context;

    public FluentCore(WebDriver delegate, String context) {
        this.delegate = delegate;
        this.context = context;
    }

    public FluentWebElement span() {
        return single(By.tagName("span"), "span");
    }

    public FluentWebElement span(By by) {
        return single(by, "span");
    }

    public FluentWebElements spans() {
        return multiple(By.tagName("span"), "span");
    }

    public FluentWebElements spans(By by) {
        return multiple(by, "span");
    }

    public FluentWebElement div() {
        return single(By.tagName("div"), "div");
    }

    public FluentWebElement div(By by) {
        return single(by, "div");
    }

    public FluentWebElements divs() {
        return multiple(By.tagName("div"), "div");
    }

    public FluentWebElements divs(By by) {
        return multiple(by, "div");
    }

    public FluentWebElement button() {
        return single(By.tagName("button"), "button");
    }

    public FluentWebElement button(By by) {
        return single(by, "button");
    }

    public FluentWebElements buttons() {
        return multiple(By.tagName("button"), "button");
    }

    public FluentWebElements buttons(By by) {
        return multiple(by, "button");
    }

    public FluentWebElement link() {
        return single(By.tagName("a"), "a");
    }

    public FluentWebElement link(By by) {
        return single(by, "a");
    }

    public FluentWebElements links() {
        return multiple(By.tagName("a"), "a");
    }

    public FluentWebElements links(By by) {
        return multiple(by, "a");
    }

    public FluentWebElement input() {
        return single(By.tagName("input"), "input");
    }

    public FluentWebElement input(By by) {
        return single(by, "input");

    }

    public FluentWebElements inputs() {
        return multiple(By.tagName("input"), "input");
    }

    public FluentWebElements inputs(By by) {
        return multiple(by, "input");
    }

    public FluentWebElement select() {
        return single(By.tagName("select"), "select");
    }

    public FluentWebElement select(By by) {
        return single(by, "select");
    }

    public FluentWebElements selects() {
        return multiple(By.tagName("select"), "select");
    }

    public FluentWebElements selects(By by) {
        return multiple(by, "select");
    }

    public FluentWebElement h1() {
        return single(By.tagName("h1"), "h1");
    }

    public FluentWebElement h1(By by) {
        return single(by, "h1");

    }

    public FluentWebElements h1s() {
        return multiple(By.tagName("h1"), "h1");
    }

    public FluentWebElements h1s(By by) {
        return multiple(by, "h1");
    }

    public FluentWebElement h2() {
        return single(By.tagName("h2"), "h2");
    }

    public FluentWebElement h2(By by) {
        return single(by, "h2");
    }

    public FluentWebElements h2s() {
        return multiple(By.tagName("h2"), "h2");
    }

    public FluentWebElements h2s(By by) {
        return multiple(by, "h2");
    }

    public FluentWebElement h3() {
        return single(By.tagName("h3"), "h3");
    }

    public FluentWebElements h3s() {
        return multiple(By.tagName("h3"), "h3");
    }

    public FluentWebElement h3(By by) {
        return single(by, "h3");
    }

    public FluentWebElements h3s(By by) {
        return multiple(by, "h3");
    }

    public FluentWebElement h4(){
        return single(By.tagName("h4"), "h4");
    }

    public FluentWebElements h4s() {
        return multiple(By.tagName("h4"), "h4");
    }

    public FluentWebElement h4(By by) {
        return single(by, "h4");
    }

    public FluentWebElements h4s(By by) {
        return multiple(by, "h4");
    }

    public FluentWebElement p() {
        return single(By.tagName("p"), "p");
    }

    public FluentWebElements ps() {
        return multiple(By.tagName("p"), "p");
    }

    public FluentWebElement p(By by) {
        return single(by, "p");
    }

    public FluentWebElements ps(By by) {
        return multiple(by, "p");
    }

    public FluentWebElement img() {
        return single(By.tagName("img"), "img");
    }

    public FluentWebElements imgs() {
        return multiple(By.tagName("img"), "img");
    }

    public FluentWebElement img(By by) {
        return single(by, "img");
    }

    public FluentWebElements imgs(By by) {
        return multiple(by, "img");
    }

    public FluentWebElement table() {
        return single(By.tagName("table"), "table");
    }

    public FluentWebElements tables() {
        return multiple(By.tagName("table"), "table");
    }

    public FluentWebElement table(By by) {
        return single(by, "table");
    }

    public FluentWebElements tables(By by) {
        return multiple(by, "table");
    }

    public FluentWebElement tr() {
        return single(By.tagName("tr"), "tr");
    }

    public FluentWebElements trs() {
        return multiple(By.tagName("tr"), "tr");
    }

    public FluentWebElement tr(By by) {
        return single(by, "tr");
    }

    public FluentWebElements trs(By by) {
        return multiple(by, "tr");
    }

    public FluentWebElement td() {
        return single(By.tagName("td"), "td");
    }

    public FluentWebElements tds() {
        return multiple(By.tagName("td"), "td");
    }

    public FluentWebElement td(By by) {
        return single(by, "td");
    }

    public FluentWebElements tds(By by) {
        return multiple(by, "td");
    }

    public FluentWebElement th() {
        return single(By.tagName("th"), "th");
    }

    public FluentWebElements ths() {
        return multiple(By.tagName("th"), "th");
    }

    public FluentWebElement th(By by) {
        return single(by, "th");
    }

    public FluentWebElements ths(By by) {
        return multiple(by, "th");
    }

    public FluentWebElement ul() {
        return single(By.tagName("ul"), "ul");
    }

    public FluentWebElements uls() {
        return multiple(By.tagName("ul"), "ul");
    }

    public FluentWebElement ul(By by) {
        return single(by, "ul");
    }

    public FluentWebElements uls(By by) {
        return multiple(by, "ul");
    }

    public FluentWebElement ol() {
        return single(By.tagName("ol"), "ol");
    }

    public FluentWebElements ols() {
        return multiple(By.tagName("ol"), "ol");
    }

    public FluentWebElement ol(By by) {
        return single(by, "ol");
    }

    public FluentWebElements ols(By by) {
        return multiple(by, "ol");
    }

    public FluentWebElement form() {
        return single(By.tagName("form"), "form");
    }

    public FluentWebElements forms() {
        return multiple(By.tagName("form"), "form");
    }

    public FluentWebElement form(By by) {
        return single(by, "form");
    }

    public FluentWebElements forms(By by) {
        return multiple(by, "form");
    }

    public FluentWebElement textarea() {
        return single(By.tagName("textarea"), "textarea");
    }

    public FluentWebElements textareas() {
        return multiple(By.tagName("textarea"), "textarea");
    }

    public FluentWebElement textarea(By by) {
        return single(by, "textarea");
    }

    public FluentWebElements textareas(By by) {
        return multiple(by, "textarea");
    }

    public FluentWebElement option() {
        return single(By.tagName("option"), "option");
    }

    public FluentWebElements options() {
        return multiple(By.tagName("option"), "option");
    }

    public FluentWebElement option(By by) {
        return single(by, "option");
    }

    public FluentWebElements options(By by) {
        return multiple(by, "option");
    }

    public FluentWebElement li() {
        return single(By.tagName("li"), "li");
    }

    public FluentWebElement li(By by) {
        return single(by, "li");
    }

    public FluentWebElements lis() {
        return multiple(By.tagName("li"), "li");
    }

    public FluentWebElements lis(By by) {
        return multiple(by, "li");
    }

    protected abstract FluentWebElement getOngoingSingleElement(WebElement result, String context);
    protected abstract FluentWebElements getOngoingMultipleElements(List<WebElement> results, String context);

    protected final By fixupBy(By by, String tagName) {
        if (by.getClass().getName().contains("ByXPath")) {
            by = By.xpath(".//" + tagName + "[" + by.toString().substring(by.toString().indexOf(":") + 1).trim() + "]");
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

    private FluentWebElement single(By by, String tagName) {
        by = fixupBy(by, tagName);
        WebElement result = null;
        String ctx = contextualize(by.toString(), tagName);
        try {
            result = findIt(by);
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
        assertTagIs(result.getTagName(), tagName);
        return getOngoingSingleElement(result, ctx);
    }

    private String contextualize(String by, String tagName) {
        if (by.equals("By.tagName: " + tagName)) {
            by = "";
        }
        return context + "." + tagName + "(" + by + ")";
    }

    private FluentWebElements multiple(By by, String tagName) {
        by = fixupBy(by, tagName);
        List<WebElement> results;
        String ctx = context + "." + tagName + "s(" + by + ")";
        try {
            results = findThem(by);
            for (WebElement webElement : results) {
                assertTagIs(webElement.getTagName(), tagName);
            }
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
        return getOngoingMultipleElements(results, ctx);
    }

    protected RuntimeException decorateRuntimeException(String ctx, RuntimeException e) {
        return new FluentExecutionStopped("RuntimeException during invocation of: " + ctx, e);
    }
    protected RuntimeException decorateAssertionError(String ctx, AssertionError e) {
        return new FluentExecutionStopped("AssertionError during invocation of: " + ctx, e);
    }

}
