package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.internal.Context;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FluentWebElementsTest {

    @Test
    public void getShouldCommunicateOutOfBounds() {
        FluentWebElements fwe = setUp();
        try {
            fwe.get(3);
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("Element index 3 not in collection of 2 elements"));
            assertThat(e.getCause(), instanceOf(IndexOutOfBoundsException.class));
        }
    }

    private FluentWebElements setUp() {
        WebDriver wd = mock(WebDriver.class);

        List<FluentWebElement> elems = asList(mock(FluentWebElement.class), mock(FluentWebElement.class));
        Context ctx = Context.singular(null, "x");
        return new FluentWebElements(wd, elems, ctx, new Monitor.NULL(), false);
    }

    @Test
    public void subListShouldCommunicateOutOfBounds() {
        FluentWebElements fwe = setUp();
        try {
            fwe.subList(3, 4);
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("Element index 3, or element index 4 not in collection of 2 elements"));
            assertThat(e.getCause(), instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test
    public void listIteratorShouldCommunicateOutOfBounds() {
        FluentWebElements fwe = setUp();
        try {
            fwe.listIterator(4);
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("Element index 4 not in collection of 2 elements"));
            assertThat(e.getCause(), instanceOf(IndexOutOfBoundsException.class));
        }
    }

}
