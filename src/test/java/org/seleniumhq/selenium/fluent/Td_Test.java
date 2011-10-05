package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Td_Test {

    private StringBuilder sb;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        fwd = new FluentWebDriverImpl(new WebDriverJournal(sb));
        FluentWebDriverImplTest.FAIL_ON_NEXT.set(null);
    }

    @Test
    public void td_functionality() {

        BaseFluentWebDriver fc = fwd.td()
                .td(By.xpath("@foo = 'bar'"))
                .td(By.cssSelector("baz"))
                .tds();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: td) -> we1\n" +
                        "we1.getTagName() -> 'td'\n" +
                        "we1.findElement(By.xpath: .//td[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'td'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'td'\n" +
                        "we3.findElements(By.tagName: td) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'td'\n" +
                        "we5.getTagName() -> 'td'\n"
        ));
    }

    @Test
    public void tds_functionality() {
        BaseFluentWebDriver fc = fwd.td()
                .tds(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: td) -> we1\n" +
                        "we1.getTagName() -> 'td'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'td'\n" +
                        "we3.getTagName() -> 'td'\n"
        ));
    }

    @Test
    public void td_mismatched() {
        try {
            fwd.td(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.td(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }

}
