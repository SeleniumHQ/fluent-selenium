/*
Copyright 2011-2013 Software Freedom Conservancy

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

import org.openqa.selenium.WebElement;

public class CompositeFluentMatcher implements FluentMatcher {

    private final LogicalOperator logicalOperator;
    private FluentMatcher[] matchers;

    public CompositeFluentMatcher(LogicalOperator logicalOperator, FluentMatcher... matchers) {
        this.logicalOperator = logicalOperator;
        this.matchers = matchers;
    }

    public boolean matches(WebElement webElement, int ix) {
        Boolean matched = null;
        for (FluentMatcher matcher : matchers) {
            matched = logicalOperator.invoke(webElement, matched, matcher, ix);
        }
        return matched;
    }

    public static FluentMatcher both(FluentMatcher one, FluentMatcher two) {
        return new CompositeFluentMatcher(new And(), one, two);
    }

    public static FluentMatcher all(FluentMatcher... matchers) {
        return new CompositeFluentMatcher(new And(), matchers);
    }

    public static FluentMatcher any(FluentMatcher... matchers) {
        return new CompositeFluentMatcher(new Or(), matchers);
    }

    public static FluentMatcher either(FluentMatcher one, FluentMatcher two) {
        return new CompositeFluentMatcher(new Or(), one, two);
    }

    private static interface LogicalOperator {
        boolean invoke(WebElement webElement, Boolean matched, FluentMatcher matcher, int ix);
    }
    
    private static class And implements LogicalOperator {
        public boolean invoke(WebElement webElement, Boolean matched, FluentMatcher matcher, int ix) {
            if (matched == null) {
                matched = true;
            }
            return matched && matcher.matches(webElement, ix);
        }
    }

    private static class Or implements LogicalOperator {
        public boolean invoke(WebElement webElement, Boolean matched, FluentMatcher matcher, int ix) {
            if (matched == null) {
                matched = false;
            }
            return matched || matcher.matches(webElement, ix);
        }
    }
}
