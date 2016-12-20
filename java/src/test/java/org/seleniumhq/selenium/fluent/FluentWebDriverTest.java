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
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.seleniumhq.selenium.fluent.TestableString.delimitWithChars;

public class FluentWebDriverTest extends BaseTest {

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");
    static final By CLASS_C = By.className("classC");


    @Test
    public void reference_test_with_mockito() {
        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        FluentWebDriver fwd = new FluentWebDriver(wd);

        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        Internal.BaseFluentWebElement fe = fwd.div(ID_A).div(ID_B);

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
        TestableValue<Point> location = fwe.getLocation();
        TestableValue<Point> pointShouldOrShouldNotBeMatchable = location.shouldBe(new Point(1, 1));
        Point locn = pointShouldOrShouldNotBeMatchable.value();
        assertThat(locn.toString(), equalTo("(1, 1)"));

        when(we.getLocation()).thenReturn(new Point(1, 1));
        try {
            fwe.getLocation().shouldBe(new Point(2, 2)).value();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().getLocation().shouldBe('(2, 2)')"));
            assertThat(e.getCause().getMessage().replace("(after 1 ms)", ""), equalTo("\nExpected: <(2, 2)>\n     but: was <(1, 1)>"));
        }

        when(we.getLocation()).thenReturn(new Point(1, 1));
        locn = fwe.getLocation().shouldNotBe(new Point(2, 2)).value();
        assertThat(locn.toString(), equalTo("(1, 1)"));

        when(we.getLocation()).thenReturn(new Point(1, 1));
        try {
            fwe.getLocation().shouldNotBe(new Point(1, 1)).value();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().getLocation().shouldNotBe('(1, 1)')"));
            assertThat(e.getCause().getMessage().replace("(after 1 ms)", ""), equalTo("\nExpected: not <(1, 1)>\n     but: was <(1, 1)>"));
        }

        when(we.getSize()).thenReturn(new Dimension(10, 10));
        Dimension size = fwe.getSize().shouldBe(new Dimension(10, 10)).value();
        assertThat(size, equalTo(new Dimension(10, 10)));


        when(we.getSize()).thenReturn(new Dimension(10, 10));
        try {
            fwe.getSize().shouldNotBe(new Dimension(10, 10));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().getSize().shouldNotBe('(10, 10)')"));
            assertThat(e.getCause().getMessage().replace("(after 1 ms)", ""), equalTo("\nExpected: not <(10, 10)>\n     but: was <(10, 10)>"));
        }

        when(we.getSize()).thenReturn(new Dimension(10, 10));
        try {
            fwe.getSize().shouldBe(new Dimension(20, 20));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().getSize().shouldBe('(20, 20)')"));
            assertThat(e.getCause().getMessage().replace("(after 1 ms)", ""), equalTo("\nExpected: <(20, 20)>\n     but: was <(10, 10)>"));
        }

        when(we.getCssValue("blort")).thenReturn("blort_value");
        assertThat(fwe.getCssValue("blort").shouldBe("blort_value"), notNullValue());

        when(we.getAttribute("valerie")).thenReturn("valerie_value");
        assertThat(fwe.getAttribute("valerie").shouldBe("valerie_value"), notNullValue());

        when(we.getTagName()).thenReturn("taggart");
        assertThat(fwe.getTagName().shouldBe("taggart"), notNullValue());

        when(we.isSelected()).thenReturn(true);
        assertThat(fwe.isSelected().shouldBe(true), notNullValue());

        when(we.isEnabled()).thenReturn(true);
        assertThat(fwe.isEnabled().shouldBe(true), notNullValue());

        when(we.isDisplayed()).thenReturn(true);
        assertThat(fwe.isDisplayed().shouldBe(true), notNullValue());

        when(we.getText()).thenReturn("Mary had 2 little lamb(s).");
        assertThat(fwe.getText().shouldBe("Mary had 2 little lamb(s)."), notNullValue());
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
            assertThat(e.getMessage(), equalTo("Nothing matched filter, during invocation of: ?.divs(By.tagName: div)" +
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

    @Test
    public void last_element_matched_from_larger_list() {

        when(wd.findElements(By.tagName("div"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we.getText()).thenReturn("Lamb(s) a plenty");
        when(we2.getTagName()).thenReturn("div");
        when(we2.getText()).thenReturn("Mary had 3 little lamb(s).");

        FluentWebElement fe = fwd.divs().last(new TextContainsWord("little lamb(s)")).click();

        assertThat(fe, notNullValue());

        verify(we2).click();

    }

    public static class TextContainsWord implements FluentMatcher {

        private String word;

        public TextContainsWord(String word) {
            this.word = word;
        }

        private int i = -1;

        public boolean matches(FluentWebElement webElement, int ix) {
            i++;
            assertThat(ix, is(i));
            return webElement.getWebElement().getText().indexOf(word) > -1;
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
        public boolean matches(FluentWebElement webElement, int ix) {
            return webElement.getWebElement().getText().contains("4 little lamb(s)");
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

        FluentWebElements fwes = new FluentWebElements(null, new ArrayList<FluentWebElement>(elems), null, new Monitor.NULL(), false);

        assertThat(fwes.size(), equalTo(4));
        assertThat(fwes.get(0), equalTo(item0));

        List<FluentWebElement> elems2 = new ArrayList<FluentWebElement>();
        elems2.add(mock(FluentWebElement.class));
        elems2.add(mock(FluentWebElement.class));

        try {
            fwes.addAll(elems2);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        assertThat(fwes.size(), equalTo(4));

        try {
            fwes.remove(item0);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        assertThat(fwes.size(), equalTo(4));

        try {
            fwes.removeAll(elems);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        assertThat(fwes.containsAll(newArrayList("a", "b")), equalTo(false));

        assertThat(fwes.size(), equalTo(4));

        try {
            fwes.remove(0);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        assertThat(fwes.size(), equalTo(4));

        assertThat(fwes.contains("foo"), equalTo(false));

        try {
            fwes.add(item0);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        assertThat(fwes.indexOf(item0), equalTo(0));
        assertThat(fwes.indexOf("foo"), equalTo(-1));

        try {
            fwes.remove(item0);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        try {
            fwes.add(0, item0);
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        assertThat(fwes.indexOf(item0), equalTo(0));
        assertThat(fwes.lastIndexOf(item0), equalTo(0));

        assertThat(fwes.toArray().length, equalTo(4));
        FluentWebElement[] wes = new FluentWebElement[0] ;
        assertThat(fwes.toArray(wes).length, equalTo(4));

        assertThat(fwes.subList(1, 2).size(), equalTo(1));

        assertThat(fwes.listIterator(), notNullValue());

        assertThat(fwes.listIterator(0), notNullValue());

        try {
            fwes.clear();
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            // expected
        }

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

        assertThat(elems.getText().toString(), equalTo("Mary had 3 little lamb(s).Mary had 4 little lamb(s)."));

        assertThat(elems.getText(delimitWithChars("|")).toString(), equalTo("Mary had 3 little lamb(s).|Mary had 4 little lamb(s)."));

        assertThat(elems.getText(fredNotMary()).toString(), equalTo("Fred had 3 little lamb(s).Fred had 4 little lamb(s)."));

        assertThat(elems.getText(fredNotMary(), noTrailingDot()).toString(), equalTo("Fred had 3 little lamb(s)Fred had 4 little lamb(s)"));

        assertThat(elems.getText(delimitWithChars("<<>>"), fredNotMary()).toString(), equalTo("Fred had 3 little lamb(s).<<>>Fred had 4 little lamb(s)."));

        assertThat(elems.getText(delimitWithChars("<<>>"), fredNotMary(), noTrailingDot()).toString(), equalTo("Fred had 3 little lamb(s)<<>>Fred had 4 little lamb(s)"));

    }

    private TestableString.StringChanger fredNotMary() {
        return new TestableString.StringChanger() {
            public String chg(String text) {
                return text.replace("Mary", "Fred");
            }
        };
    }
    private TestableString.StringChanger noTrailingDot() {
        return new TestableString.StringChanger() {
            public String chg(String text) {
                return text.endsWith(".") ? text.substring(0, text.length()-1) : text;
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
            public boolean matches(FluentWebElement webElement, int ix) {
                return webElement.getWebElement().getText().equals("it does not matter, as an exception will be thrown");
            }
            @Override
            public String toString() {
                return "myMatcher('" + toString + "')";
            }
        };
    }

    @Test
    public void xPaths_and_non_ongoing() {

        when(wd.findElement(By.tagName("div"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(By.xpath(".//span[@foo = 'bar']"))).thenReturn(we2);
        when(we2.getTagName()).thenReturn("span");

        FluentWebElement fwe = fwd.div().span(By.xpath("@foo = 'bar'"))
                .sendKeys("apple").clearField().submit();

// TODO
//        sb.setLength(0);
//        TestableString tagName = fwe.getTagName();
//        assertThat(tagName.toString(), equalTo(cs("taggart")));
//        assertThat(sb.toString(), equalTo("we2.getTagName() -> 'taggart'\n"));


        doReturn(true).when(we2).isSelected();
        boolean isSelected = fwe.isSelected().value();
        assertThat(isSelected, equalTo(true));

        doReturn(true).when(we2).isEnabled();
        boolean isEnabled = fwe.isEnabled().value();
        assertThat(isEnabled, equalTo(true));

        doReturn(true).when(we2).isDisplayed();
        boolean isDisplayed = fwe.isDisplayed().value();
        assertThat(isDisplayed, equalTo(true));

        doReturn("Mary had 3 little lamb(s).").when(we2).getText();
        TestableString text = fwe.getText();
        assertThat(text.toString(), equalTo("Mary had 3 little lamb(s)."));

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

        when(wd.findElement(By.id("foo"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");

        FluentWebElement fwe = fwd.div(By.id("foo"));

        assertThat(fwe, notNullValue());

        try {
            doThrow(throwable).when(we).getAttribute("valerie");
            TestableString valerie = fwe.getAttribute("valerie");
            valerie.toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getAttribute('valerie')"));
        }
        try {
            doThrow(throwable).when(we).getCssValue("blort");
            fwe.getCssValue("blort").toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getCssValue('blort')"));
        }

        try {
            doThrow(throwable).when(we).getTagName();
            fwe.getTagName().toString();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).getTagName()"));
        }

        try {
            doThrow(throwable).when(we).isSelected();
            fwe.isSelected().value();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isSelected()"));
        }

        try {
            doThrow(throwable).when(we).isEnabled();
            fwe.isEnabled().value();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isEnabled()"));
        }

        try {
            doThrow(throwable).when(we).isDisplayed();
            fwe.isDisplayed().value();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.div(By.id: foo).isDisplayed()"));
        }
        try {
            doThrow(throwable).when(we).getText();
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

        when(wd.findElements(By.id("foo"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        try {
            doThrow(throwable).when(we).getText();
            fwe.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(myMatcher('Hello'))"));
            assertThat(e.getCause(), instanceOf(throwable));
        }
    }

    @Test
    public void nothing_matching_in_filter_exception_handling() {

        when(wd.findElements(By.id("foo"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        try {
            when(we.getText()).thenReturn("dsfsdf");
            when(we2.getText()).thenReturn("darw3rsfsdf");
            fwe.filter(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseNothingMatchesInFilter e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).filter(myMatcher('Hello'))"));
            assertNull(e.getCause());
        }
    }

    @Test
    public void nothing_matching_in_first_exception_handling() {

        when(wd.findElements(By.id("foo"))).thenReturn(newArrayList(we, we2));
        when(we.getTagName()).thenReturn("div");
        when(we2.getTagName()).thenReturn("div");

        FluentWebElements fwe = fwd.divs(By.id("foo"));

        assertThat(fwe, notNullValue());

        try {
            when(we.getText()).thenReturn("dsfsdf");
            when(we2.getText()).thenReturn("sdfsdfew");
            fwe.first(makeMatcherThatUsesWebDriver("Hello"));
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseNothingMatchesInFilter e) {
            assertThat(e.getMessage(), containsString("?.divs(By.id: foo).first(myMatcher('Hello'))"));
            assertNull(e.getCause());
        }
    }


}
