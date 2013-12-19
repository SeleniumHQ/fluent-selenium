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

public class td extends BaseTest {

    @Test
    public void td_functionality() {

        setupExpectationsSingle("td");

        Internal.BaseFluentWebElements fe = fwd.td()
                .td(By.xpath("@foo = 'bar'"))
                .td(By.cssSelector("baz"))
                .tds();

        assertThat(fe, notNullValue());
        verificationsSingle("td");
    }

    @Test
    public void tds_functionality() {

        setupExpectationsMultiple("td");

        Internal.BaseFluentWebElements fe = fwd.td()
                .tds(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("td");

    }

    @Test
    public void td_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.td(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.td(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been td but was boo"));
        }

    }


}
