package org.seleniumhq.selenium.fluent;

import static org.hamcrest.core.IsNot.not;

public class Matchable<T> {
    private T val;

    public Matchable(T val) {
        this.val = val;
    }

    public Matchable<T> be(org.hamcrest.Matcher<T> matcher) {
        matcher.matches(val);
        return this;
    }

    public Matchable<T> notBe(org.hamcrest.Matcher<T> matcher) {
        not(matcher.matches(val));
        return this;
    }

    public T value() {
        return val;
    }
}
