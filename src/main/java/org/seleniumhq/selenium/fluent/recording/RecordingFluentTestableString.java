package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.TestableString;

public class RecordingFluentTestableString extends TestableString {

    private final FluentRecorder recording;

    public RecordingFluentTestableString(FluentRecorder recording) {
        super(null, null, null);
        this.recording = recording;
    }

    @Override
    public void shouldBe(final String shouldBe) {
        recording.returnsNothing(new OnTestableString() {
            public void doItForReal(TestableString ts) {
                ts.shouldBe(shouldBe);
            }
        });

    }
}
