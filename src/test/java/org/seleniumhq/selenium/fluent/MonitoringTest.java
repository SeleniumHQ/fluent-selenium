package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;
import static org.openqa.selenium.By.id;

@RunWith(MockitoJUnitRunner.class)
public class MonitoringTest {

    @Mock
    WebDriver webDriver;
    @Mock
    WebElement element;
    @Mock
    Monitor monitor;
    @Mock
    Monitor.Timer timer;

    FluentWebDriver fluentWebDriver;

    @Before
    public void setup() {
        fluentWebDriver = new FluentWebDriver(webDriver, monitor);
    }

    @Test
    public void single_should_monitor() {

        when(webDriver.findElement(By.tagName("div"))).thenReturn(element);
        when(element.getTagName()).thenReturn("div");
        when(monitor.start("?.div()")).thenReturn(timer);

        fluentWebDriver.div();

        verify(monitor).start("?.div()");
        verify(timer).end();
    }

    @Test
    public void plural_should_monitor() {

        when(webDriver.findElements(By.tagName("div"))).thenReturn(asList(element));
        when(element.getTagName()).thenReturn("div");
        when(monitor.start("?.divs(By.id: foo)")).thenReturn(timer);

        fluentWebDriver.divs(id("foo"));

        verify(monitor).start("?.divs(By.id: foo)");
        verify(timer).end();
    }


}
