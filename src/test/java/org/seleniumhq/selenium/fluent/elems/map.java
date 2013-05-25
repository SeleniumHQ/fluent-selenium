package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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

public class map extends BaseTest {

    private StringBuilder sb;
    private FluentWebDriverImpl fwd;
    private WebDriverJournal wd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);
    }

    @Test
    public void map_functionality() {
        FluentWebElements fe = fwd.map()
                .map(By.xpath("@foo = 'bar'"))
                .map(By.cssSelector("baz"))
                .maps();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: map) -> we1\n" +
                        "we1.getTagName() -> 'map'\n" +
                        "we1.findElement(By.xpath: .//map[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'map'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'map'\n" +
                        "we3.findElements(By.tagName: map) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'map'\n" +
                        "we5.getTagName() -> 'map'\n"
        ));
    }


    @Test
    public void maps_functionality() {
        FluentWebElements fe = fwd.map()
                .maps(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: map) -> we1\n" +
                        "we1.getTagName() -> 'map'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'map'\n" +
                        "we3.getTagName() -> 'map'\n"
        ));
    }

    @Test
    public void map_mismatched() {
        try {
            fwd.map(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.map(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }




}
