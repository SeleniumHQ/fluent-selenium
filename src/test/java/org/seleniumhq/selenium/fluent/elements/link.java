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

public class link extends BaseTest2 {

    @Test
    public void link_functionality() {

        setupExpecations("a");

        FluentWebElements fe = fwd.link()
                .link(By.xpath("@foo = 'bar'"))
                .link(By.cssSelector("baz"))
                .links();

        assertThat(fe, notNullValue());
        verifications("a");
    }

    @Test
    public void links_functionality() {

        setupExpecations2("a");

        FluentWebElements fe = fwd.link()
                .links(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("a");

    }

    @Test
    public void link_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.link(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.a(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been a but was boo"));
        }

    }


}
