package org.seleniumhq.selenium.fluent.monitors;

import com.codahale.metrics.MetricRegistry;
import org.seleniumhq.selenium.fluent.Monitor;

public class CodehaleMetricsMonitor implements Monitor {

    final MetricRegistry metrics = new MetricRegistry();

    public Timer start(String item) {
        return new Timer(metrics.timer(item));
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
