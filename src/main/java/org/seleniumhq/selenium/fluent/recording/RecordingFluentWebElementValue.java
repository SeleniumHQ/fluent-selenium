package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.WebElementValue;

public class RecordingFluentWebElementValue<T> extends WebElementValue<T> {

    private final FluentRecorder recording;

    public RecordingFluentWebElementValue(FluentRecorder recording) {
        super(null, null);
        this.recording = recording;
    }



}
