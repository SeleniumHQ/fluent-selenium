package org.seleniumhq.selenium.fluent.recording;

import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentSelects;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.Period;
import org.seleniumhq.selenium.fluent.TestableString;

public class RecordingFluentWebDriver extends FluentWebDriverImpl {

    private final FluentRecorder recording;

    public RecordingFluentWebDriver(FluentRecorder recording) {
        super(null);
        this.recording = recording;
    }


    @Override
    public FluentWebElement span() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.span();
            }
        });
    }

    @Override
    public FluentWebElement span(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.span(by);
            }
        });
    }

    @Override
    public FluentWebElements spans() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.spans();
            }
        });
    }

    @Override
    public FluentWebElements spans(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.spans(by);
            }
        });
    }

    @Override
    public FluentWebElement div() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.div();
            }
        });
    }

    @Override
    public FluentWebElement div(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.div(by);
            }
        });
    }

    @Override
    public FluentWebElements divs() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.divs();
            }
        });
    }

    @Override
    public FluentWebElements divs(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.divs(by);
            }
        });
    }

    @Override
    public FluentWebElement button() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.button();
            }
        });
    }

    @Override
    public FluentWebElement button(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.button(by);
            }
        });
    }

    @Override
    public FluentWebElements buttons() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.buttons();
            }
        });
    }

    @Override
    public FluentWebElements buttons(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.buttons(by);
            }
        });
    }

    @Override
    public FluentWebElement link() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.link();
            }
        });
    }

    @Override
    public FluentWebElement link(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.link(by);
            }
        });
    }

    @Override
    public FluentWebElements links() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.links();
            }
        });
    }

    @Override
    public FluentWebElements links(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.links(by);
            }
        });
    }

    @Override
    public FluentWebElement input() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.input();
            }
        });
    }

    @Override
    public FluentWebElement input(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.input(by);
            }
        });
    }

    @Override
    public FluentWebElements inputs() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.inputs();
            }
        });
    }

    @Override
    public FluentWebElements inputs(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.inputs(by);
            }
        });
    }

    @Override
    public FluentSelect select() {
        return recording.fluentSelect(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.select();
            }
        });
    }

    @Override
    public FluentSelect select(final By by) {
        return recording.fluentSelect(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.select(by);
            }
        });
    }

    @Override
    public FluentSelects selects() {
        return recording.fluentSelects(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.selects();
            }
        });
    }

    @Override
    public FluentSelects selects(final By by) {
        return recording.fluentSelects(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.selects(by);
            }
        });
    }

    @Override
    public FluentWebElement h1() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h1();
            }
        });
    }

    @Override
    public FluentWebElement h1(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h1(by);
            }
        });
    }

    @Override
    public FluentWebElements h1s() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h1s();
            }
        });
    }

    @Override
    public FluentWebElements h1s(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h1s(by);
            }
        });
    }

    @Override
    public FluentWebElement h2() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h2();
            }
        });
    }

    @Override
    public FluentWebElement h2(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h2(by);
            }
        });
    }

    @Override
    public FluentWebElements h2s() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h2s();
            }
        });
    }

    @Override
    public FluentWebElements h2s(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h2s(by);
            }
        });
    }

    @Override
    public FluentWebElement h3() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h3();
            }
        });
    }

    @Override
    public FluentWebElement h3(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h3(by);
            }
        });
    }

    @Override
    public FluentWebElements h3s() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h3s();
            }
        });
    }

    @Override
    public FluentWebElements h3s(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h3s(by);
            }
        });
    }

    @Override
    public FluentWebElement h4() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h4();
            }
        });
    }

    @Override
    public FluentWebElement h4(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.h4(by);
            }
        });
    }

    @Override
    public FluentWebElements h4s() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h4s();
            }
        });
    }

    @Override
    public FluentWebElements h4s(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.h4s(by);
            }
        });
    }

    @Override
    public FluentWebElement p() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.p();
            }
        });
    }

    @Override
    public FluentWebElement p(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.p(by);
            }
        });
    }

    @Override
    public FluentWebElements ps() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.ps();
            }
        });
    }

    @Override
    public FluentWebElements ps(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.ps(by);
            }
        });
    }

    @Override
    public FluentWebElement img() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.img();
            }
        });
    }

    @Override
    public FluentWebElement img(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.img(by);
            }
        });
    }

    @Override
    public FluentWebElements imgs() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.imgs();
            }
        });
    }

    @Override
    public FluentWebElements imgs(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.imgs(by);
            }
        });
    }

    @Override
    public FluentWebElement table() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.table();
            }
        });
    }

    @Override
    public FluentWebElement table(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.table(by);
            }
        });
    }

    @Override
    public FluentWebElements tables() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.tables();
            }
        });
    }

    @Override
    public FluentWebElements tables(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.tables(by);
            }
        });
    }

    @Override
    public FluentWebElement fieldset() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.fieldset();
            }
        });
    }

    @Override
    public FluentWebElement fieldset(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.fieldset(by);
            }
        });
    }

    @Override
    public FluentWebElements fieldsets() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.fieldsets();
            }
        });
    }

    @Override
    public FluentWebElements fieldsets(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.fieldsets(by);
            }
        });
    }

    @Override
    public FluentWebElement legend() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.legend();
            }
        });
    }

    @Override
    public FluentWebElement legend(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.legend(by);
            }
        });
    }

    @Override
    public FluentWebElements legends() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.legends();
            }
        });
    }

    @Override
    public FluentWebElements legends(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.legends(by);
            }
        });
    }

    @Override
    public FluentWebElement tr() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.tr();
            }
        });
    }

    @Override
    public FluentWebElement tr(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.tr(by);
            }
        });
    }

    @Override
    public FluentWebElements trs() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.trs();
            }
        });
    }

    @Override
    public FluentWebElements trs(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.trs(by);
            }
        });
    }

    @Override
    public FluentWebElement td() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.td();
            }
        });
    }

    @Override
    public FluentWebElement td(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.td(by);
            }
        });
    }

    @Override
    public FluentWebElements tds() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.tds();
            }
        });
    }

    @Override
    public FluentWebElements tds(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.tds(by);
            }
        });
    }


    @Override
    public FluentWebElement th() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.th();
            }
        });
    }

    @Override
    public FluentWebElement th(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.th(by);
            }
        });
    }

    @Override
    public FluentWebElements ths() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.ths();
            }
        });
    }

    @Override
    public FluentWebElements ths(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.ths(by);
            }
        });
    }

    @Override
    public FluentWebElement ul() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.ul();
            }
        });
    }

    @Override
    public FluentWebElement ul(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.ul(by);
            }
        });
    }

    @Override
    public FluentWebElements uls() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.uls();
            }
        });
    }

    @Override
    public FluentWebElements uls(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.uls(by);
            }
        });
    }

    @Override
    public FluentWebElement ol() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.ol();
            }
        });
    }

    @Override
    public FluentWebElement ol(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.ol(by);
            }
        });
    }

    @Override
    public FluentWebElements ols() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.ols();
            }
        });
    }

    @Override
    public FluentWebElements ols(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.ols(by);
            }
        });
    }

    @Override
    public FluentWebElement li() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.li();
            }
        });
    }

    @Override
    public FluentWebElement li(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.li(by);
            }
        });
    }

    @Override
    public FluentWebElements lis() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.lis();
            }
        });
    }

    @Override
    public FluentWebElements lis(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.lis(by);
            }
        });
    }

    @Override
    public FluentWebElement form() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.form();
            }
        });
    }

    @Override
    public FluentWebElement form(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.form(by);
            }
        });
    }

    @Override
    public FluentWebElements forms() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.forms();
            }
        });
    }

    @Override
    public FluentWebElements forms(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.forms(by);
            }
        });
    }

    @Override
    public FluentWebElement textarea() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.textarea();
            }
        });
    }

    @Override
    public FluentWebElement textarea(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.textarea(by);
            }
        });
    }

    @Override
    public FluentWebElements textareas() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.textareas();
            }
        });
    }

    @Override
    public FluentWebElements textareas(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.textareas(by);
            }
        });
    }

    @Override
    public FluentWebElement option() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.option();
            }
        });
    }

    @Override
    public FluentWebElement option(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.option(by);
            }
        });
    }

    @Override
    public FluentWebElements options() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.options();
            }
        });
    }

    @Override
    public FluentWebElements options(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.options(by);
            }
        });
    }

    @Override
    public FluentWebElement map() {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.map();
            }
        });
    }

    @Override
    public FluentWebElement map(final By by) {
        return recording.fluentWebElement(new OnFluentWebDriver() {
            public FluentWebElement doItForReal(FluentWebDriver fwd) {
                return fwd.map(by);
            }
        });
    }

    @Override
    public FluentWebElements maps() {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.maps();
            }
        });
    }

    @Override
    public FluentWebElements maps(final By by) {
        return recording.fluentWebElements(new OnFluentWebDriver() {
            public FluentWebElements doItForReal(FluentWebDriver fwd) {
                return fwd.maps(by);
            }
        });
    }

    @Override
    public FluentWebDriverImpl within(final Period period) {
        return recording.fluentWebDriverImpl(new OnFluentWebDriver() {
            public FluentWebDriverImpl doItForReal(FluentWebDriver fwd) {
                return (FluentWebDriverImpl) fwd.within(period);
            }
        });
    }

    @Override
    public TestableString url() {
        return recording.testableString(new OnFluentWebDriver() {
            public TestableString doItForReal(FluentWebDriver fwd) {
                return fwd.url();
            }
        });
    }

    @Override
    public TestableString title() {
        return recording.testableString(new OnFluentWebDriver() {
            public TestableString doItForReal(FluentWebDriver fwd) {
                return fwd.title();
            }
        });
    }

}
