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

public class h4 extends BaseTest {

    @Test
    public void h4_functionality() {

        setupExpecations("h4");

        FluentWebElements fe = fwd.h4()
                .h4(By.xpath("@foo = 'bar'"))
                .h4(By.cssSelector("baz"))
                .h4s();

        assertThat(fe, notNullValue());
        verifications("h4");
    }
    
    @Test
    public void h4_case_functionality() {

        setupExpecations("h4","H4");

        FluentWebElements fe = fwd.h4()
                .h4(By.xpath("@foo = 'bar'"))
                .h4(By.cssSelector("baz"))
                .h4s();

        assertThat(fe, notNullValue());
        verifications("h4");
    }

    @Test
    public void h4s_functionality() {

        setupExpecations2("h4");

        FluentWebElements fe = fwd.h4()
                .h4s(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("h4");

    }
    
    @Test
    public void h4s_case_functionality() {

        setupExpecations2("h4","h4");

        FluentWebElements fe = fwd.h4()
                .h4s(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("h4");

    }

    @Test
    public void h4_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.h4(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h4(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been h4 but was boo"));
        }

    }


}
