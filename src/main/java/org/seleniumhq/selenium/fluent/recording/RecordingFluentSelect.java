package org.seleniumhq.selenium.fluent.recording;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentSelects;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.Period;
import org.seleniumhq.selenium.fluent.TestableString;
import org.seleniumhq.selenium.fluent.WebElementValue;

import java.util.List;

public class RecordingFluentSelect extends FluentSelect {

    private final FluentRecorder recording;

    public RecordingFluentSelect(FluentRecorder recording) {
        super(null, null, null);
        this.recording = recording;
    }

    /**
     * @return Whether this select element support selecting multiple options at the same time? This
     *         is done by checking the value of the "multiple" attribute.
     */
    @Override
    public boolean isMultiple() {
        return recording.returnsBoolean(new OnFluentSelect() {
            public Boolean doItForReal(FluentSelect fs) {
                return fs.isMultiple();
            }
        });
    }

    /**
     * @return All options belonging to this select tag
     */
    @Override
    public List<WebElement> getOptions() {
        throw new UnsupportedOperationException("getOptions not recordable yet");    }

    /**
     * @return All selected options belonging to this select tag
     */
    @Override
    public List<WebElement> getAllSelectedOptions() {
        throw new UnsupportedOperationException("getAllSelectedOptions not recordable yet");
    }

    /**
     * @return The first selected option in this select tag (or the currently selected option in a
     *         normal select)
     */
    @Override
    public WebElement getFirstSelectedOption() {
        throw new UnsupportedOperationException("getFirstSelectedOption not recordable yet");
    }

    /**
     * Select all options that display text matching the argument. That is, when given "Bar" this
     * would select an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param text The visible text to match against
     */
    @Override
    public FluentSelect selectByVisibleText(final String text) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.selectByVisibleText(text);
            }
        });
    }

    /**
     * Select the option at the given index. This is done by examing the "index" attribute of an
     * element, and not merely by counting.
     *
     * @param index The option at this index will be selected
     */
    @Override
    public FluentSelect selectByIndex(final int index) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.selectByIndex(index);
            }
        });
    }

    /**
     * Select all options that have a value matching the argument. That is, when given "foo" this
     * would select an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     */
    @Override
    public FluentSelect selectByValue(final String value) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.selectByValue(value);
            }
        });
    }

    /**
     * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
     *
     * @throws UnsupportedOperationException If the SELECT does not support multiple selections
     */
    @Override
    public FluentSelect deselectAll() {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.deselectAll();
            }
        });
    }

    /**
     * Deselect all options that have a value matching the argument. That is, when given "foo" this
     * would deselect an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     */
    @Override
    public FluentSelect deselectByValue(final String value) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.deselectByValue(value);
            }
        });
    }

    /**
     * Deselect the option at the given index. This is done by examining the "index" attribute of an
     * element, and not merely by counting.
     *
     * @param index The option at this index will be deselected
     */
    @Override
    public FluentSelect deselectByIndex(final int index) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.deselectByIndex(index);
            }
        });
    }

    /**
     * Deselect all options that display text matching the argument. That is, when given "Bar" this
     * would deselect an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param text The visible text to match against
     */
    @Override
    public FluentSelect deselectByVisibleText(final String text) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.deselectByVisibleText(text);
            }
        });
    }


    @Override
    public FluentSelect within(final Period period) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentSelect doItForReal(FluentSelect fs) {
                return fs.within(period);
            }
        });
    }

    @Override
    public FluentWebElement click() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.click();
            }
        });
    }

    @Override
    public FluentWebElement clearField() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.clearField();
            }
        });
    }

    @Override
    public FluentWebElement submit() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.submit();
            }
        });
    }

    @Override
    public TestableString getTagName() {
        return recording.testableString(new OnFluentSelect() {
            public TestableString doItForReal(FluentSelect fs) {
                return fs.getTagName();
            }
        });
    }

    @Override
    public TestableString getCssValue(final String cssName) {
        return recording.testableString(new OnFluentSelect() {
            public TestableString doItForReal(FluentSelect fs) {
                return fs.getCssValue(cssName);
            }
        });
    }

    @Override
    public TestableString getAttribute(final String attr) {
        return recording.testableString(new OnFluentSelect() {
            public TestableString doItForReal(FluentSelect fs) {
                return fs.getAttribute(attr);
            }
        });
    }

    @Override
    public TestableString getText() {
        return recording.testableString(new OnFluentSelect() {
            public TestableString doItForReal(FluentSelect fs) {
                return fs.getText();
            }
        });
    }

    @Override
    public FluentWebElement sendKeys(final CharSequence... keysToSend) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.sendKeys(keysToSend);
            }
        });
    }

    // Return Primatives

    @Override
    public boolean isSelected() {
        return recording.returnsBoolean(new OnFluentSelect() {
            public Boolean doItForReal(FluentSelect fs) {
                return fs.isSelected();
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return recording.returnsBoolean(new OnFluentSelect() {
            public Boolean doItForReal(FluentSelect fs) {
                return fs.isEnabled();
            }
        });
    }

    @Override
    public boolean isDisplayed() {
        return recording.returnsBoolean(new OnFluentSelect() {
            public Boolean doItForReal(FluentSelect fs) {
                return fs.isDisplayed();
            }
        });
    }

    @Override
    public Point getLocation() {
        return recording.point(new OnFluentSelect() {
            public Point doItForReal(FluentSelect fs) {
                return fs.getLocation();
            }
        });
    }

    @Override
    public Dimension getSize() {
        return recording.dimension(new OnFluentSelect() {
            public Dimension doItForReal(FluentSelect fs) {
                return fs.getSize();
            }
        });
    }

    // Assertable


    @Override
    public WebElementValue<String> cssValue(final String name) {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<String> doItForReal(FluentSelect fs) {
                return fs.cssValue(name);
            }
        });
    }

    @Override
    public WebElementValue<String> attribute(final String name) {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<String> doItForReal(FluentSelect fs) {
                return fs.attribute(name);
            }
        });
    }

    @Override
    public WebElementValue<Point> location() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<Point> doItForReal(FluentSelect fs) {
                return fs.location();
            }
        });

    }

    @Override
    public WebElementValue<Dimension> size() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<Dimension> doItForReal(FluentSelect fs) {
                return fs.size();
            }
        });
    }

    @Override
    public WebElementValue<String> tagName() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<String> doItForReal(FluentSelect fs) {
                return fs.tagName();
            }
        });

    }

    @Override
    public WebElementValue<Boolean> selected() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<Boolean> doItForReal(FluentSelect fs) {
                return fs.selected();
            }
        });

    }

    @Override
    public WebElementValue<Boolean> enabled() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<Boolean> doItForReal(FluentSelect fs) {
                return fs.enabled();
            }
        });

    }

    @Override
    public WebElementValue<Boolean> displayed() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<Boolean> doItForReal(FluentSelect fs) {
                return fs.displayed();
            }
        });

    }

    @Override
    public WebElementValue<String> text() {
        return recording.webElementValue(new OnFluentSelect() {
            public WebElementValue<String> doItForReal(FluentSelect fs) {
                return fs.text();
            }
        });

    }

    // Overridden from BaseFluentWebElement


    @Override
    public FluentWebElement span() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.span();
            }
        });
    }

    @Override
    public FluentWebElement span(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.span(by);
            }
        });
    }

    @Override
    public FluentWebElements spans() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.spans();
            }
        });
    }

    @Override
    public FluentWebElements spans(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.spans(by);
            }
        });
    }

    @Override
    public FluentWebElement div() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.div();
            }
        });
    }

    @Override
    public FluentWebElement div(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.div(by);
            }
        });
    }

    @Override
    public FluentWebElements divs() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.divs();
            }
        });
    }

    @Override
    public FluentWebElements divs(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.divs(by);
            }
        });
    }

    @Override
    public FluentWebElement button() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.button();
            }
        });
    }

    @Override
    public FluentWebElement button(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.button(by);
            }
        });
    }

    @Override
    public FluentWebElements buttons() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.buttons();
            }
        });
    }

    @Override
    public FluentWebElements buttons(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.buttons(by);
            }
        });
    }

    @Override
    public FluentWebElement link() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.link();
            }
        });
    }

    @Override
    public FluentWebElement link(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.link(by);
            }
        });
    }

    @Override
    public FluentWebElements links() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.links();
            }
        });
    }

    @Override
    public FluentWebElements links(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.links(by);
            }
        });
    }

    @Override
    public FluentWebElement input() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.input();
            }
        });
    }

    @Override
    public FluentWebElement input(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.input(by);
            }
        });
    }

    @Override
    public FluentWebElements inputs() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.inputs();
            }
        });
    }

    @Override
    public FluentWebElements inputs(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.inputs(by);
            }
        });
    }

    @Override
    public FluentSelect select() {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.select();
            }
        });
    }

    @Override
    public FluentSelect select(final By by) {
        return recording.fluentSelect(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.select(by);
            }
        });
    }

    @Override
    public FluentSelects selects() {
        return recording.fluentSelects(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.selects();
            }
        });
    }

    @Override
    public FluentSelects selects(final By by) {
        return recording.fluentSelects(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.selects(by);
            }
        });
    }

    @Override
    public FluentWebElement h1() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h1();
            }
        });
    }

    @Override
    public FluentWebElement h1(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h1(by);
            }
        });
    }

    @Override
    public FluentWebElements h1s() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h1s();
            }
        });
    }

    @Override
    public FluentWebElements h1s(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h1s(by);
            }
        });
    }

    @Override
    public FluentWebElement h2() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h2();
            }
        });
    }

    @Override
    public FluentWebElement h2(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h2(by);
            }
        });
    }

    @Override
    public FluentWebElements h2s() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h2s();
            }
        });
    }

    @Override
    public FluentWebElements h2s(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h2s(by);
            }
        });
    }

    @Override
    public FluentWebElement h3() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h3();
            }
        });
    }

    @Override
    public FluentWebElement h3(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h3(by);
            }
        });
    }

    @Override
    public FluentWebElements h3s() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h3s();
            }
        });
    }

    @Override
    public FluentWebElements h3s(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h3s(by);
            }
        });
    }

    @Override
    public FluentWebElement h4() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h4();
            }
        });
    }

    @Override
    public FluentWebElement h4(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.h4(by);
            }
        });
    }

    @Override
    public FluentWebElements h4s() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h4s();
            }
        });
    }

    @Override
    public FluentWebElements h4s(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.h4s(by);
            }
        });
    }

    @Override
    public FluentWebElement p() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.p();
            }
        });
    }

    @Override
    public FluentWebElement p(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.p(by);
            }
        });
    }

    @Override
    public FluentWebElements ps() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.ps();
            }
        });
    }

    @Override
    public FluentWebElements ps(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.ps(by);
            }
        });
    }

    @Override
    public FluentWebElement img() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.img();
            }
        });
    }

    @Override
    public FluentWebElement img(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.img(by);
            }
        });
    }

    @Override
    public FluentWebElements imgs() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.imgs();
            }
        });
    }

    @Override
    public FluentWebElements imgs(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.imgs(by);
            }
        });
    }

    @Override
    public FluentWebElement table() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.table();
            }
        });
    }

    @Override
    public FluentWebElement table(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.table(by);
            }
        });
    }

    @Override
    public FluentWebElements tables() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.tables();
            }
        });
    }

    @Override
    public FluentWebElements tables(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.tables(by);
            }
        });
    }

    @Override
    public FluentWebElement fieldset() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.fieldset();
            }
        });
    }

    @Override
    public FluentWebElement fieldset(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.fieldset(by);
            }
        });
    }

    @Override
    public FluentWebElements fieldsets() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.fieldsets();
            }
        });
    }

    @Override
    public FluentWebElements fieldsets(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.fieldsets(by);
            }
        });
    }

    @Override
    public FluentWebElement legend() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.legend();
            }
        });
    }

    @Override
    public FluentWebElement legend(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.legend(by);
            }
        });
    }

    @Override
    public FluentWebElements legends() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.legends();
            }
        });
    }

    @Override
    public FluentWebElements legends(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.legends(by);
            }
        });
    }

    @Override
    public FluentWebElement tr() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.tr();
            }
        });
    }

    @Override
    public FluentWebElement tr(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.tr(by);
            }
        });
    }

    @Override
    public FluentWebElements trs() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.trs();
            }
        });
    }

    @Override
    public FluentWebElements trs(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.trs(by);
            }
        });
    }

    @Override
    public FluentWebElement td() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.td();
            }
        });
    }

    @Override
    public FluentWebElement td(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.td(by);
            }
        });
    }

    @Override
    public FluentWebElements tds() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.tds();
            }
        });
    }

    @Override
    public FluentWebElements tds(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.tds(by);
            }
        });
    }


    @Override
    public FluentWebElement th() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.th();
            }
        });
    }

    @Override
    public FluentWebElement th(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.th(by);
            }
        });
    }

    @Override
    public FluentWebElements ths() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.ths();
            }
        });
    }

    @Override
    public FluentWebElements ths(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.ths(by);
            }
        });
    }

    @Override
    public FluentWebElement ul() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.ul();
            }
        });
    }

    @Override
    public FluentWebElement ul(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.ul(by);
            }
        });
    }

    @Override
    public FluentWebElements uls() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.uls();
            }
        });
    }

    @Override
    public FluentWebElements uls(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.uls(by);
            }
        });
    }

    @Override
    public FluentWebElement ol() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.ol();
            }
        });
    }

    @Override
    public FluentWebElement ol(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.ol(by);
            }
        });
    }

    @Override
    public FluentWebElements ols() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.ols();
            }
        });
    }

    @Override
    public FluentWebElements ols(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.ols(by);
            }
        });
    }

    @Override
    public FluentWebElement li() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.li();
            }
        });
    }

    @Override
    public FluentWebElement li(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.li(by);
            }
        });
    }

    @Override
    public FluentWebElements lis() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.lis();
            }
        });
    }

    @Override
    public FluentWebElements lis(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.lis(by);
            }
        });
    }

    @Override
    public FluentWebElement form() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.form();
            }
        });
    }

    @Override
    public FluentWebElement form(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.form(by);
            }
        });
    }

    @Override
    public FluentWebElements forms() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.forms();
            }
        });
    }

    @Override
    public FluentWebElements forms(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.forms(by);
            }
        });
    }

    @Override
    public FluentWebElement textarea() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.textarea();
            }
        });
    }

    @Override
    public FluentWebElement textarea(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.textarea(by);
            }
        });
    }

    @Override
    public FluentWebElements textareas() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.textareas();
            }
        });
    }

    @Override
    public FluentWebElements textareas(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.textareas(by);
            }
        });
    }

    @Override
    public FluentWebElement option() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.option();
            }
        });
    }

    @Override
    public FluentWebElement option(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.option(by);
            }
        });
    }

    @Override
    public FluentWebElements options() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.options();
            }
        });
    }

    @Override
    public FluentWebElements options(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.options(by);
            }
        });
    }

    @Override
    public FluentWebElement map() {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.map();
            }
        });
    }

    @Override
    public FluentWebElement map(final By by) {
        return recording.fluentWebElement(new OnFluentSelect() {
            public FluentWebElement doItForReal(FluentSelect fs) {
                return fs.map(by);
            }
        });
    }

    @Override
    public FluentWebElements maps() {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.maps();
            }
        });
    }

    @Override
    public FluentWebElements maps(final By by) {
        return recording.fluentWebElements(new OnFluentSelect() {
            public FluentWebElements doItForReal(FluentSelect fs) {
                return fs.maps(by);
            }
        });
    }

    @Override
    public TestableString url() {
        return recording.testableString(new OnFluentSelect() {
            public TestableString doItForReal(FluentSelect fs) {
                return fs.url();
            }
        });
    }

    @Override
    public TestableString title() {
        return recording.testableString(new OnFluentSelect() {
            public TestableString doItForReal(FluentSelect fs) {
                return fs.title();
            }
        });
    }
    
    

}
