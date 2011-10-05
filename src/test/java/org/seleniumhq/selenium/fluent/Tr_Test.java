package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Tr_Test {

    private StringBuilder sb;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        fwd = new FluentWebDriverImpl(new WebDriverJournal(sb));
        FluentWebDriverImplTest.FAIL_ON_NEXT.set(null);
    }

    @Test
    public void tr_functionality() {

        BaseFluentWebDriver fc = fwd.tr()
                .tr(By.xpath("@foo = 'bar'"))
                .tr(By.cssSelector("baz"))
                .trs();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: tr) -> we1\n" +
                        "we1.getTagName() -> 'tr'\n" +
                        "we1.findElement(By.xpath: .//tr[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'tr'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'tr'\n" +
                        "we3.findElements(By.tagName: tr) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'tr'\n" +
                        "we5.getTagName() -> 'tr'\n"
        ));
    }

    @Test
    public void trs_functionality() {
        BaseFluentWebDriver fc = fwd.tr()
                .trs(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: tr) -> we1\n" +
                        "we1.getTagName() -> 'tr'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'tr'\n" +
                        "we3.getTagName() -> 'tr'\n"
        ));
    }

    @Test
    public void tr_mismatched() {
        try {
            fwd.tr(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.tr(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }

}
