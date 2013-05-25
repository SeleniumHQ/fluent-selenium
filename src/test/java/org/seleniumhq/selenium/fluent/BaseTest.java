package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseTest {

    protected static final ThreadLocal<Class<? extends Throwable>> FAIL_ON_NEXT = new ThreadLocal<Class<? extends Throwable>>();

    protected CharSequence cs(String string) {
        return string;
    }

    protected void setupExpecations(WebDriver wd, WebElement we, WebElement we2, WebElement we3, WebElement we4, WebElement we5, String name) {
        when(wd.findElement(By.tagName(name))).thenReturn(we);
        when(we.getTagName()).thenReturn(name);
        when(we.findElement(By.xpath(".//"+name+"[@foo = 'bar']"))).thenReturn(we2);
        when(we2.getTagName()).thenReturn(name);
        when(we2.findElement(By.cssSelector("baz"))).thenReturn(we3);
        when(we3.getTagName()).thenReturn(name);
        when(we3.findElements(By.tagName(name))).thenReturn(newArrayList(we4, we5));
        when(we4.getTagName()).thenReturn(name);
        when(we5.getTagName()).thenReturn(name);
    }

    protected void verifications(WebDriver wd, WebElement we, WebElement we2, WebElement we3, WebElement we4, WebElement we5, String name) {
        verify(wd).findElement(By.tagName(name));
        verify(we).getTagName();
        verify(we).findElement(By.xpath(".//"+name+"[@foo = 'bar']"));
        verify(we2).getTagName();
        verify(we2).findElement(By.cssSelector("baz"));
        verify(we3).getTagName();
        verify(we3).findElements(By.tagName(name));
        verify(we4).getTagName();
        verify(we5).getTagName();
    }


    protected void setupExpecations(WebDriver wd, WebElement we, WebElement we2, WebElement we3, String button) {
        when(wd.findElement(By.tagName(button))).thenReturn(we);
        when(we.getTagName()).thenReturn(button);
        when(we.findElements(By.name("qux"))).thenReturn(newArrayList(we2, we3));
        when(we2.getTagName()).thenReturn(button);
        when(we3.getTagName()).thenReturn(button);
    }

    protected void verifications(WebDriver wd, WebElement we, WebElement we2, WebElement we3, String button) {
        verify(wd).findElement(By.tagName(button));
        verify(we).getTagName();
        verify(we).findElements(By.name("qux"));
        verify(we2).getTagName();
        verify(we3).getTagName();
    }
}
