package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest2;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class th extends BaseTest2 {

    @Test
    public void th_functionality() {

        setupExpecations("th");

        FluentWebElements fe = fwd.th()
                .th(By.xpath("@foo = 'bar'"))
                .th(By.cssSelector("baz"))
                .ths();

        assertThat(fe, notNullValue());
        verifications("th");
    }

    @Test
    public void ths_functionality() {

        setupExpecations2("th");

        FluentWebElements fe = fwd.th()
                .ths(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("th");

    }

    @Test
    public void th_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.th(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.th(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been th but was boo"));
        }

    }


}
