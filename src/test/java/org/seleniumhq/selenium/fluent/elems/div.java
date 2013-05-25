package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class div extends BaseTest {

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
    public void div_functionality() {

        wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);
        WebElement we3 = mock(WebElement.class);
        WebElement we4 = mock(WebElement.class);
        WebElement we5 = mock(WebElement.class);
        fwd = new FluentWebDriverImpl(wd);
        setupExpecations(wd, we, we2, we3, we4, we5, "div");

        FluentWebElements fe = fwd.div()
                .div(By.xpath("@foo = 'bar'"))
                .div(By.cssSelector("baz"))
                .divs();

        assertThat(fe, notNullValue());
        verifications(wd, we, we2, we3, we4, we5, "div");
        verifyNoMoreInteractions(wd, we, we2, we3, we4, we5);
    }

    @Test
    public void divs_functionality() {

        wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);
        WebElement we3 = mock(WebElement.class);
        fwd = new FluentWebDriverImpl(wd);

        setupExpecations(wd, we, we2, we3, "div");


        FluentWebElements fe = fwd.div()
                .divs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications(wd, we, we2, we3, "div");

        verifyNoMoreInteractions(wd, we, we2, we3);

    }

    @Test
    public void div_mismatched() {

        wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        fwd = new FluentWebDriverImpl(wd);
        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.div(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been div but was boo"));
        }

    }


}
