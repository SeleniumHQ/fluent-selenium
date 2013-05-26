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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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
    public void xPaths_and_non_ongoing() {

        FluentWebElement fwe = fwd.div().span(By.xpath("@foo = 'bar'")).sendKeys("apple").clearField().submit();

//        assertThat(fwe, notNullValue());
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
//        Point locn = fwe.getLocation();
//        assertThat(locn.toString(), equalTo("(1, 1)"));
//        assertThat(sb.toString(), equalTo("we2.getLocation() -> 1,1\n"));
//
//        sb.setLength(0);
//        Dimension size = fwe.getSize();
//        assertThat(size.toString(), equalTo("(10, 10)"));
//        assertThat(sb.toString(), equalTo("we2.getSize() -> 10,10\n"));
//
//        sb.setLength(0);
//        TestableString cssVal = fwe.getCssValue("blort");
//        assertThat(cssVal.toString(), equalTo(cs("blort_value")));
//        assertThat(sb.toString(), equalTo("we2.getCssValue(blort) -> blort_value\n"));
//
//        sb.setLength(0);
//        TestableString value = fwe.getAttribute("valerie");
//        assertThat(value.toString(), equalTo(cs("valerie_value")));
//        assertThat(sb.toString(), equalTo("we2.getAttribute(valerie) -> valerie_value\n"));

// TODO
//        sb.setLength(0);
//        TestableString tagName = fwe.getTagName();
//        assertThat(tagName.toString(), equalTo(cs("taggart")));
//        assertThat(sb.toString(), equalTo("we2.getTagName() -> 'taggart'\n"));

        sb.setLength(0);
        boolean isSelected = fwe.isSelected();
        assertThat(isSelected, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isSelected() -> true\n"));

        sb.setLength(0);
        boolean isEnabled = fwe.isEnabled();
        assertThat(isEnabled, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isEnabled() -> true\n"));

        sb.setLength(0);
        boolean isDisplayed = fwe.isDisplayed();
        assertThat(isDisplayed, equalTo(true));
        assertThat(sb.toString(), equalTo("we2.isDisplayed() -> true\n"));

        sb.setLength(0);
        TestableString text = fwe.getText();
        assertThat(text.toString(), equalTo(cs("Mary had 3 little lamb(s).")));
        assertThat(sb.toString(), equalTo("we2.getText() -> 'Mary had 3 little lamb(s).'\n"));

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









}
