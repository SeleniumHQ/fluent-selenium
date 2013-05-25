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

public class map extends BaseTest2 {

    @Test
    public void map_functionality() {

        setupExpecations("map");

        FluentWebElements fe = fwd.map()
                .map(By.xpath("@foo = 'bar'"))
                .map(By.cssSelector("baz"))
                .maps();

        assertThat(fe, notNullValue());
        verifications("map");
    }

    @Test
    public void maps_functionality() {

        setupExpecations2("map");

        FluentWebElements fe = fwd.map()
                .maps(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("map");

    }

    @Test
    public void map_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.map(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.map(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been map but was boo"));
        }

    }


}
