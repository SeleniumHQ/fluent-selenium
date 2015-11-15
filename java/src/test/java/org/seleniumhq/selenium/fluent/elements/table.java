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

public class table extends BaseTest {

    @Test
    public void table_functionality() {

        setupExpectationsSingle("table");

        FluentWebElements fe = fwd.table()
                .table(By.xpath("@foo = 'bar'"))
                .table(By.cssSelector("baz"))
                .tables();

        assertThat(fe, notNullValue());
        verificationsSingle("table");
    }
    
    @Test
    public void table_case_functionality() {

        setupExpectationsSingle("table", "TABLE");

        FluentWebElements fe = fwd.table()
                .table(By.xpath("@foo = 'bar'"))
                .table(By.cssSelector("baz"))
                .tables();

        assertThat(fe, notNullValue());
        verificationsSingle("table");
    }

    @Test
    public void tables_functionality() {

        setupExpectationsMultiple("table");

        FluentWebElements fe = fwd.table()
                .tables(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("table");

    }
    
    @Test
    public void tables_case_functionality() {

        setupExpectationsMultiple("table", "TABLE");

        FluentWebElements fe = fwd.table()
                .tables(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("table");

    }

    @Test
    public void table_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.table(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.table(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been table but was boo"));
        }

    }


}
