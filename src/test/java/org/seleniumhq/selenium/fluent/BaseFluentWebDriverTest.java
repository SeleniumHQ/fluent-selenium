package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class BaseFluentWebDriverTest {

    private WebDriver wd = mock(WebDriver.class);
    private BaseFluentWebDriver fc;

    @Before
    public void setup() {
        fc = new BaseFluentWebDriver(wd, BaseFluentWebDriver.Context.singular(null, "dummy")) {

            @Override
            protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context) {
                return null;
            }

            @Override
            protected WebElement findIt(By by) {
                return null;
            }

            @Override
            protected List<WebElement> findThem(By by) {
                return null;
            }

            @Override
            public BaseFluentWebDriver within(Period p) {
                return null;
            }
        };

    }

    @Test
    public void assertionError_should_be_wrapped_in_context_exception() {

        try {
            BaseFluentWebDriver.Context dummy_context = BaseFluentWebDriver.Context.singular(null, "dummy");
            fc.decorateExecution(new Execution() {
                public Void execute() {
                    throw new AssertionError("Oops");
                }
            }, dummy_context);
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy()"));
            assertThat(e.getCause().getMessage(), equalTo("Oops"));
        }

    }

    @Test
    public void runtimeException_should_be_wrapped_in_context_exception() {

        try {
            fc.decorateExecution(new Execution() {
                public Void execute() {
                    throw new RuntimeException("Oops");
                }
            }, BaseFluentWebDriver.Context.singular(null, "dummy"));
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.dummy()"));
            assertThat(e.getCause().getMessage(), equalTo("Oops"));
        }

    }

    @Test
    public void unsupportedOperationException_should_not_be_wrapped() {

        try {
            fc.decorateExecution(new Execution() {
                public Void execute() {
                    throw new UnsupportedOperationException("Oops");
                }
            }, BaseFluentWebDriver.Context.singular(null, "dummy"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("Oops"));
        }
    }
}
