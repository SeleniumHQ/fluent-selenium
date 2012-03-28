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

public class h2 extends BaseTest {

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
    public void h2_functionality() {

        BaseFluentWebDriver fc = fwd.h2()
                .h2(By.xpath("@foo = 'bar'"))
                .h2(By.cssSelector("baz"))
                .h2s();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h2) -> we1\n" +
                        "we1.getTagName() -> 'h2'\n" +
                        "we1.findElement(By.xpath: .//h2[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h2'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h2'\n" +
                        "we3.findElements(By.tagName: h2) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h2'\n" +
                        "we5.getTagName() -> 'h2'\n"
        ));
    }

    @Test
    public void h2s_functionality() {
        BaseFluentWebDriver fc = fwd.h2()
                .h2s(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h2) -> we1\n" +
                        "we1.getTagName() -> 'h2'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h2'\n" +
                        "we3.getTagName() -> 'h2'\n"
        ));
    }

    @Test
    public void h2_mismatched() {
        try {
            fwd.h2(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h2(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_h2_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl().recordTo(recording)
                .h2()
                .h2(By.xpath("@foo = 'bar'"))
                .h2(By.cssSelector("baz"))
                .h2s();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h2) -> we1\n" +
                        "we1.getTagName() -> 'h2'\n" +
                        "we1.findElement(By.xpath: .//h2[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'h2'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'h2'\n" +
                        "we3.findElements(By.tagName: h2) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'h2'\n" +
                        "we5.getTagName() -> 'h2'\n"
        ));
    }

    @Test
    public void recording_h2s_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl()
               .recordTo(recording)
                .h2()
                .h2s(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: h2) -> we1\n" +
                        "we1.getTagName() -> 'h2'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'h2'\n" +
                        "we3.getTagName() -> 'h2'\n"
        ));
    }

    @Test
    public void recording_h2_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new StartRecordingImpl().recordTo(recording).h2(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.h2(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }
}
