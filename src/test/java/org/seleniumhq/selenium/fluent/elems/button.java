package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.RecordingFluentWebDriverFactoryImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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

        wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);
        WebElement we3 = mock(WebElement.class);
        WebElement we4 = mock(WebElement.class);
        WebElement we5 = mock(WebElement.class);
        fwd = new FluentWebDriverImpl(wd);
        when(wd.findElement(By.tagName("button"))).thenReturn(we);
        when(we.getTagName()).thenReturn("button");
        when(we.findElement(By.xpath(".//button[@foo = 'bar']"))).thenReturn(we2);
        when(we2.getTagName()).thenReturn("button");
        when(we2.findElement(By.cssSelector("baz"))).thenReturn(we3);
        when(we3.getTagName()).thenReturn("button");
        when(we3.findElements(By.tagName("button"))).thenReturn(newArrayList(we4, we5));
        when(we4.getTagName()).thenReturn("button");
        when(we5.getTagName()).thenReturn("button");

        FluentWebElements fe = fwd.button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons(); // very artificial, sure.

        assertThat(fe, notNullValue());

        verify(wd).findElement(By.tagName("button"));
        verify(we).getTagName();
        verify(we).findElement(By.xpath(".//button[@foo = 'bar']"));
        verify(we2).getTagName();
        verify(we2).findElement(By.cssSelector("baz"));
        verify(we3).getTagName();
        verify(we3).findElements(By.tagName("button"));
        verify(we4).getTagName();
        verify(we5).getTagName();

        verifyNoMoreInteractions(wd, we, we2, we3, we4, we5);

    }

    @Test
    public void buttons_functionality() {
        FluentWebElements fe = fwd.button()
                .buttons(By.name("qux"));

        assertThat(fe, notNullValue());
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

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl().recordTo(recording)
                .button()
                .button(By.xpath("@foo = 'bar'"))
                .button(By.cssSelector("baz"))
                .buttons();

        assertThat(fe, notNullValue());
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

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl().recordTo(recording).button().buttons(By.name("qux"));

        assertThat(fe, notNullValue());
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

        new RecordingFluentWebDriverFactoryImpl()
                .recordTo(recording).button(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording(0).playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.button(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }


}
