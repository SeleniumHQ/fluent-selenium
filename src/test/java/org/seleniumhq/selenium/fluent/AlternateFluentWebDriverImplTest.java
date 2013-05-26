package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.mockito.InOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
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

}
