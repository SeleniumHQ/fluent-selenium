package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest2;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class select extends BaseTest2 {

    @Test
    public void select_functionality() {

        setupExpecations("select");

        FluentWebElements fe = fwd.select()
                .select(By.xpath("@foo = 'bar'"))
                .select(By.cssSelector("baz"))
                .selects();

        assertThat(fe, notNullValue());
        verifications("select");
    }

    @Test
    public void method_on_select_is_invoked() {

        when(wd.findElement(By.tagName("select"))).thenReturn(we);
        when(we.getTagName()).thenReturn("select");
        when(we.getTagName()).thenReturn("select");
        when(we.getAttribute("multiple")).thenReturn("true");
        when(we.findElements(By.xpath(".//option[@value = \"bar\"]"))).thenReturn(newArrayList(we2, we3));
        when(we2.isSelected()).thenReturn(true);
        when(we3.isSelected()).thenReturn(false);

        FluentSelect fs = fwd.select().selectByValue("bar");

        assertThat(fs, notNullValue());

        verify(wd).findElement(By.tagName("select"));
        verify(we, times(2)).getTagName();
        verify(we).getAttribute("multiple");
        verify(we).findElements(By.xpath(".//option[@value = \"bar\"]"));
        verify(we2).isSelected();
        verify(we3).isSelected();
        verify(we3).click();
        verifyNoMoreInteractions(wd, we, we2, we3, we4, we5);


    }

    @Test
    public void selects_functionality() {

        setupExpecations2("select");

        FluentWebElements fe = fwd.select()
                .selects(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("select");

    }

    @Test
    public void select_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.select(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.select(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been select but was boo"));
        }

    }


}
