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

public class p extends BaseTest {

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
    public void p_functionality() {

        FluentWebElements fe = fwd.p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: p) -> we1\n" +
                        "we1.getTagName() -> 'p'\n" +
                        "we1.findElement(By.xpath: .//p[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'p'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'p'\n" +
                        "we3.findElements(By.tagName: p) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'p'\n" +
                        "we5.getTagName() -> 'p'\n"
        ));
    }

    @Test
    public void ps_functionality() {
        FluentWebElements fe = fwd.p()
                .ps(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: p) -> we1\n" +
                        "we1.getTagName() -> 'p'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'p'\n" +
                        "we3.getTagName() -> 'p'\n"
        ));
    }

    @Test
    public void p_mismatched() {
        try {
            fwd.p(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.p(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_p_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl().recordTo(recording)
                .p()
                .p(By.xpath("@foo = 'bar'"))
                .p(By.cssSelector("baz"))
                .ps();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: p) -> we1\n" +
                        "we1.getTagName() -> 'p'\n" +
                        "we1.findElement(By.xpath: .//p[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'p'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'p'\n" +
                        "we3.findElements(By.tagName: p) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'p'\n" +
                        "we5.getTagName() -> 'p'\n"
        ));
    }

    @Test
    public void recording_ps_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl()
               .recordTo(recording)
                .p()
                .ps(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: p) -> we1\n" +
                        "we1.getTagName() -> 'p'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'p'\n" +
                        "we3.getTagName() -> 'p'\n"
        ));
    }

    @Test
    public void recording_p_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecordingFluentWebDriverFactoryImpl().recordTo(recording).p(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.p(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


}
