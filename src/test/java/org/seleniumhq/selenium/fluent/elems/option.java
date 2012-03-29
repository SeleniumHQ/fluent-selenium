package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.RecordingFluentWebDriverFactoryImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class option extends BaseTest {

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
    public void option_functionality() {

        FluentWebElements fe = fwd.option()
                .option(By.xpath("@foo = 'bar'"))
                .option(By.cssSelector("baz"))
                .options();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: option) -> we1\n" +
                        "we1.getTagName() -> 'option'\n" +
                        "we1.findElement(By.xpath: .//option[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'option'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'option'\n" +
                        "we3.findElements(By.tagName: option) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'option'\n" +
                        "we5.getTagName() -> 'option'\n"
        ));
    }


    @Test
    public void options_functionality() {
        FluentWebElements fe = fwd.option()
                .options(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: option) -> we1\n" +
                        "we1.getTagName() -> 'option'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'option'\n" +
                        "we3.getTagName() -> 'option'\n"
        ));
    }


    @Test
    public void option_mismatched() {
        try {
            fwd.option(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.option(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_option_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl().recordTo(recording)
                .option()
                .option(By.xpath("@foo = 'bar'"))
                .option(By.cssSelector("baz"))
                .options();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: option) -> we1\n" +
                        "we1.getTagName() -> 'option'\n" +
                        "we1.findElement(By.xpath: .//option[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'option'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'option'\n" +
                        "we3.findElements(By.tagName: option) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'option'\n" +
                        "we5.getTagName() -> 'option'\n"
        ));
    }

    @Test
    public void recording_options_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl()
               .recordTo(recording)
                .option()
                .options(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: option) -> we1\n" +
                        "we1.getTagName() -> 'option'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'option'\n" +
                        "we3.getTagName() -> 'option'\n"
        ));
    }

    @Test
    public void recording_option_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecordingFluentWebDriverFactoryImpl().recordTo(recording).option(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.option(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


}
