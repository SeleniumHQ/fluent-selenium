package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.TestableString;

public abstract class OnTestableString extends OnFluentSomething {

    @Override
    public final Object dispatch(Object arg) {
        doItForReal((TestableString) arg);
        return null;
    }

    public abstract void doItForReal(TestableString fwe);
}
