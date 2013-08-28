package org.seleniumhq.selenium.fluent.monitors;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.By.tagName;

public class HighlightOnErrorTest {

    protected JSWebDriver wd;
    protected WebElement we;
    protected WebElement we2;
    protected WebElement we3;
    protected FluentWebDriver fwd;

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");

    public static interface JSWebDriver extends WebDriver, JavascriptExecutor {

    }

    @Before
    public void setup() {
        wd = mock(JSWebDriver.class);
        we = mock(WebElement.class);
        we2 = mock(WebElement.class);
        we3 = mock(WebElement.class);


        fwd = new FluentWebDriver(wd, new HighlightOnError(wd){
            @Override
            protected String highlightValue() {
                return "value";
            }

            @Override
            protected String highlightOperation() {
                return "op";
            }
        });
    }

    @Test
    public void lengthier_expression_causing_runtime_exception_should_cause_highlight() {

        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we2.getTagName()).thenReturn("div");

        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B);
            doThrow(new RuntimeException()).when(we2).findElement(tagName("span"));
            span.span();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.div(By.id: idA).div(By.id: idB).span()"));
            assertThat(e.getCause(), notNullValue());
            verify(wd).executeScript("op", we2, "value");

        }
    }

    @Test
    public void webelement_operation_should_cause_highlight() {

        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we2.getTagName()).thenReturn("div");
        when(we2.findElement(tagName("span"))).thenReturn(we3);
        when(we3.getTagName()).thenReturn("span");

        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B).span();
            doThrow(new RuntimeException()).when(we3).sendKeys("RAIN_IN_SPAIN");
            span.sendKeys("RAIN_IN_SPAIN");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.div(By.id: idA).div(By.id: idB).span().sendKeys('RAIN_IN_SPAIN')"));
            assertThat(e.getCause(), notNullValue());
            verify(wd).executeScript("op", we3, "value");

        }
    }

    @Test
    public void webelement_operation2_should_cause_highlight() {

        when(wd.findElement(ID_A)).thenReturn(we);
        when(we.getTagName()).thenReturn("div");
        when(we.findElement(ID_B)).thenReturn(we2);
        when(we2.getTagName()).thenReturn("div");
        when(we2.findElement(tagName("span"))).thenReturn(we3);
        when(we3.getTagName()).thenReturn("span");

        try {
            FluentWebElement span = fwd.div(ID_A).div(ID_B).span();
            doThrow(new RuntimeException()).when(we3).getText();
            span.getText().shouldBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.div(By.id: idA).div(By.id: idB).span().getText().shouldBe('foo')"));
            assertThat(e.getCause(), notNullValue());
            verify(wd).executeScript("op", we3, "value");

        }
    }

    @Test
    public void shortest_expression_causing_runtime_exception_should_not_cause_highlight() {

        doThrow(new RuntimeException()).when(wd).findElement(ID_A);

        try {
            fwd.div(ID_A);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.div(By.id: idA)"));
            assertThat(e.getCause(), notNullValue());
            verify(wd).findElement(ID_A);
            verifyNoMoreInteractions(wd);

        }
    }

}
