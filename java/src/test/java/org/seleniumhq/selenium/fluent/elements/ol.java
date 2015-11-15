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

public class ol extends BaseTest {

    @Test
    public void ol_functionality() {

        setupExpectationsSingle("ol");

        FluentWebElements fe = fwd.ol()
                .ol(By.xpath("@foo = 'bar'"))
                .ol(By.cssSelector("baz"))
                .ols();

        assertThat(fe, notNullValue());
        verificationsSingle("ol");
    }
    
    @Test
    public void ol_case_functionality() {

        setupExpectationsSingle("ol", "OL");

        FluentWebElements fe = fwd.ol()
                .ol(By.xpath("@foo = 'bar'"))
                .ol(By.cssSelector("baz"))
                .ols();

        assertThat(fe, notNullValue());
        verificationsSingle("ol");
    }

    @Test
    public void ols_functionality() {

        setupExpectationsMultiple("ol");

        FluentWebElements fe = fwd.ol()
                .ols(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("ol");

    }
    
    @Test
    public void ols_case_functionality() {

        setupExpectationsMultiple("ol", "OL");

        FluentWebElements fe = fwd.ol()
                .ols(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("ol");

    }

    @Test
    public void ol_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.ol(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.ol(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been ol but was boo"));
        }

    }


}
