package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SlowAngularPageTest {

    @Test
    public void slowAngularPageTriesToPushFluentSeleniumsButtons() {
        FirefoxDriver driver = new FirefoxDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/vvvvSlowRendering.html");

        FluentWebDriver fwd = new FluentWebDriver(driver);

        try {
            fwd.input(By.id("cheddarCheeseLoginPassword")).sendKeys("bar")
                    .getAttribute("value").shouldBe("bar").shouldBe("bar");
        } finally {
            driver.quit();
        }
    }
}
