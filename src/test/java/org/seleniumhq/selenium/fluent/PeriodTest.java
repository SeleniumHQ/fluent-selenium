package org.seleniumhq.selenium.fluent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PeriodTest {

    @Test
    public void testMillis() {
        Period period = Period.millis(123);
        assertThat(period.timeUnit(), equalTo(TimeUnit.MILLISECONDS));
        assertThat(period.howLong(), equalTo(123));
        assertThat(period.toString(), equalTo("millis(123)"));
    }

    @Test
    public void testSecs() {
        Period period = Period.secs(456);
        assertThat(period.timeUnit(), equalTo(TimeUnit.SECONDS));
        assertThat(period.howLong(), equalTo(456));
        assertThat(period.toString(), equalTo("secs(456)"));
    }

    @Test
    public void testMins() {
        Period period = Period.mins(789);
        assertThat(period.timeUnit(), equalTo(TimeUnit.MINUTES));
        assertThat(period.howLong(), equalTo(789));
        assertThat(period.toString(), equalTo("mins(789)"));

    }
}
