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

public class ul extends BaseTest {

    @Test
    public void ul_functionality() {

        setupExpectationsSingle("ul");

        FluentWebElements fe = fwd.ul()
                .ul(By.xpath("@foo = 'bar'"))
                .ul(By.cssSelector("baz"))
                .uls();

        assertThat(fe, notNullValue());
        verificationsSingle("ul");
    }

    @Test
    public void uls_functionality() {

        setupExpectationsMultiple("ul");

        FluentWebElements fe = fwd.ul()
                .uls(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("ul");

    }

    @Test
    public void ul_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.ul(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.ul(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been ul but was boo"));
        }

    }


}
