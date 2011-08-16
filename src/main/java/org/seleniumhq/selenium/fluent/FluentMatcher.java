package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.WebElement;

public interface FluentMatcher {
    boolean matches(WebElement webElement);
}
