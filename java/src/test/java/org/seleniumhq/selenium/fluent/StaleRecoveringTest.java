package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static junit.framework.TestCase.fail;

public class StaleRecoveringTest {

    @Test(timeout = 60000)
    public void vanillaWebDriverShouldCatchStaleExceptionAndBeAbleToCarryOnAnyway() {

        // We have to simulate a stale element exception as
        // we can't find a real-world example
        FirstGetAttributeIsStaleDriver driver = new FirstGetAttributeIsStaleDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/7753/index.html");
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

        assertEquals(2, driver.countOfGetAttribute[0]);

    }

    private void getAndVerify(WebElement elem) {
        String val = elem.getAttribute("value");
        assertEquals("bar", val);
    }

    @Test
    public void knownStaleCaseCanBeOvercomeInFluentWebDriverToo() {

        // We have to simulate a stale element exception as
        // we can't find a real-world example
        FirstGetAttributeIsStaleDriver driver = new FirstGetAttributeIsStaleDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/7753/index.html");

        FluentWebDriver fwd = new FluentWebDriver(driver);

        try {
            fwd.input(By.id("cheddarCheeseLoginPassword")).sendKeys("bar")
                    .getAttribute("value").shouldBe("bar").shouldBe("bar");
        } finally {
            driver.quit();
        }

        assertEquals(2, driver.countOfGetAttribute[0]);

    }

    private static class FirstGetAttributeIsStaleDriver extends FirefoxDriver {
        public final int[] countOfGetAttribute = {0};

        @Override
        public WebElement findElementById(String using) {
            final WebElement we = super.findElementById(using);
            return new WebElement() {
                public void click() {
                    we.click();
                }

                public void submit() {
                    we.submit();
                }

                public void sendKeys(CharSequence... keysToSend) {
                    we.sendKeys(keysToSend);
                }

                public void clear() {
                    we.clear();
                }

                public String getTagName() {
                    return we.getTagName();
                }

                public String getAttribute(String name) {
                    if (countOfGetAttribute[0] == 0) {
                        countOfGetAttribute[0]++;
                        throw new StaleElementReferenceException("boop");
                    }
                    countOfGetAttribute[0]++;
                    return we.getAttribute(name);
                }

                public boolean isSelected() {
                    return we.isSelected();
                }

                public boolean isEnabled() {
                    return we.isEnabled();
                }

                public String getText() {
                    return we.getText();
                }

                public List<WebElement> findElements(By by) {
                    return we.findElements(by);
                }

                public WebElement findElement(By by) {
                    return we.findElement(by);
                }

                public boolean isDisplayed() {
                    return we.isDisplayed();
                }

                public Point getLocation() {
                    return we.getLocation();
                }

                public Dimension getSize() {
                    return we.getSize();
                }

                public String getCssValue(String propertyName) {
                    return we.getCssValue(propertyName);
                }
            };
        }
    }
}
