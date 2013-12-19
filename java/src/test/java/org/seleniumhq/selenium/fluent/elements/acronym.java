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

public class acronym extends BaseTest {

    @Test
    public void acronym_functionality() {

        setupExpectationsSingle("acronym");

        FluentWebElements fe = fwd.acronym()
                .acronym(By.xpath("@foo = 'bar'"))
                .acronym(By.cssSelector("baz"))
                .acronyms();

        assertThat(fe, notNullValue());
        verificationsSingle("acronym");
    }

    @Test
    public void acronyms_functionality() {

        setupExpectationsMultiple("acronym");

        FluentWebElements fe = fwd.acronym()
                .acronyms(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("acronym");

    }

    @Test
    public void acronym_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.acronym(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.acronym(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been acronym but was boo"));
        }

    }


}
