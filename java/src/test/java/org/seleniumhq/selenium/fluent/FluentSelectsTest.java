package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.internal.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FluentSelectsTest {

    WebDriver wd;
    List<FluentWebElement> wes;
    Context ctx;
    FluentSelect fs0;
    FluentSelect fs1;

    @Before
    public void setup() {
        wd = mock(WebDriver.class);
        fs0 = mock(FluentSelect.class);
        fs1 = mock(FluentSelect.class);
        wes = asList((FluentWebElement) fs0, (FluentWebElement) fs1);
        ctx = Context.singular(null, "foo", By.id("bar"));
    }

    @Test
    public void testClick() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        FluentSelects click = fs.click();

        assertThat(click, not(sameInstance(fs)));
        assertThat(click.context.toString(), is(ctx + ".click()"));

    }

    @Test
    public void testClearField() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        FluentSelects click = fs.clearField();

        assertThat(click, not(sameInstance(fs)));
        assertThat(click.context.toString(), is(ctx + ".clearField()"));

    }

    @Test
    public void testSubmit() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        FluentSelects click = fs.submit();

        assertThat(click, not(sameInstance(fs)));
        assertThat(click.context.toString(), is(ctx + ".submit()"));
    }

    @Test
    public void testSendKeys() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        FluentSelects click = fs.sendKeys("abc");

        assertThat(click, not(sameInstance(fs)));
        assertThat(click.context.toString(), is(ctx + ".sendKeys('abc')"));
    }

    @Test
    public void testFilter() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        FluentSelects els = fs.filter(new FluentMatcher() {
            public boolean matches(FluentWebElement webElement, int ix) {
                return true;
            }
        });

        assertThat(els.size(), equalTo(2));
        assertThat(els.get(0), sameInstance(fs0));
        assertThat(els.get(1), sameInstance(fs1));

    }

    @Test
    public void testFirst() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        final int[] i = new int[1];
        i[0] = -1;

        FluentSelect el = fs.first(new FluentMatcher() {
            public boolean matches(FluentWebElement webElement, int ix) {
                i[0]++;
                assertThat(ix, is(i[0]));
                return true;
            }
        });

        assertThat(el, sameInstance(fs0));
    }

    @Test
    public void testLast() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        final int[] i = new int[1];
        i[0] = -1;

        FluentSelect el = fs.last(new FluentMatcher() {
            public boolean matches(FluentWebElement webElement, int ix) {
                i[0]++;
                assertThat(ix, is(i[0]));
                return true;
            }
        });

        assertThat(el, sameInstance(fs1));
    }

    @Test
    public void map() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        Map<Integer, FluentSelect> m = fs.map(new FluentWebElementMap<Integer, FluentSelect>() {
            @Override
            public void map(FluentWebElement elem, int ix) {
                put(ix, (FluentSelect) elem);
            }
       });

        assertThat(m.size(), is(2));
        assertThat(m.get(0), equalTo(fs0));
        assertThat(m.get(1), equalTo(fs1));
    }

    @Test
    public void each() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);
        when(fs0.getPeriod()).thenReturn(Period.secs(0));
        when(fs1.getPeriod()).thenReturn(Period.secs(1));

        final Map<Integer, Period> periods = new HashMap<Integer, Period>();

        FluentSelects fs2 = fs.each(new FluentWebElementVistor() {
            public void visit(FluentWebElement webElement, int ix) {
                periods.put(ix, webElement.getPeriod());
            }
        });

        assertThat(fs2, is(fs));
        assertThat(periods.entrySet().toString(), equalTo(new HashMap<Integer, Period>() {{
            put(0, Period.secs(0));
            put(1, Period.secs(1));
        }}.entrySet().toString()));
    }

    @Test
    public void testGet() throws Exception {
        FluentSelects fs = new FluentSelects(wd, wes, ctx, new Monitor.NULL(), false);

        FluentSelect el = fs.get(1);

        assertThat(el, sameInstance(fs1));
    }

}
