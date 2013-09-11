package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StaleRecoveringTest {

    @Test
    public void knownStaleCaseCanBeOvercome() {
        FirefoxDriver driver = new FirefoxDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/2915/index.html");

        FluentWebDriver fwd = new FluentWebDriver(driver);

        try {
            fwd.input(By.id("cheddarCheeseLoginPassword")).sendKeys("bar")
                    .getAttribute("value").shouldBe("bar").shouldBe("bar");
        } finally {
            driver.quit();
        }
    }
}
