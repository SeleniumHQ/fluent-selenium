package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentWebElement;

public abstract class OnFluentWebElement extends OnFluentSomething {

    @Override
    public final Object dispatch(Object arg) {
        return doItForReal((FluentWebElement) arg);
    }

    public abstract Object doItForReal(FluentWebElement fwe);
}
