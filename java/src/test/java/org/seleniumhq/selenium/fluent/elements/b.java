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

public class b extends BaseTest {

    @Test
    public void b_functionality() {

        setupExpecations("b");

        FluentWebElements fe = fwd.b()
                .b(By.xpath("@foo = 'bar'"))
                .b(By.cssSelector("baz"))
                .bs();

        assertThat(fe, notNullValue());
        verifications("b");
    }

    @Test
    public void bs_functionality() {

        setupExpecations2("b");

        FluentWebElements fe = fwd.b()
                .bs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("b");

    }

    @Test
    public void b_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.b(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.b(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been b but was boo"));
        }

    }


}
