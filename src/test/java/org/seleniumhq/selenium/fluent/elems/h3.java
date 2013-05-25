package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class h3 extends BaseTest {

    private StringBuilder sb;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);
    }


    @Test
    public void h3_functionality() {

        FluentWebElements fe = fwd.h3()
                .h3(By.xpath("@foo = 'bar'"))
                .h3(By.cssSelector("baz"))
                .h3s();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h3) -> we1\n" +
                        "we1.getTagName() -> 'h3'\n" +
                        "we1.findElement(By.xpath: .//h3[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h3'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h3'\n" +
                        "we3.findElements(By.tagName: h3) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h3'\n" +
                        "we5.getTagName() -> 'h3'\n"
        ));
    }

    @Test
    public void h3s_functionality() {
        FluentWebElements fe = fwd.h3()
                .h3s(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h3) -> we1\n" +
                        "we1.getTagName() -> 'h3'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h3'\n" +
                        "we3.getTagName() -> 'h3'\n"
        ));
    }

    @Test
    public void h3_mismatched() {
        try {
            fwd.h3(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h3(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


}
