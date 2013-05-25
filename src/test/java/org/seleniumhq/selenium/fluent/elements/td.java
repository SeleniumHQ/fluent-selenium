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

public class td extends BaseTest2 {

    @Test
    public void td_functionality() {

        setupExpecations("td");

        FluentWebElements fe = fwd.td()
                .td(By.xpath("@foo = 'bar'"))
                .td(By.cssSelector("baz"))
                .tds();

        assertThat(fe, notNullValue());
        verifications("td");
    }

    @Test
    public void tds_functionality() {

        setupExpecations2("td");

        FluentWebElements fe = fwd.td()
                .tds(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("td");

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
