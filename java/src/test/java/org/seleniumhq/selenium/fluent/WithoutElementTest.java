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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;
import static org.seleniumhq.selenium.fluent.Period.secs;

@RunWith(MockitoJUnitRunner.class)
public class WithoutElementTest {

    @Mock
    WebDriver wwebDriver;
    @Mock
    WebElement rootDiv;
    @Mock
    WebElement divElement;
    @Mock
    WebElement spanElement;

    FluentWebElement fluentWebElement;

    int count = 0;

    private class ExceptionCounter extends Monitor.NULL {
        @Override
        public FluentExecutionStopped exceptionDuringExecution(FluentExecutionStopped ex, WebElement webElement) {
            count++;
            return super.exceptionDuringExecution(ex, webElement);
        }
    }

    @Before
    public void setup() {
        when(wwebDriver.findElement(By.tagName("div"))).thenReturn(rootDiv);
        when(rootDiv.getTagName()).thenReturn("div");
        fluentWebElement = new FluentWebDriver(wwebDriver, new ExceptionCounter()).div();
        when(divElement.getTagName()).thenReturn("div");
        when(spanElement.getTagName()).thenReturn("span");
    }

    @Test
    public void divIsGoneBeforeWeLookForIt() {
        when(rootDiv.findElement(By.tagName("div"))).thenThrow(new NotFoundException("div"));

        fluentWebElement.without(secs(2)).div();
        Assert.assertThat(count, equalTo(0));
    }

    @Test
    public void divDisappearsInTime() {
        when(rootDiv.findElement(By.tagName("div"))).thenAnswer(new DisappearingElement(divElement, secs(1)));

        fluentWebElement.without(secs(2)).div();
        Assert.assertThat(count, equalTo(0));
    }

    @Test
    public void divFailsToDisappearInTime() {
        when(rootDiv.findElement(By.tagName("div"))).thenReturn(divElement);

        try {
            fluentWebElement.without(secs(2)).div();
            fail();
        } catch (FluentExecutionStopped executionStopped) {
            assertThat(executionStopped.getMessage(), equalTo("AssertionError during invocation of: ?.div().without(secs(2)).div()"));
            assertThat(executionStopped.getCause().getMessage(), equalTo("Element never disappeared"));
            Assert.assertThat(count, equalTo(1));
        }
    }

    @Test
    public void spanIsGoneBeforeWeLookForIt() {
        when(rootDiv.findElement(By.tagName("span"))).thenThrow(new NotFoundException("span"));

        fluentWebElement.without(secs(2)).span();
        Assert.assertThat(count, equalTo(0));
    }

    @Test
    public void spanDisappearsInTime() {
        when(rootDiv.findElement(By.tagName("span"))).thenAnswer(new DisappearingElement(spanElement, secs(1)));

        fluentWebElement.without(secs(2)).span();
        Assert.assertThat(count, equalTo(0));
    }

    @Test
    public void spanFailsToDisappearInTime() {
        when(rootDiv.findElement(By.tagName("span"))).thenReturn(spanElement);

        try {
            fluentWebElement.without(secs(2)).span();
            fail();
        } catch (FluentExecutionStopped executionStopped) {
            assertThat(executionStopped.getMessage(), equalTo("AssertionError during invocation of: ?.div().without(secs(2)).span()"));
            assertThat(executionStopped.getCause().getMessage(), equalTo("Element never disappeared"));
            Assert.assertThat(count, equalTo(1));
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
