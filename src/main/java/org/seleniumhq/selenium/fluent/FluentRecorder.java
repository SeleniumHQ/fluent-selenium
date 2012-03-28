package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.seleniumhq.selenium.fluent.recording.NoRetryStrategy;
import org.seleniumhq.selenium.fluent.recording.OnFluentSomething;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentSelect;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentSelects;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentTestableString;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebDriver;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebElement;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebElementValue;
import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebElements;
import org.seleniumhq.selenium.fluent.recording.RetryStrategy;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class FluentRecorder {
    
    private RetryStrategy[] retryStrategies;

    public FluentRecorder() {
        this.retryStrategies = new RetryStrategy[] { new NoRetryStrategy() };
    }

    public FluentRecorder(RetryStrategy... retryStrategies) {
        this.retryStrategies = retryStrategies;
    }

    private List<OnFluentSomething> list = new ArrayList<OnFluentSomething>();

    public FluentWebElement fluentWebElement(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentWebElement(this);
    }

    public FluentWebElements fluentWebElements(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentWebElements(this);
    }

    public FluentSelect fluentSelect(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentSelect(this);
    }

    public FluentSelects fluentSelects(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentSelects(this);
    }

    public FluentWebDriverImpl fluentWebDriverImpl(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentWebDriver(this);
    }

    public TestableString testableString(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentTestableString(this);
    }

    public Point point(OnFluentSomething recording) {
        list.add(recording);
        return new Point(0, 0);
    }

    public Dimension dimension(OnFluentSomething recording) {
        list.add(recording);
        return new Dimension(-1, -1);
    }

    public WebElementValue webElementValue(OnFluentSomething recording) {
        list.add(recording);
        return new RecordingFluentWebElementValue(this);
    }

    public boolean returnsBoolean(OnFluentSomething recording) {
        list.add(recording);
        return false;
    }

    public void returnsNothing(OnFluentSomething recording) {
        list.add(recording);

    }

    public FluentRecording recording() {
        return new FluentRecording(unmodifiableList(list), retryStrategies);
    }
}
