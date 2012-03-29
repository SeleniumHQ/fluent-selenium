package org.seleniumhq.selenium.fluent;

import org.seleniumhq.selenium.fluent.recording.RecordingFluentWebDriver;

public interface RecordingFluentWebDriverFactory {
    RecordingFluentWebDriver recordTo(FluentRecorder fluentRecording);
}
