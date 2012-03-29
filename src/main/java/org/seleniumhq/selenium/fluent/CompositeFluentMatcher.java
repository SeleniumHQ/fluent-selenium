package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.WebElement;

public class CompositeFluentMatcher implements FluentMatcher {

    private final LogicalOperator logicalOperator;
    private FluentMatcher[] matchers;

    public CompositeFluentMatcher(LogicalOperator logicalOperator, FluentMatcher... matchers) {
        this.logicalOperator = logicalOperator;
        this.matchers = matchers;
    }

    public boolean matches(WebElement webElement) {
        Boolean matched = null;
        for (FluentMatcher matcher : matchers) {
            matched = logicalOperator.invoke(webElement, matched, matcher);
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
        boolean invoke(WebElement webElement, Boolean matched, FluentMatcher matcher);        
    }
    
    private static class And implements LogicalOperator {
        public boolean invoke(WebElement webElement, Boolean matched, FluentMatcher matcher) {
            if (matched == null) {
                matched = true;
            }
            return matched && matcher.matches(webElement);
        }
    }

    private static class Or implements LogicalOperator {
        public boolean invoke(WebElement webElement, Boolean matched, FluentMatcher matcher) {
            if (matched == null) {
                matched = false;
            }
            return matched || matcher.matches(webElement);
        }
    }
}
