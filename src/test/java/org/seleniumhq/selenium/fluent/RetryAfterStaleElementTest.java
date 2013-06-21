package org.seleniumhq.selenium.fluent;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class RetryAfterStaleElementTest {

    @Test
    public void works_after_a_couple_of_attempts() {

        final int tries[] = new int[1];

        RetryAfterStaleElement rase = new RetryAfterStaleElement() {
            @Override
            public void toRetry() {
                if (tries[0] < 2) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    tries[0]++;
                    throw new FluentExecutionStopped.BecauseOfStaleElement("ha", new RuntimeException());
                }
            }
        };

        long start = System.currentTimeMillis();
        rase.stopAfter(secs(8));
        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed > 999);
        assertTrue(elapsed < 1010);

    }

    @Test
    public void keeps_failing_and_therefore_times_out() {

        RetryAfterStaleElement rase = new RetryAfterStaleElement() {
            @Override
            public void toRetry() {
                throw new FluentExecutionStopped.BecauseOfStaleElement("ha", new RuntimeException());
            }
        };

        long start = System.currentTimeMillis();
        try {
            rase.stopAfter(secs(1));
            fail("should have barfed");
        } catch (FluentExecutionStopped.BecauseOfStaleElement e) {
            assertThat(e.getMessage().replace("1001", "1000").replace("1002", "1000"), equalTo("ha; 1000 ms duration"));

        }

        long elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed > 999);
        assertTrue(elapsed < 1010);

    }
}
