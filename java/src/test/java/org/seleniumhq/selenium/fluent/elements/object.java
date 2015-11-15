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

public class object extends BaseTest {

    @Test
    public void object_functionality() {

        setupExpectationsSingle("object");

        FluentWebElements fe = fwd.object()
                .object(By.xpath("@foo = 'bar'"))
                .object(By.cssSelector("baz"))
                .objects();

        assertThat(fe, notNullValue());
        verificationsSingle("object");
    }

    @Test
    public void adresss_functionality() {

        setupExpectationsMultiple("object");

        FluentWebElements fe = fwd.object()
                .objects(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("object");

    }

    @Test
    public void object_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.object(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.object(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been object but was boo"));
        }

    }


}
