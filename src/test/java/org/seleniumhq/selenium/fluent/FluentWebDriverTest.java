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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.internal.matchers.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.seleniumhq.selenium.fluent.WebElementJournal.throwExceptionMaybe;

/**
 * Unit test for simple App.
 */
public class FluentWebDriverTest {

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");
    private StringBuilder sb;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);

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

        FluentCore fc = fwd.div(ID_A).div(ID_B);

        assertThat(fc, notNullValue());

        InOrder io = inOrder(we, wd, we2);
        io.verify(wd).findElement(ID_A);
        io.verify(we).getTagName();
        io.verify(we).findElement(ID_B);
        io.verify(we2).getTagName();
        verifyNoMoreInteractions(wd, we, we2);
    }

    @Test
    public void example_of_longer_query_using_IDs() {

        FluentCore fc = fwd.div(ID_A).div(ID_B).span().click();

        assertThat(fc, notNullValue());
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
    public void lengthier_expression_with_late_runtime_exception() {

        FluentCore fc = null;
        try {
            OngoingSingleElement span = fwd.div(ID_A).div(ID_B).span();

            FAIL_ON_NEXT.set(new RuntimeException());

            fc = span.sendKeys("RAIN_IN_SPAIN");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.div(By.id: idA).div(By.id: idB).span().sendKeys('RAIN_IN_SPAIN')"));
            assertThat(e.getCause(), notNullValue());
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
    public void lengthier_expression_with_late_assertion_error() {

        FluentCore fc = null;
        try {
            OngoingSingleElement span = fwd.div(ID_A).div(ID_B).span();

            FAIL_ON_NEXT.set(new AssertionError());

            fc = span.sendKeys("RAIN_IN_SPAIN");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.id: idA).div(By.id: idB).span().sendKeys('RAIN_IN_SPAIN')"));
            assertThat(e.getCause(), notNullValue());
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

        OngoingFluentWebDriver ofwd = fwd.div().span(By.xpath("@foo = 'bar'")).sendKeys("apple").clearField().submit();

        assertThat(ofwd, notNullValue());
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
        Point locn = ofwd.getLocation();
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we2.getLocation() -> 1,1\n"));

        sb.setLength(0);
        Dimension size = ofwd.getSize();
        assertThat(size.toString(), equalTo("(10, 10)"));
        assertThat(sb.toString(), equalTo("we2.getSize() -> 10,10\n"));

        sb.setLength(0);
        String cssVal = ofwd.getCssValue("blort");
        assertThat(cssVal, equalTo("blort_value"));
        assertThat(sb.toString(), equalTo("we2.getCssValue(blort) -> blort_value\n"));

        sb.setLength(0);
        String value = ofwd.getAttribute("valerie");
        assertThat(value, equalTo("valerie_value"));
        assertThat(sb.toString(), equalTo("we2.getAttribute(valerie) -> valerie_value\n"));

        sb.setLength(0);
        String tagName = ofwd.getTagName();
        assertThat(tagName, equalTo("taggart"));
        assertThat(sb.toString(), equalTo("we2.getTagName() -> 'taggart'\n"));

        sb.setLength(0);
        boolean isSelected = ofwd.isSelected();
        assertThat(isSelected, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isSelected() -> true\n"));

        sb.setLength(0);
        boolean isEnabled = ofwd.isEnabled();
        assertThat(isEnabled, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isEnabled() -> true\n"));

        sb.setLength(0);
        boolean isDisplayed = ofwd.isDisplayed();
        assertThat(isDisplayed, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isDisplayed() -> true\n"));

        sb.setLength(0);
        String text = ofwd.getText();
        assertThat(text, equalTo("Mary had 3 little lamb(s)."));
        assertThat(sb.toString(), equalTo("we2.getText() -> 'Mary had 3 little lamb(s).'\n"));

    }
    @Test

    public void assertions_against_otherwise_non_ongoing() {

        OngoingSingleElement ofwd = fwd.div();

        assertThat(ofwd, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n"
        ));

        sb.setLength(0);
        Point locn = ofwd.location().shouldBe(new Point(1, 1)).value();
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        sb.setLength(0);
        try {
            ofwd.location().shouldBe(new Point(2, 2)).value();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("?.div().location().should().be(<(2, 2)>) ~ but was <(1, 1)>"));
        }
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        sb.setLength(0);
        locn = ofwd.location().shouldNotBe(new Point(2, 2)).value();
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        sb.setLength(0);
        try {
            ofwd.location().shouldNotBe(new Point(1, 1)).value();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("?.div().location().shouldNot().be(<(1, 1)>) ~ but was."));
        }
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        {
            sb.setLength(0);
            Dimension size = ofwd.size().shouldBe(new Dimension(10, 10)).value();
            assertThat(size, equalTo(new Dimension(10, 10)));
            assertThat(sb.toString(), equalTo("we1.getSize() -> 10,10\n"));
        }
        {

            sb.setLength(0);
            Matchable<Dimension> should = ofwd.size().shouldBe(new Dimension(10,10));
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.getSize() -> 10,10\n"));
        }
        {
            sb.setLength(0);
            Matchable<String> should = ofwd.cssValue("blort").shouldBe("blort_value");
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.getCssValue(blort) -> blort_value\n"));
        }
        {
            sb.setLength(0);
            Matchable<String> should = ofwd.attribute("valerie").shouldBe("valerie_value");
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.getAttribute(valerie) -> valerie_value\n"));
        }
        {
            sb.setLength(0);
            Matchable<String> should = ofwd.tagName().shouldBe("taggart");
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.getTagName() -> 'taggart'\n"));
        }
        {
            sb.setLength(0);
            Matchable<Boolean> should = ofwd.selected().shouldBe(true);
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.isSelected() -> true\n"));
        }
        {
            sb.setLength(0);
            Matchable<Boolean> should = ofwd.enabled().shouldBe(true);
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.isEnabled() -> true\n"));
        }
        {
            sb.setLength(0);
            Matchable<Boolean> should = ofwd.displayed().shouldBe(true);
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.isDisplayed() -> true\n"));
        }
        {
            sb.setLength(0);
            Matchable<String> should = ofwd.text().shouldBe("Mary had 2 little lamb(s).");
            assertThat(should, notNullValue());
            assertThat(sb.toString(), equalTo("we1.getText() -> 'Mary had 2 little lamb(s).'\n"));
        }
//        {
//            sb.setLength(0);
//            Matchable<String> should = ofwd.text().should().have(containsString("lamb"));
//            assertThat(should, notNullValue());
//            assertThat(sb.toString(), equalTo("we1.getText() -> 'Mary had 2 little lamb(s).'\n"));
//        }
    }

    @Test
    public void runtime_exceptions_decorated_for_single_element() {
        wrap_exceptions_tests(new RuntimeException());
    }

    @Test
    public void assertion_errors_decorated_for_single_element() {
        wrap_exceptions_tests(new AssertionError());
    }

    private void wrap_exceptions_tests(Throwable throwable) {
        OngoingSingleElement ose = fwd.div(By.id("foo"));

        assertThat(ose, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            ose.sendKeys("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).sendKeys('a')"));
        }

        try {
            ose.submit();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).submit()"));
        }

        try {
            ose.clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).clearField()"));
        }

        try {
            ose.getLocation();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getLocation()"));
        }

        try {
            ose.getSize();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getSize()"));
        }

        try {
            ose.getAttribute("valerie");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getAttribute(valerie)"));
        }
        try {
            ose.getCssValue("blort");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getCssValue(blort)"));
        }

        try {
            ose.getTagName();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getTagName()"));
        }

        try {
            ose.isSelected();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isSelected()"));
        }

        try {
            ose.isEnabled();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isEnabled()"));
        }

        try {
            ose.isDisplayed();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isDisplayed()"));
        }
        try {
            ose.getText();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getText()"));
        }
    }

    @Test
    public void exceptions_decorated_for_filter() {
        filter_exception_handling(new RuntimeException());
    }

    @Test
    public void assertion_errors_decorated_for_filter() {
        filter_exception_handling(new AssertionError());
    }

    private void filter_exception_handling(Throwable throwable) {
        OngoingMultipleElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            ome.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(Hello)"));
            assertThat(e.getCause(), sameInstance(throwable));
        }
    }

    private FluentMatcher makeMatcherThatUsesWebDriver(final String toString) {
        return new FluentMatcher() {
            public boolean matches(WebElement webElement) {
                throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
                return webElement.getText().equals("it does not matter, as an exception will be thrown");
            }
            @Override
            public String toString() {
                return toString;
            }
        };
    }

    @Test
    public void runtime_exceptions_decorated_for_first() {
        first_exception_handling(new RuntimeException());
    }

    @Test
    public void assertion_error_decorated_for_first() {
        first_exception_handling(new AssertionError());
    }

    private void first_exception_handling(Throwable throwable) {
        OngoingMultipleElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            ome.first(makeMatcherThatUsesWebDriver("Goodbye"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
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
    public void rumtime_exceptions_decorated_for_multiple_element() {
        multiple_elem_exception_handling(new RuntimeException());
    }

    @Test
    public void assertion_error_decorated_for_multiple_element() {
        multiple_elem_exception_handling(new AssertionError());
    }

    private void multiple_elem_exception_handling(Throwable throwable) {
        OngoingMultipleElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            ome.sendKeys("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).sendKeys('a')"));
        }

        try {
            ome.click();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).click()"));
        }

        try {
            ome.submit();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).submit()"));
        }

        try {
            ome.clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).clearField()"));
        }

        try {
            ome.isSelected();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isSelected()"));
        }

        try {
            ome.isEnabled();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isEnabled()"));
        }

        try {
            ome.isDisplayed();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isDisplayed()"));
        }

        try {
            ome.getText();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
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

        FluentCore fc = fwd.div()
                .div(By.xpath("@foo = 'bar'"))
                .div(By.cssSelector("baz"))
                .divs();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.div()
                .divs(By.name("qux"));

        assertThat(fc, notNullValue());
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
        } catch (AssertionError e) {        // TODO
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void button_functionality() {

        FluentCore fc = fwd.button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.button()
                .buttons(By.name("qux"));

        assertThat(fc, notNullValue());
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
    } catch (AssertionError e) { // TODO
        assertTrue(e.getMessage().contains("tag was incorrect"));
    }

}

    @Test
    public void option_functionality() {

        FluentCore fc = fwd.option()
                .option(By.xpath("@foo = 'bar'"))
                .option(By.cssSelector("baz"))
                .options();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.option()
                .options(By.name("qux"));

        assertThat(fc, notNullValue());
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
        } catch (AssertionError e) { // TODO
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void select_functionality() {

        FluentCore fc = fwd.select()
                .select(By.xpath("@foo = 'bar'"))
                .select(By.cssSelector("baz"))
                .selects();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.select()
                .selects(By.name("qux"));

        assertThat(fc, notNullValue());
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
        } catch (AssertionError e) { // TODO
            assertTrue(e.getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void span_functionality() {

        FluentCore fc = fwd.span()
                .span(By.xpath("@foo = 'bar'"))
                .span(By.cssSelector("baz"))
                .spans();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.span()
                .spans(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.tr()
                .tr(By.xpath("@foo = 'bar'"))
                .tr(By.cssSelector("baz"))
                .trs();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.tr()
                .trs(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.td()
                .td(By.xpath("@foo = 'bar'"))
                .td(By.cssSelector("baz"))
                .tds();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.td()
                .tds(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.table()
                .table(By.xpath("@foo = 'bar'"))
                .table(By.cssSelector("baz"))
                .tables();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.table()
                .tables(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.h1()
                .h1(By.xpath("@foo = 'bar'"))
                .h1(By.cssSelector("baz"))
                .h1s();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.h1()
                .h1s(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.h2()
                .h2(By.xpath("@foo = 'bar'"))
                .h2(By.cssSelector("baz"))
                .h2s();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.h2()
                .h2s(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.h3()
                .h3(By.xpath("@foo = 'bar'"))
                .h3(By.cssSelector("baz"))
                .h3s();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.h3()
                .h3s(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.h4()
                .h4(By.xpath("@foo = 'bar'"))
                .h4(By.cssSelector("baz"))
                .h4s();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.h4()
                .h4s(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.img()
                .img(By.xpath("@foo = 'bar'"))
                .img(By.cssSelector("baz"))
                .imgs();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.img()
                .imgs(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.form()
                .form(By.xpath("@foo = 'bar'"))
                .form(By.cssSelector("baz"))
                .forms();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.form()
                .forms(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.textarea()
                .textarea(By.xpath("@foo = 'bar'"))
                .textarea(By.cssSelector("baz"))
                .textareas();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.textarea()
                .textareas(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.input()
                .input(By.xpath("@foo = 'bar'"))
                .input(By.cssSelector("baz"))
                .inputs();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.input()
                .inputs(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.link()
                .link(By.xpath("@foo = 'bar'"))
                .link(By.cssSelector("baz"))
                .links();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.link()
                .links(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.p()
                .ps(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.th()
                .th(By.xpath("@foo = 'bar'"))
                .th(By.cssSelector("baz"))
                .ths();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.th()
                .ths(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.ul()
                .ul(By.xpath("@foo = 'bar'"))
                .ul(By.cssSelector("baz"))
                .uls();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.ul()
                .uls(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.ol()
                .ol(By.xpath("@foo = 'bar'"))
                .ol(By.cssSelector("baz"))
                .ols();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.ol()
                .ols(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.li()
                .li(By.xpath("@foo = 'bar'"))
                .li(By.cssSelector("baz"))
                .lis();

        assertThat(fc, notNullValue());
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
        FluentCore fc = fwd.li()
                .lis(By.name("qux"));

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.divs().filter(new FourLambFilter()).click();

        assertThat(fc, notNullValue());
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

        FluentCore fc = fwd.divs().first(new TextContainsWord("lamb(s)")).click();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(),
                equalTo("wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n" +
                "we1.getText() -> 'Mary had 3 little lamb(s).'\n" +
                "we1.click()\n"));
    }

    @Test
    public void first_finds_nothing() {

        FluentCore fc = null;
        try {
            fc = fwd.divs().first(new TextContainsWord("mutton")).click();
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

    static ThreadLocal<Throwable> FAIL_ON_NEXT = new ThreadLocal<Throwable>();

}
