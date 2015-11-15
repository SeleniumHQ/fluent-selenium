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

public class p extends BaseTest {

    @Test
    public void p_functionality() {

        setupExpectationsSingle("p");

        FluentWebElements fe = fwd.p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fe, notNullValue());
        verificationsSingle("p");
    }
    @Test
    public void p_case_functionality() {

        setupExpectationsSingle("p", "P");

        FluentWebElements fe = fwd.p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fe, notNullValue());
        verificationsSingle("p");
    }

    @Test
    public void ps_case_functionality() {

        setupExpectationsMultiple("p", "P");

        FluentWebElements fe = fwd.p()
                .ps(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("p");

    }

    @Test
    public void p_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.p(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.p(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been p but was boo"));
        }

    }


}
