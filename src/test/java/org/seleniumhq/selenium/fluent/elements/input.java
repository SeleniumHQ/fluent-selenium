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

public class input extends BaseTest {

    @Test
    public void input_functionality() {

        setupExpecations("input");

        FluentWebElements fe = fwd.input()
                .input(By.xpath("@foo = 'bar'"))
                .input(By.cssSelector("baz"))
                .inputs();

        assertThat(fe, notNullValue());
        verifications("input");
    }
    
    @Test
    public void input_case_functionality() {

        setupExpecations("input", "INPUT");

        FluentWebElements fe = fwd.input()
                .input(By.xpath("@foo = 'bar'"))
                .input(By.cssSelector("baz"))
                .inputs();

        assertThat(fe, notNullValue());
        verifications("input");
    }

    @Test
    public void inputs_functionality() {

        setupExpecations2("input");

        FluentWebElements fe = fwd.input()
                .inputs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("input");

    }
    
    @Test
    public void inputs_case_functionality() {

        setupExpecations2("input", "INPUT");

        FluentWebElements fe = fwd.input()
                .inputs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("input");

    }


    @Test
    public void input_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.input(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.input(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been input but was boo"));
        }

    }


}
