package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FluentizeTest {

    @Test
    public void findElementViaWebDriverCanBeFluentized() {
        ChromeDriver driver = new ChromeDriver();

        // fork of http://www.w3schools.com/jquery/tryit.asp?filename=tryjquery_eff_delay
        driver.get("http://seleniumhq.github.io/fluent-selenium/basic.html");

        FluentWebDriver fwd = new FluentWebDriver(driver);

        // Classic WebDriver
        WebElement div = driver.findElement(By.tagName("div"));

        // carry on with FluentSelenium
        fwd.fluentize(div).span().getText().shouldBe("Hello");

        driver.close();

    }

}
