package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class button extends BaseTest {


    @Test
    public void button_functionality() {

        setupExpectationsSingle("button");

        FluentWebElements fe = fwd.button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons(); // very artificial, sure.

        assertThat(fe, notNullValue());
        verificationsSingle("button");


    }
    
    @Test
    public void button_case_functionality() {

        setupExpectationsSingle("button", "BUTTON");

        FluentWebElements fe = fwd.button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons(); // very artificial, sure.

        assertThat(fe, notNullValue());
        verificationsSingle("button");


    }

    @Test
    public void buttons_functionality() {

        setupExpectationsMultiple("button");

        FluentWebElements fe = fwd.button().buttons(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("button");

    }
    
    @Test
    public void buttons_case_functionality() {

    	setupExpectationsMultiple("button", "BUTTON" );

        FluentWebElements fe = fwd.button().buttons(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("button");

    }

    @Test
    public void button_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.button(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.button(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
