package org.seleniumhq.selenium.fluent.ops;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.TestableString;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class url extends BaseTest {

    private StringBuilder sb;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);
    }

    @Test
    public void button_functionality() {

        TestableString url = fwd.url();

        assertThat(url, notNullValue());
        assertThat(sb.toString(), equalTo("getCurrentUrl():STR#1\n"));
        assertThat("" + url, equalTo("STR#1"));
        assertThat(url.length(), equalTo(5));
        assertThat(url.charAt(2), equalTo('R'));
        assertTrue(url.equals("STR#1"));
        assertFalse(url.equals("sdkfjhsdkjfh"));
        assertThat(url.hashCode(), equalTo("STR#1".hashCode()));
        assertThat(url.subSequence(1, 3), equalTo(cs("TR")));
        assertThat(url.toString(), equalTo("STR#1"));
        assertThat(url.replace("S", "s"), equalTo("sTR#1"));
    }

}
