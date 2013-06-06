package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.TestableString;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class title extends BaseTest {

    @Test
    public void title_functionality() {

        wd = mock(WebDriver.class);
        fwd = new FluentWebDriver(wd);

        TestableString ts = fwd.title();

        assertThat(ts, notNullValue());
        verifyNoMoreInteractions(wd);
        // invocation of getTitle is lazy.
        when(wd.getTitle()).thenReturn("foo");
        String actual = ts.toString();
        assertThat(actual, equalTo("foo"));
        verify(wd).getTitle();
        verifyNoMoreInteractions(wd);
    }



}
