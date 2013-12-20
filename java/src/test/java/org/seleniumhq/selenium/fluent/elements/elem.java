package org.seleniumhq.selenium.fluent.elements;

import org.junit.Ignore;
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

public class elem extends BaseTest {

    @Test
    public void element_functionality() {

        setupExpectationsSingleGeneric();

        FluentWebElements fe = fwd.element()
            .element(By.xpath("@foo = 'bar'"))
            .element(By.cssSelector("baz"))
            .elements();

        assertThat(fe, notNullValue());
        verificationsSingleGeneric();
    }

    @Test
    public void elements_functionality() {

        setupExpectationsMultipleGeneric();

        FluentWebElements fe = fwd.element().elements(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultipleGeneric();

    }

    @Test
    @Ignore
    public void element_mismatched() {
        // We cannot test if element is mismatched, because element is not a type, It will by default return something
        // different
    }
}
