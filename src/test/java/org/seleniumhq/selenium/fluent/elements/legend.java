package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseFluentWebElements;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class legend extends BaseTest {

    @Test
    public void legend_functionality() {

        setupExpecations("legend");

        BaseFluentWebElements fe = fwd.legend()
                .legend(By.xpath("@foo = 'bar'"))
                .legend(By.cssSelector("baz"))
                .legends();

        assertThat(fe, notNullValue());
        verifications("legend");
    }

    @Test
    public void legends_functionality() {

        setupExpecations2("legend");

        BaseFluentWebElements fe = fwd.legend()
                .legends(By.name("qux"));

        assertThat(fe, notNullValue());

        verifications2("legend");

    }

    @Test
    public void legend_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.legend(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.legend(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been legend but was boo"));
        }

    }


}
