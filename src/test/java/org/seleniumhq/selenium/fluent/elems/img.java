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

public class img extends BaseTest {

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
    public void img_functionality() {

        FluentWebElements fe = fwd.img()
                .img(By.xpath("@foo = 'bar'"))
                .img(By.cssSelector("baz"))
                .imgs();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: img) -> we1\n" +
                        "we1.getTagName() -> 'img'\n" +
                        "we1.findElement(By.xpath: .//img[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'img'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'img'\n" +
                        "we3.findElements(By.tagName: img) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'img'\n" +
                        "we5.getTagName() -> 'img'\n"
        ));
    }

    @Test
    public void imgs_functionality() {
        FluentWebElements fe = fwd.img()
                .imgs(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: img) -> we1\n" +
                        "we1.getTagName() -> 'img'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'img'\n" +
                        "we3.getTagName() -> 'img'\n"
        ));
    }

    @Test
    public void img_mismatched() {
        try {
            fwd.img(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.img(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }




}
