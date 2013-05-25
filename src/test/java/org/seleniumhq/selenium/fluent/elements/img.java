package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest2;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class img extends BaseTest2 {

    @Test
    public void img_functionality() {

        setupExpecations("img");

        FluentWebElements fe = fwd.img()
                .img(By.xpath("@foo = 'bar'"))
                .img(By.cssSelector("baz"))
                .imgs();

        assertThat(fe, notNullValue());
        verifications("img");
    }

    @Test
    public void imgs_functionality() {

        setupExpecations2("img");

        FluentWebElements fe = fwd.img()
                .imgs(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("img");

    }

    @Test
    public void img_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.img(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.img(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been img but was boo"));
        }

    }


}
