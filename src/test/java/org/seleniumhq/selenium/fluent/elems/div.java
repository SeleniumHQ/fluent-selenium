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

public class div extends BaseTest {

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
    public void div_functionality() {

        BaseFluentWebDriver fc = fwd.div()
                .div(By.xpath("@foo = 'bar'"))
                .div(By.cssSelector("baz"))
                .divs();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(By.xpath: .//div[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'div'\n" +
                        "we3.findElements(By.tagName: div) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'div'\n" +
                        "we5.getTagName() -> 'div'\n"
        ));
    }

    @Test
    public void divs_functionality() {
        BaseFluentWebDriver fc = fwd.div()
                .divs(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we3.getTagName() -> 'div'\n"
        ));
    }

    @Test
    public void div_mismatched() {
        try {
            fwd.div(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void recording_div_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl().recordTo(recording)
                .div()
                .div(By.xpath("@foo = 'bar'"))
                .div(By.cssSelector("baz"))
                .divs();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElement(By.xpath: .//div[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'div'\n" +
                        "we3.findElements(By.tagName: div) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'div'\n" +
                        "we5.getTagName() -> 'div'\n"
        ));
    }

    @Test
    public void recording_divs_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl()
               .recordTo(recording)
                .div()
                .divs(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: div) -> we1\n" +
                        "we1.getTagName() -> 'div'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "we3.getTagName() -> 'div'\n"
        ));
    }

    @Test
    public void recording_div_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new StartRecordingImpl().recordTo(recording).div(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.div(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
