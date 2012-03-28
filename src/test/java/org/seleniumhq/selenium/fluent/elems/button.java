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

public class button extends BaseTest {

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
    public void button_functionality() {

        BaseFluentWebDriver fc = fwd.button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: button) -> we1\n" +
                        "we1.getTagName() -> 'button'\n" +
                        "we1.findElement(By.xpath: .//button[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'button'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'button'\n" +
                        "we3.findElements(By.tagName: button) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'button'\n" +
                        "we5.getTagName() -> 'button'\n"
        ));
    }

    @Test
    public void buttons_functionality() {
        BaseFluentWebDriver fc = fwd.button()
                .buttons(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: button) -> we1\n" +
                        "we1.getTagName() -> 'button'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'button'\n" +
                        "we3.getTagName() -> 'button'\n"
        ));
    }

    @Test
    public void button_mismatched() {
        try {
            fwd.button(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.button(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

    @Test
    public void recorded_button_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl().recordTo(recording)
                .button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: button) -> we1\n" +
                        "we1.getTagName() -> 'button'\n" +
                        "we1.findElement(By.xpath: .//button[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'button'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'button'\n" +
                        "we3.findElements(By.tagName: button) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'button'\n" +
                        "we5.getTagName() -> 'button'\n"
        ));
    }

    @Test
    public void recorded_buttons_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl().recordTo(recording).button().buttons(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: button) -> we1\n" +
                        "we1.getTagName() -> 'button'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'button'\n" +
                        "we3.getTagName() -> 'button'\n"
        ));
    }

    @Test
    public void recorded_button_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new StartRecordingImpl().recordTo(recording).button(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.button(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }


}
