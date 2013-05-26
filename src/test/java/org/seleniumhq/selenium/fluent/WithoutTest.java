package org.seleniumhq.selenium.fluent;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;
import static org.seleniumhq.selenium.fluent.Period.secs;

@RunWith(MockitoJUnitRunner.class)
public class WithoutTest {

    @Mock
    WebDriver webDriver;
    @Mock
    WebElement divElement;
    @Mock
    WebElement spanElement;

    FluentWebDriver fluentWebDriver;

    @Before
    public void setup() {
        fluentWebDriver = new FluentWebDriverImpl(webDriver);
        when(divElement.getTagName()).thenReturn("div");
        when(spanElement.getTagName()).thenReturn("span");
    }

    @Test
    public void divIsGoneBeforeWeLookForIt() {
        when(webDriver.findElement(By.tagName("div"))).thenThrow(new ElementNotFoundException("div", null, null));

        fluentWebDriver.without(secs(2)).div();
    }

    @Test
    public void divDisappearsInTime() {
        when(webDriver.findElement(By.tagName("div"))).thenAnswer(new DisappearingElement(divElement, secs(1)));

        fluentWebDriver.without(secs(2)).div();
    }

    @Test
    public void divFailsToDisappearInTime() {
        when(webDriver.findElement(By.tagName("div"))).thenReturn(divElement);

        try {
            fluentWebDriver.without(secs(2)).div();
            fail();
        } catch (FluentExecutionStopped executionStopped) {
            assertThat(executionStopped.getMessage(), equalTo("AssertionError during invocation of: ?.without(secs(2)).div()"));
            assertThat(executionStopped.getCause().getMessage(), equalTo("Element never disappeared"));
        }
    }


    @Test
    public void spanIsGoneBeforeWeLookForIt() {
        when(webDriver.findElement(By.tagName("span"))).thenThrow(new ElementNotFoundException("span", null, null));

        fluentWebDriver.without(secs(2)).span();
    }

    @Test
    public void spanDisappearsInTime() {
        when(webDriver.findElement(By.tagName("span"))).thenAnswer(new DisappearingElement(spanElement, secs(1)));

        fluentWebDriver.without(secs(2)).span();
    }

    @Test
    public void spanFailsToDisappearInTime() {
        when(webDriver.findElement(By.tagName("span"))).thenReturn(spanElement);

        try {
            fluentWebDriver.without(secs(2)).span();
            fail();
        } catch (FluentExecutionStopped executionStopped) {
            assertThat(executionStopped.getMessage(), equalTo("AssertionError during invocation of: ?.without(secs(2)).span()"));
            assertThat(executionStopped.getCause().getMessage(), equalTo("Element never disappeared"));
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
                throw new ElementNotFoundException(null, null, null);
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
