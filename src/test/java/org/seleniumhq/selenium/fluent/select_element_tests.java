package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class select_element_tests {

    static final By ID_A = By.id("idA");
    static final By ID_B = By.id("idB");
    static final By CLASS_C = By.className("classC");
    private StringBuilder sb;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FluentWebDriverImplTest.FAIL_ON_NEXT.set(null);
    }

    @Test
    public void select_functionality() {

        BaseFluentWebDriver fc = fwd.select()
                .select(By.xpath("@foo = 'bar'"))
                .select(By.cssSelector("baz"))
                .deselectAll()
                .selects();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "we1.findElement(By.xpath: .//select[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'select'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'select'\n" +
                        "we3.getAttribute(multiple) -> multiple_value\n" +
                        "we3.findElements(By.tagName: option) -> [we4, we5]\n" +
                        "we4.isSelected() -> true\n" +
                        "we4.click()\n" +
                        "we5.isSelected() -> false\n" +
                        "we3.findElements(By.tagName: select) -> [we6, we7]\n" +
                        "we6.getTagName() -> 'select'\n" +
                        "we7.getTagName() -> 'select'\n"
        ));
    }

    @Test
    public void method_on_select_is_invoked() {

        BaseFluentWebDriver fc = fwd.select().selectByValue("bar");

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "we1.getAttribute(multiple) -> multiple_value\n" +
                        "we1.findElements(By.xpath: .//option[@value = \"bar\"]) -> [we2, we3]\n" +
                        "we2.isSelected() -> true\n" +
                        "we3.isSelected() -> false\n" +
                        "we3.click()\n"
        ));
    }

    @Test
    public void selects_functionality() {
        BaseFluentWebDriver fc = fwd.select()
                .selects(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'select'\n" +
                        "we3.getTagName() -> 'select'\n"
        ));
    }

    @Test
    public void select_mismatched() {
        try {
            fwd.select(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.select(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }

    @Test
    public void selectByValue_delegates() {

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        final Select wdSelect = mock(Select.class);

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
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

        sb.setLength(0);
        FluentSelect select = new FluentSelect(wd, we, "DUMMY_CONTEXT") {
            @Override
            protected Select getSelect() {
                return wdSelect;
            }

            @Override
            protected Void execute(Execution execution, String ctx) {
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

    @Test
    public void wait_should_wait() {

        fwd.select().within(Period.secs(10)).div();

        assertThat(sb.toString(),
                equalTo("wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "wd0.manage().timeouts().implictlyWait(10,SECONDS)\n" +
                        "we1.findElement(By.tagName: div) -> we2\n" +
                        "we2.getTagName() -> 'div'\n" +
                        "wd0.manage().timeouts().implictlyWait(0,SECONDS)\n"));
    }


    @Test
    public void wait_should_reset_even_if_exceptions_are_thrown() {

        try {
            FluentSelect within = fwd.select().within(Period.secs(10));
            FluentWebDriverImplTest.FAIL_ON_NEXT.set(AssertionError.class);
            within.div(); // consequential stub getTagName() with throw
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.select().div()"));
            assertTrue(e.getCause() instanceof AssertionError);
        }

        assertThat(sb.toString(),
                equalTo("wd0.findElement(By.tagName: select) -> we1\n" +
                        "we1.getTagName() -> 'select'\n" +
                        "wd0.manage().timeouts().implictlyWait(10,SECONDS)\n" +
                        // throws here
                        "wd0.manage().timeouts().implictlyWait(0,SECONDS)\n"));
    }


}
