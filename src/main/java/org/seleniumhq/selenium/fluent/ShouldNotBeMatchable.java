package org.seleniumhq.selenium.fluent;

public class ShouldNotBeMatchable<T> extends ShouldOrShouldNotBeMatchable<T> {
    public ShouldNotBeMatchable(T val, T shouldOrShouldNotBe, String context) {
        super(val, shouldOrShouldNotBe, context, false);
    }
}
