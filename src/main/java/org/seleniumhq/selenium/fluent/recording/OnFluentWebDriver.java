package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentWebDriver;

public abstract class OnFluentWebDriver extends OnFluentSomething {

    @Override
    public final Object dispatch(Object arg) {
        return doItForReal((FluentWebDriver) arg);
    }

    public abstract Object doItForReal(FluentWebDriver fwd);
}
