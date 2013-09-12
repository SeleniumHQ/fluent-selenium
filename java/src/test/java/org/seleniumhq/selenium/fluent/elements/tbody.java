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

public class tbody extends BaseTest {

    @Test
    public void tbody_functionality() {

        setupExpecations("tbody");

        FluentWebElements fe = fwd.tbody()
                .tbody(By.xpath("@foo = 'bar'"))
                .tbody(By.cssSelector("baz"))
                .tbodys();

        assertThat(fe, notNullValue());
        verifications("tbody");
    }

    @Test
    public void adresss_functionality() {

        setupExpecations2("tbody");

        FluentWebElements fe = fwd.tbody()
                .tbodys(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("tbody");

    }

    @Test
    public void tbody_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.tbody(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.tbody(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been tbody but was boo"));
        }

    }


}
