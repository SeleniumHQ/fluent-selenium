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

public class blockquote extends BaseTest {

    @Test
    public void blockquote_functionality() {

        setupExpectationsSingle("blockquote");

        FluentWebElements fe = fwd.blockquote()
                .blockquote(By.xpath("@foo = 'bar'"))
                .blockquote(By.cssSelector("baz"))
                .blockquotes();

        assertThat(fe, notNullValue());
        verificationsSingle("blockquote");
    }

    @Test
    public void blockquotes_functionality() {

        setupExpectationsMultiple("blockquote");

        FluentWebElements fe = fwd.blockquote()
                .blockquotes(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("blockquote");

    }

    @Test
    public void blockquote_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.blockquote(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.blockquote(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been blockquote but was boo"));
        }

    }


}
