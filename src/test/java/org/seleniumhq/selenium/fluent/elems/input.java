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
import org.seleniumhq.selenium.fluent.RecorderFacotryImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class input extends BaseTest {

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
    public void input_functionality() {

        FluentWebElements fe = fwd.input()
                .input(By.xpath("@foo = 'bar'"))
                .input(By.cssSelector("baz"))
                .inputs();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: input) -> we1\n" +
                        "we1.getTagName() -> 'input'\n" +
                        "we1.findElement(By.xpath: .//input[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'input'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'input'\n" +
                        "we3.findElements(By.tagName: input) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'input'\n" +
                        "we5.getTagName() -> 'input'\n"
        ));
    }

    @Test
    public void inputs_functionality() {
        FluentWebElements fe = fwd.input()
                .inputs(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: input) -> we1\n" +
                        "we1.getTagName() -> 'input'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'input'\n" +
                        "we3.getTagName() -> 'input'\n"
        ));
    }

    @Test
    public void input_mismatched() {
        try {
            fwd.input(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.input(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_input_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecorderFacotryImpl().recordTo(recording)
                .input()
                .input(By.xpath("@foo = 'bar'"))
                .input(By.cssSelector("baz"))
                .inputs();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: input) -> we1\n" +
                        "we1.getTagName() -> 'input'\n" +
                        "we1.findElement(By.xpath: .//input[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'input'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'input'\n" +
                        "we3.findElements(By.tagName: input) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'input'\n" +
                        "we5.getTagName() -> 'input'\n"
        ));
    }

    @Test
    public void recording_inputs_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecorderFacotryImpl()
               .recordTo(recording)
                .input()
                .inputs(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: input) -> we1\n" +
                        "we1.getTagName() -> 'input'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'input'\n" +
                        "we3.getTagName() -> 'input'\n"
        ));
    }

    @Test
    public void recording_input_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecorderFacotryImpl().recordTo(recording).input(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.input(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }



}
