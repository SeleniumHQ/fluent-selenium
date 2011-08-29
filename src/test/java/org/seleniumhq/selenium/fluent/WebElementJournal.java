package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static java.util.Arrays.asList;

public class WebElementJournal implements WebElement {

    private final StringBuilder sb;
    private final WebDriverJournal webDriverJournal;
    private final int ct;

    public WebElementJournal(StringBuilder sb, WebDriverJournal webDriverJournal) {
        this.sb = sb;
        this.webDriverJournal = webDriverJournal;
        ct = webDriverJournal.CTR++;
    }

    public void click() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".click()\n");
    }

    public void submit() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".submit()\n");
    }

    public void sendKeys(CharSequence... charSequences) {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".sendKeys("+charSequences[0]+")\n");
    }

    public void clear() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".clear()\n");
    }

    static void throwExceptionMaybe(Throwable e) {
        if (e != null) {
            if (e instanceof AssertionError) {
                throw (AssertionError) e;
            }
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
        }
    }

    public String getTagName() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement elem : stackTraceElements) {
            String methodName = elem.getMethodName();
            if (methodName.endsWith("s")) {
                methodName = methodName.substring(0, methodName.length() - 1);
            }
            String className = elem.getClassName();
            if (methodName.equals("<init>") && className.equals(Select.class.getName())) {
                return "select";
            }
            if ((className.equals(FluentCore.class.getName())
                    || className.equals(FluentWebElement.class.getName()))
                    && !methodName.equals("execute")
                    && !methodName.equals("multiple")
                    && !methodName.equals("single")) {
                if (methodName.equals("link")) {
                    methodName = "a";
                }
                if (methodName.equals("getTagName") || methodName.equals("tagName")) {
                    methodName = "taggart";
                }
                sb.append(this + ".getTagName() -> '" + methodName + "'\n");
                return methodName;
            }
        }

        throw new UnsupportedOperationException("FluentBase not in stack, can't work out method name");
    }

    public String getAttribute(String s) {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".getAttribute("+s+") -> " + s + "_value\n");
        return s + "_value";
    }


    public String getText() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        String msg = "Mary had " + webDriverJournal.CTR++ + " little lamb(s).";
        sb.append(this + ".getText() -> '" + msg + "'\n");
        return msg;
    }

    public List<WebElement> findElements(By by) {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        WebElement we = new WebElementJournal(sb, webDriverJournal);
        WebElement we2 = new WebElementJournal(sb, webDriverJournal);
        List<WebElement> elems = asList(we, we2);
        sb.append(this + ".findElements(" + by + ") -> " + elems + "\n");
        return elems;
    }

    public WebElement findElement(By by) {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        WebElementJournal rv = new WebElementJournal(sb, webDriverJournal);
        sb.append(this + ".findElement(" + by + ") -> " + rv + "\n");
        return rv;
    }

    public boolean isDisplayed() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        webDriverJournal.lastDisplayed = !webDriverJournal.lastDisplayed;
        sb.append(this + ".isDisplayed() -> "+webDriverJournal.lastDisplayed+"\n");
        return webDriverJournal.lastDisplayed;
    }

    public boolean isSelected() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        webDriverJournal.lastSelected = !webDriverJournal.lastSelected;
        sb.append(this + ".isSelected() -> "+webDriverJournal.lastSelected+"\n");
        return webDriverJournal.lastSelected;
    }

    public boolean isEnabled() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        webDriverJournal.lastEnabled = !webDriverJournal.lastEnabled;
        sb.append(this + ".isEnabled() -> "+webDriverJournal.lastEnabled+"\n");
        return webDriverJournal.lastEnabled;
    }

    public Point getLocation() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".getLocation() -> 1,1\n");
        return new Point(1,1);
    }

    public Dimension getSize() {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".getSize() -> 10,10\n");
        return new Dimension(10,10);
    }

    public String getCssValue(String s) {
        throwExceptionMaybe(FluentWebDriverTest.FAIL_ON_NEXT.get());
        sb.append(this + ".getCssValue("+s+") -> "+s+"_value\n");
        return s + "_value";
    }

    @Override
    public String toString() {
        return "we" + ct;
    }
}
