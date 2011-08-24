package org.seleniumhq.selenium.fluent;

public class FluentExecutionStopped extends RuntimeException {
    public FluentExecutionStopped(String message, Throwable cause) {
        super(message, cause);
    }
}
