package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentWebElements;

public abstract class OnFluentWebElements extends OnFluentSomething {

    @Override
    public final Object dispatch(Object arg) {
        return doItForReal((FluentWebElements) arg);
    }

    public abstract Object doItForReal(FluentWebElements fwe);
}
