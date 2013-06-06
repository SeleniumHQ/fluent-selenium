package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.Internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class p extends BaseTest {

    @Test
    public void p_functionality() {

        setupExpecations("p");

        Internal.FluentWebElements fe = fwd.p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fe, notNullValue());
        verifications("p");
    }

    @Test
    public void ps_functionality() {

        setupExpecations2("p");

        Internal.FluentWebElements fe = fwd.p()
                .ps(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("p");

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
