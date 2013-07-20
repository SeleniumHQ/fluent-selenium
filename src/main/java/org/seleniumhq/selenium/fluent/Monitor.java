package org.seleniumhq.selenium.fluent;

public interface Monitor {

    Timer start(String item);

    public interface Timer {
        void end();
        public class NULL implements Timer {
            public void end() {
            }
        }
    }

    public static class NULL implements Monitor {
        public Timer start(String item) {
            return new Timer.NULL();
        }
    }

}
