package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.seleniumhq.selenium.fluent.recording.OnFluentSomething;
import org.seleniumhq.selenium.fluent.recording.OnFluentWebDriver;
import org.seleniumhq.selenium.fluent.recording.ShouldTimeOut;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FluentRecordingTest {
    
    @Test
    public void foo() {

        List<OnFluentSomething> onFluentSomethings = new ArrayList<OnFluentSomething>();
        onFluentSomethings.add(new OnFluentWebDriver() {
            @Override
            public Object doItForReal(FluentWebDriver fwd) {
                throw new FluentExecutionStopped("ha ha");
            }
        });

        FluentRecording fr = new FluentRecording(onFluentSomethings, 51, new ShouldTimeOut(Period.millis(300)));
        FluentWebDriver fwd = mock(FluentWebDriver.class);
        verifyNoMoreInteractions(fwd);

        try {
            fr.playback(fwd);
        } catch (FluentExecutionStopped e) {
            String message = e.getMessage();
            message = message.replaceAll("\\d", "N");
            assertThat(message, equalTo("N retries over NNN millis; ha ha"));
        }

    }
}
