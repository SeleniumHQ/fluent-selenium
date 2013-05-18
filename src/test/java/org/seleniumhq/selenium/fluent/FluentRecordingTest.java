package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.seleniumhq.selenium.fluent.recording.OnFluentSomething;
import org.seleniumhq.selenium.fluent.recording.OnFluentWebDriver;
import org.seleniumhq.selenium.fluent.recording.ShouldTimeOut;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FluentRecordingTest {
    
    @Test
    public void fluent_exception_should_be_decorated_with_retries_and_duration_information() {

        List<OnFluentSomething> onFluentSomethings = new ArrayList<OnFluentSomething>();
        onFluentSomethings.add(new OnFluentWebDriver() {
            @Override
            public Object doItForReal(FluentWebDriver fwd) {
                throw new FluentExecutionStopped("ha ha");
            }
        });

        FluentWebDriverExecutor fluentWebDriverExecutor = new RetryingFluentWebDriverExecutor(51, new ShouldTimeOut(Period.millis(300)));

        FluentRecording fr = new FluentRecording(onFluentSomethings, fluentWebDriverExecutor);
        FluentWebDriver fwd = mock(FluentWebDriver.class);
        verifyNoMoreInteractions(fwd);

        try {
            fr.playback(fwd);
        } catch (FluentExecutionStopped e) {
            String message = e.getMessage();
            String[] parts = message.split(" ");
            message = message.replaceAll("\\d", "N");
            assertThat(message, equalTo("ha ha; N retries; NNN ms duration"));
            assertThat(Integer.parseInt(parts[2]), greaterThan(5)); // in lieu of 'between' matcher
            assertThat(Integer.parseInt(parts[2]), lessThan(8));
            assertThat(Integer.parseInt(parts[4]), greaterThan(300));
            assertThat(Integer.parseInt(parts[4]), lessThan(400));
        }

    }

    @Test
    public void assertion_errors_too_can_be_subject_to_retries() {

        List<OnFluentSomething> onFluentSomethings = new ArrayList<OnFluentSomething>();
        onFluentSomethings.add(new OnFluentWebDriver() {
            @Override
            public Object doItForReal(FluentWebDriver fwd) {
                throw new AssertionError("ha ha");
            }
        });

        FluentWebDriverExecutor fluentWebDriverExecutor = new RetryingFluentWebDriverExecutor(51, new ShouldTimeOut(Period.millis(300)));

        FluentRecording fr = new FluentRecording(onFluentSomethings, fluentWebDriverExecutor);
        FluentWebDriver fwd = mock(FluentWebDriver.class);
        verifyNoMoreInteractions(fwd);

        long start = System.currentTimeMillis();
        try {
            fr.playback(fwd);
        } catch (AssertionError e) {            
            assertThat(System.currentTimeMillis() - start, greaterThan(300L));
        }

    }

    @Test
    public void default_is_not_to_retry() {

        List<OnFluentSomething> onFluentSomethings = new ArrayList<OnFluentSomething>();
        onFluentSomethings.add(new OnFluentWebDriver() {
            @Override
            public Object doItForReal(FluentWebDriver fwd) {
                throw new FluentExecutionStopped("ha ha");
            }
        });

        FluentWebDriverExecutor fluentWebDriverExecutor = new FluentWebDriverExecutor.Default();

        FluentRecording fr = new FluentRecording(onFluentSomethings, fluentWebDriverExecutor);
        FluentWebDriver fwd = mock(FluentWebDriver.class);
        verifyNoMoreInteractions(fwd);

        long start = System.currentTimeMillis();
        try {
            fr.playback(fwd);
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("ha ha"));
            assertThat(System.currentTimeMillis() - start, lessThan(2L));
        }

    }
}
