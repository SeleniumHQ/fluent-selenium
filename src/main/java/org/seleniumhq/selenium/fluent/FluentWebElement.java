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

import org.openqa.selenium.*;

import java.util.List;

public class FluentWebElement extends OngoingFluentWebDriver {

    protected final WebElement currentElement;

    public FluentWebElement(WebDriver delegate, WebElement currentElement, String context) {
        super(delegate, context);
        this.currentElement = currentElement;
    }

    protected WebElement findIt(By by) {
        return currentElement.findElement(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return currentElement.findElements(by);
    }

    public FluentWebElement click() {
        String ctx = context + ".click()";
        execute(new Execution() {
            public void execute() {
                currentElement.click();
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public FluentWebElement clearField() {
        String ctx = context + ".clearField()";
        execute(new Execution() {
            public void execute() {
                currentElement.clear();
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }


    public FluentWebElement submit() {
        String ctx = context + ".submit()";
        execute(new Execution() {
            public void execute() {
                currentElement.submit();
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }

    // These are as they would be in the WebElement API

    public FluentWebElement sendKeys(final CharSequence... keysToSend) {
        String ctx = context + ".sendKeys(" + charSeqArrayAsHumanString(keysToSend) + ")";
        execute(new Execution() {
            public void execute() {
                currentElement.sendKeys(keysToSend);
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }


    public String getTagName() {
        String ctx = context + ".getTagName()";
        final String[] tn = new String[1];
        execute(new Execution() {
            public void execute() {
                tn[0] = currentElement.getTagName();
            }
        }, ctx);
        return tn[0];
    }

    public boolean isSelected() {
        String ctx = context + ".isSelected()";
        final boolean[] is = new boolean[1];
        execute(new Execution() {
            public void execute() {
                is[0] = currentElement.isSelected();
            }
        }, ctx);
        return is[0];
    }

    public boolean isEnabled() {
        String ctx = context + ".isEnabled()";
        final boolean[] is = new boolean[1];
        execute(new Execution() {
            public void execute() {
                is[0] = currentElement.isEnabled();
            }
        }, ctx);
        return is[0];
    }

    public boolean isDisplayed() {
        String ctx = context + ".isDisplayed()";
        final boolean[] is = new boolean[1];
        execute(new Execution() {
            public void execute() {
                is[0] = currentElement.isDisplayed();
            }
        }, ctx);
        return is[0];
    }

    public Point getLocation() {
        String ctx = context + ".getLocation()";
        final Point[] locn = new Point[1];
        execute(new Execution() {
            public void execute() {
                locn[0] = currentElement.getLocation();
            }
        }, ctx);
        return locn[0];
    }

    public Dimension getSize() {
        String ctx = context + ".getSize()";
        final Dimension[] dim = new Dimension[1];
        execute(new Execution() {
            public void execute() {
                dim[0] = currentElement.getSize();
            }
        }, ctx);
        return dim[0];
    }

    public String getCssValue(final String cssName) {
        String ctx = context + ".getCssValue("+cssName+")";
        final String[] val = new String[1];
        execute(new Execution() {
            public void execute() {
                val[0] = currentElement.getCssValue(cssName);
            }
        }, ctx);
        return val[0];
    }

    public String getAttribute(final String attr) {
        String ctx = context + ".getAttribute("+attr+")";
        final String[] val = new String[1];
        execute(new Execution() {
            public void execute() {
                val[0] = currentElement.getAttribute(attr);
            }
        }, ctx);
        return val[0];
    }

    public String getText() {
        String ctx = context + ".getText()";
        final String[] val = new String[1];
        execute(new Execution() {
            public void execute() {
                val[0] = currentElement.getText();
            }
        }, ctx);
        return val[0];
    }

    //@Override
    public WebElementValue<Point> location() {
        return new WebElementValue<Point>(currentElement.getLocation(), context + ".location()");
    }

    //@Override
    public WebElementValue<Dimension> size() {
        return new WebElementValue<Dimension>(currentElement.getSize(), context + ".size()");
    }

    //@Override
    public WebElementValue<String> cssValue(String name) {
        return new WebElementValue<String>(currentElement.getCssValue(name), context + ".cssValue(" + name + ")");
    }

    //@Override
    public WebElementValue<String> attribute(String name) {
        return new WebElementValue<String>(currentElement.getAttribute(name), context + ".attribute(" + name + ")");
    }

    //@Override
    public WebElementValue<String> tagName() {
        return new WebElementValue<String>(currentElement.getTagName(), context + ".tagName()");
    }

    //@Override
    public WebElementValue<Boolean> selected() {
        return new WebElementValue<Boolean>(currentElement.isSelected(), context + ".selected()");
    }

    //@Override
    public WebElementValue<Boolean> enabled() {
        return new WebElementValue<Boolean>(currentElement.isEnabled(), context + ".enabled()");
    }

    //@Override
    public WebElementValue<Boolean> displayed() {
        return new WebElementValue<Boolean>(currentElement.isDisplayed(), context + ".displayed()");
    }

    //@Override
    public WebElementValue<String> text() {
        return new WebElementValue<String>(currentElement.getText(), context + ".text()");
    }
}
