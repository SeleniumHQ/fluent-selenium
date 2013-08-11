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

public class header extends BaseTest {

    @Test
    public void b_functionality() {

        setupExpecations("header");

        FluentWebElements fe = fwd.header()
                .header(By.xpath("@foo = 'bar'"))
                .header(By.cssSelector("baz"))
                .headers();

        assertThat(fe, notNullValue());
        verifications("header");
    }

    @Test
    public void bs_functionality() {

        setupExpecations2("header");

        FluentWebElements fe = fwd.header()
                .headers(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("header");

    }

    @Test
    public void b_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.header(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.header(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been header but was boo"));
        }

    }


}
