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
import java.util.concurrent.TimeUnit;

public class FluentWebElement extends BaseFluentWebElement {

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
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                currentElement.click();
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public FluentWebElement clearField() {
        String ctx = context + ".clearField()";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                currentElement.clear();
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }


    public FluentWebElement submit() {
        String ctx = context + ".submit()";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                currentElement.submit();
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }

    // These are as they would be in the WebElement API

    public FluentWebElement sendKeys(final CharSequence... keysToSend) {
        String ctx = context + ".sendKeys(" + charSeqArrayAsHumanString(keysToSend) + ")";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                currentElement.sendKeys(keysToSend);
                return true;
            }
        }, ctx);
        return getFluentWebElement(currentElement, ctx, FluentWebElement.class);
    }


    public String getTagName() {
        String tagName = execute(new Execution<String>() {
            public String execute() {
                return currentElement.getTagName();
            }
        }, context + ".getTagName()");
        return tagName;
    }

    public boolean isSelected() {
        return execute(new Execution<Boolean>() {
            public Boolean execute() {
                return currentElement.isSelected();
            }
        }, context + ".isSelected()");
    }

    public boolean isEnabled() {
        return execute(new Execution<Boolean>() {
            public Boolean execute() {
                return currentElement.isEnabled();
            }
        }, context + ".isEnabled()");
    }

    public boolean isDisplayed() {
        return execute(new Execution<Boolean>() {
            public Boolean execute() {
                return currentElement.isDisplayed();
            }
        }, context + ".isDisplayed()");
    }

    public Point getLocation() {
        return execute(new Execution<Point>() {
            public Point execute() {
                return currentElement.getLocation();
            }
        }, context + ".getLocation()");
    }

    public Dimension getSize() {
        return execute(new Execution<Dimension>() {
            public Dimension execute() {
                return currentElement.getSize();
            }
        }, context + ".getSize()");
    }

    public String getCssValue(final String cssName) {
        return execute(new Execution<String>() {
            public String execute() {
                return currentElement.getCssValue(cssName);
            }
        }, context + ".getCssValue("+cssName+")");
    }

    public String getAttribute(final String attr) {
        return execute(new Execution<String>() {
            public String execute() {
                return currentElement.getAttribute(attr);
            }
        }, context + ".getAttribute("+attr+")");
    }

    public String getText() {
        return execute(new Execution<String>() {
            public String execute() {
                return currentElement.getText();
            }
        }, context + ".getText()");
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

    public FluentWebElement within(Period period) {
        return new MorePatientFluentWebElement(delegate, currentElement, context, period);
    }

    private class MorePatientFluentWebElement extends FluentWebElement {

        private final Period period;

        public MorePatientFluentWebElement(WebDriver webDriver, WebElement currentElement, String context, Period period) {
            super(webDriver, currentElement, context);
            this.period = period;
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


}
