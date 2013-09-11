package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.seleniumhq.selenium.fluent.Period.millis;

public class IfInvisibleWaitUpToTest {

    @Test
    public void ifInvisibleWaitUpTo_can_time_out_before_actually_visible() {
        FirefoxDriver driver = new FirefoxDriver();

        // fork of http://www.w3schools.com/jquery/tryit.asp?filename=tryjquery_eff_delay
        driver.get("http://seleniumhq.github.io/fluent-selenium/slowlyVisible.html");

        FluentWebDriver fwd = new FluentWebDriver(driver);

        fwd.button().click();
        long start = System.currentTimeMillis();
        try {
            FluentWebElement div5 = fwd.div(By.id("div5")).ifInvisibleWaitUpTo(millis(500));
            boolean displayed = div5.isDisplayed().value();
            assertThat(displayed, equalTo(false));
            assertThat((int) (System.currentTimeMillis() - start), lessThan(799));
            assertThat((int) (System.currentTimeMillis() - start), greaterThan(500));

        } finally {
            driver.quit();
        }
    }

    @Test
    public void ifInvisibleWaitUpTo_can_wait_long_enough() {
        FirefoxDriver driver = new FirefoxDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/slowlyVisible.html");

        FluentWebDriver fwd = new FluentWebDriver(driver);

        fwd.button().click();
        long start = System.currentTimeMillis();
        try {
            boolean displayed = fwd.div(By.id("div3")).ifInvisibleWaitUpTo(millis(900)).isDisplayed().value();
            assertThat(displayed, equalTo(true));
            assertThat((int) (System.currentTimeMillis() - start), lessThan(950));
            assertThat((int) (System.currentTimeMillis() - start), greaterThan(800));
        } finally {
            driver.quit();
        }
    }
}
