package org.seleniumhq.selenium.fluent;

public class BaseTest {

    protected static final ThreadLocal<Class<? extends Throwable>> FAIL_ON_NEXT = new ThreadLocal<Class<? extends Throwable>>();

    protected CharSequence cs(String string) {
        return string;
    }

}
