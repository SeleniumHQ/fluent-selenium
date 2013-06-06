package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.Internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class h2 extends BaseTest {

    @Test
    public void h2_functionality() {

        setupExpecations("h2");

        Internal.BaseFluentWebElements fe = fwd.h2()
                .h2(By.xpath("@foo = 'bar'"))
                .h2(By.cssSelector("baz"))
                .h2s();

        assertThat(fe, notNullValue());
        verifications("h2");
    }

    @Test
    public void h2s_functionality() {

        setupExpecations2("h2");

        Internal.BaseFluentWebElements fe = fwd.h2()
                .h2s(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("h2");

    }

    @Test
    public void h2_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.h2(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h2(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been h2 but was boo"));
        }

    }


}
