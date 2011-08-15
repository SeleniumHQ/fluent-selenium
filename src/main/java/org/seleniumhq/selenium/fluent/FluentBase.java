package org.seleniumhq.selenium.fluent;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class FluentBase extends ArrayList<WebElement> {

    protected final WebDriver delegate;

    public FluentBase(WebDriver delegate) {
        this.delegate = delegate;
    }

    public OngoingFluentWebDriver span() {
        return single(By.tagName("span"), "span");
    }

    public OngoingFluentWebDriver span(By by) {
        return single(by, "span");
    }

    public OngoingFluentWebDriver spans() {
        return multiple(By.tagName("span"), "span");
    }

    public OngoingFluentWebDriver spans(By by) {
        return multiple(by, "span");
    }

    public OngoingFluentWebDriver div() {
        return single(By.tagName("div"), "div");
    }

    public OngoingFluentWebDriver div(By by) {
        return single(by, "div");
    }

    public OngoingFluentWebDriver divs() {
        return multiple(By.tagName("div"), "div");
    }

    public OngoingFluentWebDriver divs(By by) {
        return multiple(by, "div");
    }

    public OngoingFluentWebDriver link() {
        return single(By.tagName("a"), "a");
    }

    public OngoingFluentWebDriver link(By by) {
        return single(by, "a");
    }

    public OngoingFluentWebDriver links() {
        return multiple(By.tagName("a"), "a");
    }

    public OngoingFluentWebDriver links(By by) {
        return multiple(by, "a");
    }

    public OngoingFluentWebDriver input() {
        return single(By.tagName("input"), "input");
    }

    public OngoingFluentWebDriver input(By by) {
        return single(by, "input");

    }

    public OngoingFluentWebDriver inputs() {
        return multiple(By.tagName("input"), "input");
    }

    public OngoingFluentWebDriver inputs(By by) {
        return multiple(by, "input");
    }

    public OngoingFluentWebDriver select() {
        return single(By.tagName("select"), "select");
    }

    public OngoingFluentWebDriver select(By by) {
        return single(by, "select");
    }

    public OngoingFluentWebDriver selects() {
        return multiple(By.tagName("select"), "select");
    }

    public OngoingFluentWebDriver selects(By by) {
        return multiple(by, "select");
    }

    public OngoingFluentWebDriver h1() {
        return single(By.tagName("h1"), "h1");
    }

    public OngoingFluentWebDriver h1(By by) {
        return single(by, "h1");

    }

    public OngoingFluentWebDriver h1s() {
        return multiple(By.tagName("h1"), "h1");
    }

    public OngoingFluentWebDriver h1s(By by) {
        return multiple(by, "h1");
    }

    public OngoingFluentWebDriver h2() {
        return single(By.tagName("h2"), "h2");
    }

    public OngoingFluentWebDriver h2(By by) {
        return single(by, "h2");
    }

    public OngoingFluentWebDriver h2s() {
        return multiple(By.tagName("h2"), "h2");
    }

    public OngoingFluentWebDriver h2s(By by) {
        return multiple(by, "h2");
    }

    public OngoingFluentWebDriver h3() {
        return single(By.tagName("h3"), "h3");
    }

    public OngoingFluentWebDriver h3s() {
        return multiple(By.tagName("h3"), "h3");
    }

    public OngoingFluentWebDriver h3(By by) {
        return single(by, "h3");
    }

    public OngoingFluentWebDriver h3s(By by) {
        return multiple(by, "h3");
    }

    public OngoingFluentWebDriver h4(){
        return single(By.tagName("h4"), "h4");
    }

    public OngoingFluentWebDriver h4s() {
        return multiple(By.tagName("h4"), "h4");
    }

    public OngoingFluentWebDriver h4(By by) {
        return single(by, "h4");
    }

    public OngoingFluentWebDriver h4s(By by) {
        return multiple(by, "h4");
    }

    public OngoingFluentWebDriver p() {
        return single(By.tagName("p"), "p");
    }

    public OngoingFluentWebDriver ps() {
        return multiple(By.tagName("p"), "p");
    }

    public OngoingFluentWebDriver p(By by) {
        return single(by, "p");
    }

    public OngoingFluentWebDriver ps(By by) {
        return multiple(by, "p");
    }

    public OngoingFluentWebDriver img() {
        return single(By.tagName("img"), "img");
    }

    public OngoingFluentWebDriver imgs() {
        return multiple(By.tagName("img"), "img");
    }

    public OngoingFluentWebDriver img(By by) {
        return single(by, "img");
    }

    public OngoingFluentWebDriver imgs(By by) {
        return multiple(by, "img");
    }

    public OngoingFluentWebDriver table() {
        return single(By.tagName("table"), "table");
    }

    public OngoingFluentWebDriver tables() {
        return multiple(By.tagName("table"), "table");
    }

    public OngoingFluentWebDriver table(By by) {
        return single(by, "table");
    }

    public OngoingFluentWebDriver tables(By by) {
        return multiple(by, "table");
    }

    public OngoingFluentWebDriver tr() {
        return single(By.tagName("tr"), "tr");
    }

    public OngoingFluentWebDriver trs() {
        return multiple(By.tagName("tr"), "tr");
    }

    public OngoingFluentWebDriver tr(By by) {
        return single(by, "tr");
    }

    public OngoingFluentWebDriver trs(By by) {
        return multiple(by, "tr");
    }

    public OngoingFluentWebDriver td() {
        return single(By.tagName("td"), "td");
    }

    public OngoingFluentWebDriver tds() {
        return multiple(By.tagName("td"), "td");
    }

    public OngoingFluentWebDriver td(By by) {
        return single(by, "td");
    }

    public OngoingFluentWebDriver tds(By by) {
        return multiple(by, "td");
    }

    public OngoingFluentWebDriver th() {
        return single(By.tagName("th"), "th");
    }

    public OngoingFluentWebDriver ths() {
        return multiple(By.tagName("th"), "th");
    }

    public OngoingFluentWebDriver th(By by) {
        return single(by, "th");
    }

    public OngoingFluentWebDriver ths(By by) {
        return multiple(by, "th");
    }

    public OngoingFluentWebDriver ul() {
        return single(By.tagName("ul"), "ul");
    }

    public OngoingFluentWebDriver uls() {
        return multiple(By.tagName("ul"), "ul");
    }

    public OngoingFluentWebDriver ul(By by) {
        return single(by, "ul");
    }

    public OngoingFluentWebDriver uls(By by) {
        return multiple(by, "ul");
    }

    public OngoingFluentWebDriver ol() {
        return single(By.tagName("ol"), "ol");
    }

    public OngoingFluentWebDriver ols() {
        return multiple(By.tagName("ol"), "ol");
    }

    public OngoingFluentWebDriver ol(By by) {
        return single(by, "ol");
    }

    public OngoingFluentWebDriver ols(By by) {
        return multiple(by, "ol");
    }

    public OngoingFluentWebDriver form() {
        return single(By.tagName("form"), "form");
    }

    public OngoingFluentWebDriver forms() {
        return multiple(By.tagName("form"), "form");
    }

    public OngoingFluentWebDriver form(By by) {
        return single(by, "form");
    }

    public OngoingFluentWebDriver forms(By by) {
        return multiple(by, "form");
    }

    public OngoingFluentWebDriver textarea() {
        return single(By.tagName("textarea"), "textarea");
    }

    public OngoingFluentWebDriver textareas() {
        return multiple(By.tagName("textarea"), "textarea");
    }

    public OngoingFluentWebDriver textarea(By by) {
        return single(by, "textarea");
    }

    public OngoingFluentWebDriver textareas(By by) {
        return multiple(by, "textarea");
    }

    public OngoingFluentWebDriver option() {
        return single(By.tagName("option"), "option");
    }

    public OngoingFluentWebDriver options() {
        return multiple(By.tagName("option"), "option");
    }

    public OngoingFluentWebDriver option(By by) {
        return single(by, "option");
    }

    public OngoingFluentWebDriver options(By by) {
        return multiple(by, "option");
    }

    public OngoingFluentWebDriver li() {
        return single(By.tagName("li"), "li");
    }

    public OngoingFluentWebDriver li(By by) {
        return single(by, "li");
    }

    public OngoingFluentWebDriver lis() {
        return multiple(By.tagName("li"), "li");
    }

    public OngoingFluentWebDriver lis(By by) {
        return multiple(by, "li");
    }

    protected abstract OngoingFluentWebDriver getSubsequentFluentWebDriver();

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

    private OngoingFluentWebDriver single(By by, String tagName) {
        by = fixupBy(by, tagName);
        WebElement result = findIt(by);
        assertEquals(result.getTagName(), tagName);
        return getSubsequentFluentWebDriver();
    }

    private OngoingFluentWebDriver multiple(By by, String tagName) {
        by = fixupBy(by, tagName);
        List<WebElement> results = findThem(by);
        for (WebElement webElement : results) {
            assertEquals(webElement.getTagName(), tagName);
        }
        clear();
        addAll(results);
        return getSubsequentFluentWebDriver();
    }

}
