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
        fc = new BaseFluentWebDriver(wd, "DUMMY_CONTEXT") {

            @Override
            protected <T> T getFluentWebElement(WebElement result, String context, Class<T> webElementClass) {
                return null;
            }

            @Override
            protected FluentWebElements getFluentWebElements(List<WebElement> results, String context) {
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
        };

    }

    @Test
    public void assertionError_should_be_wrapped_in_context_exception() {

        try {
            fc.execute(new BaseFluentWebDriver.Execution() {
                public Void execute() {
                    throw new AssertionError("Oops");
                }
            }, "DUMMY_CONTEXT");
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: DUMMY_CONTEXT"));
            assertThat(e.getCause().getMessage(), equalTo("Oops"));
        }

    }

    @Test
    public void runtimeException_should_be_wrapped_in_context_exception() {

        try {
            fc.execute(new BaseFluentWebDriver.Execution() {
                public Void execute() {
                    throw new RuntimeException("Oops");
                }
            }, "DUMMY_CONTEXT");
            fail("should have barfed");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: DUMMY_CONTEXT"));
            assertThat(e.getCause().getMessage(), equalTo("Oops"));
        }

    }

    @Test
    public void unsupportedOperationException_should_not_be_wrapped() {

        try {
            fc.execute(new BaseFluentWebDriver.Execution() {
                public Void execute() {
                    throw new UnsupportedOperationException("Oops");
                }
            }, "DUMMY_CONTEXT");
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("Oops"));
        }
    }
}
