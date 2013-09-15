package org.seleniumhq.selenium.fluent.internal;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentBy;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class NotAttributeTest extends BaseTest {

    @Test
    public void abbr_functionality2() {

        when(wd.findElement(By.xpath(".//abbr[not(@foo)]"))).thenReturn(we);
        when(we.getTagName()).thenReturn("abbr");

        FluentWebElement fe = fwd.abbr(FluentBy.notAttribute("foo"));

        assertThat(fe, notNullValue());

    }

}
