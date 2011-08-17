package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public abstract class FluentCore {

    protected final WebDriver delegate;
    protected final String context;

    public FluentCore(WebDriver delegate, String context) {
        this.delegate = delegate;
        this.context = context;
    }

    public OngoingSingleElement span() {
        return single(By.tagName("span"), "span");
    }

    public OngoingSingleElement span(By by) {
        return single(by, "span");
    }

    public OngoingMultipleElements spans() {
        return multiple(By.tagName("span"), "span");
    }

    public OngoingMultipleElements spans(By by) {
        return multiple(by, "span");
    }

    public OngoingSingleElement div() {
        return single(By.tagName("div"), "div");
    }

    public OngoingSingleElement div(By by) {
        return single(by, "div");
    }

    public OngoingMultipleElements divs() {
        return multiple(By.tagName("div"), "div");
    }

    public OngoingMultipleElements divs(By by) {
        return multiple(by, "div");
    }

    public OngoingSingleElement button() {
        return single(By.tagName("button"), "button");
    }

    public OngoingSingleElement button(By by) {
        return single(by, "button");
    }

    public OngoingMultipleElements buttons() {
        return multiple(By.tagName("button"), "button");
    }

    public OngoingMultipleElements buttons(By by) {
        return multiple(by, "button");
    }

    public OngoingSingleElement link() {
        return single(By.tagName("a"), "a");
    }

    public OngoingSingleElement link(By by) {
        return single(by, "a");
    }

    public OngoingMultipleElements links() {
        return multiple(By.tagName("a"), "a");
    }

    public OngoingMultipleElements links(By by) {
        return multiple(by, "a");
    }

    public OngoingSingleElement input() {
        return single(By.tagName("input"), "input");
    }

    public OngoingSingleElement input(By by) {
        return single(by, "input");

    }

    public OngoingMultipleElements inputs() {
        return multiple(By.tagName("input"), "input");
    }

    public OngoingMultipleElements inputs(By by) {
        return multiple(by, "input");
    }

    public OngoingSingleElement select() {
        return single(By.tagName("select"), "select");
    }

    public OngoingSingleElement select(By by) {
        return single(by, "select");
    }

    public OngoingMultipleElements selects() {
        return multiple(By.tagName("select"), "select");
    }

    public OngoingMultipleElements selects(By by) {
        return multiple(by, "select");
    }

    public OngoingSingleElement h1() {
        return single(By.tagName("h1"), "h1");
    }

    public OngoingSingleElement h1(By by) {
        return single(by, "h1");

    }

    public OngoingMultipleElements h1s() {
        return multiple(By.tagName("h1"), "h1");
    }

    public OngoingMultipleElements h1s(By by) {
        return multiple(by, "h1");
    }

    public OngoingSingleElement h2() {
        return single(By.tagName("h2"), "h2");
    }

    public OngoingSingleElement h2(By by) {
        return single(by, "h2");
    }

    public OngoingMultipleElements h2s() {
        return multiple(By.tagName("h2"), "h2");
    }

    public OngoingMultipleElements h2s(By by) {
        return multiple(by, "h2");
    }

    public OngoingSingleElement h3() {
        return single(By.tagName("h3"), "h3");
    }

    public OngoingMultipleElements h3s() {
        return multiple(By.tagName("h3"), "h3");
    }

    public OngoingSingleElement h3(By by) {
        return single(by, "h3");
    }

    public OngoingMultipleElements h3s(By by) {
        return multiple(by, "h3");
    }

    public OngoingSingleElement h4(){
        return single(By.tagName("h4"), "h4");
    }

    public OngoingMultipleElements h4s() {
        return multiple(By.tagName("h4"), "h4");
    }

    public OngoingSingleElement h4(By by) {
        return single(by, "h4");
    }

    public OngoingMultipleElements h4s(By by) {
        return multiple(by, "h4");
    }

    public OngoingSingleElement p() {
        return single(By.tagName("p"), "p");
    }

    public OngoingMultipleElements ps() {
        return multiple(By.tagName("p"), "p");
    }

    public OngoingSingleElement p(By by) {
        return single(by, "p");
    }

    public OngoingMultipleElements ps(By by) {
        return multiple(by, "p");
    }

    public OngoingSingleElement img() {
        return single(By.tagName("img"), "img");
    }

    public OngoingMultipleElements imgs() {
        return multiple(By.tagName("img"), "img");
    }

    public OngoingSingleElement img(By by) {
        return single(by, "img");
    }

    public OngoingMultipleElements imgs(By by) {
        return multiple(by, "img");
    }

    public OngoingSingleElement table() {
        return single(By.tagName("table"), "table");
    }

    public OngoingMultipleElements tables() {
        return multiple(By.tagName("table"), "table");
    }

    public OngoingSingleElement table(By by) {
        return single(by, "table");
    }

    public OngoingMultipleElements tables(By by) {
        return multiple(by, "table");
    }

    public OngoingSingleElement tr() {
        return single(By.tagName("tr"), "tr");
    }

    public OngoingMultipleElements trs() {
        return multiple(By.tagName("tr"), "tr");
    }

    public OngoingSingleElement tr(By by) {
        return single(by, "tr");
    }

    public OngoingMultipleElements trs(By by) {
        return multiple(by, "tr");
    }

    public OngoingSingleElement td() {
        return single(By.tagName("td"), "td");
    }

    public OngoingMultipleElements tds() {
        return multiple(By.tagName("td"), "td");
    }

    public OngoingSingleElement td(By by) {
        return single(by, "td");
    }

    public OngoingMultipleElements tds(By by) {
        return multiple(by, "td");
    }

    public OngoingSingleElement th() {
        return single(By.tagName("th"), "th");
    }

    public OngoingMultipleElements ths() {
        return multiple(By.tagName("th"), "th");
    }

    public OngoingSingleElement th(By by) {
        return single(by, "th");
    }

    public OngoingMultipleElements ths(By by) {
        return multiple(by, "th");
    }

    public OngoingSingleElement ul() {
        return single(By.tagName("ul"), "ul");
    }

    public OngoingMultipleElements uls() {
        return multiple(By.tagName("ul"), "ul");
    }

    public OngoingSingleElement ul(By by) {
        return single(by, "ul");
    }

    public OngoingMultipleElements uls(By by) {
        return multiple(by, "ul");
    }

    public OngoingSingleElement ol() {
        return single(By.tagName("ol"), "ol");
    }

    public OngoingMultipleElements ols() {
        return multiple(By.tagName("ol"), "ol");
    }

    public OngoingSingleElement ol(By by) {
        return single(by, "ol");
    }

    public OngoingMultipleElements ols(By by) {
        return multiple(by, "ol");
    }

    public OngoingSingleElement form() {
        return single(By.tagName("form"), "form");
    }

    public OngoingMultipleElements forms() {
        return multiple(By.tagName("form"), "form");
    }

    public OngoingSingleElement form(By by) {
        return single(by, "form");
    }

    public OngoingMultipleElements forms(By by) {
        return multiple(by, "form");
    }

    public OngoingSingleElement textarea() {
        return single(By.tagName("textarea"), "textarea");
    }

    public OngoingMultipleElements textareas() {
        return multiple(By.tagName("textarea"), "textarea");
    }

    public OngoingSingleElement textarea(By by) {
        return single(by, "textarea");
    }

    public OngoingMultipleElements textareas(By by) {
        return multiple(by, "textarea");
    }

    public OngoingSingleElement option() {
        return single(By.tagName("option"), "option");
    }

    public OngoingMultipleElements options() {
        return multiple(By.tagName("option"), "option");
    }

    public OngoingSingleElement option(By by) {
        return single(by, "option");
    }

    public OngoingMultipleElements options(By by) {
        return multiple(by, "option");
    }

    public OngoingSingleElement li() {
        return single(By.tagName("li"), "li");
    }

    public OngoingSingleElement li(By by) {
        return single(by, "li");
    }

    public OngoingMultipleElements lis() {
        return multiple(By.tagName("li"), "li");
    }

    public OngoingMultipleElements lis(By by) {
        return multiple(by, "li");
    }

    protected abstract OngoingSingleElement getOngoingSingleElement(WebElement result, String context);
    protected abstract OngoingMultipleElements getOngoingMultipleElements(List<WebElement> results);

    protected final By fixupBy(By by, String tagName) {
        if (by.getClass().getName().contains("ByXPath")) {
            by = By.xpath(".//" + tagName + "[" + by.toString().substring(by.toString().indexOf(":") + 1).trim() + "]");
        }
        return by;
    }

    protected final void assertEquals(String is, String shouldBe) {
        if (!is.equals(shouldBe)) {
            throw new AssertionError("tag was incorrect, should have been " + shouldBe + " but was " + is);
        }
    }

    protected abstract WebElement findIt(By by);

    protected abstract List<WebElement> findThem(By by);

    private OngoingSingleElement single(By by, String tagName) {
        by = fixupBy(by, tagName);
        WebElement result = null;
        String ctx = contextualize(by.toString(), tagName);
        try {
            result = findIt(by);
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
        assertEquals(result.getTagName(), tagName);
        return getOngoingSingleElement(result, ctx);
    }

    private String contextualize(String by, String tagName) {
        if (by.equals("By.tagName: " + tagName)) {
            by = "";
        }
        return context + "." + tagName + "(" + by + ")";
    }

    private OngoingMultipleElements multiple(By by, String tagName) {
        by = fixupBy(by, tagName);
        List<WebElement> results;
        String ctx = context + "." + tagName + "s(" + by + ")";
        try {
            results = findThem(by);
            for (WebElement webElement : results) {
                assertEquals(webElement.getTagName(), tagName);
            }
        } catch (WebDriverException e) {
            throw decorateWebDriverException(ctx, e);
        }
        return getOngoingMultipleElements(results);
    }

    protected RuntimeException decorateWebDriverException(String ctx, WebDriverException e) {
        return new RuntimeException("WebDriver exception during invocation of : " + ctx, e);
    }

}
