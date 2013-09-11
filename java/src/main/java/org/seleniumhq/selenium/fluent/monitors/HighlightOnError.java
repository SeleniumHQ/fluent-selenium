package org.seleniumhq.selenium.fluent.monitors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.Monitor;

public class HighlightOnError extends Monitor.NULL {

    protected final WebDriver driver;

    public HighlightOnError(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public FluentExecutionStopped exceptionDuringExecution(FluentExecutionStopped ex, WebElement webElement) {
        if (webElement != null) {
            try {
                executeScript(webElement);
            } catch (WebDriverException e) {
                // possible, but distracting
            }
        }
        return ex;
    }

    protected void executeScript(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript(highlightOperation(), webElement, highlightValue());
    }

    protected String highlightValue() {
        return "border: 2px dashed red;";
    }

    protected String highlightOperation() {
        return "arguments[0].setAttribute('style', arguments[1]);";
    }
}
