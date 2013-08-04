package org.seleniumhq.selenium.fluent.monitors;

import org.junit.Test;
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
        RuntimeException boo = new RuntimeException("boo");
        RuntimeException boo2 = new RuntimeException("boo2");

        Monitor m1 = mock(Monitor.class);
        when(m1.exceptionDuringExecution(boo)).thenReturn(boo);
        Monitor m2 = mock(Monitor.class);
        when(m2.exceptionDuringExecution(boo)).thenReturn(boo);
        Monitor m3 = mock(Monitor.class);
        when(m3.exceptionDuringExecution(boo)).thenReturn(boo2);

        CompositeMonitor monitor = new CompositeMonitor(m1, m2, m3);
        RuntimeException newException = monitor.exceptionDuringExecution(boo);

        assertThat(newException, equalTo(boo2));

        verify(m1).exceptionDuringExecution(boo);
        verify(m2).exceptionDuringExecution(boo);
        verify(m3).exceptionDuringExecution(boo);
    }
}
