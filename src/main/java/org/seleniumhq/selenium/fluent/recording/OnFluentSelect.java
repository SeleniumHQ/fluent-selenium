package org.seleniumhq.selenium.fluent.recording;

import org.seleniumhq.selenium.fluent.FluentSelect;

public abstract class OnFluentSelect extends OnFluentSomething {

    @Override
    public final Object dispatch(Object arg) {
        return doItForReal((FluentSelect) arg);
    }

    public abstract Object doItForReal(FluentSelect fs);
}
