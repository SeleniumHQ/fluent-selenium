package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;

public class NegatingFluentWebDriver {
    private final FluentWebDriver delegate;
    private final Period duration;
    private final Long startedAt;

    protected NegatingFluentWebDriver(WebDriver delegate, Period duration, BaseFluentWebDriver.Context context) {
        this.delegate = new FluentWebDriver(delegate, context) {
            protected <T> T decorateExecution(Execution<T> execution, BaseFluentWebDriver.Context ctx) {
                final T successfullyAbsent = null;
                while (!durationHasElapsed(startedAt)) {
                    try {
                        super.decorateExecution(execution, ctx);
                    } catch (FluentExecutionStopped executionStopped) {
                        final boolean elementGone = executionStopped.getCause() instanceof NotFoundException;

                        if (elementGone) {
                            return successfullyAbsent;
                        }
                    }
                }
                throw decorateAssertionError(ctx, new AssertionError("Element never disappeared"));
            }
        };
        this.duration = duration;
        startedAt = System.currentTimeMillis();
    }



    protected Boolean durationHasElapsed(Long startMillis) {
        return duration.getEndMillis(startMillis) <= System.currentTimeMillis();
    }

    public void span() {
        delegate.span();
    }

    public void span(By by) {
        delegate.span(by);
    }


    public void div() {
        delegate.div();
    }

    public void div(By by) {
        delegate.div(by);
    }



    public void button() {
        delegate.button();
    }

    public void button(By by) {
        delegate.button(by);
    }


    public void link() {
        delegate.link();
    }

    public void link(By by) {
        delegate.link(by);
    }

    public void input() {
        delegate.input();
    }

    public void input(By by) {
        delegate.input(by);
    }


    public void select() {
        delegate.select();
    }

    public void select(By by) {
        delegate.select(by);
    }

    public void h1() {
        delegate.h1();
    }

    public void h1(By by) {
        delegate.h1(by);
    }


    public void h2() {
        delegate.h2();
    }

    public void h2(By by) {
        delegate.h2(by);
    }

    public void h3() {
        delegate.h3();
    }

    public void h3(By by) {
        delegate.h3(by);
    }

    public void h4() {
        delegate.h4();
    }

    public void h4(By by) {
        delegate.h4(by);
    }


    public void p() {
        delegate.p();
    }


    public void p(By by) {
        delegate.p(by);
    }


    public void img() {
        delegate.img();
    }

    public void img(By by) {
        delegate.img(by);
    }


    public void table() {
        delegate.table();
    }

    public void table(By by) {
        delegate.table(by);
    }

    public void fieldset() {
        delegate.fieldset();
    }


    public void fieldset(By by) {
        delegate.fieldset(by);
    }


    public void legend() {
        delegate.legend();
    }


    public void legend(By by) {
        delegate.legend(by);
    }


    public void tr() {
        delegate.tr();
    }

    public void tr(By by) {
        delegate.tr(by);
    }


    public void td() {
        delegate.td();
    }


    public void td(By by) {
        delegate.td(by);
    }


    public void th() {
        delegate.th();
    }


    public void th(By by) {
        delegate.th(by);
    }

    public void ul() {
        delegate.ul();
    }


    public void ul(By by) {
        delegate.ul(by);
    }


    public void ol() {
        delegate.ol();
    }


    public void ol(By by) {
        delegate.ol(by);
    }

    public void form() {
        delegate.form();
    }


    public void form(By by) {
        delegate.form(by);
    }


    public void textarea() {
        delegate.textarea();
    }


    public void textarea(By by) {
        delegate.textarea(by);
    }


    public void option() {
        delegate.option();
    }


    public void option(By by) {
        delegate.option(by);
    }

    public void li() {
        delegate.li();
    }

    public void li(By by) {
        delegate.li(by);
    }


    public void map() {
        delegate.map();
    }


    public void map(By by) {
        delegate.map(by);
    }


}
