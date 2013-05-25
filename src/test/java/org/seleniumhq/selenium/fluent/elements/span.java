package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest2;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class span extends BaseTest2 {

    @Test
    public void span_functionality() {

        setupExpecations("span");

        FluentWebElements fe = fwd.span()
                .span(By.xpath("@foo = 'bar'"))
                .span(By.cssSelector("baz"))
                .spans();

        assertThat(fe, notNullValue());
        verifications("span");
    }

    @Test
    public void spans_functionality() {

        setupExpecations2("span");

        FluentWebElements fe = fwd.span()
                .spans(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("span");

    }

    @Test
    public void span_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.span(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.span(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been span but was boo"));
        }

    }


}
