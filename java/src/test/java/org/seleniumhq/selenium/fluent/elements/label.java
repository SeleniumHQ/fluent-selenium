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

public class label extends BaseTest {

    @Test
    public void label_functionality() {

        setupExpectationsSingle("label");

        FluentWebElements fe = fwd.label()
                .label(By.xpath("@foo = 'bar'"))
                .label(By.cssSelector("baz"))
                .labels();

        assertThat(fe, notNullValue());
        verificationsSingle("label");
    }

    @Test
    public void labels_functionality() {

        setupExpectationsMultiple("label");

        FluentWebElements fe = fwd.label()
                .labels(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("label");

    }

    @Test
    public void label_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.label(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.label(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been label but was boo"));
        }

    }


}
