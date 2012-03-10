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

        BaseFluentWebDriver fc = fwd.div(ID_A).div(ID_B);

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

        BaseFluentWebDriver fc = fwd.div(ID_A).div(ID_B).span().click();

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
    public void example_of_longer_query_using_classes() {

        BaseFluentWebDriver fc = fwd.div(ID_A).div(CLASS_C).span().click();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.id: idA) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(composite([By.tagName: div, By.className: classC])) -> we2\n" +
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
        TestableString cssVal = ofwd.getCssValue("blort");
        assertThat(cssVal, equalTo(cs("blort_value")));
        assertThat(sb.toString(), equalTo("we2.getCssValue(blort) -> blort_value\n"));

        sb.setLength(0);
        TestableString value = ofwd.getAttribute("valerie");
        assertThat(value, equalTo(cs("valerie_value")));
        assertThat(sb.toString(), equalTo("we2.getAttribute(valerie) -> valerie_value\n"));

        sb.setLength(0);
        TestableString tagName = ofwd.getTagName();
        assertThat(tagName, equalTo(cs("taggart")));
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
        assertThat(text, equalTo(cs("Mary had 3 little lamb(s).")));
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
            ofwd.location().shouldBe(new Point(2, 2)).value();
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
        FluentWebElement ose = fwd.div(By.id("foo"));

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
        filter_exception_handling(RuntimeException.class);
    }

    @Test
    public void assertion_errors_decorated_for_filter() {
        filter_exception_handling(AssertionError.class);
    }

    private void filter_exception_handling(Class<? extends Throwable> throwable) {
        FluentWebElements ome = fwd.divs(By.id("foo"));

        assertThat(ome, notNullValue());

        FAIL_ON_NEXT.set(throwable);

        try {
            ome.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(Hello)"));
            assertThat(e.getCause(), instanceOf(throwable));
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
                return toString;
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
        FluentWebElements ome = fwd.divs(By.id("foo"));

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
            FluentWebElement ose = fwd.divs(By.xpath("foo")).span(By.xpath("bar"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void rumtime_exceptions_decorated_for_multiple_element() {
        multiple_elem_exception_handling(RuntimeException.class);
    }

    @Test
    public void assertion_error_decorated_for_multiple_element() {
        multiple_elem_exception_handling(AssertionError.class);
    }

    private void multiple_elem_exception_handling(Class<? extends Throwable> throwable) {
        FluentWebElements ome = fwd.divs(By.id("foo"));

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
        assertThat(text, equalTo(cs("Mary had 3 little lamb(s).Mary had 4 little lamb(s).")));
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
            assertThat(e.getMessage(), equalTo("getCssValue() has no meaning for multiple elements"));
        }

        sb.setLength(0);
        try {
            elems.getAttribute("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getAttribute() has no meaning for multiple elements"));
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

        List<WebElement> elems = new ArrayList<WebElement>();
        WebElement item0 = mock(WebElement.class);
        elems.add(item0);
        elems.add(mock(WebElement.class));
        elems.add(mock(WebElement.class));
        elems.add(mock(WebElement.class));

        FluentWebElements ogme = new FluentWebElements(null, new ArrayList<WebElement>(elems), "");

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

        BaseFluentWebDriver fc = fwd.divs().filter(new FourLambFilter()).click();

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

        BaseFluentWebDriver fc = fwd.divs().first(new TextContainsWord("lamb(s)")).click();

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

        try {
            fwd.divs().first(new TextContainsWord("mutton")).click();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.divs(By.tagName: div)" +
                    ".filter(TextContainsWord{word='mutton'})"));
            assertTrue(e.getCause() instanceof NothingMatches);
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
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().div()"));
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
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().div()"));
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
