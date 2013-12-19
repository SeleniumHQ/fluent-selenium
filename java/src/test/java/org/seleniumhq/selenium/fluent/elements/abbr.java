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

public class abbr extends BaseTest {

    @Test
    public void abbr_functionality() {

        setupExpectationsSingle("abbr");

        FluentWebElements fe = fwd.abbr()
                .abbr(By.xpath("@foo = 'bar'"))
                .abbr(By.cssSelector("baz"))
                .abbrs();

        assertThat(fe, notNullValue());
        verificationsSingle("abbr");
    }

    @Test
    public void abbrs_functionality() {

        setupExpectationsMultiple("abbr");

        FluentWebElements fe = fwd.abbr()
                .abbrs(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("abbr");

    }

    @Test
    public void abbr_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.abbr(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.abbr(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been abbr but was boo"));
        }

    }


}
