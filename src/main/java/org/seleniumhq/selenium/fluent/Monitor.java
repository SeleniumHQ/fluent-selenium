package org.seleniumhq.selenium.fluent;

public interface Monitor {

    Timer start(String item);

    RuntimeException exceptionDuringExecution(RuntimeException ex);

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

        public RuntimeException exceptionDuringExecution(RuntimeException ex) {
            return ex;
        }
    }

}
