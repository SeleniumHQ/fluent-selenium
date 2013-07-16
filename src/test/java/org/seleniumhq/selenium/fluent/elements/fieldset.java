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

public class fieldset extends BaseTest {

    @Test
    public void fieldset_functionality() {

        setupExpecations("fieldset");

        FluentWebElements fe = fwd.fieldset()
                .fieldset(By.xpath("@foo = 'bar'"))
                .fieldset(By.cssSelector("baz"))
                .fieldsets();

        assertThat(fe, notNullValue());
        verifications("fieldset");
    }
    
    @Test
    public void fieldset_case_functionality() {

        setupExpecations("fieldset", "FIELDSET");

        FluentWebElements fe = fwd.fieldset()
                .fieldset(By.xpath("@foo = 'bar'"))
                .fieldset(By.cssSelector("baz"))
                .fieldsets();

        assertThat(fe, notNullValue());
        verifications("fieldset");
    }

    @Test
    public void fieldsets_functionality() {

        setupExpecations2("fieldset");

        FluentWebElements fe = fwd.fieldset()
                .fieldsets(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("fieldset");

    }
    
    @Test
    public void fieldsets_case_functionality() {

        setupExpecations2("fieldset", "FIELDSET");

        FluentWebElements fe = fwd.fieldset()
                .fieldsets(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("fieldset");

    }

    @Test
    public void fieldset_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.fieldset(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.fieldset(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been fieldset but was boo"));
        }

    }


}
