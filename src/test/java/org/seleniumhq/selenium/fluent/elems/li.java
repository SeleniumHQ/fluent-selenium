package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseFluentWebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.StartRecordingImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class li extends BaseTest {

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
    public void li_functionality() {

        BaseFluentWebDriver fc = fwd.li()
                .li(By.xpath("@foo = 'bar'"))
                .li(By.cssSelector("baz"))
                .lis();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: li) -> we1\n" +
                        "we1.getTagName() -> 'li'\n" +
                        "we1.findElement(By.xpath: .//li[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'li'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'li'\n" +
                        "we3.findElements(By.tagName: li) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'li'\n" +
                        "we5.getTagName() -> 'li'\n"
        ));
    }

    @Test
    public void lis_functionality() {
        BaseFluentWebDriver fc = fwd.li()
                .lis(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: li) -> we1\n" +
                        "we1.getTagName() -> 'li'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'li'\n" +
                        "we3.getTagName() -> 'li'\n"
        ));
    }

    @Test
    public void li_mismatched() {
        try {
            fwd.li(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_li_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl().recordTo(recording)
                .li()
                .li(By.xpath("@foo = 'bar'"))
                .li(By.cssSelector("baz"))
                .lis();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: li) -> we1\n" +
                        "we1.getTagName() -> 'li'\n" +
                        "we1.findElement(By.xpath: .//li[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'li'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'li'\n" +
                        "we3.findElements(By.tagName: li) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'li'\n" +
                        "we5.getTagName() -> 'li'\n"
        ));
    }

    @Test
    public void recording_lis_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl()
               .recordTo(recording)
                .li()
                .lis(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: li) -> we1\n" +
                        "we1.getTagName() -> 'li'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'li'\n" +
                        "we3.getTagName() -> 'li'\n"
        ));
    }

    @Test
    public void recording_li_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new StartRecordingImpl().recordTo(recording).li(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.li(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
