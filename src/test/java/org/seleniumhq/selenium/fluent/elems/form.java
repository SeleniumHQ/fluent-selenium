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

public class form extends BaseTest {

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
    public void form_functionality() {

        BaseFluentWebDriver fc = fwd.form()
                .form(By.xpath("@foo = 'bar'"))
                .form(By.cssSelector("baz"))
                .forms();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: form) -> we1\n" +
                        "we1.getTagName() -> 'form'\n" +
                        "we1.findElement(By.xpath: .//form[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'form'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'form'\n" +
                        "we3.findElements(By.tagName: form) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'form'\n" +
                        "we5.getTagName() -> 'form'\n"
        ));
    }

    @Test
    public void forms_functionality() {
        BaseFluentWebDriver fc = fwd.form()
                .forms(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: form) -> we1\n" +
                        "we1.getTagName() -> 'form'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'form'\n" +
                        "we3.getTagName() -> 'form'\n"
        ));
    }

    @Test
    public void form_mismatched() {
        try {
            fwd.form(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.form(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_form_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl().recordTo(recording)
                .form()
                .form(By.xpath("@foo = 'bar'"))
                .form(By.cssSelector("baz"))
                .forms();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: form) -> we1\n" +
                        "we1.getTagName() -> 'form'\n" +
                        "we1.findElement(By.xpath: .//form[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'form'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'form'\n" +
                        "we3.findElements(By.tagName: form) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'form'\n" +
                        "we5.getTagName() -> 'form'\n"
        ));
    }

    @Test
    public void recording_forms_functionality() {

        FluentRecorder recording = new FluentRecorder();

        BaseFluentWebDriver fc = new StartRecordingImpl()
               .recordTo(recording)
                .form()
                .forms(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: form) -> we1\n" +
                        "we1.getTagName() -> 'form'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'form'\n" +
                        "we3.getTagName() -> 'form'\n"
        ));
    }

    @Test
    public void recording_form_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new StartRecordingImpl().recordTo(recording).form(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.form(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
