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

public class option extends BaseTest {

    @Test
    public void option_functionality() {

        setupExpectationsSingle("option");

        FluentWebElements fe = fwd.option()
                .option(By.xpath("@foo = 'bar'"))
                .option(By.cssSelector("baz"))
                .options();

        assertThat(fe, notNullValue());
        verificationsSingle("option");
    }
    
    @Test
    public void option_case_functionality() {

        setupExpectationsSingle("option", "OPTION");

        FluentWebElements fe = fwd.option()
                .option(By.xpath("@foo = 'bar'"))
                .option(By.cssSelector("baz"))
                .options();

        assertThat(fe, notNullValue());
        verificationsSingle("option");
    }

    @Test
    public void options_functionality() {

        setupExpectationsMultiple("option");

        FluentWebElements fe = fwd.option()
                .options(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("option");

    }
    
    @Test
    public void options_case_functionality() {

        setupExpectationsMultiple("option", "OPTION");

        FluentWebElements fe = fwd.option()
                .options(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("option");

    }

    @Test
    public void option_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.option(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.option(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been option but was boo"));
        }

    }


}
