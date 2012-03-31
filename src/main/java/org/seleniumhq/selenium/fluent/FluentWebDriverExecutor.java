package org.seleniumhq.selenium.fluent;

public interface FluentWebDriverExecutor {

    Object playback(FluentWebDriver driver, FluentWebDriverExecution execution);

    public static interface FluentWebDriverExecution {
        /**
         * Don't catch exceptions in implementations of this.
         * @param driver the FluentWebDriver to operate on
         * @return The resulting FluentWebElement, FluentWebElements or similar.
         */
        Object execute(FluentWebDriver driver);
    }

    public static class Default implements FluentWebDriverExecutor {
        public Object playback(FluentWebDriver driver, FluentWebDriverExecution execution) {
            return execution.execute(driver);
        }
    }
}
