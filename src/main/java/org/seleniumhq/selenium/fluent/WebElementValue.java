package org.seleniumhq.selenium.fluent;

public class WebElementValue<T> {

    private T val;

    public WebElementValue(T val) {
        this.val = val;
    }

    public Matchable<T> should() {
        return new Matchable<T>(val);
    }
}
