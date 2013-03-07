package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentSelects;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

public class RecordingFluentSelects extends FluentSelects {

    private final FluentRecorder recording;

    public RecordingFluentSelects(FluentRecorder recording) {
        super(null, null, null);
        this.recording = recording;
    }

    @Override
    public FluentSelects click() {
        return recording.fluentSelects(new OnFluentSelects() {
            public FluentSelects doItForReal(FluentSelects fs) {
                return fs.click();
            }
        });
    }

    @Override
    public FluentSelects clearField() {
        return recording.fluentSelects(new OnFluentSelects() {
            public FluentSelects doItForReal(FluentSelects fs) {
                return fs.clearField();
            }
        });
    }

    @Override
    public FluentSelects submit() {
        return recording.fluentSelects(new OnFluentSelects() {
            public FluentSelects doItForReal(FluentSelects fs) {
                return fs.submit();
            }
        });
    }

    @Override
    public FluentSelects sendKeys(final CharSequence... keysToSend) {
        return recording.fluentSelects(new OnFluentSelects() {
            public FluentSelects doItForReal(FluentSelects fs) {
                return fs.sendKeys(keysToSend);
            }
        });
    }

    @Override
    public FluentSelects within(final Period p) {
        return recording.fluentSelects(new OnFluentSelects() {
            public FluentSelects doItForReal(FluentSelects fs) {
                return fs.within(p);
            }
        });
    }

    @Override
    public FluentSelects filter(final FluentMatcher matcher) {
        return recording.fluentSelects(new OnFluentSelects() {
            public FluentSelects doItForReal(FluentSelects fs) {
                return fs.filter(matcher);
            }
        });
    }

    @Override
    public FluentSelect first(final FluentMatcher matcher) {
        return recording.fluentSelect(new OnFluentSelects() {
            public FluentSelect doItForReal(FluentSelects fs) {
                return fs.first(matcher);
            }
        });
    }


    @Override
    public FluentSelect get(int i) {
        throw notRecordableYet();
    }

    private UnsupportedOperationException notRecordableYet() {
        return new UnsupportedOperationException("Not recordable yet");
    }

    @Override
    public FluentSelect set(int i, FluentWebElement webElement) {
        throw notRecordableYet();
    }


    @Override
    public FluentSelect remove(int i) {
        throw notRecordableYet();
    }

}
