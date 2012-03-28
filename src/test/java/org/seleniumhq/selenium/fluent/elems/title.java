package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.StartRecordingImpl;
import org.seleniumhq.selenium.fluent.TestableString;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class title extends BaseTest {

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

        TestableString title = fwd.title();

        assertThat(title, notNullValue());
        assertThat(sb.toString(), equalTo("getTitle():STR#1\n"));
        assertThat("" + title, equalTo("STR#1"));
        assertThat(title.length(), equalTo(5));
        assertThat(title.charAt(2), equalTo('R'));
        assertTrue(title.equals("STR#1"));
        assertFalse(title.equals("sdkfjhsdkjfh"));
        assertThat(title.hashCode(), equalTo("STR#1".hashCode()));
        assertThat(title.subSequence(1,3), equalTo(cs("TR")));
        assertThat(title.toString(), equalTo("STR#1"));
        assertThat(title.replace("S", "s"), equalTo("sTR#1"));
    }

    @Test
    public void recording_button_functionality() {

        FluentRecorder recording = new FluentRecorder();

        TestableString title = new StartRecordingImpl().recordTo(recording).title();

        assertThat(title, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        title = (TestableString) recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo("getTitle():STR#1\n"));
        assertThat("" + title, equalTo("STR#1"));
        assertThat(title.length(), equalTo(5));
        assertThat(title.charAt(2), equalTo('R'));
        assertTrue(title.equals("STR#1"));
        assertFalse(title.equals("sdkfjhsdkjfh"));
        assertThat(title.hashCode(), equalTo("STR#1".hashCode()));
        assertThat(title.subSequence(1,3), equalTo(cs("TR")));
        assertThat(title.toString(), equalTo("STR#1"));
        assertThat(title.replace("S", "s"), equalTo("sTR#1"));
    }

}
