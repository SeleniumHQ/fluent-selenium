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

public class legend extends BaseTest {

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
    public void legend_functionality() {
        FluentWebElements fe = fwd.legend()
                .legend(By.xpath("@foo = 'bar'"))
                .legend(By.cssSelector("baz"))
                .legends();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: legend) -> we1\n" +
                        "we1.getTagName() -> 'legend'\n" +
                        "we1.findElement(By.xpath: .//legend[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'legend'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'legend'\n" +
                        "we3.findElements(By.tagName: legend) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'legend'\n" +
                        "we5.getTagName() -> 'legend'\n"
        ));
    }


    @Test
    public void legends_functionality() {
        FluentWebElements fe = fwd.legend()
                .legends(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: legend) -> we1\n" +
                        "we1.getTagName() -> 'legend'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'legend'\n" +
                        "we3.getTagName() -> 'legend'\n"
        ));
    }

    @Test
    public void legend_mismatched() {
        try {
            fwd.legend(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.legend(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }


    @Test
    public void recording_legend_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecorderFacotryImpl().recordTo(recording)
                .legend()
                .legend(By.xpath("@foo = 'bar'"))
                .legend(By.cssSelector("baz"))
                .legends();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: legend) -> we1\n" +
                        "we1.getTagName() -> 'legend'\n" +
                        "we1.findElement(By.xpath: .//legend[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'legend'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'legend'\n" +
                        "we3.findElements(By.tagName: legend) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'legend'\n" +
                        "we5.getTagName() -> 'legend'\n"
        ));
    }

    @Test
    public void recording_legends_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecorderFacotryImpl()
               .recordTo(recording)
                .legend()
                .legends(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: legend) -> we1\n" +
                        "we1.getTagName() -> 'legend'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'legend'\n" +
                        "we3.getTagName() -> 'legend'\n"
        ));
    }

    @Test
    public void recording_legend_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecorderFacotryImpl().recordTo(recording).legend(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.legend(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
