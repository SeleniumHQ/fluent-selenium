package org.seleniumhq.selenium.fluent.internal;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.Monitor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;
import static org.seleniumhq.selenium.fluent.Period.millis;

public class NegatingFluentWebDriverTest {

    private static final By BYID = id("1");

    WebDriver wd;
    NegatingFluentWebDriver nfwd;

    @Before
    public void setup() {
        wd = mock(WebDriver.class);
        nfwd = new NegatingFluentWebDriver(wd, millis(100), Context.singular(null, "foo", BYID), new Monitor.NULL());
    }

    @Test
    public void testSpan() throws Exception {
        when(wd.findElement(tagName("span"))).thenThrow(new NotFoundException());
        nfwd.span();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.span(BYID);
    }

    @Test
    public void testDiv() throws Exception {
        when(wd.findElement(tagName("div"))).thenThrow(new NotFoundException());
        nfwd.div();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.div(BYID);
    }

    @Test
    public void testButton() throws Exception {
        when(wd.findElement(tagName("button"))).thenThrow(new NotFoundException());
        nfwd.button();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.button(BYID);
    }


    @Test
    public void testLink() throws Exception {
        when(wd.findElement(tagName("a"))).thenThrow(new NotFoundException());
        nfwd.link();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.link(BYID);

    }

    @Test
    public void testInput() throws Exception {
        when(wd.findElement(tagName("input"))).thenThrow(new NotFoundException());
        nfwd.input();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.input(BYID);
    }


    @Test
    public void testSelect() throws Exception {
        when(wd.findElement(tagName("select"))).thenThrow(new NotFoundException());
        nfwd.select();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.select(BYID);
    }


    @Test
    public void testH1() throws Exception {
        when(wd.findElement(tagName("h1"))).thenThrow(new NotFoundException());
        nfwd.h1();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.h1(BYID);
    }

    @Test
    public void testH2() throws Exception {
        when(wd.findElement(tagName("h2"))).thenThrow(new NotFoundException());
        nfwd.h2();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.h2(BYID);
    }

    @Test
    public void testH3() throws Exception {
        when(wd.findElement(tagName("h3"))).thenThrow(new NotFoundException());
        nfwd.h3();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.h3(BYID);
    }

    @Test
    public void testH4() throws Exception {
        when(wd.findElement(tagName("h4"))).thenThrow(new NotFoundException());
        nfwd.h4();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.h4(BYID);
    }

    @Test
    public void testP() throws Exception {
        when(wd.findElement(tagName("p"))).thenThrow(new NotFoundException());
        nfwd.p();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.p(BYID);
    }

    @Test
    public void testB() throws Exception {
        when(wd.findElement(tagName("b"))).thenThrow(new NotFoundException());
        nfwd.b();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.b(BYID);
    }

    @Test
    public void testPre() throws Exception {
        when(wd.findElement(tagName("pre"))).thenThrow(new NotFoundException());
        nfwd.pre();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.pre(BYID);
    }

    @Test
    public void testheader() throws Exception {
        when(wd.findElement(tagName("header"))).thenThrow(new NotFoundException());
        nfwd.header();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.header(BYID);
    }

    @Test
    public void testfooter() throws Exception {
        when(wd.findElement(tagName("footer"))).thenThrow(new NotFoundException());
        nfwd.footer();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.footer(BYID);
    }

    @Test
    public void testImg() throws Exception {
        when(wd.findElement(tagName("img"))).thenThrow(new NotFoundException());
        nfwd.img();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.img(BYID);
    }

    @Test
    public void testTable() throws Exception {
        when(wd.findElement(tagName("table"))).thenThrow(new NotFoundException());
        nfwd.table();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.table(BYID);
    }

    @Test
    public void testFieldset() throws Exception {
        when(wd.findElement(tagName("fieldset"))).thenThrow(new NotFoundException());
        nfwd.fieldset();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.fieldset(BYID);
    }


    @Test
    public void testLegend() throws Exception {
        when(wd.findElement(tagName("legend"))).thenThrow(new NotFoundException());
        nfwd.legend();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.legend(BYID);
    }

    @Test
    public void testTr() throws Exception {
        when(wd.findElement(tagName("tr"))).thenThrow(new NotFoundException());
        nfwd.tr();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.tr(BYID);
    }

    @Test
    public void testTd() throws Exception {
        when(wd.findElement(tagName("td"))).thenThrow(new NotFoundException());
        nfwd.td();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.td(BYID);
    }

    @Test
    public void testTh() throws Exception {
        when(wd.findElement(tagName("th"))).thenThrow(new NotFoundException());
        nfwd.th();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.th(BYID);
    }

    @Test
    public void testUl() throws Exception {
        when(wd.findElement(tagName("ul"))).thenThrow(new NotFoundException());
        nfwd.ul();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.ul(BYID);
    }

    @Test
    public void testOl() throws Exception {
        when(wd.findElement(tagName("ol"))).thenThrow(new NotFoundException());
        nfwd.ol();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.ol(BYID);
    }

    @Test
    public void testForm() throws Exception {
        when(wd.findElement(tagName("form"))).thenThrow(new NotFoundException());
        nfwd.form();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.form(BYID);
    }

    @Test
    public void testTextarea() throws Exception {
        when(wd.findElement(tagName("textarea"))).thenThrow(new NotFoundException());
        nfwd.textarea();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.textarea(BYID);
    }

    @Test
    public void testOption() throws Exception {
        when(wd.findElement(tagName("option"))).thenThrow(new NotFoundException());
        nfwd.option();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.option(BYID);
    }

    @Test
    public void testLi() throws Exception {
        when(wd.findElement(tagName("li"))).thenThrow(new NotFoundException());
        nfwd.li();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.li(BYID);
    }

    @Test
    public void testMap() throws Exception {
        when(wd.findElement(tagName("map"))).thenThrow(new NotFoundException());
        nfwd.map();
        when(wd.findElement(BYID)).thenThrow(new NotFoundException());
        nfwd.map(BYID);
    }
}
