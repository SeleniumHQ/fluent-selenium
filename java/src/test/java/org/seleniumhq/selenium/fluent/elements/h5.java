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

public class h5 extends BaseTest {

    @Test
    public void h5_functionality() {

        setupExpectationsSingle("h5");

        FluentWebElements fe = fwd.h5()
                .h5(By.xpath("@foo = 'bar'"))
                .h5(By.cssSelector("baz"))
                .h5s();

        assertThat(fe, notNullValue());
        verificationsSingle("h5");
    }

    @Test
    public void h5_case_functionality() {

        setupExpectationsSingle("h5","h5");

        FluentWebElements fe = fwd.h5()
                .h5(By.xpath("@foo = 'bar'"))
                .h5(By.cssSelector("baz"))
                .h5s();

        assertThat(fe, notNullValue());
        verificationsSingle("h5");
    }

    @Test
    public void h5s_functionality() {

        setupExpectationsMultiple("h5");

        FluentWebElements fe = fwd.h5()
                .h5s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h5");

    }

    @Test
    public void h5s_case_functionality() {

        setupExpectationsMultiple("h5","h5");

        FluentWebElements fe = fwd.h5()
                .h5s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h5");

    }

    @Test
    public void h5_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.h5(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h5(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been h5 but was boo"));
        }

    }


}
