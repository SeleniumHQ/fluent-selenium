/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FluentSelect extends FluentWebElement {

    private Select currentSelect;

    public FluentSelect(WebDriver delegate, WebElement currentElement, Context context) {
        super(delegate, currentElement, context);
    }

    private FluentSelect(WebDriver delegate, Select currentSelect, WebElement currentElement, Context context) {
        super(delegate, currentElement, context);
        this.currentSelect = currentSelect;
    }

    /**
     * @return Whether this select element support selecting multiple options at the same time? This
     *         is done by checking the value of the "multiple" attribute.
     */
    public boolean isMultiple() {
        return decorateExecution(new IsMultiple(), Context.singular(context, "isMultiple"));
    }

    /**
     * @return All options belonging to this select tag
     */
    public List<WebElement> getOptions() {
        return decorateExecution(new GetOptions(), Context.singular(context, "getOptions"));
    }

    /**
     * @return All selected options belonging to this select tag
     */
    public List<WebElement> getAllSelectedOptions() {
        return decorateExecution(new GetAllSelectedOptions(), Context.singular(context, "getAllSelectedOptions"));
    }

    /**
     * @return The first selected option in this select tag (or the currently selected option in a
     *         normal select)
     */
    public WebElement getFirstSelectedOption() {
        return decorateExecution(new GetFirstSelectedOption(), Context.singular(context, "getFirstSelectedOption"));
    }

    /**
     * Select all options that display text matching the argument. That is, when given "Bar" this
     * would select an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param text The visible text to match against
     */
    public FluentSelect selectByVisibleText(final String text) {
        decorateExecution(new SelectByVisibleText(text), Context.singular(context, "selectByVisibleText", null, text));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    /**
     * Select the option at the given index. This is done by examing the "index" attribute of an
     * element, and not merely by counting.
     *
     * @param index The option at this index will be selected
     */
    public FluentSelect selectByIndex(final int index) {
        decorateExecution(new SelectByIndex(index), Context.singular(context, "selectByIndex", null, index));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    /**
     * Select all options that have a value matching the argument. That is, when given "foo" this
     * would select an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     */
    public FluentSelect selectByValue(final String value) {
        decorateExecution(new SelectByValue(value), Context.singular(context, "selectByValue", null, value));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    /**
     * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
     *
     * @throws UnsupportedOperationException If the SELECT does not support multiple selections
     */
    public FluentSelect deselectAll() {
        decorateExecution(new DeselectAll(), Context.singular(context, "deselectAll"));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    /**
     * Deselect all options that have a value matching the argument. That is, when given "foo" this
     * would deselect an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     */
    public FluentSelect deselectByValue(final String value) {
        decorateExecution(new DeselectByValue(value), Context.singular(context, "deselectByValue", null, value));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    /**
     * Deselect the option at the given index. This is done by examining the "index" attribute of an
     * element, and not merely by counting.
     *
     * @param index The option at this index will be deselected
     */
    public FluentSelect deselectByIndex(final int index) {
        decorateExecution(new DeselectByIndex(index), Context.singular(context, "deselectByIndex", null, index));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    /**
     * Deselect all options that display text matching the argument. That is, when given "Bar" this
     * would deselect an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param text The visible text to match against
     */
    public FluentSelect deselectByVisibleText(final String text) {
        decorateExecution(new DeselectByVisibleText(text), Context.singular(context, "deselectByVisibleText", null, text));
        return new FluentSelect(super.delegate, currentElement, this.context);
    }

    protected synchronized Select getSelect() {
        if (currentSelect == null) {
            currentSelect = new Select(currentElement);
        }
        return currentSelect;
    }

    public FluentSelect within(Period period) {
        return new MorePatientFluentSelect(delegate, Context.singular(context, "within", null, period), currentSelect, currentElement, period);
    }

    // TODO rename
    private class MorePatientFluentSelect extends FluentSelect {

        private final Period period;

        public MorePatientFluentSelect(WebDriver webDriver, Context context, Select currentSelect, WebElement currentElement, Period period) {
            super(webDriver, currentSelect, currentElement, context);
            this.period = period;
        }

        @Override
        protected WebElement findIt(By by) {
            return retryingFindIt(by);
        }

        @Override
        protected List<WebElement> findThem(By by) {
            return retryingFindThem(by);
        }

        @Override
        protected Period getPeriod() {
            return period;
        }

        @Override
        protected void changeTimeout() {
            delegate.manage().timeouts().implicitlyWait(period.howLong(), period.timeUnit());
        }

        @Override
        protected void resetTimeout() {
            delegate.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        }

    }


    private class DeselectAll implements Execution<Boolean> {
        public Boolean execute() {
            getSelect().deselectAll();
            return true;
        }
    }

    private class SelectByValue implements Execution<Boolean> {
        private final String value;

        public SelectByValue(String value) {
            this.value = value;
        }

        public Boolean execute() {
            Select select = getSelect();
            select.selectByValue(value);
            return true;
        }
    }

    private class SelectByIndex implements Execution<Boolean> {
        private final int index;

        public SelectByIndex(int index) {
            this.index = index;
        }

        public Boolean execute() {
            getSelect().selectByIndex(index);
            return true;
        }
    }

    private class SelectByVisibleText implements Execution<Boolean> {
        private final String text;

        public SelectByVisibleText(String text) {
            this.text = text;
        }

        public Boolean execute() {
            getSelect().selectByVisibleText(text);
            return true;
        }
    }

    private class GetFirstSelectedOption implements Execution<WebElement> {
        public WebElement execute() {
            return getSelect().getFirstSelectedOption();
        }
    }

    private class GetAllSelectedOptions implements Execution<List<WebElement>> {
        public List<WebElement> execute() {
            return getSelect().getAllSelectedOptions();
        }
    }

    private class GetOptions implements Execution<List<WebElement>> {
        public List<WebElement> execute() {
            return getSelect().getOptions();
        }
    }

    private class IsMultiple implements Execution<Boolean> {
        public Boolean execute() {
            return getSelect().isMultiple();
        }
    }

    private class DeselectByValue implements Execution<Boolean> {
        private final String value;

        public DeselectByValue(String value) {
            this.value = value;
        }

        public Boolean execute() {
            getSelect().deselectByValue(value);
            return true;
        }
    }

    private class DeselectByIndex implements Execution<Boolean> {
        private final int index;

        public DeselectByIndex(int index) {
            this.index = index;
        }

        public Boolean execute() {
            getSelect().deselectByIndex(index);
            return true;
        }
    }

    private class DeselectByVisibleText implements Execution<Boolean> {
        private final String text;

        public DeselectByVisibleText(String text) {
            this.text = text;
        }

        public Boolean execute() {
            getSelect().deselectByVisibleText(text);
            return true;
        }
    }
}
