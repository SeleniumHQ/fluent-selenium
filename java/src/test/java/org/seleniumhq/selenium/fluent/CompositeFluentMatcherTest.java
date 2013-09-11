package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeFluentMatcherTest {
    
    @Test 
    public void permutations_of_both() {

        // both true

        WebElement elem = mock(WebElement.class);
        FluentMatcher one = mock(FluentMatcher.class);
        when(one.matches(elem)).thenReturn(true);
        FluentMatcher two = mock(FluentMatcher.class);
        when(two.matches(elem)).thenReturn(true);
        boolean result = CompositeFluentMatcher.both(one, two).matches(elem);
        assertThat(result, is(true));

        // one true, one false

        when(one.matches(elem)).thenReturn(true);
        when(two.matches(elem)).thenReturn(false);
        result = CompositeFluentMatcher.both(one, two).matches(elem);
        assertThat(result, is(false));

        // other permutation

        when(one.matches(elem)).thenReturn(false);
        when(two.matches(elem)).thenReturn(true);

        result = CompositeFluentMatcher.both(one, two).matches(elem);
        assertThat(result, is(false));

        // neither true

        when(one.matches(elem)).thenReturn(false);
        when(two.matches(elem)).thenReturn(false);
        result = CompositeFluentMatcher.both(one, two).matches(elem);
        assertThat(result, is(false));
    }

    @Test
    public void permutations_of_either() {

        // one true one false

        WebElement elem = mock(WebElement.class);
        FluentMatcher one = mock(FluentMatcher.class);
        when(one.matches(elem)).thenReturn(true);
        FluentMatcher two = mock(FluentMatcher.class);
        when(two.matches(elem)).thenReturn(false);
        boolean result = CompositeFluentMatcher.either(one, two).matches(elem);
        assertThat(result, is(true));

        // other permutation

        when(one.matches(elem)).thenReturn(true);
        when(two.matches(elem)).thenReturn(false);

        result = CompositeFluentMatcher.either(one, two).matches(elem);
        assertThat(result, is(true));

        // both

        when(one.matches(elem)).thenReturn(true);
        when(two.matches(elem)).thenReturn(true);

        result = CompositeFluentMatcher.either(one, two).matches(elem);
        assertThat(result, is(true));

        // neither

        when(one.matches(elem)).thenReturn(false);
        when(two.matches(elem)).thenReturn(false);

        result = CompositeFluentMatcher.either(one, two).matches(elem);
        assertThat(result, is(false));
    }

    @Test
    public void a_permutation_of_any() {

        // one true, two false

        WebElement elem = mock(WebElement.class);
        FluentMatcher one = mock(FluentMatcher.class);
        when(one.matches(elem)).thenReturn(false);
        FluentMatcher two = mock(FluentMatcher.class);
        when(two.matches(elem)).thenReturn(false);
        FluentMatcher three = mock(FluentMatcher.class);
        when(two.matches(elem)).thenReturn(true);
        boolean result = CompositeFluentMatcher.any(one, two, three).matches(elem);
        assertThat(result, is(true));

    }

    @Test
    public void two_permutations_of_all() {

        // one true, two false

        WebElement elem = mock(WebElement.class);
        FluentMatcher one = mock(FluentMatcher.class);
        when(one.matches(elem)).thenReturn(false);
        FluentMatcher two = mock(FluentMatcher.class);
        when(two.matches(elem)).thenReturn(false);
        FluentMatcher three = mock(FluentMatcher.class);
        when(three.matches(elem)).thenReturn(true);
        boolean result = CompositeFluentMatcher.all(one, two, three).matches(elem);
        assertThat(result, is(false));

        // one three true

        when(one.matches(elem)).thenReturn(true);
        when(two.matches(elem)).thenReturn(true);
        when(three.matches(elem)).thenReturn(true);
        result = CompositeFluentMatcher.all(one, two, three).matches(elem);
        assertThat(result, is(true));

    }


}
