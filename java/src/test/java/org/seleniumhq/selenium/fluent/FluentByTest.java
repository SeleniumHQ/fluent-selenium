package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class FluentByTest {

    private Field xpathExpression;

    @Before
    public void setup() throws NoSuchFieldException {
        xpathExpression = By.ByXPath.class.getDeclaredField("xpathExpression");
        xpathExpression.setAccessible(true);
    }

    @Test
    public void attribute() throws IllegalAccessException {
        By fooBar = FluentBy.attribute("foo", "bar");
        assertThat(fooBar.toString(), is("FluentBy.attribute: foo = 'bar'"));

        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);
        when(context.findElement(By.xpath(".//*[@foo = 'bar']"))).thenReturn(we);
        WebElement bar = fooBar.findElement(context);
        assertThat(bar, is(we));

        // last of ..

        By lastFooBar = FluentBy.last(fooBar);
        assertThat(lastFooBar.toString(), is("FluentBy.last(FluentBy.attribute: foo = 'bar')"));

        when(context.findElement(By.xpath(".//*[@foo = 'bar' and position() = last()]"))).thenReturn(we);
        WebElement lastBar = fooBar.findElement(context);
        assertThat(lastBar, is(we));
    }

    @Test
    public void not_attribute() throws IllegalAccessException {
        By notFoo = FluentBy.notAttribute("foo");
        String actual = notFoo.toString();
        assertThat(actual, is("FluentBy.notAttribute: foo"));

        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);
        when(context.findElement(By.xpath(".//*[not(@foo)]"))).thenReturn(we);
        WebElement bar = notFoo.findElement(context);

        assertThat(bar, is(we));

        // last of ..

        By lastFooBar = FluentBy.last(notFoo);
        assertThat(lastFooBar.toString(), is("FluentBy.last(FluentBy.notAttribute: foo)"));

        when(context.findElement(By.xpath(".//*[@foo = 'bar' and position() = last()]"))).thenReturn(we);
        WebElement lastBar = notFoo.findElement(context);
        assertThat(lastBar, is(we));
    }

    @Test
    public void attribute_without_value() throws IllegalAccessException {
        By fooBar = FluentBy.attribute("foo");
        assertThat(fooBar.toString(), is("FluentBy.attribute: foo"));

        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        when(context.findElement(By.xpath(".//*[@foo]"))).thenReturn(we);
        WebElement bar = fooBar.findElement(context);
        assertThat(bar, is(we));

        when(context.findElements(By.xpath(".//*[@foo]"))).thenReturn(newArrayList(we, we2));
        List<WebElement> bars = fooBar.findElements(context);
        assertThat(bars.get(0), is(we));

    }

    @Test
    public void last() throws IllegalAccessException {
        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);

        By lastFooBar = FluentBy.last();
        assertThat(lastFooBar.toString(), is("FluentBy.last()"));

        when(context.findElement(By.xpath(".//*[position() = last()]"))).thenReturn(we);
        WebElement lastBar = lastFooBar.findElement(context);
        assertThat(lastBar, is(we));
    }

    @Test
    public void strict_class_name() throws IllegalAccessException {
        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        By scn = FluentBy.strictClassName("blort");
        assertThat(scn.toString(), is("FluentBy.strictClassName: blort"));

        when(context.findElement(By.className("blort"))).thenReturn(we);
        WebElement blort = scn.findElement(context);
        assertThat(blort, is(we));

        when(context.findElements(By.className("blort"))).thenReturn(newArrayList(we, we2));
        List<WebElement> blorts = scn.findElements(context);
        //verifyNoMoreInteractions(we,we2, context);
        assertThat(blorts.get(0), is(we));

    }

    @Test
    public void composite_classname() throws IllegalAccessException {
        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        By aB = FluentBy.composite(new By.ByTagName("a"), new By.ByClassName("b"));
        assertThat(aB.toString(), is("FluentBy.composite([By.tagName: a, By.className: b])"));

        when(context.findElement(By.xpath(".//a[contains(concat(' ',normalize-space(@class),' '),' b ')]"))).thenReturn(we);
        WebElement blort = aB.findElement(context);
        assertThat(blort, is(we));

        when(context.findElements(By.xpath(".//a[contains(concat(' ',normalize-space(@class),' '),' b ')]"))).thenReturn(newArrayList(we, we2));
        List<WebElement> blorts = aB.findElements(context);
        assertThat(blorts.get(0), is(we));
    }

    @Test
    public void composite_attribute() throws IllegalAccessException {
        FindsByXPathSearchContext context = mock(FindsByXPathSearchContext.class);
        WebElement we = mock(WebElement.class);
        WebElement we2 = mock(WebElement.class);

        By aB = FluentBy.composite(new By.ByTagName("a"), new FluentBy.ByAttribute("b", null));
        assertThat(aB.toString(), is("FluentBy.composite([By.tagName: a, FluentBy.attribute: b])"));

        when(context.findElement(By.xpath(".//a[@b]"))).thenReturn(we);
        WebElement blort = aB.findElement(context);
        //verifyNoMoreInteractions(context);
        assertThat(blort, is(we));

        when(context.findElements(By.xpath(".//a[@b]"))).thenReturn(newArrayList(we, we2));
        List<WebElement> blorts = aB.findElements(context);
        assertThat(blorts.get(0), is(we));

    }

    private static interface FindsByXPathSearchContext extends SearchContext {
    }
}
