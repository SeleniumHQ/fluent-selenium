package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.TestableString;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RecordingFluentWebElements extends FluentWebElements {

    private final FluentRecorder recording;

    public RecordingFluentWebElements(FluentRecorder recording) {
        super(null, null, null);
        this.recording = recording;
    }

    @Override
    public FluentWebElements click() {
        return recording.fluentWebElements(new OnFluentWebElements() {
            public FluentWebElements doItForReal(FluentWebElements fwe) {
                return fwe.click();
            }
        });
    }

    @Override
    public FluentWebElements clearField() {
        return recording.fluentWebElements(new OnFluentWebElements() {
            public FluentWebElements doItForReal(FluentWebElements fwe) {
                return fwe.clearField();
            }
        });
    }

    @Override
    public FluentWebElements submit() {
        return recording.fluentWebElements(new OnFluentWebElements() {
            public FluentWebElements doItForReal(FluentWebElements fwe) {
                return fwe.submit();
            }
        });
    }

    @Override
    public FluentWebElements sendKeys(final CharSequence... keysToSend) {
        return recording.fluentWebElements(new OnFluentWebElements() {
            public FluentWebElements doItForReal(FluentWebElements fwe) {
                return fwe.sendKeys(keysToSend);
            }
        });
    }

    @Override
    public boolean isSelected() {
        return recording.returnsBoolean(new OnFluentWebElements() {
            public Boolean doItForReal(FluentWebElements fwe) {
                return fwe.isSelected();
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return recording.returnsBoolean(new OnFluentWebElements() {
            public Boolean doItForReal(FluentWebElements fwe) {
                return fwe.isEnabled();
            }
        });
    }

    @Override
    public boolean isDisplayed() {
        return recording.returnsBoolean(new OnFluentWebElements() {
            public Boolean doItForReal(FluentWebElements fwe) {
                return fwe.isDisplayed();
            }
        });
    }

    @Override
    public TestableString getText() {
        return recording.testableString(new OnFluentWebElements() {
            public TestableString doItForReal(FluentWebElements fwe) {
                return fwe.getText();
            }
        });

    }

    @Override
    public FluentWebElements filter(final FluentMatcher matcher) {
        return recording.fluentWebElements(new OnFluentWebElements() {
            public FluentWebElements doItForReal(FluentWebElements fwe) {
                return fwe.filter(matcher);
            }
        });
    }

    @Override
    public FluentWebElement first(final FluentMatcher matcher) {
        return recording.fluentWebElement(new OnFluentWebElements() {
            public FluentWebElement doItForReal(FluentWebElements fwe) {
                return fwe.first(matcher);
            }
        });
    }


    // From java.util.List

    public void clear() {
        throw notRecordableYet();
    }

    private UnsupportedOperationException notRecordableYet() {
        return new UnsupportedOperationException("Not recordable yet");
    }

    public int size() {
        throw notRecordableYet();
    }

    public boolean isEmpty() {
        throw notRecordableYet();
    }

    public boolean contains(Object o) {
        throw notRecordableYet();
    }

    public Iterator<FluentWebElement> iterator() {
        throw notRecordableYet();
    }

    public Object[] toArray() {
        throw notRecordableYet();
    }

    public <FluentWebElement> FluentWebElement[] toArray(FluentWebElement[] ts) {
        throw notRecordableYet();
    }

    public boolean add(FluentWebElement webElement) {
        throw notRecordableYet();
    }

    public boolean remove(Object o) {
        throw notRecordableYet();
    }

    public boolean containsAll(Collection<?> objects) {
        throw notRecordableYet();
    }

    public boolean addAll(Collection<? extends FluentWebElement> webElements) {
        throw notRecordableYet();
    }

    public boolean addAll(int i, Collection<? extends FluentWebElement> webElements) {
        throw notRecordableYet();
    }

    public boolean removeAll(Collection<?> objects) {
        throw notRecordableYet();
    }

    public boolean retainAll(Collection<?> objects) {
        throw notRecordableYet();
    }

    public FluentWebElement get(final int i) {
        return recording.fluentWebElement(new OnFluentWebElements() {
            public FluentWebElement doItForReal(FluentWebElements fwe) {
                return fwe.get(i);
            }
        });
    }

    public FluentWebElement set(int i, FluentWebElement webElement) {
        throw notRecordableYet();
    }

    public void add(int i, FluentWebElement webElement) {
        throw notRecordableYet();
    }

    public FluentWebElement remove(int i) {
        throw notRecordableYet();
    }

    public int indexOf(Object o) {
        throw notRecordableYet();
    }

    public int lastIndexOf(Object o) {
        throw notRecordableYet();
    }

    public ListIterator<FluentWebElement> listIterator() {
        throw notRecordableYet();
    }

    public ListIterator<FluentWebElement> listIterator(int i) {
        throw notRecordableYet();
    }

    public List<FluentWebElement> subList(int i, int i1) {
        throw notRecordableYet();
    }


}
