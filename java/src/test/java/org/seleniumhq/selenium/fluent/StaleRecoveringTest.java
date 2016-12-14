package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class StaleRecoveringTest {

    @Test(timeout = 60000)
    public void vanillaWebDriverShouldCatchStaleExceptionAndBeAbleToCarryOnAnyway() {

        // We have to simulate a stale element exception as
        // we can't find a real-world example
        FirstGetAttributeIsStaleDriver driver = new FirstGetAttributeIsStaleDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/7753/index.html");
        assertEquals(false, driver.staleElementThrown[0]);

        WebElement elem;
        try {
            elem = driver.findElement(By.id("cheddarCheeseLoginPassword"));
            elem.sendKeys("bar");
            getAttrAndVerify(elem); // causes staleness - see FirstGetAttributeIsStaleDriver
            fail("should have barfed");
        } catch(StaleElementReferenceException sere) {
            elem = driver.findElement(By.id("cheddarCheeseLoginPassword"));
            getAttrAndVerify(elem);
        } finally {
            driver.quit();
        }

        assertEquals(2, driver.countOfGetAttribute[0]);
        assertEquals(true, driver.staleElementThrown[0]);

    }

    private void getAttrAndVerify(WebElement elem) {
        String val = elem.getAttribute("value");
        try {
            assertEquals("bar", val);
        } catch (AssertionError e) {
            assertEquals("I can Haz Passwordbar", val);
        }
    }

    @Test
    public void knownStaleCaseCanBeOvercomeInFluentWebDriverToo() {

        // We have to simulate a stale element exception as
        // we can't find a real-world example
        FirstGetAttributeIsStaleDriver driver = new FirstGetAttributeIsStaleDriver();

        driver.get("http://seleniumhq.github.io/fluent-selenium/7753/index.html");
        assertEquals(false, driver.staleElementThrown[0]);

        FluentWebDriver fwd = new FluentWebDriver(driver);

        try {
            String val = fwd.input(By.id("cheddarCheeseLoginPassword"))
                    .sendKeys(Keys.chord(Keys.CONTROL, "a") + "bar")
                    .getAttribute("value").toString();
            try {
                assertThat(val, equalTo("bar"));
            } catch (AssertionError e) {
                // unrelated to the staleness, sometimes the field isn't getting overwritten in the sendKeys()
                assertThat(val, equalTo("barI can Haz Password"));
            }
        } finally {
            driver.quit();
        }

        assertEquals(2, driver.countOfGetAttribute[0]);
        assertEquals(true, driver.staleElementThrown[0]);

    }

    private static class FirstGetAttributeIsStaleDriver extends ChromeDriver {
        public final int[] countOfGetAttribute = {0};
        public final boolean[] staleElementThrown = {false};

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
                        staleElementThrown[0] = true;
                        throw new StaleElementReferenceException("boop");
                    } else {
                        countOfGetAttribute[0]++;
                        return we.getAttribute(name);
                    }
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

                public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException { return we.getScreenshotAs(outputType); }

                public Rectangle getRect() {
                    return we.getRect();
                }
            };
        }
    }
}
