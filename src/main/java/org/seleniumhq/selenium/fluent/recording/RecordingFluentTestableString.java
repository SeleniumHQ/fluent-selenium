package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.TestableString;

public class RecordingFluentTestableString extends TestableString {

    private final FluentRecorder recording;

    public RecordingFluentTestableString(FluentRecorder recording) {
        super(null, null);
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

    @Override
    public void shouldContain(final String shouldContain) {
        recording.returnsNothing(new OnTestableString() {
            public void doItForReal(TestableString ts) {
                ts.shouldContain(shouldContain);
            }
        });
    }

    @Override
    public void shouldNotBe(final String shouldNotBe) {
        recording.returnsNothing(new OnTestableString() {
            public void doItForReal(TestableString ts) {
                ts.shouldNotBe(shouldNotBe);
            }
        });
    }

    @Override
    public void shouldNotContain(final String shouldNotContain) {
        recording.returnsNothing(new OnTestableString() {
            public void doItForReal(TestableString ts) {
                ts.shouldNotContain(shouldNotContain);
            }
        });
    }
}
