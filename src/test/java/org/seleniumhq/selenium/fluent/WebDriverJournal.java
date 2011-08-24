package org.seleniumhq.selenium.fluent;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebDriverJournal implements WebDriver {

    Integer CTR = 1;
    private final StringBuilder sb;
    public boolean lastSelected;
    public boolean lastEnabled;
    public boolean lastDisplayed;

    private static WebElement bogusElem;
    static {
        bogusElem = mock(WebElement.class);
        when(bogusElem.getTagName()).thenReturn("bogus");
    }

    public WebDriverJournal(StringBuilder sb) {
        this.sb = sb;
    }

    public void get(String s) {
        sb.append("get(" + s + ")\n");
    }

    public String getCurrentUrl() {
        String rv = "STR#" + CTR++;
        sb.append("getCurrentUrl():" + rv + "\n");
        return rv;
    }

    public String getTitle() {
        String rv = "STR#" + CTR++;
        sb.append("getTitle():" + rv + "\n");
        return rv;
    }

    public List<WebElement> findElements(By by) {
        WebElement we = new WebElementJournal(sb, this);
        WebElement we2 = new WebElementJournal(sb, this);
        List<WebElement> elems = asList(we, we2);
        sb.append(this + ".findElements(" + by + ") -> " + elems + "\n");
        return elems;
    }

    public WebElement findElement(By by) {
        WebElement rv;
        if (by.toString().contains("mismatching_tag_name")) {
            rv = bogusElem;
        } else {
            rv = new WebElementJournal(sb, this);
        }
        sb.append(this + ".findElement(" + by + ") -> " + rv + "\n");
        return rv;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {
    }

    public void quit() {
    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        return null;
    }

    public TargetLocator switchTo() {
        return null;
    }

    public Navigation navigate() {
        return null;
    }

    public Options manage() {
        return null;
    }

    @Override
    public String toString() {
        return "wd0";
    }
}
