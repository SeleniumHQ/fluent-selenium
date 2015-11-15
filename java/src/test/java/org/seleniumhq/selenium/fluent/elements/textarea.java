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

public class textarea extends BaseTest {

    @Test
    public void textarea_functionality() {

        setupExpectationsSingle("textarea");

        FluentWebElements fe = fwd.textarea()
                .textarea(By.xpath("@foo = 'bar'"))
                .textarea(By.cssSelector("baz"))
                .textareas();

        assertThat(fe, notNullValue());
        verificationsSingle("textarea");
    }
    
    @Test
    public void textarea_case_functionality() {

        setupExpectationsSingle("textarea", "TEXTAREA");

        FluentWebElements fe = fwd.textarea()
                .textarea(By.xpath("@foo = 'bar'"))
                .textarea(By.cssSelector("baz"))
                .textareas();

        assertThat(fe, notNullValue());
        verificationsSingle("textarea");
    }

    @Test
    public void textareas_functionality() {

        setupExpectationsMultiple("textarea");

        FluentWebElements fe = fwd.textarea()
                .textareas(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("textarea");

    }
    
    @Test
    public void textareas_case_functionality() {

        setupExpectationsMultiple("textarea", "TEXTAREA");

        FluentWebElements fe = fwd.textarea()
                .textareas(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("textarea");

    }

    @Test
    public void textarea_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.textarea(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.textarea(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been textarea but was boo"));
        }

    }


}
