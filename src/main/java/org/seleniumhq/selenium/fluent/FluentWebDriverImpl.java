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
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FluentWebDriverImpl extends BaseFluentWebDriver {

    public FluentWebDriverImpl(WebDriver delegate) {
        super(delegate, null);
    }

    protected FluentWebDriverImpl(WebDriver delegate, Context context) {
        super(delegate, context);
    }

    @Override
    protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context) {
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
        return new RetryingFluentWebDriver(delegate, period, Context.singular(context, "within", null, period));
    }

    private class RetryingFluentWebDriver extends FluentWebDriverImpl {

        private final Period period;

        public RetryingFluentWebDriver(WebDriver webDriver, Period period, Context context) {
            super(webDriver, context);
            this.period = period;
        }

        @Override
        protected WebElement findIt(By by) {
            long endMillis = period.getEndMillis();
            RuntimeException exceptionCausingRetry = new RuntimeException();
            WebElement it = null;
            while (exceptionCausingRetry != null && endMillis - System.currentTimeMillis() > 0) {
                try {
                    it = super.findIt(by);
                    exceptionCausingRetry = null;
                    return it;
                } catch (WebDriverException e) {
                    exceptionCausingRetry = e;
                }
            }
            if (exceptionCausingRetry != null) {
                throw exceptionCausingRetry;
            }
            return it;
        }

        @Override
        protected List<WebElement> findThem(By by) {
            long endMillis = period.getEndMillis();
            RuntimeException exceptionCausingRetry = new RuntimeException();
            List<WebElement> them = null;
            while (exceptionCausingRetry != null && endMillis - System.currentTimeMillis() > 0) {
                try {
                    them = super.findThem(by);
                    exceptionCausingRetry = null;
                    return them;
                } catch (WebDriverException e) {
                    exceptionCausingRetry = e;
                }
            }
            if (exceptionCausingRetry != null) {
                throw exceptionCausingRetry;
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
