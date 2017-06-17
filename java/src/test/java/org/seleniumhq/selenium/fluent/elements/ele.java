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

public class ele extends BaseTest {

    @Test
    public void ele_functionality() {

        setupExpectationsSingle("div");

        FluentWebElements fe = fwd.element("div")
                .element("div", By.xpath("@foo = 'bar'"))
                .element("div", By.cssSelector("baz"))
                .elements("div");

        assertThat(fe, notNullValue());
        verificationsSingle("div");
    }
    
    @Test
    public void ele_case_functionality() {

        setupExpectationsSingle("div","DIV");

        FluentWebElements fe = fwd.element("div")
                .element("div", By.xpath("@foo = 'bar'"))
                .element("div", By.cssSelector("baz"))
                .elements("div");

        assertThat(fe, notNullValue());
        verificationsSingle("div");
    }

    @Test
    public void elements_functionality() {

        setupExpectationsMultiple("div");

        FluentWebElements fe = fwd.element("div").elements("div", By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("div");

    }
    
    @Test
    public void elements_case_functionality() {

        setupExpectationsMultiple("div", "DIV");

        FluentWebElements fe = fwd.element("div").elements("div", By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("div");

    }

    @Test
    public void ele_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.element("div", By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been div but was boo"));
        }

    }


}
