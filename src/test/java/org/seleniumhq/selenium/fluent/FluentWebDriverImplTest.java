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
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.seleniumhq.selenium.fluent.WebElementJournal.throwExceptionMaybe;

/**
 * Unit test for simple App.
 */
public class FluentWebDriverImplTest extends BaseTest {

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");
    static final By CLASS_C = By.className("classC");
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

        FluentWebElement fe = fwd.div(ID_A).div(ID_B);

        assertThat(fe, notNullValue());

        InOrder io = inOrder(we, wd, we2);
        io.verify(wd).findElement(ID_A);
        io.verify(we).getTagName();
        io.verify(we).findElement(ID_B);
        io.verify(we2).getTagName();
        verifyNoMoreInteractions(wd, we, we2);
    }

    @Test
    public void example_of_longer_query_using_IDs() {

        FluentWebElement fe = fwd.div(ID_A).div(ID_B).span().click();

        assertThat(fe, notNullValue());
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
    public void example_of_longer_query_using_classes() {

        FluentWebElement fe = fwd.div(ID_A).div(CLASS_C).span().click();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.id: idA) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(FluentBy.composite([By.tagName: div, By.className: classC])) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we2.findElement(By.tagName: span) -> we3\n" +
                        "we3.getTagName() -> 'span'\n" +
                        "we3.click()\n"
        ));
    }

    @Test
    public void lengthier_expression_with_late_runtime_exception() {

        BaseFluentWebDriver fc = null;
        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B).span();

            FAIL_ON_NEXT.set(RuntimeException.class);

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

        BaseFluentWebDriver fc = null;
        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B).span();

            FAIL_ON_NEXT.set(AssertionError.class);

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

        FluentWebElement ofwd = fwd.div().span(By.xpath("@foo = 'bar'")).sendKeys("apple").clearField().submit();

//        assertThat(ofwd, notNullValue());
//        assertThat(sb.toString(), equalTo(
//                "wd0.findElement(By.tagName: div) -> we1\n" +
//                        "we1.getTagName() -> 'div'\n" +
//                        "we1.findElement(By.xpath: .//span[@foo = 'bar']) -> we2\n" +
//                        "we2.getTagName() -> 'span'\n" +
//                        "we2.sendKeys(apple)\n" +
//                        "we2.clear()\n" +
//                        "we2.submit()\n"
//        ));
//
//        sb.setLength(0);
//        Point locn = ofwd.getLocation();
//        assertThat(locn.toString(), equalTo("(1, 1)"));
//        assertThat(sb.toString(), equalTo("we2.getLocation() -> 1,1\n"));
//
//        sb.setLength(0);
//        Dimension size = ofwd.getSize();
//        assertThat(size.toString(), equalTo("(10, 10)"));
//        assertThat(sb.toString(), equalTo("we2.getSize() -> 10,10\n"));
//
//        sb.setLength(0);
//        TestableString cssVal = ofwd.getCssValue("blort");
//        assertThat(cssVal.toString(), equalTo(cs("blort_value")));
//        assertThat(sb.toString(), equalTo("we2.getCssValue(blort) -> blort_value\n"));
//
//        sb.setLength(0);
//        TestableString value = ofwd.getAttribute("valerie");
//        assertThat(value.toString(), equalTo(cs("valerie_value")));
//        assertThat(sb.toString(), equalTo("we2.getAttribute(valerie) -> valerie_value\n"));

        sb.setLength(0);
        TestableString tagName = ofwd.getTagName();
        assertThat(tagName.toString(), equalTo(cs("taggart")));
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
        TestableString text = ofwd.getText();
        assertThat(text.toString(), equalTo(cs("Mary had 3 little lamb(s).")));
        assertThat(sb.toString(), equalTo("we2.getText() -> 'Mary had 3 little lamb(s).'\n"));

    }
    @Test

    public void assertions_against_otherwise_non_ongoing() {

        FluentWebElement ofwd = fwd.div();

        assertThat(ofwd, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n"
        ));

        sb.setLength(0);
        WebElementValue<Point> location = ofwd.location();
        Point shouldBe = new Point(1, 1);
        Matchable<Point> pointMatchable = location.shouldBe(shouldBe);
        Point locn = pointMatchable.value();
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        sb.setLength(0);
        try {
            WebElementValue<Point> location1 = ofwd.location();
            location1.shouldBe(new Point(2, 2)).value();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("?.div().location().shouldBe((2, 2)) ~ but was <(1, 1)>"));
        }
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        sb.setLength(0);
        locn = ofwd.location().shouldNotBe(new Point(2, 2)).value();
        assertThat(locn.toString(), equalTo("(1, 1)"));
        assertThat(sb.toString(), equalTo("we1.getLocation() -> 1,1\n"));

        sb.setLength(0);
        try {
            ofwd.location().shouldNotBe(new Point(1, 1)).value();
            fail("should have barfed");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("?.div().location().shouldNotBe((1, 1)) ~ but was."));
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
        wrap_exceptions_tests(RuntimeException.class);
    }

    @Test
    public void assertion_errors_decorated_for_single_element() {
        wrap_exceptions_tests(AssertionError.class);
    }

    private void wrap_exceptions_tests(Class<? extends Throwable> throwable) {
        FluentWebElement fwe = fwd.div(By.id("foo"));

        assertThat(fwe, notNullValue());

        FAIL_ON_NEXT.set(throwable);

//        try {
//            fwe.sendKeys("a");
//            fail("should have barfed");
//        } catch (FluentExecutionStopped e) {
//            assertThat(e.getMessage(), containsString("?.div(By.id: foo).sendKeys('a')"));
//        }
//
//        try {
//            fwe.submit();
//            fail("should have barfed");
//        } catch (FluentExecutionStopped e) {
//            assertThat(e.getMessage(), containsString("?.div(By.id: foo).submit()"));
//        }
//
//        try {
//            fwe.clearField();
//            fail("should have barfed");
//        } catch (FluentExecutionStopped e) {
//            assertThat(e.getMessage(), containsString("?.div(By.id: foo).clearField()"));
//        }
//
//        try {
//            fwe.getLocation();
//            fail("should have barfed");
//        } catch (FluentExecutionStopped e) {
//            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getLocation()"));
//        }
//
//        try {
//            fwe.getSize();
//            fail("should have barfed");
//        } catch (FluentExecutionStopped e) {
//            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getSize()"));
//        }

        try {
            TestableString valerie = fwe.getAttribute("valerie");
            valerie.toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getAttribute('valerie')"));
        }
        try {
            fwe.getCssValue("blort").toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getCssValue('blort')"));
        }

        try {
            fwe.getTagName().toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getTagName()"));
        }

        try {
            fwe.isSelected();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isSelected()"));
        }

        try {
            fwe.isEnabled();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isEnabled()"));
        }

        try {
            fwe.isDisplayed();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isDisplayed()"));
        }
        try {
            fwe.getText().toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getText()"));
        }
    }

    @Test
    public void exceptions_decorated_for_filter() {
        filter_exception_handling(RuntimeException.class);
    }

    @Test
    public void assertion_errors_decorated_for_filter() {
        filter_exception_handling(AssertionError.class);
    }

    private void filter_exception_handling(Class<? extends Throwable> throwable) {
        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            fwe.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(myMatcher('Hello'))"));
            assertThat(e.getCause(), instanceOf(throwable));
        }
    }
    
    @Test
    public void nothing_matching_in_filter_exception_handling() {
        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        FAIL_ON_NEXT.set(NothingMatches.class);

        try {
            fwe.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseNothingMatchesInFilter e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(myMatcher('Hello'))"));
            assertNull(e.getCause());
        }
    }

    @Test
    public void nothing_matching_in_first_exception_handling() {
        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        FAIL_ON_NEXT.set(NothingMatches.class);

        try {
            fwe.first(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseNothingMatchesInFilter e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).first(myMatcher('Hello'))"));
            assertNull(e.getCause());
        }
    }

    private FluentMatcher makeMatcherThatUsesWebDriver(final String toString) {
        return new FluentMatcher() {
            public boolean matches(WebElement webElement) {
                throwExceptionMaybe(FluentWebDriverImplTest.FAIL_ON_NEXT.get());
                return webElement.getText().equals("it does not matter, as an exception will be thrown");
            }
            @Override
            public String toString() {
                return "myMatcher('" + toString + "')";
            }
        };
    }

    @Test
    public void runtime_exceptions_decorated_for_first() {
        first_exception_handling(RuntimeException.class);
    }

    @Test
    public void assertion_error_decorated_for_first() {
        first_exception_handling(AssertionError.class);
    }

    private void first_exception_handling(Class<? extends Throwable> throwable) {
        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            fwe.first(makeMatcherThatUsesWebDriver("Goodbye"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).first(myMatcher('Goodbye'))"));
        }
    }

    @Test
    public void further_find_element_after_multiple_is_unsupported() {

        try {
            fwd.divs(By.xpath("foo")).span(By.xpath("bar"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void runtime_exceptions_decorated_for_multiple_element() {
        multiple_elem_exception_handling(RuntimeException.class);
    }

    @Test
    public void assertion_error_decorated_for_multiple_element() {
        multiple_elem_exception_handling(AssertionError.class);
    }

    private void multiple_elem_exception_handling(Class<? extends Throwable> throwable) {
        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            fwe.sendKeys("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).sendKeys('a')"));
        }

        try {
            fwe.click();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).click()"));
        }

        try {
            fwe.submit();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).submit()"));
        }

        try {
            fwe.clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).clearField()"));
        }

        try {
            fwe.isSelected();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isSelected()"));
        }

        try {
            fwe.isEnabled();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isEnabled()"));
        }

        try {
            fwe.isDisplayed();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isDisplayed()"));
        }

        try {
            fwe.getText().toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).getText()"));
        }
    }

    @Test
    public void multiple_hits_from_the_outset_and_operations_on_the_resulting_list() {

        FluentWebElements elems = fwd.divs();

        assertThat(elems, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n"
        ));

        sb.setLength(0);
        FluentWebElements ofwd2 = elems.clearField();
        assertThat(ofwd2, notNullValue());
        assertThat(sb.toString(), equalTo("we1.clear()\nwe2.clear()\n"));

        sb.setLength(0);
        FluentWebElements ofwd3 = elems.click();
        assertThat(ofwd3, notNullValue());
        assertThat(sb.toString(), equalTo("we1.click()\nwe2.click()\n"));

        sb.setLength(0);
        boolean areSelected = elems.isSelected();
        assertThat(areSelected, equalTo(false));
        assertThat(sb.toString(), equalTo("we1.isSelected() -> true\nwe2.isSelected() -> false\n"));

        sb.setLength(0);
        boolean areEnabled = elems.isEnabled();
        assertThat(areEnabled, equalTo(false));
        assertThat(sb.toString(), equalTo("we1.isEnabled() -> true\nwe2.isEnabled() -> false\n"));

        sb.setLength(0);
        boolean areDisplayed = elems.isDisplayed();
        assertThat(areDisplayed, equalTo(false));
        assertThat(sb.toString(), equalTo("we1.isDisplayed() -> true\nwe2.isDisplayed() -> false\n"));

        sb.setLength(0);
        FluentWebElements ofwd4 = elems.sendKeys("aaa");
        assertThat(ofwd4, notNullValue());
        assertThat(sb.toString(), equalTo("we1.sendKeys(aaa)\nwe2.sendKeys(aaa)\n"));

        sb.setLength(0);
        FluentWebElements ofwd5 = elems.submit();
        assertThat(ofwd5, notNullValue());
        assertThat(sb.toString(), equalTo("we1.submit()\nwe2.submit()\n"));

        sb.setLength(0);
        CharSequence text = elems.getText();
        assertThat(text.toString(), equalTo(cs("Mary had 3 little lamb(s).Mary had 4 little lamb(s).")));
        assertThat(sb.toString(), equalTo("we1.getText() -> 'Mary had 3 little lamb(s).'\nwe2.getText() -> 'Mary had 4 little lamb(s).'\n"));

        sb.setLength(0);
        try {
            elems.getLocation();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getLocation() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            elems.getSize();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getSize() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            elems.getCssValue("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getCssValue('x') has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            elems.getAttribute("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getAttribute('x') has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            elems.getTagName();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getTagName() has no meaning for multiple elements"));
        }
    }


    @Test
    public void is_a_list() {

        List<FluentWebElement> elems = new ArrayList<FluentWebElement>();
        FluentWebElement item0 = mock(FluentWebElement.class);
        elems.add(item0);
        elems.add(mock(FluentWebElement.class));
        elems.add(mock(FluentWebElement.class));
        elems.add(mock(FluentWebElement.class));

        FluentWebElements ogme = new FluentWebElements(null, new ArrayList<FluentWebElement>(elems), null);

        assertThat(ogme.size(), equalTo(4));
        assertThat(ogme.get(0), equalTo(item0));

        {
            List<FluentWebElement> elems2 = new ArrayList<FluentWebElement>();
            elems2.add(mock(FluentWebElement.class));
            elems2.add(mock(FluentWebElement.class));

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
        FluentWebElement[] wes = new FluentWebElement[0] ;
        assertThat(ogme.toArray(wes).length, equalTo(4));

        assertThat(ogme.subList(1, 2).size(), equalTo(1));

        assertThat(ogme.listIterator(), notNullValue());

        assertThat(ogme.listIterator(0), notNullValue());

        ogme.clear();

        assertThat(ogme.size(), equalTo(0));

    }

    @Test
    public void filtering() {

        FluentWebElements fe = fwd.divs().filter(new FourLambFilter()).click();

        assertThat(fe, notNullValue());
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

        FluentWebElement fe = fwd.divs().first(new TextContainsWord("lamb(s)")).click();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(),
                equalTo("wd0.findElements(By.tagName: div) -> [we1, we2]\n" +
                "we1.getTagName() -> 'div'\n" +
                "we2.getTagName() -> 'div'\n" +
                "we1.getText() -> 'Mary had 3 little lamb(s).'\n" +
                "we1.click()\n"));
    }

    @Test
    public void first_finds_nothing() {

        try {
            fwd.divs().first(new TextContainsWord("mutton")).click();
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseNothingMatchesInFilter e) {
            assertThat(e.getMessage(), equalTo("org.seleniumhq.selenium.fluent.NothingMatches during invocation of: ?.divs(By.tagName: div)" +
                    ".first(TextContainsWord{word='mutton'})"));
            assertNull(e.getCause());
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

        @Override
        public String toString() {
            return "TextContainsWord{" +
                    "word='" + word + '\'' +
                    '}';
        }
    }


    @Test
    public void wait_should_wait() {

        fwd.within(Period.secs(10)).div();

        assertThat(sb.toString(),
                equalTo("wd0.manage().timeouts().implictlyWait(10,SECONDS)\n" +
                        "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "wd0.manage().timeouts().implictlyWait(0,SECONDS)\n"));
    }

    @Test
    public void wait_should_reset_even_if_exceptions_are_thrown() {

        try {
            FluentWebElement within = fwd.div().within(Period.secs(10));
            FAIL_ON_NEXT.set(AssertionError.class);
            within.div(); // consequential stub getTagName() with throw
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().within(secs(10)).div()"));
            assertTrue(e.getCause() instanceof AssertionError);
        }

        assertThat(sb.toString(),
                equalTo("wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "wd0.manage().timeouts().implictlyWait(10,SECONDS)\n" +
                        "we1.findElement(By.tagName: div) -> ✘\n" + // ✘ means caused the exception
                        "wd0.manage().timeouts().implictlyWait(0,SECONDS)\n"));
    }

  @Test
    public void wait_should_wait_on_element() {

        fwd.within(Period.secs(10)).div();

        assertThat(sb.toString(),
                equalTo("wd0.manage().timeouts().implictlyWait(10,SECONDS)\n" +
                        "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "wd0.manage().timeouts().implictlyWait(0,SECONDS)\n"));
    }

    @Test
    public void wait_should_reset_even_if_exceptions_are_thrown_on_element() {

        try {
            FluentWebElement within = fwd.div().within(Period.secs(10));
            FAIL_ON_NEXT.set(AssertionError.class);
            within.div(); // consequential stub getTagName() with throw
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().within(secs(10)).div()"));
            assertTrue(e.getCause() instanceof AssertionError);
        }

        assertThat(sb.toString(),
                equalTo("wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "wd0.manage().timeouts().implictlyWait(10,SECONDS)\n" +
                        "we1.findElement(By.tagName: div) -> ✘\n" + // ✘ means caused the exception
                        "wd0.manage().timeouts().implictlyWait(0,SECONDS)\n"));
    }


}
