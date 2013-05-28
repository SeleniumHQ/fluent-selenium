package org.seleniumhq.selenium.fluent;
import org.openqa.selenium.*;
import java.util.List;
public class DisappearedFluentWebElement extends FluentWebElement {
    public DisappearedFluentWebElement(WebDriver delegate, Context context) {
        super(delegate, null, context);
    }
    private UnsupportedOperationException meaningless(final String invocation) {
        return new UnsupportedOperationException(invocation + " has no meaning for disappeared element");
    }
    @Override
    protected WebElement findIt(By by) {
        throw meaningless("findIt('" + by + "')");
    }
    @Override
    protected List<WebElement> findThem(By by) {
        throw meaningless("findThem('" + by + "')");
    }
    @Override
    protected WebElement actualFindIt(By by) {
        throw meaningless("actualFindIt('" + by + "')");
    }
    @Override
    protected List<WebElement> actualFindThem(By by) {
        throw meaningless("actualFindThem('" + by + "')");
    }
    @Override
    public FluentWebElement click() {
        throw meaningless("click()");
    }
    @Override
    public FluentWebElement clearField() {
        throw meaningless("clearField()");
    }
    @Override
    public FluentWebElement submit() {
        throw meaningless("submit()");
    }
    @Override
    public FluentWebElement sendKeys(CharSequence... keysToSend) {
        throw meaningless("sendkeys('" + keysToSend + "')");
    }
    @Override
    public TestableString getTagName() {
        throw meaningless("getTagName()");
    }
    @Override
    public boolean isSelected() {
        throw meaningless("isSelected()");
    }
    @Override
    public boolean isEnabled() {
        throw meaningless("isEnabled()");
    }
    @Override
    public boolean isDisplayed() {
        throw meaningless("isEnabled()");
    }
    @Override
    public Point getLocation() {
        throw meaningless("getLocation()");
    }
    @Override
    public Dimension getSize() {
        throw meaningless("getSize()");
    }
    @Override
    public TestableString cssValue(String cssName) {
        throw meaningless("cssValue('" + cssName + "')");
    }
    @Override
    public TestableString attribute(String attr) {
        throw meaningless("attribute('" + attr + "')");
    }
    @Override
    public TestableString getText() {
        throw meaningless("getSize()");
    }
    @Override
    public WebElementValue<Point> location() {
        throw meaningless("location()");
    }
    @Override
    public WebElementValue<Dimension> size() {
        throw meaningless("size()");
    }
    @Override
    public WebElementValue<String> tagName() {
        throw meaningless("tagName()");
    }
    @Override
    public WebElementValue<Boolean> selected() {
        throw meaningless("selected()");
    }
    @Override
    public WebElementValue<Boolean> enabled() {
        throw meaningless("enabled()");
    }
    @Override
    public WebElementValue<Boolean> displayed() {
        throw meaningless("displayed()");
    }
    @Override
    public WebElementValue<String> text() {
        throw meaningless("text()");
    }
    @Override
    public FluentWebElement within(Period period) {
        throw meaningless("within('" + period.toString() + "')");
    }
    @Override
    public FluentWebDriver without(Period period) {
        throw meaningless("without('" + period.toString() + "')");
    }
    @Override
    protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context) {
        throw meaningless("makeFluentWebElements('" + results.toString() + ", " + context.toString() + "')");
    }
    @Override
    protected String charSeqArrayAsHumanString(CharSequence[] keysToSend) {
        throw meaningless("charSeqArrayAsHumanString('" + keysToSend + "')");
    }
    @Override
    public FluentWebElement span() {
        throw meaningless("span()");
    }
    @Override
    public FluentWebElement span(By by) {
        throw meaningless("span('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements spans() {
        throw meaningless("spans()");
    }
    @Override
    public FluentWebElements spans(By by) {
        throw meaningless("spans('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement div() {
        throw meaningless("div()");
    }
    @Override
    public FluentWebElement div(By by) {
        throw meaningless("div('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements divs() {
        throw meaningless("divs()");
    }
    @Override
    public FluentWebElements divs(By by) {
        throw meaningless("divs('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement button() {
        throw meaningless("button()");
    }
    @Override
    public FluentWebElement button(By by) {
        throw meaningless("button('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements buttons() {
        throw meaningless("buttons()");
    }
    @Override
    public FluentWebElements buttons(By by) {
        throw meaningless("buttons('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement link() {
        throw meaningless("link()");
    }
    @Override
    public FluentWebElement link(By by) {
        throw meaningless("link('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements links() {
        throw meaningless("links()");
    }
    @Override
    public FluentWebElements links(By by) {
        throw meaningless("links('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement input() {
        throw meaningless("input()");
    }
    @Override
    public FluentWebElement input(By by) {
        throw meaningless("input('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements inputs() {
        throw meaningless("inputs()");
    }
    @Override
    public FluentWebElements inputs(By by) {
        throw meaningless("input('" + by.toString() + "')");
    }
    @Override
    public FluentSelect select() {
        throw meaningless("select()");
    }
    @Override
    public FluentSelect select(By by) {
        throw meaningless("select('" + by.toString() + "')");
    }
    @Override
    public FluentSelects selects() {
        throw meaningless("selects()");
    }
    @Override
    public FluentSelects selects(By by) {
        throw meaningless("selects('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement h1() {
        return super.h1();
    }
    @Override
    public FluentWebElement h1(By by) {
        throw meaningless("h1('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements h1s() {
        throw meaningless("h1s()");
    }
    @Override
    public FluentWebElements h1s(By by) {
        throw meaningless("h1s('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement h2() {
        throw meaningless("h2()");
    }
    @Override
    public FluentWebElement h2(By by) {
        throw meaningless("h2('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements h2s() {
        throw meaningless("h2s()");
    }
    @Override
    public FluentWebElements h2s(By by) {
        throw meaningless("h2s('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement h3() {
        throw meaningless("h3()");
    }
    @Override
    public FluentWebElements h3s() {
        throw meaningless("h3s()");
    }
    @Override
    public FluentWebElement h3(By by) {
        throw meaningless("h3('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements h3s(By by) {
        throw meaningless("h3s('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement h4() {
        throw meaningless("h4()");
    }
    @Override
    public FluentWebElements h4s() {
        throw meaningless("h4s()");
    }
    @Override
    public FluentWebElement h4(By by) {
        throw meaningless("h4('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements h4s(By by) {
        throw meaningless("h4s('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement p() {
        throw meaningless("p()");
    }
    @Override
    public FluentWebElements ps() {
        throw meaningless("ps()");
    }
    @Override
    public FluentWebElement p(By by) {
        throw meaningless("p('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements ps(By by) {
        throw meaningless("ps('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement img() {
        throw meaningless("img()");
    }
    @Override
    public FluentWebElements imgs() {
        throw meaningless("imgs()");
    }
    @Override
    public FluentWebElement img(By by) {
        throw meaningless("img('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements imgs(By by) {
        throw meaningless("imgs('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement table() {
        throw meaningless("table()");
    }
    @Override
    public FluentWebElements tables() {
        throw meaningless("tables()");
    }
    @Override
    public FluentWebElement table(By by) {
        throw meaningless("table('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements tables(By by) {
        throw meaningless("tables('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement fieldset() {
        throw meaningless("fieldset()");
    }
    @Override
    public FluentWebElements fieldsets() {
        throw meaningless("fieldsets()");
    }
    @Override
    public FluentWebElement fieldset(By by) {
        throw meaningless("fieldset('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements fieldsets(By by) {
        throw meaningless("fieldsets('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement legend() {
        throw meaningless("legend()");
    }
    @Override
    public FluentWebElements legends() {
        throw meaningless("legends()");
    }
    @Override
    public FluentWebElement legend(By by) {
        throw meaningless("legend('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements legends(By by) {
        throw meaningless("legends('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement tr() {
        throw meaningless("tr()");
    }
    @Override
    public FluentWebElements trs() {
        throw meaningless("trs()");
    }
    @Override
    public FluentWebElement tr(By by) {
        throw meaningless("tr('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements trs(By by) {
        throw meaningless("trs('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement td() {
        throw meaningless("td()");
    }
    @Override
    public FluentWebElements tds() {
        throw meaningless("tds()");
    }
    @Override
    public FluentWebElement td(By by) {
        throw meaningless("td('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements tds(By by) {
        throw meaningless("tds('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement th() {
        throw meaningless("th()");
    }
    @Override
    public FluentWebElements ths() {
        throw meaningless("ths()");
    }
    @Override
    public FluentWebElement th(By by) {
        throw meaningless("th('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements ths(By by) {
        throw meaningless("ths('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement ul() {
        throw meaningless("ul()");
    }
    @Override
    public FluentWebElements uls() {
        throw meaningless("uls()");
    }
    @Override
    public FluentWebElement ul(By by) {
        throw meaningless("ul('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements uls(By by) {
        throw meaningless("uls('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement ol() {
        throw meaningless("ol()");
    }
    @Override
    public FluentWebElements ols() {
        throw meaningless("ols()");
    }
    @Override
    public FluentWebElement ol(By by) {
        throw meaningless("ol('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements ols(By by) {
        throw meaningless("ols('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement form() {
        throw meaningless("form()");
    }
    @Override
    public FluentWebElements forms() {
        throw meaningless("forms()");
    }
    @Override
    public FluentWebElement form(By by) {
        throw meaningless("form('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements forms(By by) {
        throw meaningless("forms('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement textarea() {
        throw meaningless("textarea()");
    }
    @Override
    public FluentWebElements textareas() {
        throw meaningless("textareas()");
    }
    @Override
    public FluentWebElement textarea(By by) {
        throw meaningless("textarea('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements textareas(By by) {
        throw meaningless("textareas('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement option() {
        throw meaningless("option()");
    }
    @Override
    public FluentWebElements options() {
        throw meaningless("options()");
    }
    @Override
    public FluentWebElement option(By by) {
        throw meaningless("option('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements options(By by) {
        throw meaningless("options('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement li() {
        throw meaningless("li()");
    }
    @Override
    public FluentWebElement li(By by) {
        throw meaningless("li('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements lis() {
        throw meaningless("lis()");
    }
    @Override
    public FluentWebElements lis(By by) {
        throw meaningless("lis('" + by.toString() + "')");
    }
    @Override
    public FluentWebElement map() {
        throw meaningless("map()");
    }
    @Override
    public FluentWebElements maps() {
        throw meaningless("maps()");
    }
    @Override
    public FluentWebElement map(By by) {
        throw meaningless("map('" + by.toString() + "')");
    }
    @Override
    public FluentWebElements maps(By by) {
        throw meaningless("maps('" + by.toString() + "')");
    }
    @Override
    public TestableString url() {
        throw meaningless("url()");
    }
    @Override
    protected Period getPeriod() {
        throw meaningless("getPeriod()");
    }
    @Override
    public TestableString title() {
        throw meaningless("title()");
    }
}