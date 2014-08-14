import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.monitors.HighlightOnError;

import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.By.id;

public class HighlightOnErrorForDisabledElementsTest {

    private final String searchId = "DisabledTest";

    protected JSWebDriver wd;
    protected WebElement we;
    protected HighlightOnError.ForDisabledElements highlight;

    public static interface JSWebDriver extends WebDriver, JavascriptExecutor {
    }

    @Before
    public void setup() {
        wd = mock(JSWebDriver.class);
        we = mock(WebElement.class);
        when(wd.findElement(id(searchId))).thenReturn(we);
        highlight = new HighlightOnError.ForDisabledElements(wd);
    }

    @Test
    public void disabledElementShouldGetItsHtmlUpdated() {
        when(we.isDisplayed()).thenReturn(false);
        highlight.executeScript(we);

        verify(wd).executeScript(contains("innerHTML"), same(we), contains("This element was not being displayed"));
    }

    @Test
    public void enabledElementShouldNotGetItsHtmlUpdated() {
        when(we.isDisplayed()).thenReturn(true);
        highlight.executeScript(we);

        verify(wd, never()).executeScript(contains("innerHTML"), same(we), contains("This element was not being displayed"));
    }

}
