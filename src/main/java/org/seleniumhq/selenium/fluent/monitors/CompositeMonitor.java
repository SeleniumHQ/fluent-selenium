package org.seleniumhq.selenium.fluent.monitors;

import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.Monitor;

public class CompositeMonitor implements Monitor {

    private Monitor[] monitors;

    public CompositeMonitor(Monitor... monitors) {
        this.monitors = monitors;
        assert monitors.length != 0;
    }

    public Timer start(String item) {
        for (Monitor monitor : monitors) {
            Timer foo = monitor.start(item);
            // return first non NULL timer.
            if (!(foo instanceof Timer.NULL)) {
                return foo;
            }
        }
        return monitors[0].start(item);
    }

    public RuntimeException exceptionDuringExecution(RuntimeException ex, WebElement webElement) {
        RuntimeException rv = ex;
        for (Monitor monitor : monitors) {
            rv = monitor.exceptionDuringExecution(rv, webElement);
        }
        return rv;
    }
}
