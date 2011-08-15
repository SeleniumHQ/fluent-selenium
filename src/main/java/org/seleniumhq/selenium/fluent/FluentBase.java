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

    public MultipleOngoingFluentWebDriver spans() {
        return multiple(By.tagName("span"), "span");
    }

    public MultipleOngoingFluentWebDriver spans(By by) {
        return multiple(by, "span");
    }

    public OngoingFluentWebDriver div() {
        return single(By.tagName("div"), "div");
    }

    public OngoingFluentWebDriver div(By by) {
        return single(by, "div");
    }

    public MultipleOngoingFluentWebDriver divs() {
        return multiple(By.tagName("div"), "div");
    }

    public MultipleOngoingFluentWebDriver divs(By by) {
        return multiple(by, "div");
    }

    public OngoingFluentWebDriver link() {
        return single(By.tagName("a"), "a");
    }

    public OngoingFluentWebDriver link(By by) {
        return single(by, "a");
    }

    public MultipleOngoingFluentWebDriver links() {
        return multiple(By.tagName("a"), "a");
    }

    public MultipleOngoingFluentWebDriver links(By by) {
        return multiple(by, "a");
    }

    public OngoingFluentWebDriver input() {
        return single(By.tagName("input"), "input");
    }

    public OngoingFluentWebDriver input(By by) {
        return single(by, "input");

    }

    public MultipleOngoingFluentWebDriver inputs() {
        return multiple(By.tagName("input"), "input");
    }

    public MultipleOngoingFluentWebDriver inputs(By by) {
        return multiple(by, "input");
    }

    public OngoingFluentWebDriver select() {
        return single(By.tagName("select"), "select");
    }

    public OngoingFluentWebDriver select(By by) {
        return single(by, "select");
    }

    public MultipleOngoingFluentWebDriver selects() {
        return multiple(By.tagName("select"), "select");
    }

    public MultipleOngoingFluentWebDriver selects(By by) {
        return multiple(by, "select");
    }

    public OngoingFluentWebDriver h1() {
        return single(By.tagName("h1"), "h1");
    }

    public OngoingFluentWebDriver h1(By by) {
        return single(by, "h1");

    }

    public MultipleOngoingFluentWebDriver h1s() {
        return multiple(By.tagName("h1"), "h1");
    }

    public MultipleOngoingFluentWebDriver h1s(By by) {
        return multiple(by, "h1");
    }

    public OngoingFluentWebDriver h2() {
        return single(By.tagName("h2"), "h2");
    }

    public OngoingFluentWebDriver h2(By by) {
        return single(by, "h2");
    }

    public MultipleOngoingFluentWebDriver h2s() {
        return multiple(By.tagName("h2"), "h2");
    }

    public MultipleOngoingFluentWebDriver h2s(By by) {
        return multiple(by, "h2");
    }

    public OngoingFluentWebDriver h3() {
        return single(By.tagName("h3"), "h3");
    }

    public MultipleOngoingFluentWebDriver h3s() {
        return multiple(By.tagName("h3"), "h3");
    }

    public OngoingFluentWebDriver h3(By by) {
        return single(by, "h3");
    }

    public MultipleOngoingFluentWebDriver h3s(By by) {
        return multiple(by, "h3");
    }

    public OngoingFluentWebDriver h4(){
        return single(By.tagName("h4"), "h4");
    }

    public MultipleOngoingFluentWebDriver h4s() {
        return multiple(By.tagName("h4"), "h4");
    }

    public OngoingFluentWebDriver h4(By by) {
        return single(by, "h4");
    }

    public MultipleOngoingFluentWebDriver h4s(By by) {
        return multiple(by, "h4");
    }

    public OngoingFluentWebDriver p() {
        return single(By.tagName("p"), "p");
    }

    public MultipleOngoingFluentWebDriver ps() {
        return multiple(By.tagName("p"), "p");
    }

    public OngoingFluentWebDriver p(By by) {
        return single(by, "p");
    }

    public MultipleOngoingFluentWebDriver ps(By by) {
        return multiple(by, "p");
    }

    public OngoingFluentWebDriver img() {
        return single(By.tagName("img"), "img");
    }

    public MultipleOngoingFluentWebDriver imgs() {
        return multiple(By.tagName("img"), "img");
    }

    public OngoingFluentWebDriver img(By by) {
        return single(by, "img");
    }

    public MultipleOngoingFluentWebDriver imgs(By by) {
        return multiple(by, "img");
    }

    public OngoingFluentWebDriver table() {
        return single(By.tagName("table"), "table");
    }

    public MultipleOngoingFluentWebDriver tables() {
        return multiple(By.tagName("table"), "table");
    }

    public OngoingFluentWebDriver table(By by) {
        return single(by, "table");
    }

    public MultipleOngoingFluentWebDriver tables(By by) {
        return multiple(by, "table");
    }

    public OngoingFluentWebDriver tr() {
        return single(By.tagName("tr"), "tr");
    }

    public MultipleOngoingFluentWebDriver trs() {
        return multiple(By.tagName("tr"), "tr");
    }

    public OngoingFluentWebDriver tr(By by) {
        return single(by, "tr");
    }

    public MultipleOngoingFluentWebDriver trs(By by) {
        return multiple(by, "tr");
    }

    public OngoingFluentWebDriver td() {
        return single(By.tagName("td"), "td");
    }

    public MultipleOngoingFluentWebDriver tds() {
        return multiple(By.tagName("td"), "td");
    }

    public OngoingFluentWebDriver td(By by) {
        return single(by, "td");
    }

    public MultipleOngoingFluentWebDriver tds(By by) {
        return multiple(by, "td");
    }

    public OngoingFluentWebDriver th() {
        return single(By.tagName("th"), "th");
    }

    public MultipleOngoingFluentWebDriver ths() {
        return multiple(By.tagName("th"), "th");
    }

    public OngoingFluentWebDriver th(By by) {
        return single(by, "th");
    }

    public MultipleOngoingFluentWebDriver ths(By by) {
        return multiple(by, "th");
    }

    public OngoingFluentWebDriver ul() {
        return single(By.tagName("ul"), "ul");
    }

    public MultipleOngoingFluentWebDriver uls() {
        return multiple(By.tagName("ul"), "ul");
    }

    public OngoingFluentWebDriver ul(By by) {
        return single(by, "ul");
    }

    public MultipleOngoingFluentWebDriver uls(By by) {
        return multiple(by, "ul");
    }

    public OngoingFluentWebDriver ol() {
        return single(By.tagName("ol"), "ol");
    }

    public MultipleOngoingFluentWebDriver ols() {
        return multiple(By.tagName("ol"), "ol");
    }

    public OngoingFluentWebDriver ol(By by) {
        return single(by, "ol");
    }

    public MultipleOngoingFluentWebDriver ols(By by) {
        return multiple(by, "ol");
    }

    public OngoingFluentWebDriver form() {
        return single(By.tagName("form"), "form");
    }

    public MultipleOngoingFluentWebDriver forms() {
        return multiple(By.tagName("form"), "form");
    }

    public OngoingFluentWebDriver form(By by) {
        return single(by, "form");
    }

    public MultipleOngoingFluentWebDriver forms(By by) {
        return multiple(by, "form");
    }

    public OngoingFluentWebDriver textarea() {
        return single(By.tagName("textarea"), "textarea");
    }

    public MultipleOngoingFluentWebDriver textareas() {
        return multiple(By.tagName("textarea"), "textarea");
    }

    public OngoingFluentWebDriver textarea(By by) {
        return single(by, "textarea");
    }

    public MultipleOngoingFluentWebDriver textareas(By by) {
        return multiple(by, "textarea");
    }

    public OngoingFluentWebDriver option() {
        return single(By.tagName("option"), "option");
    }

    public MultipleOngoingFluentWebDriver options() {
        return multiple(By.tagName("option"), "option");
    }

    public OngoingFluentWebDriver option(By by) {
        return single(by, "option");
    }

    public MultipleOngoingFluentWebDriver options(By by) {
        return multiple(by, "option");
    }

    public OngoingFluentWebDriver li() {
        return single(By.tagName("li"), "li");
    }

    public OngoingFluentWebDriver li(By by) {
        return single(by, "li");
    }

    public MultipleOngoingFluentWebDriver lis() {
        return multiple(By.tagName("li"), "li");
    }

    public MultipleOngoingFluentWebDriver lis(By by) {
        return multiple(by, "li");
    }

    protected abstract SingleOngoingFluentWebDriver getSingleOngoingFluentWebDriver();
    protected abstract MultipleOngoingFluentWebDriver getMultipleOngoingFluentWebDriver();

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

    private SingleOngoingFluentWebDriver single(By by, String tagName) {
        by = fixupBy(by, tagName);
        WebElement result = findIt(by);
        assertEquals(result.getTagName(), tagName);
        return getSingleOngoingFluentWebDriver();
    }

    private MultipleOngoingFluentWebDriver multiple(By by, String tagName) {
        by = fixupBy(by, tagName);
        List<WebElement> results = findThem(by);
        for (WebElement webElement : results) {
            assertEquals(webElement.getTagName(), tagName);
        }
        clear();
        addAll(results);
        return getMultipleOngoingFluentWebDriver();
    }

}
