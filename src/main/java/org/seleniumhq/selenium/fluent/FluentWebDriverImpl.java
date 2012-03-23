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
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import javax.swing.text.rtf.RTFEditorKit;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FluentWebDriverImpl extends BaseFluentWebDriver implements FluentWebDriver {

    public FluentWebDriverImpl(WebDriver delegate) {
        super(delegate, "?");
    }

    protected FluentWebDriverImpl(WebDriver delegate, String context) {
        super(delegate, context);
    }

    @Override
    protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, String context) {
        return new FluentWebElements(super.delegate, results, context);
    }

    protected WebElement findIt(By by) {
        return delegate.findElement(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return delegate.findElements(by);
    }

    public FluentWebDriverImpl within(final Period period) {
        return new RetryingFluentWebDriver(delegate, period, context + ".within(" + period + ")");
    }

    private class RetryingFluentWebDriver extends FluentWebDriverImpl {

        private final Period period;

        public RetryingFluentWebDriver(WebDriver webDriver, Period period, String context) {
            super(webDriver, context);
            this.period = period;
        }

        @Override
        protected WebElement findIt(By by) {
            long endMillis = period.getEndMillis();
            RuntimeException exceptionToRetry = new RuntimeException();
            WebElement it = null;
            while (exceptionToRetry != null && endMillis - System.currentTimeMillis() > 0) {
                try {
                    it = super.findIt(by);
                    exceptionToRetry = null;
                    return it;
                } catch (WebDriverException e) {
                    exceptionToRetry = e;
                }
            }
            if (exceptionToRetry != null) {
                throw exceptionToRetry;
            }
            return it;
        }

        @Override
        protected List<WebElement> findThem(By by) {
            long endMillis = period.getEndMillis();
            RuntimeException exceptionToRetry = new RuntimeException();
            List<WebElement> them = null;
            while (exceptionToRetry != null && endMillis - System.currentTimeMillis() > 0) {
                try {
                    them = super.findThem(by);
                    exceptionToRetry = null;
                    return them;
                } catch (WebDriverException e) {
                    exceptionToRetry = e;
                }
            }
            if (exceptionToRetry != null) {
                throw exceptionToRetry;
            }
            return them;
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
}
