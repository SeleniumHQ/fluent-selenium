package org.seleniumhq.selenium.fluent.recording;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentSelects;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.Period;
import org.seleniumhq.selenium.fluent.TestableString;
import org.seleniumhq.selenium.fluent.WebElementValue;

public class RecordingFluentWebElement extends FluentWebElement {

    private final FluentRecorder recording;

    public RecordingFluentWebElement(FluentRecorder recording) {
        super(null, null, null);
        this.recording = recording;
    }

    @Override
    public FluentWebElement click() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.click();
            }
        });
    }

    @Override
    public FluentWebElement clearField() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.clearField();
            }
        });
    }

    @Override
    public FluentWebElement submit() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.submit();
            }
        });
    }

    @Override
    public TestableString getTagName() {
        return recording.testableString(new OnFluentWebElement() {
            public TestableString doItForReal(FluentWebElement fwe) {
                return fwe.getTagName();
            }
        });
    }

    @Override
    public TestableString getCssValue(final String cssName) {
        return recording.testableString(new OnFluentWebElement() {
            public TestableString doItForReal(FluentWebElement fwe) {
                return fwe.getCssValue(cssName);
            }
        });
    }

    @Override
    public TestableString getAttribute(final String attr) {
        return recording.testableString(new OnFluentWebElement() {
            public TestableString doItForReal(FluentWebElement fwe) {
                return fwe.getAttribute(attr);
            }
        });
    }

    @Override
    public TestableString getText() {
        return recording.testableString(new OnFluentWebElement() {
            public TestableString doItForReal(FluentWebElement fwe) {
                return fwe.getText();
            }
        });
    }

    @Override
    public FluentWebElement sendKeys(final CharSequence... keysToSend) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.sendKeys(keysToSend);
            }
        });
    }

    @Override
    public FluentWebElement within(final Period period) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.within(period);
            }
        });
    }



    // Return Primatives

    @Override
    public boolean isSelected() {
        return recording.returnsBoolean(new OnFluentWebElement() {
            public Boolean doItForReal(FluentWebElement fwe) {
                return fwe.isSelected();
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return recording.returnsBoolean(new OnFluentWebElement() {
            public Boolean doItForReal(FluentWebElement fwe) {
                return fwe.isEnabled();
            }
        });
    }

    @Override
    public boolean isDisplayed() {
        return recording.returnsBoolean(new OnFluentWebElement() {
            public Boolean doItForReal(FluentWebElement fwe) {
                return fwe.isDisplayed();
            }
        });
    }

    @Override
    public Point getLocation() {
        return recording.point(new OnFluentWebElement() {
            public Point doItForReal(FluentWebElement fwe) {
                return fwe.getLocation();
            }
        });
    }

    @Override
    public Dimension getSize() {
        return recording.dimension(new OnFluentWebElement() {
            public Dimension doItForReal(FluentWebElement fwe) {
                return fwe.getSize();
            }
        });
    }

    // Assertable


    @Override
    public WebElementValue<String> cssValue(final String name) {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<String> doItForReal(FluentWebElement fwe) {
                return fwe.cssValue(name);
            }
        });
    }

    @Override
    public WebElementValue<String> attribute(final String name) {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<String> doItForReal(FluentWebElement fwe) {
                return fwe.attribute(name);
            }
        });
    }

    @Override
    public WebElementValue<Point> location() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<Point> doItForReal(FluentWebElement fwe) {
                return fwe.location();
            }
        });

    }

    @Override
    public WebElementValue<Dimension> size() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<Dimension> doItForReal(FluentWebElement fwe) {
                return fwe.size();
            }
        });
    }

    @Override
    public WebElementValue<String> tagName() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<String> doItForReal(FluentWebElement fwe) {
                return fwe.tagName();
            }
        });

    }

    @Override
    public WebElementValue<Boolean> selected() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<Boolean> doItForReal(FluentWebElement fwe) {
                return fwe.selected();
            }
        });

    }

    @Override
    public WebElementValue<Boolean> enabled() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<Boolean> doItForReal(FluentWebElement fwe) {
                return fwe.enabled();
            }
        });

    }

    @Override
    public WebElementValue<Boolean> displayed() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<Boolean> doItForReal(FluentWebElement fwe) {
                return fwe.displayed();
            }
        });

    }

    @Override
    public WebElementValue<String> text() {
        return recording.webElementValue(new OnFluentWebElement() {
            public WebElementValue<String> doItForReal(FluentWebElement fwe) {
                return fwe.text();
            }
        });

    }

    // Overridden from BaseFluentWebElement


    @Override
    public FluentWebElement span() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.span();
            }
        });
    }

    @Override
    public FluentWebElement span(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.span(by);
            }
        });
    }

    @Override
    public FluentWebElements spans() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.spans();
            }
        });
    }

    @Override
    public FluentWebElements spans(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.spans(by);
            }
        });
    }

    @Override
    public FluentWebElement div() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.div();
            }
        });
    }

    @Override
    public FluentWebElement div(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.div(by);
            }
        });
    }

    @Override
    public FluentWebElements divs() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.divs();
            }
        });
    }

    @Override
    public FluentWebElements divs(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.divs(by);
            }
        });
    }

    @Override
    public FluentWebElement button() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.button();
            }
        });
    }

    @Override
    public FluentWebElement button(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.button(by);
            }
        });
    }

    @Override
    public FluentWebElements buttons() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.buttons();
            }
        });
    }

    @Override
    public FluentWebElements buttons(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.buttons(by);
            }
        });
    }

    @Override
    public FluentWebElement link() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.link();
            }
        });
    }

    @Override
    public FluentWebElement link(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.link(by);
            }
        });
    }

    @Override
    public FluentWebElements links() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.links();
            }
        });
    }

    @Override
    public FluentWebElements links(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.links(by);
            }
        });
    }

    @Override
    public FluentWebElement input() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.input();
            }
        });
    }

    @Override
    public FluentWebElement input(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.input(by);
            }
        });
    }

    @Override
    public FluentWebElements inputs() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.inputs();
            }
        });
    }

    @Override
    public FluentWebElements inputs(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.inputs(by);
            }
        });
    }

    @Override
    public FluentSelect select() {
        return recording.fluentSelect(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.select();
            }
        });
    }

    @Override
    public FluentSelect select(final By by) {
        return recording.fluentSelect(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.select(by);
            }
        });
    }

    @Override
    public FluentSelects selects() {
        return recording.fluentSelects(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.selects();
            }
        });
    }

    @Override
    public FluentSelects selects(final By by) {
        return recording.fluentSelects(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.selects(by);
            }
        });
    }

    @Override
    public FluentWebElement h1() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h1();
            }
        });
    }

    @Override
    public FluentWebElement h1(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h1(by);
            }
        });
    }

    @Override
    public FluentWebElements h1s() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h1s();
            }
        });
    }

    @Override
    public FluentWebElements h1s(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h1s(by);
            }
        });
    }

    @Override
    public FluentWebElement h2() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h2();
            }
        });
    }

    @Override
    public FluentWebElement h2(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h2(by);
            }
        });
    }

    @Override
    public FluentWebElements h2s() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h2s();
            }
        });
    }

    @Override
    public FluentWebElements h2s(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h2s(by);
            }
        });
    }

    @Override
    public FluentWebElement h3() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h3();
            }
        });
    }

    @Override
    public FluentWebElement h3(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h3(by);
            }
        });
    }

    @Override
    public FluentWebElements h3s() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h3s();
            }
        });
    }

    @Override
    public FluentWebElements h3s(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h3s(by);
            }
        });
    }

    @Override
    public FluentWebElement h4() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h4();
            }
        });
    }

    @Override
    public FluentWebElement h4(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.h4(by);
            }
        });
    }

    @Override
    public FluentWebElements h4s() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h4s();
            }
        });
    }

    @Override
    public FluentWebElements h4s(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.h4s(by);
            }
        });
    }

    @Override
    public FluentWebElement p() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.p();
            }
        });
    }

    @Override
    public FluentWebElement p(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.p(by);
            }
        });
    }

    @Override
    public FluentWebElements ps() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.ps();
            }
        });
    }

    @Override
    public FluentWebElements ps(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.ps(by);
            }
        });
    }

    @Override
    public FluentWebElement img() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.img();
            }
        });
    }

    @Override
    public FluentWebElement img(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.img(by);
            }
        });
    }

    @Override
    public FluentWebElements imgs() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.imgs();
            }
        });
    }

    @Override
    public FluentWebElements imgs(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.imgs(by);
            }
        });
    }

    @Override
    public FluentWebElement table() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.table();
            }
        });
    }

    @Override
    public FluentWebElement table(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.table(by);
            }
        });
    }

    @Override
    public FluentWebElements tables() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.tables();
            }
        });
    }

    @Override
    public FluentWebElements tables(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.tables(by);
            }
        });
    }

    @Override
    public FluentWebElement fieldset() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.fieldset();
            }
        });
    }

    @Override
    public FluentWebElement fieldset(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.fieldset(by);
            }
        });
    }

    @Override
    public FluentWebElements fieldsets() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.fieldsets();
            }
        });
    }

    @Override
    public FluentWebElements fieldsets(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.fieldsets(by);
            }
        });
    }

    @Override
    public FluentWebElement legend() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.legend();
            }
        });
    }

    @Override
    public FluentWebElement legend(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.legend(by);
            }
        });
    }

    @Override
    public FluentWebElements legends() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.legends();
            }
        });
    }

    @Override
    public FluentWebElements legends(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.legends(by);
            }
        });
    }

    @Override
    public FluentWebElement tr() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.tr();
            }
        });
    }

    @Override
    public FluentWebElement tr(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.tr(by);
            }
        });
    }

    @Override
    public FluentWebElements trs() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.trs();
            }
        });
    }

    @Override
    public FluentWebElements trs(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.trs(by);
            }
        });
    }

    @Override
    public FluentWebElement td() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.td();
            }
        });
    }

    @Override
    public FluentWebElement td(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.td(by);
            }
        });
    }

    @Override
    public FluentWebElements tds() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.tds();
            }
        });
    }

    @Override
    public FluentWebElements tds(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.tds(by);
            }
        });
    }


    @Override
    public FluentWebElement th() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.th();
            }
        });
    }

    @Override
    public FluentWebElement th(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.th(by);
            }
        });
    }

    @Override
    public FluentWebElements ths() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.ths();
            }
        });
    }

    @Override
    public FluentWebElements ths(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.ths(by);
            }
        });
    }

    @Override
    public FluentWebElement ul() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.ul();
            }
        });
    }

    @Override
    public FluentWebElement ul(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.ul(by);
            }
        });
    }

    @Override
    public FluentWebElements uls() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.uls();
            }
        });
    }

    @Override
    public FluentWebElements uls(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.uls(by);
            }
        });
    }

    @Override
    public FluentWebElement ol() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.ol();
            }
        });
    }

    @Override
    public FluentWebElement ol(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.ol(by);
            }
        });
    }

    @Override
    public FluentWebElements ols() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.ols();
            }
        });
    }

    @Override
    public FluentWebElements ols(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.ols(by);
            }
        });
    }

    @Override
    public FluentWebElement li() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.li();
            }
        });
    }

    @Override
    public FluentWebElement li(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.li(by);
            }
        });
    }

    @Override
    public FluentWebElements lis() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.lis();
            }
        });
    }

    @Override
    public FluentWebElements lis(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.lis(by);
            }
        });
    }

    @Override
    public FluentWebElement form() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.form();
            }
        });
    }

    @Override
    public FluentWebElement form(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.form(by);
            }
        });
    }

    @Override
    public FluentWebElements forms() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.forms();
            }
        });
    }

    @Override
    public FluentWebElements forms(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.forms(by);
            }
        });
    }

    @Override
    public FluentWebElement textarea() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.textarea();
            }
        });
    }

    @Override
    public FluentWebElement textarea(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.textarea(by);
            }
        });
    }

    @Override
    public FluentWebElements textareas() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.textareas();
            }
        });
    }

    @Override
    public FluentWebElements textareas(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.textareas(by);
            }
        });
    }

    @Override
    public FluentWebElement option() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.option();
            }
        });
    }

    @Override
    public FluentWebElement option(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.option(by);
            }
        });
    }

    @Override
    public FluentWebElements options() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.options();
            }
        });
    }

    @Override
    public FluentWebElements options(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.options(by);
            }
        });
    }

    @Override
    public FluentWebElement map() {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.map();
            }
        });
    }

    @Override
    public FluentWebElement map(final By by) {
        return recording.fluentWebElement(new OnFluentWebElement() {
            public FluentWebElement doItForReal(FluentWebElement fwe) {
                return fwe.map(by);
            }
        });
    }

    @Override
    public FluentWebElements maps() {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.maps();
            }
        });
    }

    @Override
    public FluentWebElements maps(final By by) {
        return recording.fluentWebElements(new OnFluentWebElement() {
            public FluentWebElements doItForReal(FluentWebElement fwe) {
                return fwe.maps(by);
            }
        });
    }

    @Override
    public TestableString url() {
        return recording.testableString(new OnFluentWebElement() {
            public TestableString doItForReal(FluentWebElement fwe) {
                return fwe.url();
            }
        });
    }

    @Override
    public TestableString title() {
        return recording.testableString(new OnFluentWebElement() {
            public TestableString doItForReal(FluentWebElement fwe) {
                return fwe.title();
            }
        });
    }


}
