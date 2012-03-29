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

public class link extends BaseTest {

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
    public void link_functionality() {

        FluentWebElements fe = fwd.link()
                .link(By.xpath("@foo = 'bar'"))
                .link(By.cssSelector("baz"))
                .links();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: a) -> we1\n" +
                        "we1.getTagName() -> 'a'\n" +
                        "we1.findElement(By.xpath: .//a[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'a'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'a'\n" +
                        "we3.findElements(By.tagName: a) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'a'\n" +
                        "we5.getTagName() -> 'a'\n"
        ));
    }

    @Test
    public void links_functionality() {
        FluentWebElements fe = fwd.link()
                .links(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: a) -> we1\n" +
                        "we1.getTagName() -> 'a'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'a'\n" +
                        "we3.getTagName() -> 'a'\n"
        ));
    }

    @Test
    public void link_mismatched() {
        try {
            fwd.link(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.a(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_link_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl().recordTo(recording)
                .link()
                .link(By.xpath("@foo = 'bar'"))
                .link(By.cssSelector("baz"))
                .links();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: a) -> we1\n" +
                        "we1.getTagName() -> 'a'\n" +
                        "we1.findElement(By.xpath: .//a[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'a'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'a'\n" +
                        "we3.findElements(By.tagName: a) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'a'\n" +
                        "we5.getTagName() -> 'a'\n"
        ));
    }

    @Test
    public void recording_links_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl()
               .recordTo(recording)
                .link()
                .links(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: a) -> we1\n" +
                        "we1.getTagName() -> 'a'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'a'\n" +
                        "we3.getTagName() -> 'a'\n"
        ));
    }

    @Test
    public void recording_link_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecordingFluentWebDriverFactoryImpl().recordTo(recording).link(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.a(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
