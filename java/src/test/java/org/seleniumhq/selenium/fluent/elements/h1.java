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

public class h1 extends BaseTest {

    @Test
    public void h1_functionality() {

        setupExpectationsSingle("h1");

        FluentWebElements fe = fwd.h1()
                .h1(By.xpath("@foo = 'bar'"))
                .h1(By.cssSelector("baz"))
                .h1s();

        assertThat(fe, notNullValue());
        verificationsSingle("h1");
    }
    
    @Test
    public void h1_case_functionality() {

        setupExpectationsSingle("h1", "H1");

        FluentWebElements fe = fwd.h1()
                .h1(By.xpath("@foo = 'bar'"))
                .h1(By.cssSelector("baz"))
                .h1s();

        assertThat(fe, notNullValue());
        verificationsSingle("h1");
    }

    @Test
    public void h1s_functionality() {

        setupExpectationsMultiple("h1");

        FluentWebElements fe = fwd.h1()
                .h1s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h1");

    }
    
    @Test
    public void h1s_case_functionality() {

        setupExpectationsMultiple("h1","H1");

        FluentWebElements fe = fwd.h1()
                .h1s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h1");

    }

    @Test
    public void h1_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.h1(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h1(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been h1 but was boo"));
        }

    }


}
