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

public class div extends BaseTest {

    @Test
    public void div_functionality() {

        setupExpecations("div");

        FluentWebElements fe = fwd.div()
                .div(By.xpath("@foo = 'bar'"))
                .div(By.cssSelector("baz"))
                .divs();

        assertThat(fe, notNullValue());
        verifications("div");
    }

    @Test
    public void divs_functionality() {

        setupExpecations2("div");

        FluentWebElements fe = fwd.div().divs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("div");

    }

    @Test
    public void div_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.div(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been div but was boo"));
        }

    }


}
