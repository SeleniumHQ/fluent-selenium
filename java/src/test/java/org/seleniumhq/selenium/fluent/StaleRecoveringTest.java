package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static junit.framework.TestCase.fail;

public class StaleRecoveringTest {

    @Test(timeout = 60000)
    public void vanillaWebDriverShouldCatchStaleExceptionAndBeAbleToCarryOnAnyway() {

        FirefoxDriver driver = new FirefoxDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/2915/index.html");
        WebElement elem;
        try {
            elem = driver.findElement(By.id("cheddarCheeseLoginPassword"));
            elem.sendKeys("bar");
            getAndVerify(elem);
            fail("should have barfed");
        } catch(StaleElementReferenceException sere) {
            elem = driver.findElement(By.id("cheddarCheeseLoginPassword"));
            getAndVerify(elem);
        } finally {
            driver.quit();
        }
    }

    private void getAndVerify(WebElement elem) {
        String val = elem.getAttribute("value");
        assertEquals("bar", val);
    }

    @Test
    public void knownStaleCaseCanBeOvercomeInFluentWebDriverToo() {
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
