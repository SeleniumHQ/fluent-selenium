package org.seleniumhq.selenium.fluent.monitors;

import com.codahale.metrics.MetricRegistry;
import org.seleniumhq.selenium.fluent.Monitor;

import java.util.HashSet;
import java.util.Set;

public class CodehaleMetricsMonitor implements Monitor {

    final MetricRegistry metrics = new MetricRegistry();
    private final String toStripFromClassName;
    private Set<String> classes = new HashSet<String>();

    public CodehaleMetricsMonitor(String toStripFromClassName) {
        this.toStripFromClassName = toStripFromClassName;
    }

    public Timer start(String item) {
        StackTraceElement[] elems = Thread.currentThread().getStackTrace();
        String prefix = "";
        for (StackTraceElement elem : elems) {
            if (classes.contains(elem.getClassName())) {
                prefix = prefix + ":" + elem.getClassName().replace(toStripFromClassName, "") + "." + elem.getMethodName();
                break;
            }
        }
        return new Timer(metrics.timer(prefix.substring(1) + item));
    }

    public synchronized void addClass(Class clazz) {
        if (clazz.isAnonymousClass()) {
            clazz = clazz.getSuperclass();
        }
        classes.add(clazz.getName());
    }

    public MetricRegistry getMetrics() {
        return metrics;
    }

    public static class Timer implements Monitor.Timer {
        private final com.codahale.metrics.Timer.Context timing;

        public Timer(com.codahale.metrics.Timer timer) {
            timing = timer.time();
        }

        public void end() {
            timing.stop();
        }
    }

}
