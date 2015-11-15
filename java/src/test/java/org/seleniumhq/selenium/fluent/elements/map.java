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

public class map extends BaseTest {

    @Test
    public void map_functionality() {

        setupExpectationsSingle("map");

        Internal.BaseFluentWebElements fe = fwd.map()
                .map(By.xpath("@foo = 'bar'"))
                .map(By.cssSelector("baz"))
                .maps();

        assertThat(fe, notNullValue());
        verificationsSingle("map");
    }

    @Test
    public void maps_functionality() {

        setupExpectationsMultiple("map");

        Internal.BaseFluentWebElements fe = fwd.map()
                .maps(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("map");

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
