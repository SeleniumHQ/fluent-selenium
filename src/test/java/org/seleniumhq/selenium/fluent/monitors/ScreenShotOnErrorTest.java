package org.seleniumhq.selenium.fluent.monitors;

import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScreenShotOnErrorTest {

    @Test
    public void canTakeScreenShotAndMoveIt() throws IOException {

        TakesScreenshot ts = mock(TakesScreenshot.class);
        File file = new File(".foo");
        file.createNewFile();
        file.deleteOnExit();
        when(ts.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        ///scm/oss/fluent-selenium/baz_screenshot.png

        ScreenShotOnError ssoe = new ScreenShotOnError(ts, ScreenShotOnErrorTest.class, "target/test-classes/", "target/");
        ssoe.setContext("baz");
        RuntimeException boo = new RuntimeException("boo");
        RuntimeException bar = ssoe.exceptionDuringExecution(boo);
        assertThat(bar, equalTo(boo));

        assertThat(new File("target/baz_screenshot.png").exists(), equalTo(true));

    }

    @Test
    public void canExtractTestClassAndMethodName() throws IOException {


        ScreenShotOnError.WithUnitTestFrameWorkContext ssoe = new ScreenShotOnError.WithUnitTestFrameWorkContext(null, ScreenShotOnErrorTest.class, "a", "b");

        String s = ssoe.getContext();

        assertThat(s, equalTo("org.seleniumhq.selenium.fluent.monitors.ScreenShotOnErrorTest.canExtractTestClassAndMethodName"));


    }


}
