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

public class nav extends BaseTest {

    @Test
    public void nav_functionality() {

        setupExpecations("nav");

        FluentWebElements fe = fwd.nav()
                .nav(By.xpath("@foo = 'bar'"))
                .nav(By.cssSelector("baz"))
                .navs();

        assertThat(fe, notNullValue());
        verifications("nav");
    }

    @Test
    public void navs_functionality() {

        setupExpecations2("nav");

        FluentWebElements fe = fwd.nav()
                .navs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("nav");

    }

    @Test
    public void nav_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.nav(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.nav(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been nav but was boo"));
        }

    }


}
