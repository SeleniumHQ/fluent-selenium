package org.seleniumhq.selenium.fluent.monitors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.Monitor;

import java.io.File;
import java.io.IOException;

public class ScreenShotOnError extends Monitor.NULL {

    private final String path;
    private TakesScreenshot webDriver;
    private String context;

    public ScreenShotOnError(TakesScreenshot webDriver, Class classCloseToOutputPath, String pathFrom, String pathTo) {
        this.webDriver = webDriver;
        path = classCloseToOutputPath.getProtectionDomain().getCodeSource().getLocation().getPath().replace(pathFrom, pathTo);
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    @Override
    public FluentExecutionStopped exceptionDuringExecution(FluentExecutionStopped ex, WebElement webElement) {
        String pathname = path + getContext() + "_screenshot.png";
        try {
            File scrFile = webDriver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(pathname));
        } catch (WebDriverException e) {
            // fail silently (ish)
            System.err.println("ScreenShotOnError: Can't get ScreenShot from WebDriver.");
        } catch (IOException e) {
            // fail silently (ish)
            System.err.println("ScreenShotOnError: Can't write screenshot to '" + pathname + "'");
        }

        return ex;
    }

    public static class WithUnitTestFrameWorkContext extends ScreenShotOnError {

        public WithUnitTestFrameWorkContext(TakesScreenshot webDriver, Class classCloseToOutputPath, String pathFrom, String pathTo) {
            super(webDriver, classCloseToOutputPath, pathFrom, pathTo);
        }

        @Override
        public String getContext() {
            StackTraceElement[] elems = Thread.currentThread().getStackTrace();
            StackTraceElement lastNonReflectionElem = null;
            for (StackTraceElement elem : elems) {
                String elemClassName = elem.getClassName();
                if (elemClassName.startsWith("org.junit.runners") || elemClassName.startsWith("org.testng.internal")) {
                    return lastNonReflectionElem.getClassName() + "." + lastNonReflectionElem.getMethodName();
                }
                if (!elemClassName.startsWith("sun.reflect.") && !elemClassName.startsWith("java.lang.reflect")) {
                    lastNonReflectionElem = elem;
                }

            }

            return this.getClass().getName() +"_Cant_Extract_JUnit_ClassName";
        }
    }

}
