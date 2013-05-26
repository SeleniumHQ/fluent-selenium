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
        return actualFindIt(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return actualFindThem(by);
    }

    protected final WebElement actualFindIt(By by) {
        return delegate.findElement(by);
    }

    protected final List<WebElement> actualFindThem(By by) {
        return delegate.findElements(by);
    }

    public FluentWebDriverImpl within(final Period period) {
        return new RetryingFluentWebDriver(delegate, period, Context.singular(context, "within", null, period));
    }

    public NegatingFluentWebDriver without(Period period) {
        return new NegatingFluentWebDriver(delegate, period, Context.singular(context, "without", null, period));
    }

    private class RetryingFluentWebDriver extends FluentWebDriverImpl {

        private final Period period;

        public RetryingFluentWebDriver(WebDriver webDriver, Period period, Context context) {
            super(webDriver, context);
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


}
