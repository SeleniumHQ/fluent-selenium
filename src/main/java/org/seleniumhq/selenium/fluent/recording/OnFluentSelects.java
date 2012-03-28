package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentSelects;

public abstract class OnFluentSelects extends OnFluentSomething {

    @Override
    public final Object dispatch(Object arg) {
        return doItForReal((FluentSelects) arg);
    }

    public abstract Object doItForReal(FluentSelects fwe);
}
