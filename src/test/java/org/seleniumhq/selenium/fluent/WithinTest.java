package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithinTest {

    @Test
    public void within_should_wait_and_reset() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebDriver.Options options = mock(WebDriver.Options.class);
        WebDriver.Timeouts timeouts = mock(WebDriver.Timeouts.class);

        when(wd.manage()).thenReturn(options);
        when(options.timeouts()).thenReturn(timeouts);
        when(timeouts.implicitlyWait(10, TimeUnit.SECONDS)).thenReturn(timeouts);
        when(wd.findElement(By.tagName("div"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(timeouts.implicitlyWait(0, TimeUnit.SECONDS)).thenReturn(timeouts);
        FluentWebDriver fwd = new FluentWebDriver(wd);

        fwd.within(Period.secs(10)).div();

        verify(wd, times(2)).manage();
        verify(options, times(2)).timeouts();
        verify(timeouts).implicitlyWait(10, TimeUnit.SECONDS);
        verify(wd).findElement(By.tagName("div"));
        verify(we).getTagName();
        verify(timeouts).implicitlyWait(0, TimeUnit.SECONDS);

    }

    @Test
    public void within_should_reset_even_if_exceptions_are_thrown() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);
        WebDriver.Options options = mock(WebDriver.Options.class);
        WebDriver.Timeouts timeouts = mock(WebDriver.Timeouts.class);

        when(wd.findElement(By.tagName("div"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(wd.manage()).thenReturn(options);
        when(options.timeouts()).thenReturn(timeouts);
        when(timeouts.implicitlyWait(10, TimeUnit.SECONDS)).thenReturn(timeouts);

        FluentWebDriver fwd = new FluentWebDriver(wd);


        try {
            Internal.FluentWebElement within = fwd.div().within(Period.secs(10));

            when(we.findElement(By.tagName("div"))).thenReturn(we2);
            when(we2.getTagName()).thenThrow(new AssertionError("barf"));

            when(timeouts.implicitlyWait(0, TimeUnit.SECONDS)).thenReturn(timeouts);

            within.div();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div().within(secs(10)).div()"));
            assertTrue(e.getCause() instanceof AssertionError);
        }

    }

}
