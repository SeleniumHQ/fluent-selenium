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

public class li extends BaseTest {

    @Test
    public void li_functionality() {

        setupExpectationsSingle("li");

        FluentWebElements fe = fwd.li()
                .li(By.xpath("@foo = 'bar'"))
                .li(By.cssSelector("baz"))
                .lis();

        assertThat(fe, notNullValue());
        verificationsSingle("li");
    }

    @Test
    public void lis_functionality() {

        setupExpectationsMultiple("li");

        FluentWebElements fe = fwd.li()
                .lis(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("li");

    }

    @Test
    public void li_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.li(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.li(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been li but was boo"));
        }

    }


}
