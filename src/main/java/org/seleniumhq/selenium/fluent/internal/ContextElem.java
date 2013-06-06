package org.seleniumhq.selenium.fluent.internal;

import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.seleniumhq.selenium.fluent.Period;

public class ContextElem {
    private final String tagName;
    private final By by;
    private final Object arg;

    public ContextElem(String tagName, By by, Object arg) {
        this.tagName = tagName;
        this.by = by;
        this.arg = arg;
    }

    @Override
    public String toString() {
        StringBuilder sb =
                new StringBuilder(".")
                        .append(tagName);
        if (by == null && arg == null) {
            return sb.append("()").toString();
        }
        if (by != null) {
            return sb.append("(").append(by).append(")").toString();

        }
        String quote = "'";
        if (arg instanceof Number || arg instanceof Period || arg instanceof FluentMatcher) {
            quote = "";
        }
        return sb.append("(").append(quote).append(arg).append(quote).append(")").toString();
    }
}
