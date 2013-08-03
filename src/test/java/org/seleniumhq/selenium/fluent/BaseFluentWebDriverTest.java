package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class BaseFluentWebDriverTest {

    private WebDriver wd = mock(WebDriver.class);
    private Internal.BaseFluentWebDriver fc;

    @Before
    public void setup() {
        fc = new Internal.BaseFluentWebDriver(wd, Context.singular(null, "dummy"), new Monitor.NULL(), false) {

            @Override
            protected FluentWebElements makeFluentWebElements(List<FluentWebElement> results, Context context, Monitor monitor1) {
                return null;
            }

            @Override
            protected WebElement findIt(By by, Context ctx) {
                return null;
            }

            @Override
            protected List<WebElement> findThem(By by, Context ctx) {
                return null;
            }

            public Internal.BaseFluentWebDriver within(Period p) {
                return null;
            }

            public FluentWebDriver without(Period p) {
                return null;
            }

            @Override
            protected WebElement actualFindIt(By by, Context ctx) {
                return null;
            }

            @Override
            protected List<WebElement> actualFindThem(By by, Context ctx) {
                return null;
            }

        };

    }

    @Test
    public void assertionError_should_be_wrapped_in_context_exception() {

        try {
            Context dummy_context = Context.singular(null, "dummy");
            fc.decorateExecution(new Execution() {
                public Void execute() {
                    throw new AssertionError("Oops");
                }
            }, dummy_context);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
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
            }, Context.singular(null, "dummy"));
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.dummy()"));
            assertThat(e.getCause().getMessage(), equalTo("Oops"));
        }

    }

    @Test
    public void staleElementException_should_be_wrapped_in_context_exception() {

        try {
            fc.decorateExecution(new Execution() {
                public Void execute() {
                    throw new StaleElementReferenceException("Oops");
                }
            }, Context.singular(null, "dummy"));
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseOfStaleElement e) {
            assertThat(e.getMessage(), equalTo("StaleElementReferenceException during invocation of: ?.dummy()"));
            assertThat(e.getCause().getMessage(), startsWith("Oops\nFor documentation"));
        }

    }

    @Test
    public void unsupportedOperationException_should_not_be_wrapped() {

        try {
            fc.decorateExecution(new Execution() {
                public Void execute() {
                    throw new UnsupportedOperationException("Oops");
                }
            }, Context.singular(null, "dummy"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("Oops"));
        }
    }
}
