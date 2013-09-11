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

public class pre extends BaseTest {

    @Test
    public void pre_functionality() {

        setupExpecations("pre");

        FluentWebElements fe = fwd.pre()
                .pre(By.xpath("@foo = 'bar'"))
                .pre(By.cssSelector("baz"))
                .pres();

        assertThat(fe, notNullValue());
        verifications("pre");
    }

    @Test
    public void pres_functionality() {

        setupExpecations2("pre");

        FluentWebElements fe = fwd.pre()
                .pres(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("pre");

    }

    @Test
    public void pre_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.pre(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.pre(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been pre but was boo"));
        }

    }


}
