package org.seleniumhq.selenium.fluent.monitors;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.Monitor;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompositeMonitorTest {

    @Test
    public void onlyOneTimerIsStarted() {
        Monitor m1 = mock(Monitor.class);
        when(m1.start("foo")).thenReturn(new Monitor.Timer.NULL());
        Monitor m2 = mock(Monitor.class);
        Monitor.Timer t2 = mock(Monitor.Timer.class);
        when(m2.start("foo")).thenReturn(t2);
        Monitor m3 = mock(Monitor.class);
        when(m3.start("foo")).thenReturn(new Monitor.Timer.NULL());

        CompositeMonitor monitor = new CompositeMonitor(m1, m2, m3);
        Monitor.Timer chosenTimer = monitor.start("foo");

        assertThat(chosenTimer, equalTo(t2));
    }

    @Test
    public void allAreVisitedWithExceptionAndLastExceptionIsReturned() {
        FluentExecutionStopped boo = new FluentExecutionStopped("boo", null);
        FluentExecutionStopped boo2 = new FluentExecutionStopped("boo2", null);

        WebElement we = mock(WebElement.class);

        Monitor m1 = mock(Monitor.class);
        when(m1.exceptionDuringExecution(boo, we)).thenReturn(boo);
        Monitor m2 = mock(Monitor.class);
        when(m2.exceptionDuringExecution(boo, we)).thenReturn(boo);
        Monitor m3 = mock(Monitor.class);
        when(m3.exceptionDuringExecution(boo, we)).thenReturn(boo2);

        CompositeMonitor monitor = new CompositeMonitor(m1, m2, m3);
        FluentExecutionStopped newException = monitor.exceptionDuringExecution(boo, we);

        assertThat(newException, equalTo(boo2));

        verify(m1).exceptionDuringExecution(boo, we);
        verify(m2).exceptionDuringExecution(boo, we);
        verify(m3).exceptionDuringExecution(boo, we);
    }
}
