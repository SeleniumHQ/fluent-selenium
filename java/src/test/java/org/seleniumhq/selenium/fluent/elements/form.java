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

public class form extends BaseTest {

    @Test
    public void form_functionality() {

        setupExpectationsSingle("form");

        FluentWebElements fe = fwd.form()
                .form(By.xpath("@foo = 'bar'"))
                .form(By.cssSelector("baz"))
                .forms();

        assertThat(fe, notNullValue());
        verificationsSingle("form");
    }

    @Test
    public void forms_functionality() {

        setupExpectationsMultiple("form");

        FluentWebElements fe = fwd.form()
                .forms(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("form");

    }

    @Test
    public void form_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.form(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.form(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been form but was boo"));
        }

    }


}
