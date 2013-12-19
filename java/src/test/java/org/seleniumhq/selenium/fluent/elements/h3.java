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

public class h3 extends BaseTest {

    @Test
    public void h3_functionality() {

        setupExpectationsSingle("h3");

        FluentWebElements fe = fwd.h3()
                .h3(By.xpath("@foo = 'bar'"))
                .h3(By.cssSelector("baz"))
                .h3s();

        assertThat(fe, notNullValue());
        verificationsSingle("h3");
    }

    @Test
    public void h3s_functionality() {

        setupExpectationsMultiple("h3");

        FluentWebElements fe = fwd.h3()
                .h3s(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("h3");

    }

    @Test
    public void h3_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.h3(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h3(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been h3 but was boo"));
        }

    }


}
