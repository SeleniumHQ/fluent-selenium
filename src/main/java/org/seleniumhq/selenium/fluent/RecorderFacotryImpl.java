package org.seleniumhq.selenium.fluent;

import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebDriver;

public class RecorderFacotryImpl implements RecorderFactory {

    public RecordingFluentWebDriver recordTo(FluentRecorder fluentRecording) {
        return new RecordingFluentWebDriver(fluentRecording);
    }

}
