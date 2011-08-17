package org.seleniumhq.selenium.fluent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.internal.matchers.StringContains.containsString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit test for simple App.
 */
public class FluentWebDriverTest {

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");
    private StringBuilder sb;
    private static WebElement bogusElem;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    static {
        bogusElem = mock(WebElement.class);
        when(bogusElem.getTagName()).thenReturn("bogus");
    }

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(false);
    }

    @Test
    public void reference_test_with_mockito() {
        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        FluentWebDriverImpl fwd = new FluentWebDriverImpl(wd);

        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentCore fb = fwd.div(ID_A).div(ID_B);

        assertThat(fb, notNullValue());

        InOrder io = inOrder(we, wd, we2);
        io.verify(wd).findElement(ID_A);
        io.verify(we).getTagName();
        io.verify(we).findElement(ID_B);
        io.verify(we2).getTagName();
        verifyNoMoreInteractions(wd, we, we2);
    }

    @Test
    public void example_of_longer_query_using_IDs() {

        FluentCore fb = fwd.div(ID_A).div(ID_B).span().click();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.id: idA) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(By.id: idB) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we2.findElement(By.tagName: span) -> we3\n" +
                        "we3.getTagName() -> 'span'\n" +
                        "we3.click()\n"
        ));
    }

    @Test
    public void lengthier_expression_with_late_exception() {

        FluentCore fb = null;
        try {
            fb = fwd.div(ID_A).div(ID_B).span().sendKeys("RAIN_IN_SPAIN");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("WebDriver exception during invocation of : ?.div(By.id: idA).div(By.id: idB).span().sendKeys('RAIN_IN_SPAIN')"));
            assertThat(e.getCause().getMessage(), containsString("FAILED AS PER STUB SETUP"));
        }

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.id: idA) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(By.id: idB) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we2.findElement(By.tagName: span) -> we3\n" +
                        "we3.getTagName() -> 'span'\n"
        ));
    }

    @Test
    public void xPaths_and_non_ongoing() {

        OngoingFluentWebDriver sfwd = fwd.div().span(By.xpath("@foo = 'bar'")).sendKeys("apple").clearField().submit();

        assertThat(sfwd, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(By.xpath: .//span[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we2.sendKeys(apple)\n" +
                        "we2.clear()\n" +
                        "we2.submit()\n"
        ));

        sb.setLength(0);
        Point locn = sfwd.getLocation();
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we2.getLocation() -> 1,1\n"));

        sb.setLength(0);
        Dimension size = sfwd.getSize();
        assertThat(size.toString(), equalTo("(10, 10)"));
        assertThat(sb.toString(), equalTo("we2.getSize() -> 10,10\n"));

        sb.setLength(0);
        String cssVal = sfwd.getCssValue("blort");
        assertThat(cssVal.toString(), equalTo("blort_value"));
        assertThat(sb.toString(), equalTo("we2.getCssValue(blort) -> blort_value\n"));

        sb.setLength(0);
        String value = sfwd.getAttribute("valerie");
        assertThat(value.toString(), equalTo("valerie_value"));
        assertThat(sb.toString(), equalTo("we2.getAttribute(valerie) -> valerie_value\n"));

        sb.setLength(0);
        String tagName = sfwd.getTagName();
        assertThat(tagName.toString(), equalTo("taggart"));
        assertThat(sb.toString(), equalTo("we2.getTagName() -> 'taggart'\n"));

        sb.setLength(0);
        boolean isSelected = sfwd.isSelected();
        assertThat(isSelected, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isSelected() -> true\n"));

        sb.setLength(0);
        boolean isEnabled = sfwd.isEnabled();
        assertThat(isEnabled, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isEnabled() -> true\n"));

        sb.setLength(0);
        boolean isDisplayed = sfwd.isDisplayed();
        assertThat(isDisplayed, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isDisplayed() -> true\n"));

        sb.setLength(0);
        String text = sfwd.getText();
        assertThat(text, equalTo("Mary had 3 little lamb(s)."));
        assertThat(sb.toString(), equalTo("we2.getText() -> 'Mary had 3 little lamb(s).'\n"));

    }

    @Test
    public void exceptions_decorated_for_single_element() {

        OngoingSingleElement ose = fwd.div(By.id("foo"));

        assertThat(ose, notNullValue());

        FAIL_ON_NEXT.set(true);

        try {
            ose.sendKeys("a");
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).sendKeys('a')"));
        }

        try {
            ose.submit();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).submit()"));
        }

        try {
            ose.clearField();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).clearField()"));
        }

        try {
            ose.getLocation();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getLocation()"));
        }

        try {
            ose.getSize();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getSize()"));
        }

        try {
            ose.getAttribute("valerie");
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getAttribute(valerie)"));
        }
        try {
            ose.getCssValue("blort");
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getCssValue(blort)"));
        }

        try {
            ose.getTagName();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getTagName()"));
        }

        try {
            ose.isSelected();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isSelected()"));
        }

        try {
            ose.isEnabled();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isEnabled()"));
        }

        try {
            ose.isDisplayed();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isDisplayed()"));
        }
        try {
            ose.getText();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getText()"));
        }

    }

    @Test
    public void exceptions_decorated_for_filter() {

        OngoingMultipleElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(true);

        try {
            ome.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(Hello)"));
        }
    }

    private FluentMatcher makeMatcherThatUsesWebDriver(final String toString) {
        return new FluentMatcher() {
            public boolean matches(WebElement webElement) {
                return webElement.getText().equals("it does not matter, as an exception will be thrown");
            }
            @Override
            public String toString() {
                return toString;
            }
        };
    }

    @Test
    public void exceptions_decorated_for_first() {

        OngoingMultipleElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(true);

        try {
            ome.first(makeMatcherThatUsesWebDriver("Goodbye"));
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(Goodbye)"));
        }
    }

    @Test
    public void further_find_element_after_multiple_is_unsupported() {

        try {
            OngoingSingleElement ose = fwd.divs(By.xpath("foo")).span(By.xpath("bar"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void exceptions_decorated_for_multiple_element() {

        OngoingMultipleElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(true);

        try {
            ome.sendKeys("a");
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).sendKeys('a')"));
        }

        try {
            ome.click();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).click()"));
        }

        try {
            ome.submit();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).submit()"));
        }

        try {
            ome.clearField();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).clearField()"));
        }

        try {
            ome.isSelected();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isSelected()"));
        }

        try {
            ome.isEnabled();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isEnabled()"));
        }

        try {
            ome.isDisplayed();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isDisplayed()"));
        }

        try {
            ome.getText();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).getText()"));
        }

    }

    @Test
    public void multiple_hits_from_the_outset_and_operations_on_the_resulting_list() {

        OngoingFluentWebDriver ofwd = fwd.divs();

        assertThat(ofwd, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n"
        ));

        sb.setLength(0);
        OngoingFluentWebDriver ofwd2 = ofwd.clearField();
        assertThat(ofwd2, notNullValue());
        assertThat(sb.toString(), equalTo("we1.clear()\nwe2.clear()\n"));

        sb.setLength(0);
        OngoingFluentWebDriver ofwd3 = ofwd.click();
        assertThat(ofwd3, notNullValue());
        assertThat(sb.toString(), equalTo("we1.click()\nwe2.click()\n"));

        sb.setLength(0);
        boolean areSelected = ofwd.isSelected();
        assertThat(areSelected, equalTo(false));
        assertThat(sb.toString(), equalTo("we1.isSelected() -> true\nwe2.isSelected() -> false\n"));

        sb.setLength(0);
        boolean areEnabled = ofwd.isEnabled();
        assertThat(areEnabled, equalTo(false));
        assertThat(sb.toString(), equalTo("we1.isEnabled() -> true\nwe2.isEnabled() -> false\n"));

        sb.setLength(0);
        boolean areDisplayed = ofwd.isDisplayed();
        assertThat(areDisplayed, equalTo(false));
        assertThat(sb.toString(), equalTo("we1.isDisplayed() -> true\nwe2.isDisplayed() -> false\n"));

        sb.setLength(0);
        OngoingFluentWebDriver ofwd4 = ofwd.sendKeys("aaa");
        assertThat(ofwd4, notNullValue());
        assertThat(sb.toString(), equalTo("we1.sendKeys(aaa)\nwe2.sendKeys(aaa)\n"));

        sb.setLength(0);
        OngoingFluentWebDriver ofwd5 = ofwd.submit();
        assertThat(ofwd5, notNullValue());
        assertThat(sb.toString(), equalTo("we1.submit()\nwe2.submit()\n"));

        sb.setLength(0);
        String text = ofwd.getText();
        assertThat(text, equalTo("Mary had 3 little lamb(s).Mary had 4 little lamb(s)."));
        assertThat(sb.toString(), equalTo("we1.getText() -> 'Mary had 3 little lamb(s).'\nwe2.getText() -> 'Mary had 4 little lamb(s).'\n"));

        sb.setLength(0);
        try {
            ofwd.getLocation();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getLocation() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            ofwd.getSize();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getSize() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            ofwd.getCssValue("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getCssValue() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            ofwd.getAttribute("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getAttribute() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            ofwd.getTagName();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getTagName() has no meaning for multiple elements"));
        }
    }

    @Test
    public void div_functionality() {

        FluentCore fb = fwd.div()
                .div(By.xpath("@foo = 'bar'"))
                .div(By.cssSelector("baz"))
                .divs();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(By.xpath: .//div[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'div'\n" +
                        "we3.findElements(By.tagName: div) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'div'\n" +
                        "we5.getTagName() -> 'div'\n"
        ));
    }

    @Test
    public void divs_functionality() {
        FluentCore fb = fwd.div()
                .divs(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we3.getTagName() -> 'div'\n"
        ));
    }

    @Test
    public void div_mismatched() {
        try {
            fwd.div(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void button_functionality() {

        FluentCore fb = fwd.button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: button) -> we1\n" +
                        "we1.getTagName() -> 'button'\n" +
                        "we1.findElement(By.xpath: .//button[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'button'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'button'\n" +
                        "we3.findElements(By.tagName: button) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'button'\n" +
                        "we5.getTagName() -> 'button'\n"
        ));
    }

    @Test
    public void buttons_functionality() {
        FluentCore fb = fwd.button()
                .buttons(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: button) -> we1\n" +
                        "we1.getTagName() -> 'button'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'button'\n" +
                        "we3.getTagName() -> 'button'\n"
        ));
    }

    @Test
    public void button_mismatched() {
    try {
        fwd.button(By.linkText("mismatching_tag_name"))
                .clearField();
        fail("should have barfed");
    } catch (AssertionError e) {
        assertTrue(e.getMessage().contains("tag was incorrect"));
    }

}

    @Test
    public void option_functionality() {

        FluentCore fb = fwd.option()
                .option(By.xpath("@foo = 'bar'"))
                .option(By.cssSelector("baz"))
                .options();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: option) -> we1\n" +
                        "we1.getTagName() -> 'option'\n" +
                        "we1.findElement(By.xpath: .//option[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'option'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'option'\n" +
                        "we3.findElements(By.tagName: option) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'option'\n" +
                        "we5.getTagName() -> 'option'\n"
        ));
    }


    @Test
    public void options_functionality() {
        FluentCore fb = fwd.option()
                .options(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: option) -> we1\n" +
                        "we1.getTagName() -> 'option'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'option'\n" +
                        "we3.getTagName() -> 'option'\n"
        ));
    }


    @Test
    public void option_mismatched() {
        try {
            fwd.option(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void select_functionality() {

        FluentCore fb = fwd.select()
                .select(By.xpath("@foo = 'bar'"))
                .select(By.cssSelector("baz"))
                .selects();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "we1.findElement(By.xpath: .//select[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'select'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'select'\n" +
                        "we3.findElements(By.tagName: select) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'select'\n" +
                        "we5.getTagName() -> 'select'\n"
        ));
    }


    @Test
    public void selects_functionality() {
        FluentCore fb = fwd.select()
                .selects(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'select'\n" +
                        "we3.getTagName() -> 'select'\n"
        ));
    }


    @Test
    public void select_mismatched() {
        try {
            fwd.select(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void span_functionality() {

        FluentCore fb = fwd.span()
                .span(By.xpath("@foo = 'bar'"))
                .span(By.cssSelector("baz"))
                .spans();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: span) -> we1\n" +
                        "we1.getTagName() -> 'span'\n" +
                        "we1.findElement(By.xpath: .//span[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'span'\n" +
                        "we3.findElements(By.tagName: span) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'span'\n" +
                        "we5.getTagName() -> 'span'\n"
        ));
    }


    @Test
    public void spans_functionality() {
        FluentCore fb = fwd.span()
                .spans(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: span) -> we1\n" +
                        "we1.getTagName() -> 'span'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we3.getTagName() -> 'span'\n"
        ));
    }


    @Test
    public void span_mismatched() {
        try {
            fwd.span(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void tr_functionality() {

        FluentCore fb = fwd.tr()
                .tr(By.xpath("@foo = 'bar'"))
                .tr(By.cssSelector("baz"))
                .trs();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: tr) -> we1\n" +
                        "we1.getTagName() -> 'tr'\n" +
                        "we1.findElement(By.xpath: .//tr[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'tr'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'tr'\n" +
                        "we3.findElements(By.tagName: tr) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'tr'\n" +
                        "we5.getTagName() -> 'tr'\n"
        ));
    }


    @Test
    public void trs_functionality() {
        FluentCore fb = fwd.tr()
                .trs(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: tr) -> we1\n" +
                        "we1.getTagName() -> 'tr'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'tr'\n" +
                        "we3.getTagName() -> 'tr'\n"
        ));
    }


    @Test
    public void tr_mismatched() {
        try {
            fwd.tr(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void td_functionality() {

        FluentCore fb = fwd.td()
                .td(By.xpath("@foo = 'bar'"))
                .td(By.cssSelector("baz"))
                .tds();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: td) -> we1\n" +
                        "we1.getTagName() -> 'td'\n" +
                        "we1.findElement(By.xpath: .//td[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'td'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'td'\n" +
                        "we3.findElements(By.tagName: td) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'td'\n" +
                        "we5.getTagName() -> 'td'\n"
        ));
    }


    @Test
    public void tds_functionality() {
        FluentCore fb = fwd.td()
                .tds(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: td) -> we1\n" +
                        "we1.getTagName() -> 'td'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'td'\n" +
                        "we3.getTagName() -> 'td'\n"
        ));
    }


    @Test
    public void td_mismatched() {
        try {
            fwd.td(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void table_functionality() {

        FluentCore fb = fwd.table()
                .table(By.xpath("@foo = 'bar'"))
                .table(By.cssSelector("baz"))
                .tables();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: table) -> we1\n" +
                        "we1.getTagName() -> 'table'\n" +
                        "we1.findElement(By.xpath: .//table[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'table'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'table'\n" +
                        "we3.findElements(By.tagName: table) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'table'\n" +
                        "we5.getTagName() -> 'table'\n"
        ));
    }


    @Test
    public void tables_functionality() {
        FluentCore fb = fwd.table()
                .tables(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: table) -> we1\n" +
                        "we1.getTagName() -> 'table'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'table'\n" +
                        "we3.getTagName() -> 'table'\n"
        ));
    }


    @Test
    public void table_mismatched() {
        try {
            fwd.table(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void h1_functionality() {

        FluentCore fb = fwd.h1()
                .h1(By.xpath("@foo = 'bar'"))
                .h1(By.cssSelector("baz"))
                .h1s();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h1) -> we1\n" +
                        "we1.getTagName() -> 'h1'\n" +
                        "we1.findElement(By.xpath: .//h1[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h1'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h1'\n" +
                        "we3.findElements(By.tagName: h1) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h1'\n" +
                        "we5.getTagName() -> 'h1'\n"
        ));
    }

    @Test
    public void h1s_functionality() {
        FluentCore fb = fwd.h1()
                .h1s(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h1) -> we1\n" +
                        "we1.getTagName() -> 'h1'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h1'\n" +
                        "we3.getTagName() -> 'h1'\n"
        ));
    }

    @Test
    public void h1_mismatched() {
        try {
            fwd.h1(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void h2_functionality() {

        FluentCore fb = fwd.h2()
                .h2(By.xpath("@foo = 'bar'"))
                .h2(By.cssSelector("baz"))
                .h2s();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h2) -> we1\n" +
                        "we1.getTagName() -> 'h2'\n" +
                        "we1.findElement(By.xpath: .//h2[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h2'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h2'\n" +
                        "we3.findElements(By.tagName: h2) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h2'\n" +
                        "we5.getTagName() -> 'h2'\n"
        ));
    }

    @Test
    public void h2s_functionality() {
        FluentCore fb = fwd.h2()
                .h2s(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h2) -> we1\n" +
                        "we1.getTagName() -> 'h2'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h2'\n" +
                        "we3.getTagName() -> 'h2'\n"
        ));
    }

    @Test
    public void h2_mismatched() {
        try {
            fwd.h2(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void h3_functionality() {

        FluentCore fb = fwd.h3()
                .h3(By.xpath("@foo = 'bar'"))
                .h3(By.cssSelector("baz"))
                .h3s();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h3) -> we1\n" +
                        "we1.getTagName() -> 'h3'\n" +
                        "we1.findElement(By.xpath: .//h3[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h3'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h3'\n" +
                        "we3.findElements(By.tagName: h3) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h3'\n" +
                        "we5.getTagName() -> 'h3'\n"
        ));
    }

    @Test
    public void h3s_functionality() {
        FluentCore fb = fwd.h3()
                .h3s(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h3) -> we1\n" +
                        "we1.getTagName() -> 'h3'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h3'\n" +
                        "we3.getTagName() -> 'h3'\n"
        ));
    }

    @Test
    public void h3_mismatched() {
        try {
            fwd.h3(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void h4_functionality() {

        FluentCore fb = fwd.h4()
                .h4(By.xpath("@foo = 'bar'"))
                .h4(By.cssSelector("baz"))
                .h4s();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h4) -> we1\n" +
                        "we1.getTagName() -> 'h4'\n" +
                        "we1.findElement(By.xpath: .//h4[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h4'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h4'\n" +
                        "we3.findElements(By.tagName: h4) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h4'\n" +
                        "we5.getTagName() -> 'h4'\n"
        ));
    }

    @Test
    public void h4s_functionality() {
        FluentCore fb = fwd.h4()
                .h4s(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h4) -> we1\n" +
                        "we1.getTagName() -> 'h4'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h4'\n" +
                        "we3.getTagName() -> 'h4'\n"
        ));
    }

    @Test
    public void h4_mismatched() {
        try {
            fwd.h4(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void img_functionality() {

        FluentCore fb = fwd.img()
                .img(By.xpath("@foo = 'bar'"))
                .img(By.cssSelector("baz"))
                .imgs();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: img) -> we1\n" +
                        "we1.getTagName() -> 'img'\n" +
                        "we1.findElement(By.xpath: .//img[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'img'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'img'\n" +
                        "we3.findElements(By.tagName: img) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'img'\n" +
                        "we5.getTagName() -> 'img'\n"
        ));
    }

    @Test
    public void imgs_functionality() {
        FluentCore fb = fwd.img()
                .imgs(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: img) -> we1\n" +
                        "we1.getTagName() -> 'img'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'img'\n" +
                        "we3.getTagName() -> 'img'\n"
        ));
    }

    @Test
    public void img_mismatched() {
        try {
            fwd.img(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void form_functionality() {

        FluentCore fb = fwd.form()
                .form(By.xpath("@foo = 'bar'"))
                .form(By.cssSelector("baz"))
                .forms();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: form) -> we1\n" +
                        "we1.getTagName() -> 'form'\n" +
                        "we1.findElement(By.xpath: .//form[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'form'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'form'\n" +
                        "we3.findElements(By.tagName: form) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'form'\n" +
                        "we5.getTagName() -> 'form'\n"
        ));
    }

    @Test
    public void forms_functionality() {
        FluentCore fb = fwd.form()
                .forms(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: form) -> we1\n" +
                        "we1.getTagName() -> 'form'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'form'\n" +
                        "we3.getTagName() -> 'form'\n"
        ));
    }

    @Test
    public void form_mismatched() {
        try {
            fwd.form(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void textarea_functionality() {

        FluentCore fb = fwd.textarea()
                .textarea(By.xpath("@foo = 'bar'"))
                .textarea(By.cssSelector("baz"))
                .textareas();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: textarea) -> we1\n" +
                        "we1.getTagName() -> 'textarea'\n" +
                        "we1.findElement(By.xpath: .//textarea[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'textarea'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'textarea'\n" +
                        "we3.findElements(By.tagName: textarea) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'textarea'\n" +
                        "we5.getTagName() -> 'textarea'\n"
        ));
    }

    @Test
    public void textareas_functionality() {
        FluentCore fb = fwd.textarea()
                .textareas(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: textarea) -> we1\n" +
                        "we1.getTagName() -> 'textarea'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'textarea'\n" +
                        "we3.getTagName() -> 'textarea'\n"
        ));
    }

    @Test
    public void textarea_mismatched() {
        try {
            fwd.textarea(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void input_functionality() {

        FluentCore fb = fwd.input()
                .input(By.xpath("@foo = 'bar'"))
                .input(By.cssSelector("baz"))
                .inputs();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: input) -> we1\n" +
                        "we1.getTagName() -> 'input'\n" +
                        "we1.findElement(By.xpath: .//input[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'input'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'input'\n" +
                        "we3.findElements(By.tagName: input) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'input'\n" +
                        "we5.getTagName() -> 'input'\n"
        ));
    }

    @Test
    public void inputs_functionality() {
        FluentCore fb = fwd.input()
                .inputs(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: input) -> we1\n" +
                        "we1.getTagName() -> 'input'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'input'\n" +
                        "we3.getTagName() -> 'input'\n"
        ));
    }

    @Test
    public void input_mismatched() {
        try {
            fwd.input(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void link_functionality() {

        FluentCore fb = fwd.link()
                .link(By.xpath("@foo = 'bar'"))
                .link(By.cssSelector("baz"))
                .links();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: a) -> we1\n" +
                        "we1.getTagName() -> 'a'\n" +
                        "we1.findElement(By.xpath: .//a[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'a'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'a'\n" +
                        "we3.findElements(By.tagName: a) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'a'\n" +
                        "we5.getTagName() -> 'a'\n"
        ));
    }

    @Test
    public void links_functionality() {
        FluentCore fb = fwd.link()
                .links(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: a) -> we1\n" +
                        "we1.getTagName() -> 'a'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'a'\n" +
                        "we3.getTagName() -> 'a'\n"
        ));
    }

    @Test
    public void link_mismatched() {
        try {
            fwd.link(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void p_functionality() {

        FluentCore fb = fwd.p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: p) -> we1\n" +
                        "we1.getTagName() -> 'p'\n" +
                        "we1.findElement(By.xpath: .//p[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'p'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'p'\n" +
                        "we3.findElements(By.tagName: p) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'p'\n" +
                        "we5.getTagName() -> 'p'\n"
        ));
    }

    @Test
    public void ps_functionality() {
        FluentCore fb = fwd.p()
                .ps(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: p) -> we1\n" +
                        "we1.getTagName() -> 'p'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'p'\n" +
                        "we3.getTagName() -> 'p'\n"
        ));
    }

    @Test
    public void p_mismatched() {
        try {
            fwd.p(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void th_functionality() {

        FluentCore fb = fwd.th()
                .th(By.xpath("@foo = 'bar'"))
                .th(By.cssSelector("baz"))
                .ths();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: th) -> we1\n" +
                        "we1.getTagName() -> 'th'\n" +
                        "we1.findElement(By.xpath: .//th[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'th'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'th'\n" +
                        "we3.findElements(By.tagName: th) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'th'\n" +
                        "we5.getTagName() -> 'th'\n"
        ));
    }

    @Test
    public void ths_functionality() {
        FluentCore fb = fwd.th()
                .ths(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: th) -> we1\n" +
                        "we1.getTagName() -> 'th'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'th'\n" +
                        "we3.getTagName() -> 'th'\n"
        ));
    }

    @Test
    public void th_mismatched() {
        try {
            fwd.th(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void ul_functionality() {

        FluentCore fb = fwd.ul()
                .ul(By.xpath("@foo = 'bar'"))
                .ul(By.cssSelector("baz"))
                .uls();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: ul) -> we1\n" +
                        "we1.getTagName() -> 'ul'\n" +
                        "we1.findElement(By.xpath: .//ul[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'ul'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'ul'\n" +
                        "we3.findElements(By.tagName: ul) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'ul'\n" +
                        "we5.getTagName() -> 'ul'\n"
        ));
    }

    @Test
    public void uls_functionality() {
        FluentCore fb = fwd.ul()
                .uls(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: ul) -> we1\n" +
                        "we1.getTagName() -> 'ul'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'ul'\n" +
                        "we3.getTagName() -> 'ul'\n"
        ));
    }

    @Test
    public void ul_mismatched() {
        try {
            fwd.ul(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void ol_functionality() {

        FluentCore fb = fwd.ol()
                .ol(By.xpath("@foo = 'bar'"))
                .ol(By.cssSelector("baz"))
                .ols();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: ol) -> we1\n" +
                        "we1.getTagName() -> 'ol'\n" +
                        "we1.findElement(By.xpath: .//ol[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'ol'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'ol'\n" +
                        "we3.findElements(By.tagName: ol) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'ol'\n" +
                        "we5.getTagName() -> 'ol'\n"
        ));
    }

    @Test
    public void ols_functionality() {
        FluentCore fb = fwd.ol()
                .ols(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: ol) -> we1\n" +
                        "we1.getTagName() -> 'ol'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'ol'\n" +
                        "we3.getTagName() -> 'ol'\n"
        ));
    }

    @Test
    public void ol_mismatched() {
        try {
            fwd.ol(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void li_functionality() {

        FluentCore fb = fwd.li()
                .li(By.xpath("@foo = 'bar'"))
                .li(By.cssSelector("baz"))
                .lis();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: li) -> we1\n" +
                        "we1.getTagName() -> 'li'\n" +
                        "we1.findElement(By.xpath: .//li[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'li'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'li'\n" +
                        "we3.findElements(By.tagName: li) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'li'\n" +
                        "we5.getTagName() -> 'li'\n"
        ));
    }

    @Test
    public void lis_functionality() {
        FluentCore fb = fwd.li()
                .lis(By.name("qux"));

        assertThat(fb, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: li) -> we1\n" +
                        "we1.getTagName() -> 'li'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'li'\n" +
                        "we3.getTagName() -> 'li'\n"
        ));
    }

    @Test
    public void li_mismatched() {
        try {
            fwd.li(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void is_a_list() {

        List<WebElement> elems = new ArrayList<WebElement>();
        WebElement item0 = mock(WebElement.class);
        elems.add(item0);
        elems.add(mock(WebElement.class));
        elems.add(mock(WebElement.class));
        elems.add(mock(WebElement.class));

        OngoingMultipleElements ogme = new OngoingMultipleElements(null, new ArrayList<WebElement>(elems), "");

        assertThat(ogme.size(), equalTo(4));
        assertThat(ogme.get(0), equalTo(item0));

        {
            List<WebElement> elems2 = new ArrayList<WebElement>();
            elems2.add(mock(WebElement.class));
            elems2.add(mock(WebElement.class));

            ogme.addAll(elems2);
        }

        assertThat(ogme.size(), equalTo(6));

        ogme.remove(item0);

        assertThat(ogme.size(), equalTo(5));

        ogme.removeAll(elems);

        assertThat(ogme.size(), equalTo(2));

        ogme.remove(0);

        assertThat(ogme.size(), equalTo(1));

        assertThat(ogme.contains("foo"), equalTo(false));

        ogme.add(item0);

        assertThat(ogme.indexOf(item0), equalTo(1));
        assertThat(ogme.indexOf("foo"), equalTo(-1));

        ogme.remove(item0);

        ogme.add(0, item0);

        assertThat(ogme.indexOf(item0), equalTo(0));
        assertThat(ogme.lastIndexOf(item0), equalTo(0));

        ogme.remove(0);
        assertThat(ogme.size(), equalTo(1));
        assertThat(ogme.isEmpty(), equalTo(false));
        ogme.remove(0);
        assertThat(ogme.size(), equalTo(0));
        assertThat(ogme.isEmpty(), equalTo(true));

        ogme.addAll(0, elems);
        assertThat(ogme.size(), equalTo(4));

        assertThat(ogme.toArray().length, equalTo(4));
        WebElement[] wes = new WebElement[0] ;
        assertThat(ogme.toArray(wes).length, equalTo(4));

        assertThat(ogme.subList(1, 2).size(), equalTo(1));

        assertThat(ogme.listIterator(), notNullValue());

        assertThat(ogme.listIterator(0), notNullValue());

        ogme.clear();

        assertThat(ogme.size(), equalTo(0));

    }


    @Test
    public void filtering() {

        FluentCore fb = fwd.divs().filter(new FourLambFilter()).click();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(),
                equalTo("wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n" +
                "we1.getText() -> 'Mary had 3 little lamb(s).'\n" +
                "we2.getText() -> 'Mary had 4 little lamb(s).'\n" +
                "we2.click()\n"));
    }

    public static class FourLambFilter implements FluentMatcher {
        public boolean matches(WebElement webElement) {
            return webElement.getText().contains("4 little lamb(s)");
        }
    }

    @Test
    public void first_element_matched_from_larger_list() {

        FluentCore fb = fwd.divs().first(new TextContainsWord("lamb(s)")).click();

        assertThat(fb, notNullValue());
        assertThat(sb.toString(),
                equalTo("wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n" +
                "we1.getText() -> 'Mary had 3 little lamb(s).'\n" +
                "we1.click()\n"));
    }

    @Test
    public void first_finds_nothing() {

        FluentCore fb = null;
        try {
            fb = fwd.divs().first(new TextContainsWord("mutton")).click();
            fail("should have barfed");
        } catch (NothingMatches e) {
            // expected;
        }

        assertThat(sb.toString(),
                equalTo("wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n" +
                "we1.getText() -> 'Mary had 3 little lamb(s).'\n" +
                "we2.getText() -> 'Mary had 4 little lamb(s).'\n"));
    }
    public static class TextContainsWord implements FluentMatcher {

        private String word;

        public TextContainsWord(String word) {
            this.word = word;
        }

        public boolean matches(WebElement webElement) {
            return webElement.getText().contains(word);
        }
    }


    private static ThreadLocal<Boolean> FAIL_ON_NEXT = new ThreadLocal<Boolean>();

    private static class WebDriverJournal implements WebDriver {

        private Integer CTR = 1;
        private final StringBuilder sb;
        public boolean lastSelected;
        public boolean lastEnabled;
        public boolean lastDisplayed;

        public WebDriverJournal(StringBuilder sb) {
            this.sb = sb;
        }

        public void get(String s) {
            sb.append("get(" + s + ")\n");
        }

        public String getCurrentUrl() {
            String rv = "STR#" + CTR++;
            sb.append("getCurrentUrl():" + rv + "\n");
            return rv;
        }

        public String getTitle() {
            String rv = "STR#" + CTR++;
            sb.append("getTitle():" + rv + "\n");
            return rv;
        }

        public List<WebElement> findElements(By by) {
            WebElement we = new WebElementJournal(sb, this);
            WebElement we2 = new WebElementJournal(sb, this);
            List<WebElement> elems = asList(we, we2);
            sb.append(this + ".findElements(" + by + ") -> " + elems + "\n");
            return elems;
        }

        public WebElement findElement(By by) {
            WebElement rv;
            if (by.toString().contains("mismatching_tag_name")) {
                rv = bogusElem;
            } else {
                rv = new WebElementJournal(sb, this);
            }
            sb.append(this + ".findElement(" + by + ") -> " + rv + "\n");
            return rv;
        }

        public String getPageSource() {
            return null;
        }

        public void close() {
        }

        public void quit() {
        }

        public Set<String> getWindowHandles() {
            return null;
        }

        public String getWindowHandle() {
            return null;
        }

        public TargetLocator switchTo() {
            return null;
        }

        public Navigation navigate() {
            return null;
        }

        public Options manage() {
            return null;
        }

        @Override
        public String toString() {
            return "wd0";
        }
    }

    private static class WebElementJournal implements WebElement {

        private final StringBuilder sb;
        private final WebDriverJournal webDriverJournal;
        private final int ct;

        public WebElementJournal(StringBuilder sb, WebDriverJournal webDriverJournal) {
            this.sb = sb;
            this.webDriverJournal = webDriverJournal;
            ct = webDriverJournal.CTR++;
        }

        public void click() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".click()\n");
        }

        public void submit() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".submit()\n");
        }

        public void sendKeys(CharSequence... charSequences) {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            if (charSequences[0].equals("RAIN_IN_SPAIN")) {
                throw new WebDriverException("FAILED AS PER STUB SETUP");
            }
            sb.append(this + ".sendKeys("+charSequences[0]+")\n");
        }

        public void clear() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".clear()\n");
        }

        public String getTagName() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }

            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            for (StackTraceElement elem : stackTraceElements) {
                String methodName = elem.getMethodName();
                if (methodName.endsWith("s")) {
                    methodName = methodName.substring(0, methodName.length() - 1);
                }
                String className = elem.getClassName();
                if ((className.equals(FluentCore.class.getName())
                        || className.equals(OngoingSingleElement.class.getName()))
                        && !methodName.equals("multiple")
                        && !methodName.equals("single")) {
                    if (methodName.equals("link")) {
                        methodName = "a";
                    }
                    if (methodName.equals("getTagName")) {
                        methodName = "taggart";
                    }
                    sb.append(this + ".getTagName() -> '" + methodName + "'\n");
                    return methodName;
                }
            }

            throw new UnsupportedOperationException("FluentBase not in stack, can't work out method name");
        }

        public String getAttribute(String s) {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".getAttribute("+s+") -> " + s + "_value\n");
            return s + "_value";
        }


        public String getText() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            String msg = "Mary had " + webDriverJournal.CTR++ + " little lamb(s).";
            sb.append(this + ".getText() -> '" + msg + "'\n");
            return msg;
        }

        public List<WebElement> findElements(By by) {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            WebElement we = new WebElementJournal(sb, webDriverJournal);
            WebElement we2 = new WebElementJournal(sb, webDriverJournal);
            List<WebElement> elems = asList(we, we2);
            sb.append(this + ".findElements(" + by + ") -> " + elems + "\n");
            return elems;
        }

        public WebElement findElement(By by) {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            WebElementJournal rv = new WebElementJournal(sb, webDriverJournal);
            sb.append(this + ".findElement(" + by + ") -> " + rv + "\n");
            return rv;
        }

        public boolean isDisplayed() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            webDriverJournal.lastDisplayed = !webDriverJournal.lastDisplayed;
            sb.append(this + ".isDisplayed() -> "+webDriverJournal.lastDisplayed+"\n");
            return webDriverJournal.lastDisplayed;
        }

        public boolean isSelected() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            webDriverJournal.lastSelected = !webDriverJournal.lastSelected;
            sb.append(this + ".isSelected() -> "+webDriverJournal.lastSelected+"\n");
            return webDriverJournal.lastSelected;
        }

        public boolean isEnabled() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            webDriverJournal.lastEnabled = !webDriverJournal.lastEnabled;
            sb.append(this + ".isEnabled() -> "+webDriverJournal.lastEnabled+"\n");
            return webDriverJournal.lastEnabled;
        }

        public Point getLocation() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".getLocation() -> 1,1\n");
            return new Point(1,1);
        }

        public Dimension getSize() {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".getSize() -> 10,10\n");
            return new Dimension(10,10);
        }

        public String getCssValue(String s) {
            if (FAIL_ON_NEXT.get()) {
                throw new WebDriverException();
            }
            sb.append(this + ".getCssValue("+s+") -> "+s+"_value\n");
            return s + "_value";
        }

        @Override
        public String toString() {
            return "we" + ct;
        }
    }


}
