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
import static org.junit.Assert.assertThat;

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
    public void url_functionality() {

        TestableString url = fwd.url();

        assertThat(url, notNullValue());
        assertThat(sb.toString(), equalTo("getCurrentUrl():STR#1\n"));
        assertThat("" + url, equalTo("STR#1"));
        assertThat(url.toString(), equalTo("STR#1"));
    }

}
