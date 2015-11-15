package org.seleniumhq.selenium.fluent.elements;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.Internal;
import org.seleniumhq.selenium.fluent.Monitor;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class select extends BaseTest {

    @Test
    public void select_functionality() {

        setupExpectationsSingle("select");

        FluentWebElements fe = fwd.select()
                .select(By.xpath("@foo = 'bar'"))
                .select(By.cssSelector("baz"))
                .selects();

        assertThat(fe, notNullValue());
        verificationsSingle("select");
    }

    @Test
    public void method_on_select_is_invoked() {

        when(wd.findElement(By.tagName("select"))).thenReturn(we);
        when(we.getTagName()).thenReturn("select");
        when(we.getTagName()).thenReturn("select");
        when(we.getAttribute("multiple")).thenReturn("true");
        when(we.findElements(By.xpath(".//option[@value = \"bar\"]"))).thenReturn(newArrayList(we2, we3));
        when(we2.isSelected()).thenReturn(true);
        when(we3.isSelected()).thenReturn(false);

        FluentSelect fs = fwd.select().selectByValue("bar");

        assertThat(fs, notNullValue());

        verify(wd).findElement(By.tagName("select"));
        verify(we, times(2)).getTagName();
        verify(we).getAttribute("multiple");
        verify(we).findElements(By.xpath(".//option[@value = \"bar\"]"));
        verify(we2).isSelected();
        verify(we3).isSelected();
        verify(we3).click();
        verifyNoMoreInteractions(wd, we, we2, we3, we4, we5);


    }

    @Test
    public void selects_functionality() {

        setupExpectationsMultiple("select");

        FluentWebElements fe = fwd.select()
                .selects(By.name("qux"));

        assertThat(fe, notNullValue());

        verificationsMultiple("select");

    }

    @Test
    public void select_mismatched() {

        when(wd.findElement(By.linkText("mismatching_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("boo");

        try {
            fwd.select(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.select(By.linkText: mismatching_tag_name)"));
            assertThat(e.getCause().getMessage(), equalTo("tag was incorrect, should have been select but was boo"));
        }

    }

    @Test
    public void selectByValue_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.selectByValue("humphrey");

        verify(wdSelect, times(1)).selectByValue("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void selectByValue_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).selectByValue("humphrey");

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.selectByValue("humphrey");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).selectByValue("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void selectByIndex_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.selectByIndex(444);

        verify(wdSelect, times(1)).selectByIndex(444);

        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void selectByIndex_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).selectByIndex(444);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.selectByIndex(444);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).selectByIndex(444);
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void selectByVisibleText_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.selectByVisibleText("humphrey");

        verify(wdSelect, times(1)).selectByVisibleText("humphrey");

        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void selectByVisibleText_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).selectByVisibleText("humphrey");

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.selectByVisibleText("humphrey");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).selectByVisibleText("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectAll_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.deselectAll();

        verify(wdSelect, times(1)).deselectAll();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectAll_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).deselectAll();

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.deselectAll();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).deselectAll();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectByValue_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.deselectByValue("humphrey");

        verify(wdSelect, times(1)).deselectByValue("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectByValue_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).deselectByValue("humphrey");

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.deselectByValue("humphrey");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).deselectByValue("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectByVisibleText_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.deselectByVisibleText("humphrey");

        verify(wdSelect, times(1)).deselectByVisibleText("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectByVisibleText_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).deselectByVisibleText("humphrey");

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.deselectByVisibleText("humphrey");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).deselectByVisibleText("humphrey");
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deselectByIndex_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.deselectByIndex(444);

        verify(wdSelect, times(1)).deselectByIndex(444);
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void deSelectByIndex_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).deselectByIndex(444);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.deselectByIndex(444);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).deselectByIndex(444);
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void getFirstSelectedOption_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.getFirstSelectedOption();

        verify(wdSelect, times(1)).getFirstSelectedOption();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void getFirstSelectedOption_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).getFirstSelectedOption();

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.getFirstSelectedOption();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).getFirstSelectedOption();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void isMultiple_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.isMultiple();

        verify(wdSelect, times(1)).isMultiple();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void isMultiple_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).isMultiple();

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.isMultiple();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).isMultiple();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void getOptions_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.getOptions();

        verify(wdSelect, times(1)).getOptions();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void getOptions_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).getOptions();

        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.getOptions();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).getOptions();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void getAllSelectedOptions_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        
        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }
        };
        select.getAllSelectedOptions();

        verify(wdSelect, times(1)).getAllSelectedOptions();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }

    @Test
    public void getAllSelectedOptions_wraps_on_failure() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        final WebDriverException fooE = new WebDriverException("foo");
        doThrow(fooE).when(wdSelect).getAllSelectedOptions();

        
        FluentSelect select = new FluentSelect(wd, we, Context.singular(null, "dummy"), new Monitor.NULL(), false) {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void executeAndWrapReThrowIfNeeded(Execution execution, Internal.WebElementHolder currentElement, Context ctx, boolean expectedToBeThere) {
                try {
                    execution.execute();
                    fail("should have barfed");
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    assertTrue(e == fooE);
                }
                throw new RuntimeException("bar");
            }
        };

        try {
            select.getAllSelectedOptions();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("bar"));
        }

        verify(wdSelect).getAllSelectedOptions();
        verifyNoMoreInteractions(wd, we, wdSelect);
    }


}
