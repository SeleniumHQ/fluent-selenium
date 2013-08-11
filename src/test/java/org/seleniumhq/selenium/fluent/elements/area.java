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

public class area extends BaseTest {

    @Test
    public void area_functionality() {

        setupExpecations("area");

        FluentWebElements fe = fwd.area()
                .area(By.xpath("@foo = 'bar'"))
                .area(By.cssSelector("baz"))
                .areas();

        assertThat(fe, notNullValue());
        verifications("area");
    }

    @Test
    public void adresss_functionality() {

        setupExpecations2("area");

        FluentWebElements fe = fwd.area()
                .areas(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("area");

    }

    @Test
    public void area_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.area(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.area(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been area but was boo"));
        }

    }


}
