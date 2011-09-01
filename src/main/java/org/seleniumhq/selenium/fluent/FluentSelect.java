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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class FluentSelect extends FluentWebElement {

    private Select select;

    public FluentSelect(WebDriver delegate, WebElement currentElement, String context) {
        super(delegate, currentElement, context);
    }

    /**
     * @return Whether this select element support selecting multiple options at the same time? This
     *         is done by checking the value of the "multiple" attribute.
     */
    public boolean isMultiple() {
        return execute(new Execution<Boolean>() {
            public Boolean execute() {
                return getSelect().isMultiple();
            }
        }, context + ".isMultiple()");
    }

    /**
     * @return All options belonging to this select tag
     */
    public List<WebElement> getOptions() {
        return execute(new Execution<List<WebElement>>() {
            public List<WebElement> execute() {
                return getSelect().getOptions();
            }
        }, context + ".getOptions()");
    }

    /**
     * @return All selected options belonging to this select tag
     */
    public List<WebElement> getAllSelectedOptions() {
        return execute(new Execution<List<WebElement>>() {
            public List<WebElement> execute() {
                return getSelect().getAllSelectedOptions();
            }
        }, context + ".getAllSelectedOptions()");
    }

    /**
     * @return The first selected option in this select tag (or the currently selected option in a
     *         normal select)
     */
    public WebElement getFirstSelectedOption() {
        return execute(new Execution<WebElement>() {
            public WebElement execute() {
                return getSelect().getFirstSelectedOption();
            }
        }, context + ".getFirstSelectedOption()");
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
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().selectByVisibleText(text);
                return true;
            }
        }, context + ".selectByVisibleText(" + text + ")");
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
    }

    /**
     * Select the option at the given index. This is done by examing the "index" attribute of an
     * element, and not merely by counting.
     *
     * @param index The option at this index will be selected
     */
    public FluentSelect selectByIndex(final int index) {
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().selectByIndex(index);
                return true;
            }
        }, context + ".selectByIndex(" + index + ")");
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
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
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().selectByValue(value);
                return true;
            }
        }, context + ".selectByValue(" + value + ")");
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
    }

    /**
     * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
     *
     * @throws UnsupportedOperationException If the SELECT does not support multiple selections
     */
    public FluentSelect deselectAll() {
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().deselectAll();
                return true;
            }
        }, context + ".deselectAll()");
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
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
        String ctx = context + ".deselectByValue(" + value + ")";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().deselectByValue(value);
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
    }

    /**
     * Deselect the option at the given index. This is done by examining the "index" attribute of an
     * element, and not merely by counting.
     *
     * @param index The option at this index will be deselected
     */
    public FluentSelect deselectByIndex(final int index) {
        String ctx = context + ".deselectByIndex(" + index + ")";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().deselectByIndex(index);
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
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
        String ctx = context + ".deselectByVisibleText(" + text + ")";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                getSelect().deselectByVisibleText(text);
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, this.context, FluentSelect.class);
    }

    protected synchronized Select getSelect() {
        if (select == null) {
            select = new Select(currentElement);
        }
        return select;
    }
}
