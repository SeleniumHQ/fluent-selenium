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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
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
        FluentWebElements fwes = elems.clearField();
        assertThat(fwes, notNullValue());
        assertThat(sb.toString(), equalTo("we1.clear()\nwe2.clear()\n"));

        sb.setLength(0);
        FluentWebElements fwe3 = elems.click();
        assertThat(fwe3, notNullValue());
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
        FluentWebElements fwe4 = elems.sendKeys("aaa");
        assertThat(fwe4, notNullValue());
        assertThat(sb.toString(), equalTo("we1.sendKeys(aaa)\nwe2.sendKeys(aaa)\n"));

        sb.setLength(0);
        FluentWebElements fwe5 = elems.submit();
        assertThat(fwe5, notNullValue());
        assertThat(sb.toString(), equalTo("we1.submit()\nwe2.submit()\n"));

        sb.setLength(0);
        CharSequence text = elems.getText().toString();
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

        FluentWebElements fwes = new FluentWebElements(null, new ArrayList<FluentWebElement>(elems), null);

        assertThat(fwes.size(), equalTo(4));
        assertThat(fwes.get(0), equalTo(item0));

        {
            List<FluentWebElement> elems2 = new ArrayList<FluentWebElement>();
            elems2.add(mock(FluentWebElement.class));
            elems2.add(mock(FluentWebElement.class));

            fwes.addAll(elems2);
        }

        assertThat(fwes.size(), equalTo(6));

        fwes.remove(item0);

        assertThat(fwes.size(), equalTo(5));

        fwes.removeAll(elems);

        assertThat(fwes.size(), equalTo(2));

        fwes.remove(0);

        assertThat(fwes.size(), equalTo(1));

        assertThat(fwes.contains("foo"), equalTo(false));

        fwes.add(item0);

        assertThat(fwes.indexOf(item0), equalTo(1));
        assertThat(fwes.indexOf("foo"), equalTo(-1));

        fwes.remove(item0);

        fwes.add(0, item0);

        assertThat(fwes.indexOf(item0), equalTo(0));
        assertThat(fwes.lastIndexOf(item0), equalTo(0));

        fwes.remove(0);
        assertThat(fwes.size(), equalTo(1));
        assertThat(fwes.isEmpty(), equalTo(false));
        fwes.remove(0);
        assertThat(fwes.size(), equalTo(0));
        assertThat(fwes.isEmpty(), equalTo(true));

        fwes.addAll(0, elems);
        assertThat(fwes.size(), equalTo(4));

        assertThat(fwes.toArray().length, equalTo(4));
        FluentWebElement[] wes = new FluentWebElement[0] ;
        assertThat(fwes.toArray(wes).length, equalTo(4));

        assertThat(fwes.subList(1, 2).size(), equalTo(1));

        assertThat(fwes.listIterator(), notNullValue());

        assertThat(fwes.listIterator(0), notNullValue());

        fwes.clear();

        assertThat(fwes.size(), equalTo(0));

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




}
