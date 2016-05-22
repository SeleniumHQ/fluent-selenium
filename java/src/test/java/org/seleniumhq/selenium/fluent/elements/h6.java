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

public class h6 extends BaseTest {

    @Test
    public void h6_functionality() {

        setupExpectationsSingle("h6");

        FluentWebElements fe = fwd.h6()
                .h6(By.xpath("@foo = 'bar'"))
                .h6(By.cssSelector("baz"))
                .h6s();

        assertThat(fe, notNullValue());
        verificationsSingle("h6");
    }

    @Test
    public void h6_case_functionality() {

        setupExpectationsSingle("h6","h6");

        FluentWebElements fe = fwd.h6()
                .h6(By.xpath("@foo = 'bar'"))
                .h6(By.cssSelector("baz"))
                .h6s();

        assertThat(fe, notNullValue());
        verificationsSingle("h6");
    }

    @Test
    public void h6s_functionality() {

        setupExpectationsMultiple("h6");

        FluentWebElements fe = fwd.h6()
                .h6s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h6");

    }

    @Test
    public void h6s_case_functionality() {

        setupExpectationsMultiple("h6","h6");

        FluentWebElements fe = fwd.h6()
                .h6s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h6");

    }

    @Test
    public void h6_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.h6(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h6(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been h6 but was boo"));
        }

    }


}