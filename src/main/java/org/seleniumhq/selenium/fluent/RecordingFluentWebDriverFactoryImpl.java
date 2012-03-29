package org.seleniumhq.selenium.fluent;

import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebDriver;

public class RecordingFluentWebDriverFactoryImpl implements RecordingFluentWebDriverFactory {

    public RecordingFluentWebDriver recordTo(FluentRecorder fluentRecording) {
        return new RecordingFluentWebDriver(fluentRecording);
    }

}
