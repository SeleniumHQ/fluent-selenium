package org.seleniumhq.selenium.fluent;

public class ShouldBeMatchable<T> extends ShouldOrShouldNotBeMatchable<T> {
    public ShouldBeMatchable(T val, T shouldOrShouldNotBe, String context) {
        super(val, shouldOrShouldNotBe, context, true);
    }
}
