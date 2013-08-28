package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.WebElement;

public interface Monitor {

    Timer start(String item);

    FluentExecutionStopped exceptionDuringExecution(FluentExecutionStopped ex, WebElement webElement);

    public interface Timer {
        void end(boolean success);
        public class NULL implements Timer {
            public void end(boolean success) {
            }
        }
    }

    public static class NULL implements Monitor {

        public Timer start(String item) {
            return new Timer.NULL();
        }

        public FluentExecutionStopped exceptionDuringExecution(FluentExecutionStopped ex, WebElement webElement) {
            return ex;
        }
    }

}
