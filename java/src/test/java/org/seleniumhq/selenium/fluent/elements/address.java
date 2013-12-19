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

public class address extends BaseTest {

    @Test
    public void address_functionality() {

        setupExpectationsSingle("address");

        FluentWebElements fe = fwd.address()
                .address(By.xpath("@foo = 'bar'"))
                .address(By.cssSelector("baz"))
                .addresses();

        assertThat(fe, notNullValue());
        verificationsSingle("address");
    }

    @Test
    public void adresss_functionality() {

        setupExpectationsMultiple("address");

        FluentWebElements fe = fwd.address()
                .addresses(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("address");

    }

    @Test
    public void address_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.address(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.address(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been address but was boo"));
        }

    }


}
