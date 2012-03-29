package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.BaseFluentWebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.RecorderFacotryImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class fieldset extends BaseTest {

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
    public void fieldset_functionality() {
        FluentWebElements fe = fwd.fieldset()
                .fieldset(By.xpath("@foo = 'bar'"))
                .fieldset(By.cssSelector("baz"))
                .fieldsets();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: fieldset) -> we1\n" +
                        "we1.getTagName() -> 'fieldset'\n" +
                        "we1.findElement(By.xpath: .//fieldset[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'fieldset'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'fieldset'\n" +
                        "we3.findElements(By.tagName: fieldset) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'fieldset'\n" +
                        "we5.getTagName() -> 'fieldset'\n"
        ));
    }


    @Test
    public void fieldsets_functionality() {
        FluentWebElements fe = fwd.fieldset()
                .fieldsets(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: fieldset) -> we1\n" +
                        "we1.getTagName() -> 'fieldset'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'fieldset'\n" +
                        "we3.getTagName() -> 'fieldset'\n"
        ));
    }

    @Test
    public void fieldset_mismatched() {
        try {
            fwd.fieldset(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.fieldset(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }


    @Test
    public void recording_fieldset_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecorderFacotryImpl().recordTo(recording)
                .fieldset()
                .fieldset(By.xpath("@foo = 'bar'"))
                .fieldset(By.cssSelector("baz"))
                .fieldsets();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: fieldset) -> we1\n" +
                        "we1.getTagName() -> 'fieldset'\n" +
                        "we1.findElement(By.xpath: .//fieldset[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'fieldset'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'fieldset'\n" +
                        "we3.findElements(By.tagName: fieldset) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'fieldset'\n" +
                        "we5.getTagName() -> 'fieldset'\n"
        ));
    }

    @Test
    public void recording_fieldsets_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecorderFacotryImpl()
               .recordTo(recording)
                .fieldset()
                .fieldsets(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: fieldset) -> we1\n" +
                        "we1.getTagName() -> 'fieldset'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'fieldset'\n" +
                        "we3.getTagName() -> 'fieldset'\n"
        ));
    }

    @Test
    public void recording_fieldset_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecorderFacotryImpl().recordTo(recording).fieldset(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.fieldset(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }
}
