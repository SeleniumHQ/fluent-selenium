package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.seleniumhq.selenium.fluent.Period.millis;

public class TestableValueTest {

    @Test
    public void can_be_true() {

        Execution<Boolean> exTrue = mock(Execution.class);
        when(exTrue.execute()).thenReturn(true);

        Context ctx = Context.singular(null, "dummy");
        TestableValue<Boolean> wev = new TestableValue<Boolean>(exTrue, ctx);

        TestableValue<Boolean> foo = wev.shouldBe(true);
        assertEquals(true, foo.value());
        foo = wev.shouldNotBe(false);
        assertEquals(true, foo.value());

    }

    @Test
    public void can_be_false() {

        Execution<Boolean> exFalse = mock(Execution.class);
        when(exFalse.execute()).thenReturn(false);

        Context ctx = Context.singular(null, "dummy");
        TestableValue<Boolean> wev = new TestableValue<Boolean>(exFalse, ctx);

        TestableValue<Boolean> foo = wev.shouldBe(false);
        assertEquals(false, foo.value());
        foo = wev.shouldNotBe(true);
        assertEquals(false, foo.value());


    }

    @Test
    public void within_works() {

        Execution<Boolean> exFalse = mock(Execution.class);
        when(exFalse.execute()).thenReturn(false);
        when(exFalse.execute()).thenReturn(true);

        Context ctx = Context.singular(null, "dummy");
        TestableValue<Boolean> wev = new TestableValue<Boolean>(exFalse, ctx);

        TestableValue<Boolean> foo = wev.within(millis(100)).shouldBe(true);
        assertEquals(true, foo.value());


    }

}
