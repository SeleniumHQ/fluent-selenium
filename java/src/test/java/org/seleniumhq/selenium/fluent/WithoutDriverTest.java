package org.seleniumhq.selenium.fluent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;
import static org.openqa.selenium.By.id;
import static org.seleniumhq.selenium.fluent.Period.secs;

@RunWith(MockitoJUnitRunner.class)
public class WithoutDriverTest {

    @Mock
    WebDriver webDriver;
    @Mock
    WebElement divElement;
    @Mock
    WebElement spanElement;

    FluentWebDriver fluentWebDriver;
    int exceptionsThrown = 0;

    private class ExceptionCounter extends Monitor.NULL {
        @Override
        public FluentExecutionStopped exceptionDuringExecution(FluentExecutionStopped ex, WebElement webElement) {
            exceptionsThrown++;
            return super.exceptionDuringExecution(ex, webElement);
        }
    }

    @Before
    public void setup() {

        fluentWebDriver = new FluentWebDriver(webDriver, new ExceptionCounter());
        when(divElement.getTagName()).thenReturn("div");
        when(spanElement.getTagName()).thenReturn("span");
    }

    @Test
    public void divIsGoneBeforeWeLookForIt() {
        when(webDriver.findElement(By.tagName("div"))).thenThrow(new NotFoundException("div"));

        fluentWebDriver.without(secs(2)).div();
        Assert.assertThat(exceptionsThrown, equalTo(0));
    }

    @Test
    public void divDisappearsInTime() {
        when(webDriver.findElement(By.tagName("div"))).thenAnswer(new DisappearingElement(divElement, secs(1)));

        fluentWebDriver.without(secs(2)).div();
        Assert.assertThat(exceptionsThrown, equalTo(0));
    }

    @Test
    public void divFailsToDisappearInTime() {
        when(webDriver.findElement(By.tagName("div"))).thenReturn(divElement);

        try {
            fluentWebDriver.without(secs(2)).div();
            fail();
        } catch (FluentExecutionStopped executionStopped) {
            assertThat(executionStopped.getMessage(), equalTo("AssertionError during invocation of: ?.without(secs(2)).div()"));
            exceptionSaysDidntDisappearInTwoSeconds(executionStopped);
            Assert.assertThat(exceptionsThrown, equalTo(1));
        }
    }

    public static void exceptionSaysDidntDisappearInTwoSeconds(FluentExecutionStopped executionStopped) {
        String msg = executionStopped.getCause().getMessage();
        assertThat(msg, startsWith("Element never disappeared after:"));
        String howLong = msg.split(":")[1].trim();
        Assert.assertThat(Integer.parseInt(howLong), greaterThan(1999));
    }

    @Test
    public void spanIsGoneBeforeWeLookForIt() {
        when(webDriver.findElement(By.tagName("span"))).thenThrow(new NotFoundException("span"));

        fluentWebDriver.without(secs(2)).span();
        Assert.assertThat(exceptionsThrown, equalTo(0));
    }

    @Test
    public void bySpanIsGoneBeforeWeLookForIt() {
        By id = id("id");
        when(webDriver.findElement(id)).thenThrow(new NotFoundException("span"));
        fluentWebDriver.without(secs(2)).span(id);
        Assert.assertThat(exceptionsThrown, equalTo(0));
    }

    @Test
    public void spanDisappearsInTime() {
        when(webDriver.findElement(By.tagName("span"))).thenAnswer(new DisappearingElement(spanElement, secs(1)));

        fluentWebDriver.without(secs(2)).span();
        Assert.assertThat(exceptionsThrown, equalTo(0));
    }

    @Test
    public void bySpanDisappearsInTime() {
        By id = By.id("id");
        when(webDriver.findElement(id)).thenAnswer(new DisappearingElement(spanElement, secs(1)));

        fluentWebDriver.without(secs(2)).span(id);
        Assert.assertThat(exceptionsThrown, equalTo(0));
    }

    @Test
    public void spanFailsToDisappearInTime() {
        when(webDriver.findElement(By.tagName("span"))).thenReturn(spanElement);

        try {
            fluentWebDriver.without(secs(2)).span();
            fail();
        } catch (FluentExecutionStopped executionStopped) {
            assertThat(executionStopped.getMessage(), equalTo("AssertionError during invocation of: ?.without(secs(2)).span()"));
            exceptionSaysDidntDisappearInTwoSeconds(executionStopped);
            Assert.assertThat(exceptionsThrown, equalTo(1));
        }
    }

    private static class DisappearingElement implements Answer<WebElement> {

        private final WebElement webElement;
        private final Period duration;
        private Long startTime;

        public DisappearingElement(WebElement webElement, Period duration) {
            this.webElement = webElement;
            this.duration = duration;
        }

        public WebElement answer(InvocationOnMock invocation) throws Throwable {
            long now = System.currentTimeMillis();
            boolean durationHasElapsed = duration.getEndMillis(startedAt()) <= now;
            if (durationHasElapsed) {
                throw new NotFoundException("");
            }
            return webElement;
        }

        private Long startedAt() {
            if (startTime == null) {
                startTime = System.currentTimeMillis();
            }
            return startTime;
        }
    }
}
