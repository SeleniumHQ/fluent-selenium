package org.seleniumhq.selenium.fluent;

import org.seleniumhq.selenium.fluent.recording.OnFluentSomething;

import java.util.List;

public class FluentRecording {

    private final List<OnFluentSomething> onFluentSomethings;
    private final FluentWebDriverExecutor executor;

    public FluentRecording(List<OnFluentSomething> onFluentSomethings, FluentWebDriverExecutor executor) {
        this.onFluentSomethings = onFluentSomethings;
        this.executor = executor;
    }

    public Object playback(FluentWebDriver driver) {
        return executor.playback(driver, new ExecuteRecordedFluentSeries());
    }

    private class ExecuteRecordedFluentSeries implements RetryingFluentWebDriverExecutor.FluentWebDriverExecution {
        public Object execute(FluentWebDriver driver) {
            Object last = driver;
            for (OnFluentSomething o : onFluentSomethings) {
                last = o.dispatch(last);
            }
            return last;
        }
    }
}
