package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class figure extends BaseTest {

    @Test
    public void figure_functionality() {

        setupExpectationsSingle("figure");

        FluentWebElements fe = fwd.figure()
                .figure(By.xpath("@foo = 'bar'"))
                .figure(By.cssSelector("baz"))
                .figures();

        assertThat(fe, notNullValue());
        verificationsSingle("figure");
    }

    @Test
    public void figures_functionality() {

        setupExpectationsMultiple("figure");

        FluentWebElements fe = fwd.figure()
                .figures(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("figure");

    }

    @Test
    public void figure_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.figure(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.figure(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been figure but was boo"));
        }

    }


}
