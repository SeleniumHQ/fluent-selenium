package org.seleniumhq.selenium.fluent;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.By.tagName;

public class BooleanInsteadOfNoSuchElementExceptionTest {

    int count = 0;

    private class ExceptionCounter extends Monitor.NULL {
        @Override
        public RuntimeException exceptionDuringExecution(RuntimeException ex) {
            count++;
            return super.exceptionDuringExecution(ex);
        }
    }

    @Test
    public void referenceException() {

        WebDriver wd = mock(WebDriver.class);
        when(wd.findElement(tagName("button"))).thenThrow(new NoSuchElementException("boo"));

        FluentWebDriver fwd = new FluentWebDriver(wd, new ExceptionCounter());

        try {
            fwd.button();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getCause(), instanceOf(NoSuchElementException.class));
            Assert.assertThat(count, IsEqual.equalTo(1));
        }

    }

    @Test
    public void elementMissingScenarios() {

        WebDriver wd = mock(WebDriver.class);

        FluentWebDriver fwd = new FluentWebDriver(wd, new ExceptionCounter());

        when(wd.findElement(tagName("button"))).thenThrow(new NoSuchElementException("boo"));
        assertThat(fwd.hasMissing().button(), equalTo(true));
        assertThat(fwd.has().button(), equalTo(false));
        Assert.assertThat(count, IsEqual.equalTo(0));

    }

    @Test
    public void elementPresentScenarios() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);

        FluentWebDriver fwd = new FluentWebDriver(wd, new ExceptionCounter());

        when(wd.findElement(tagName("button"))).thenReturn(we);
        assertThat(fwd.hasMissing().button(), equalTo(false));
        assertThat(fwd.has().button(), equalTo(true));
        Assert.assertThat(count, IsEqual.equalTo(0));

    }

    @Test
    public void elementMissingScenarios2() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);

        FluentWebDriver fwd = new FluentWebDriver(wd, new ExceptionCounter());

        when(wd.findElement(tagName("div"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(tagName("button"))).thenThrow(new NoSuchElementException("boo"));

        assertThat(fwd.div().hasMissing().button(), equalTo(true));
        assertThat(fwd.div().has().button(), equalTo(false));
        Assert.assertThat(count, IsEqual.equalTo(0));

    }

    @Test
    public void elementPresentScenarios2() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        FluentWebDriver fwd = new FluentWebDriver(wd, new ExceptionCounter());

        when(wd.findElement(tagName("div"))).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(tagName("button"))).thenReturn(we2);
        assertThat(fwd.div().hasMissing().button(), equalTo(false));
        assertThat(fwd.div().has().button(), equalTo(true));
        Assert.assertThat(count, IsEqual.equalTo(0));

    }

    @Test
    public void testAllTheMethods() throws IllegalAccessException, InvocationTargetException {
        WebDriver wd = mock(WebDriver.class);
        Monitor m = new Monitor.NULL();
        Context c = Context.singular(null, "y");
        By byID = By.id("foo");

        FluentWebDriver.BooleanResultsAdapter bwa = new FluentWebDriver.BooleanResultsAdapter(wd, m, c);

        int count = 0;

        Method[] methods = FluentWebDriver.BooleanResultsAdapter.class.getDeclaredMethods();
        when(wd.findElement(byID)).thenThrow(new NoSuchElementException("boo"));
        for (Method method : methods) {
            String name = method.getName();
            if (method.getReturnType().equals(Boolean.TYPE) && !name.equals("returnBool")) {
                if (method.getParameterTypes().length == 0) {
                    when(wd.findElement(tagName(name.replace("link","a")))).thenThrow(new NoSuchElementException("boo"));
                    assertFalse((Boolean) method.invoke(bwa));
                } else {
                    assertFalse((Boolean) method.invoke(bwa, byID));
                }
                count++;
            }
        }
        assertThat(count, equalTo(74));
    }

}
