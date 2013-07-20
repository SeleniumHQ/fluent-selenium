package org.seleniumhq.selenium.fluent.monitors;

import com.codahale.metrics.MetricRegistry;
import org.seleniumhq.selenium.fluent.Monitor;

public class CodaHaleMetricsMonitor implements Monitor {

    final MetricRegistry metrics = new MetricRegistry();
    private final String toStripFromClassName;
    private final String replaceWith;

    public CodaHaleMetricsMonitor(String toStripFromClassName, String replaceWith) {
        this.toStripFromClassName = toStripFromClassName;
        this.replaceWith = replaceWith;
    }

    public CodaHaleMetricsMonitor(String toStripFromClassName) {
        this(toStripFromClassName, "");
    }

    public Timer start(String item) {
        StackTraceElement[] elems = Thread.currentThread().getStackTrace();
        String prefix = "";
        for (StackTraceElement elem : elems) {
            if (elem.getClassName().startsWith(toStripFromClassName)) {
                if (elem.getClassName().matches("(.*)\\$\\d+$")) {
                    try {
                        Class clazz = Class.forName(elem.getClassName());
                        if (clazz.isAnonymousClass()) {
                            clazz = clazz.getSuperclass();
                            prefix = prefix + clazz.getName().replace(toStripFromClassName, "") + "." + elem.getMethodName() + ":";
                        }
                    } catch (ClassNotFoundException e) {
                    }
                    continue;
                }
                prefix = elem.getClassName().replace(toStripFromClassName, replaceWith) + "." + elem.getMethodName() + ":" + prefix;
                break;
            }
        }
        return new Timer(metrics.timer(prefix + item));
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
