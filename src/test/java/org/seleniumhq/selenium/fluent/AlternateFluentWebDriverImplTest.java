package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.mockito.InOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.seleniumhq.selenium.fluent.WebElementJournal.throwExceptionMaybe;

public class AlternateFluentWebDriverImplTest extends BaseTest2 {

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");
    static final By CLASS_C = By.className("classC");


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
    public void lengthier_expression_with_late_runtime_exception() {

        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we2.getTagName()).thenReturn("div");
        when(we2.findElement(By.tagName("span"))).thenReturn(we3);
        when(we3.getTagName()).thenReturn("span");

        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B).span();
            doThrow(new RuntimeException()).when(we3).sendKeys("RAIN_IN_SPAIN");
            span.sendKeys("RAIN_IN_SPAIN");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.div(By.id: idA).div(By.id: idB).span().sendKeys('RAIN_IN_SPAIN')"));
            assertThat(e.getCause(), notNullValue());
        }
    }

    @Test
    public void lengthier_expression_with_late_assertion_error() {
        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we2.getTagName()).thenReturn("div");
        when(we2.findElement(By.tagName("span"))).thenReturn(we3);
        when(we3.getTagName()).thenReturn("span");

        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B).span();

            doThrow(new AssertionError()).when(we3).sendKeys("RAIN_IN_SPAIN");;

            span.sendKeys("RAIN_IN_SPAIN");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.id: idA).div(By.id: idB).span().sendKeys('RAIN_IN_SPAIN')"));
            assertThat(e.getCause(), notNullValue());
        }
    }

    @Test

    public void assertions_against_otherwise_non_ongoing() {

        when(wd.findElement(By.tagName("div"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");

        FluentWebElement fwe = fwd.div();

        assertThat(fwe, notNullValue());

        when(we.getLocation()).thenReturn(new Point(1, 1));
        WebElementValue<Point> location = fwe.location();
        Matchable<Point> pointMatchable = location.shouldBe(new Point(1, 1));
        Point locn = pointMatchable.value();
        assertThat(locn.toString(), equalTo("(1, 1)"));

        when(we.getLocation()).thenReturn(new Point(1, 1));
        try {
            fwe.location().shouldBe(new Point(2, 2)).value();
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("?.div().location().shouldBe((2, 2)) ~ but was <(1, 1)>"));
        }

        when(we.getLocation()).thenReturn(new Point(1, 1));
        locn = fwe.location().shouldNotBe(new Point(2, 2)).value();
        assertThat(locn.toString(), equalTo("(1, 1)"));

        when(we.getLocation()).thenReturn(new Point(1, 1));
        try {
            fwe.location().shouldNotBe(new Point(1, 1)).value();
            fail("should have barfed");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("?.div().location().shouldNotBe((1, 1)) ~ but was."));
        }

        when(we.getSize()).thenReturn(new Dimension(10, 10));
        Dimension size = fwe.size().shouldBe(new Dimension(10, 10)).value();
        assertThat(size, equalTo(new Dimension(10, 10)));


        when(we.getSize()).thenReturn(new Dimension(10, 10));
        try {
            fwe.size().shouldNotBe(new Dimension(10, 10));
            fail("should have barfed");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("?.div().size().shouldNotBe((10, 10)) ~ but was."));
        }


        when(we.getCssValue("blort")).thenReturn("blort_value");
        assertThat(fwe.cssValue("blort").shouldBe("blort_value"), notNullValue());

        when(we.getAttribute("valerie")).thenReturn("valerie_value");
        assertThat(fwe.attribute("valerie").shouldBe("valerie_value"), notNullValue());

        when(we.getTagName()).thenReturn("taggart");
        assertThat(fwe.tagName().shouldBe("taggart"), notNullValue());

        when(we.isSelected()).thenReturn(true);
        assertThat(fwe.selected().shouldBe(true), notNullValue());

        when(we.isEnabled()).thenReturn(true);
        assertThat(fwe.enabled().shouldBe(true), notNullValue());

        when(we.isDisplayed()).thenReturn(true);
        assertThat(fwe.displayed().shouldBe(true), notNullValue());

        when(we.getText()).thenReturn("Mary had 2 little lamb(s).");
        assertThat(fwe.text().shouldBe("Mary had 2 little lamb(s)."), notNullValue());
    }

    @Test
    public void first_can_find_nothing() {

        when(wd.findElements(By.tagName("div"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");
        when(we.getText()).thenReturn("Mary had 3 little lamb(s).");
        when(we2.getText()).thenReturn("Mary had 4 little lamb(s).");

        try {
            fwd.divs().first(new TextContainsWord("mutton")).click();
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseNothingMatchesInFilter e) {
            assertThat(e.getMessage(), equalTo("org.seleniumhq.selenium.fluent.NothingMatches during invocation of: ?.divs(By.tagName: div)" +
                    ".first(TextContainsWord{word='mutton'})"));
            assertNull(e.getCause());
        }

    }

    @Test
    public void first_element_matched_from_larger_list() {

        when(wd.findElements(By.tagName("div"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");
        when(we.getText()).thenReturn("Mary had 3 little lamb(s).");

        FluentWebElement fe = fwd.divs().first(new TextContainsWord("lamb(s)")).click();

        assertThat(fe, notNullValue());

        verify(we).click();

    }

    public static class TextContainsWord implements FluentMatcher {

        private String word;

        public TextContainsWord(String word) {
            this.word = word;
        }

        public boolean matches(WebElement webElement) {
            return webElement.getText().indexOf(word) > -1;
        }

        @Override
        public String toString() {
            return "TextContainsWord{word='" + word + "'}";
        }
    }

    @Test
    public void filtering() {

        when(wd.findElements(By.tagName("div"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");
        when(we.getText()).thenReturn("Mary had 3 little lamb(s).");
        when(we2.getText()).thenReturn("Mary had 4 little lamb(s).");

        FluentWebElements fe = fwd.divs().filter(new FourLambFilter()).click();

        assertThat(fe, notNullValue());

        verify(we2).click();
    }

    public static class FourLambFilter implements FluentMatcher {
        public boolean matches(WebElement webElement) {
            return webElement.getText().contains("4 little lamb(s)");
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
    public void multiple_hits_from_the_outset_and_operations_on_the_resulting_list() {


        when(wd.findElements(By.tagName("div"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentWebElements elems = fwd.divs();

        assertThat(elems, notNullValue());

        FluentWebElements fwes = elems.clearField();
        assertThat(fwes, notNullValue());
        verify(we).clear();
        verify(we2).clear();

        FluentWebElements fwe3 = elems.click();
        assertThat(fwe3, notNullValue());
        verify(we).click();
        verify(we2).click();

        // TODO should be selected() in line with singular ?
        when(we.isSelected()).thenReturn(true);
        when(we2.isSelected()).thenReturn(false);
        boolean areSelected = elems.isSelected();
        assertThat(areSelected, equalTo(false));
        verify(we).isSelected();
        verify(we2).isSelected();

        when(we.isEnabled()).thenReturn(true);
        when(we2.isEnabled()).thenReturn(false);
        boolean areEnabled = elems.isEnabled();
        assertThat(areEnabled, equalTo(false));
        verify(we).isEnabled();
        verify(we2).isEnabled();

        when(we.isDisplayed()).thenReturn(true);
        when(we2.isDisplayed()).thenReturn(false);
        boolean areDisplayed = elems.isDisplayed();
        assertThat(areDisplayed, equalTo(false));
        verify(we).isDisplayed();
        verify(we2).isDisplayed();

        FluentWebElements fwe4 = elems.sendKeys("aaa");
        assertThat(fwe4, notNullValue());
        verify(we).sendKeys("aaa");
        verify(we2).sendKeys("aaa");

        FluentWebElements fwe5 = elems.submit();
        assertThat(fwe5, notNullValue());
        verify(we).submit();
        verify(we2).submit();

        when(we.getText()).thenReturn("Mary had 3 little lamb(s).");
        when(we2.getText()).thenReturn("Mary had 4 little lamb(s).");
        CharSequence text = elems.getText().toString();
        assertThat(text.toString(), equalTo("Mary had 3 little lamb(s).Mary had 4 little lamb(s)."));

        try {
            elems.getLocation();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getLocation() has no meaning for multiple elements"));
        }
        try {
            elems.getSize();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getSize() has no meaning for multiple elements"));
        }

        try {
            elems.getCssValue("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getCssValue('x') has no meaning for multiple elements"));
        }

        try {
            elems.getAttribute("x");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getAttribute('x') has no meaning for multiple elements"));
        }

        try {
            elems.getTagName();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("getTagName() has no meaning for multiple elements"));
        }
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

        when(wd.findElements(By.id("foo"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        when(we.getText()).thenThrow(throwable);

        try {
            fwe.first(makeMatcherThatUsesWebDriver("Goodbye"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).first(myMatcher('Goodbye'))"));
        }
    }

    @Test
    public void further_find_element_after_multiple_is_unsupported() {

        when(wd.findElements(By.xpath("foo"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");
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

        when(wd.findElements(By.id("foo"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());


        try {
            doThrow(throwable).when(we).sendKeys("a");
            fwe.sendKeys("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).sendKeys('a')"));
        }

        try {
            doThrow(throwable).when(we).click();
            fwe.click();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).click()"));
        }

        try {
            doThrow(throwable).when(we).submit();
            fwe.submit();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).submit()"));
        }

        try {
            doThrow(throwable).when(we).clear();
            fwe.clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).clearField()"));
        }

        try {
            when(we.isSelected()).thenThrow(throwable);
            fwe.isSelected();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isSelected()"));
        }

        try {
            when(we.isEnabled()).thenThrow(throwable);
            fwe.isEnabled();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isEnabled()"));
        }

        try {
            when(we.isDisplayed()).thenThrow(throwable);
            fwe.isDisplayed();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).isDisplayed()"));
        }

        try {
            when(we.getText()).thenThrow(throwable);
            fwe.getText().toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).getText()"));
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
