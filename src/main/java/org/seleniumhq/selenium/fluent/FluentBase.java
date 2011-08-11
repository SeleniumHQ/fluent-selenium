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

    public SubsequentFluentWebDriver span() {
        return single(By.tagName("span"), "span");
    }

    public SubsequentFluentWebDriver span(By by) {
        return single(by, "span");
    }

    public SubsequentFluentWebDriver spans() {
        return multiple(By.tagName("span"), "span");
    }

    public SubsequentFluentWebDriver spans(By by) {
        return multiple(by, "span");
    }

    public SubsequentFluentWebDriver div() {
        return single(By.tagName("div"), "div");
    }

    public SubsequentFluentWebDriver div(By by) {
        return single(by, "div");
    }

    public SubsequentFluentWebDriver divs() {
        return multiple(By.tagName("div"), "div");
    }

    public SubsequentFluentWebDriver divs(By by) {
        return multiple(by, "div");
    }

    public SubsequentFluentWebDriver link() {
        return single(By.tagName("a"), "a");
    }

    public SubsequentFluentWebDriver link(By by) {
        return single(by, "a");
    }

    public SubsequentFluentWebDriver links() {
        return multiple(By.tagName("a"), "a");
    }

    public SubsequentFluentWebDriver links(By by) {
        return multiple(by, "a");
    }

    public SubsequentFluentWebDriver input() {
        return single(By.tagName("input"), "input");
    }

    public SubsequentFluentWebDriver input(By by) {
        return single(by, "input");

    }

    public SubsequentFluentWebDriver inputs() {
        return multiple(By.tagName("input"), "input");
    }

    public SubsequentFluentWebDriver inputs(By by) {
        return multiple(by, "input");
    }

    public SubsequentFluentWebDriver select() {
        return single(By.tagName("select"), "select");
    }

    public SubsequentFluentWebDriver select(By by) {
        return single(by, "select");
    }

    public SubsequentFluentWebDriver selects() {
        return multiple(By.tagName("select"), "select");
    }

    public SubsequentFluentWebDriver selects(By by) {
        return multiple(by, "select");
    }

    public SubsequentFluentWebDriver h1() {
        return single(By.tagName("h1"), "h1");
    }

    public SubsequentFluentWebDriver h1(By by) {
        return single(by, "h1");

    }

    public SubsequentFluentWebDriver h1s() {
        return multiple(By.tagName("h1"), "h1");
    }

    public SubsequentFluentWebDriver h1s(By by) {
        return multiple(by, "h1");
    }

    public SubsequentFluentWebDriver h2() {
        return single(By.tagName("h2"), "h2");
    }

    public SubsequentFluentWebDriver h2(By by) {
        return single(by, "h2");
    }

    public SubsequentFluentWebDriver h2s() {
        return multiple(By.tagName("h2"), "h2");
    }

    public SubsequentFluentWebDriver h2s(By by) {
        return multiple(by, "h2");
    }

    public SubsequentFluentWebDriver h3() {
        return single(By.tagName("h3"), "h3");
    }

    public SubsequentFluentWebDriver h3s() {
        return multiple(By.tagName("h3"), "h3");
    }

    public SubsequentFluentWebDriver h3(By by) {
        return single(by, "h3");
    }

    public SubsequentFluentWebDriver h3s(By by) {
        return multiple(by, "h3");
    }

    public SubsequentFluentWebDriver h4(){
        return single(By.tagName("h4"), "h4");
    }

    public SubsequentFluentWebDriver h4s() {
        return multiple(By.tagName("h4"), "h4");
    }

    public SubsequentFluentWebDriver h4(By by) {
        return single(by, "h4");
    }

    public SubsequentFluentWebDriver h4s(By by) {
        return multiple(by, "h4");
    }

    public SubsequentFluentWebDriver p() {
        return single(By.tagName("p"), "p");
    }

    public SubsequentFluentWebDriver ps() {
        return multiple(By.tagName("p"), "p");
    }

    public SubsequentFluentWebDriver p(By by) {
        return single(by, "p");
    }

    public SubsequentFluentWebDriver ps(By by) {
        return multiple(by, "p");
    }

    public SubsequentFluentWebDriver img() {
        return single(By.tagName("img"), "img");
    }

    public SubsequentFluentWebDriver imgs() {
        return multiple(By.tagName("img"), "img");
    }

    public SubsequentFluentWebDriver img(By by) {
        return single(by, "img");
    }

    public SubsequentFluentWebDriver imgs(By by) {
        return multiple(by, "img");
    }

    public SubsequentFluentWebDriver table() {
        return single(By.tagName("table"), "table");
    }

    public SubsequentFluentWebDriver tables() {
        return multiple(By.tagName("table"), "table");
    }

    public SubsequentFluentWebDriver table(By by) {
        return single(by, "table");
    }

    public SubsequentFluentWebDriver tables(By by) {
        return multiple(by, "table");
    }

    public SubsequentFluentWebDriver tr() {
        return single(By.tagName("tr"), "tr");
    }

    public SubsequentFluentWebDriver trs() {
        return multiple(By.tagName("tr"), "tr");
    }

    public SubsequentFluentWebDriver tr(By by) {
        return single(by, "tr");
    }

    public SubsequentFluentWebDriver trs(By by) {
        return multiple(by, "tr");
    }

    public SubsequentFluentWebDriver td() {
        return single(By.tagName("td"), "td");
    }

    public SubsequentFluentWebDriver tds() {
        return multiple(By.tagName("td"), "td");
    }

    public SubsequentFluentWebDriver td(By by) {
        return single(by, "td");
    }

    public SubsequentFluentWebDriver tds(By by) {
        return multiple(by, "td");
    }

    public SubsequentFluentWebDriver th() {
        return single(By.tagName("th"), "th");
    }

    public SubsequentFluentWebDriver ths() {
        return multiple(By.tagName("th"), "th");
    }

    public SubsequentFluentWebDriver th(By by) {
        return single(by, "th");
    }

    public SubsequentFluentWebDriver ths(By by) {
        return multiple(by, "th");
    }

    public SubsequentFluentWebDriver ul() {
        return single(By.tagName("ul"), "ul");
    }

    public SubsequentFluentWebDriver uls() {
        return multiple(By.tagName("ul"), "ul");
    }

    public SubsequentFluentWebDriver ul(By by) {
        return single(by, "ul");
    }

    public SubsequentFluentWebDriver uls(By by) {
        return multiple(by, "ul");
    }

    public SubsequentFluentWebDriver ol() {
        return single(By.tagName("ol"), "ol");
    }

    public SubsequentFluentWebDriver ols() {
        return multiple(By.tagName("ol"), "ol");
    }

    public SubsequentFluentWebDriver ol(By by) {
        return single(by, "ol");
    }

    public SubsequentFluentWebDriver ols(By by) {
        return multiple(by, "ol");
    }

    public SubsequentFluentWebDriver form() {
        return single(By.tagName("form"), "form");
    }

    public SubsequentFluentWebDriver forms() {
        return multiple(By.tagName("form"), "form");
    }

    public SubsequentFluentWebDriver form(By by) {
        return single(by, "form");
    }

    public SubsequentFluentWebDriver forms(By by) {
        return multiple(by, "form");
    }

    public SubsequentFluentWebDriver textarea() {
        return single(By.tagName("textarea"), "textarea");
    }

    public SubsequentFluentWebDriver textareas() {
        return multiple(By.tagName("textarea"), "textarea");
    }

    public SubsequentFluentWebDriver textarea(By by) {
        return single(by, "textarea");
    }

    public SubsequentFluentWebDriver textareas(By by) {
        return multiple(by, "textarea");
    }

    public SubsequentFluentWebDriver option() {
        return single(By.tagName("option"), "option");
    }

    public SubsequentFluentWebDriver options() {
        return multiple(By.tagName("option"), "option");
    }

    public SubsequentFluentWebDriver option(By by) {
        return single(by, "option");
    }

    public SubsequentFluentWebDriver options(By by) {
        return multiple(by, "option");
    }

    public SubsequentFluentWebDriver li() {
        return single(By.tagName("li"), "li");
    }

    public SubsequentFluentWebDriver li(By by) {
        return single(by, "li");
    }

    public SubsequentFluentWebDriver lis() {
        return multiple(By.tagName("li"), "li");
    }

    public SubsequentFluentWebDriver lis(By by) {
        return multiple(by, "li");
    }

    protected abstract SubsequentFluentWebDriver getSubsequentFluentWebDriver();

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

    private SubsequentFluentWebDriver single(By by, String tagName) {
        by = fixupBy(by, tagName);
        WebElement result = findIt(by);
        assertEquals(result.getTagName(), tagName);
        return getSubsequentFluentWebDriver();
    }

    private SubsequentFluentWebDriver multiple(By by, String tagName) {
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
