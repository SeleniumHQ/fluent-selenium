package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class body extends BaseTest {

    @Test
    public void body_functionality() {

        setupExpectationsSingle("body");

        FluentWebElement fe = fwd.body();

        assertThat(fe, notNullValue());

        verify(wd).findElement(By.tagName("body"));
        verify(we).getTagName();
        verifyNoMoreInteractions(wd, we, we2, we3, we4, we5);

    }


}
