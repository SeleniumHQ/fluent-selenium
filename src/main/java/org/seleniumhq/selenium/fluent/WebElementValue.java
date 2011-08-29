package org.seleniumhq.selenium.fluent;

public class WebElementValue<T> {

    private T val;
    private final String context;

    public WebElementValue(T val, String context) {
        this.val = val;
        this.context = context;
    }

    public Matchable<T> shouldBe(T shouldBe) {
        Matchable<T> tMatchable = new Matchable<T>(val, shouldBe, context + ".shouldBe(" + shouldBe + ")", true);
        tMatchable.match();
        return tMatchable;
    }

    public Matchable<T> shouldNotBe(T shouldNotBe) {
        Matchable<T> tMatchable = new Matchable<T>(val, shouldNotBe, context + ".shouldNotBe(" + shouldNotBe + ")", false);
        tMatchable.match();
        return tMatchable;
    }
}
