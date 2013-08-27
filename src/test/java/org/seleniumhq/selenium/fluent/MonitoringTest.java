package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;
import static org.openqa.selenium.By.id;

@RunWith(MockitoJUnitRunner.class)
public class MonitoringTest {

    @Mock
    WebDriver webDriver;
    @Mock
    WebElement element2;
    @Mock
    WebElement element;
    @Mock
    Monitor monitor;
    @Mock
    Monitor.Timer timer;
    @Mock
    Monitor.Timer timer2;

    FluentWebDriver fluentWebDriver;

    @Before
    public void setup() {
        fluentWebDriver = new FluentWebDriver(webDriver, monitor);
    }

    @Test
    public void single_should_monitor() {

        when(webDriver.findElement(By.tagName("div"))).thenReturn(element);
        when(element.getTagName()).thenReturn("div");
        when(monitor.start("div()")).thenReturn(timer);

        fluentWebDriver.div();

        verify(monitor).start("div()");
        verify(timer).end(true);
    }

    @Test
    public void plural_should_monitor() {

        when(webDriver.findElements(By.tagName("div"))).thenReturn(asList(element));
        when(element.getTagName()).thenReturn("div");
        when(monitor.start("divs(By.id: foo)")).thenReturn(timer);

        fluentWebDriver.divs(id("foo"));

        verify(monitor).start("divs(By.id: foo)");
        verify(timer).end(true);
    }

    @Test
    public void single_should_monitor_for_elements_too() {

        when(webDriver.findElement(By.tagName("div"))).thenReturn(element);
        when(element.getTagName()).thenReturn("div");
        when(element.findElement(By.tagName("span"))).thenReturn(element2);
        when(element2.getTagName()).thenReturn("span");

        when(monitor.start("div()")).thenReturn(timer);
        when(monitor.start("div().span()")).thenReturn(timer2);

        fluentWebDriver.div().span();

        verify(monitor).start("div()");
        verify(timer).end(true);
        verify(monitor).start("div().span()");
        verify(timer).end(true);
        verify(timer2).end(true);
    }

    @Test
    public void plural_should_monitor_for_elements_too() {

        when(webDriver.findElement(By.tagName("div"))).thenReturn(element);
        when(element.getTagName()).thenReturn("div");
        when(element.findElements(By.tagName("span"))).thenReturn(asList(element2));
        when(element2.getTagName()).thenReturn("span");

        when(monitor.start("div()")).thenReturn(timer);
        when(monitor.start("div().spans(By.id: foo)")).thenReturn(timer2);

        fluentWebDriver.div().spans(id("foo"));

        verify(monitor).start("div()");
        verify(timer).end(true);
        verify(monitor).start("div().spans(By.id: foo)");
        verify(timer).end(true);
        verify(timer2).end(true);
    }

    @Test
    public void click_should_monitor() {

        when(webDriver.findElement(By.tagName("div"))).thenReturn(element);
        when(element.getTagName()).thenReturn("div");
        when(monitor.start("div()")).thenReturn(timer);
        when(monitor.start("div().click()")).thenReturn(timer2);

        fluentWebDriver.div().click();

        verify(monitor).start("div()");
        verify(monitor).start("div().click()");
        verify(timer).end(true);
        verify(timer2).end(true);
    }

    @Test
    public void sendkeys_should_monitor() {

        when(webDriver.findElement(By.tagName("div"))).thenReturn(element);
        when(element.getTagName()).thenReturn("div");
        when(monitor.start("div()")).thenReturn(timer);
        when(monitor.start("div().sendKeys('abc')")).thenReturn(timer2);

        fluentWebDriver.div().sendKeys("abc");

        verify(monitor).start("div()");
        verify(monitor).start("div().sendKeys('abc')");
        verify(timer).end(true);
        verify(timer2).end(true);
    }

    @Test
    public void sendkeys_should_monitor_even_for_failing_situation() {

        FluentExecutionStopped fes = new FluentExecutionStopped("x", new NullPointerException());

        when(webDriver.findElement(By.tagName("div"))).thenThrow(new NoSuchElementException("boo"));
        when(monitor.start("div()")).thenReturn(timer);
        when(monitor.exceptionDuringExecution(any(FluentExecutionStopped.class), any(WebElement.class))).thenReturn(fes);

        try {
            fluentWebDriver.div().sendKeys("abc");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e, is(fes));
        }

        verify(monitor).start("div()");
        verify(monitor).exceptionDuringExecution(any(FluentExecutionStopped.class), any(WebElement.class));
        verify(timer).end(false);
    }


}
