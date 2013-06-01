# FluentSelenium

Refer my "Fluent Selenium Examples Blog Entry":http://paulhammant.com/2013/05/19/fluent-selenium-examples for an actual use.

## Basic Use

WebDriver wd = new FirefoxDriver();
FluentWebDriver fwd = new FluentWebDriver(wd);

fwd.div(id("foo").div(className("bar").button().click();

fwd.span(id("results").getText().shouldBe("1 result");

## Situations where the DOM is changing slowly

fwd.div(id("foo").div(className("bar").within(secs(5)).button().click();

fwd.span(id("results").within(millis(200)).getText().shouldBe("1 result");

## Built-in Assertions

### Strings

Many things return a string (actually a TestableString). Some elements of a page
are designed to have a string representation.  Input fields and spans are obvious,
but any element supports getText() and WebDriver will try to make a chunk of text
that represents that (often with carriage returns).

fwd.div(id("foo").getText().shouldBe("1 bar");
fwd.div(id("foo").getText().shouldNotBe("0 bars");
fwd.div(id("foo").getText().shouldContain("bar");
fwd.div(id("foo").getText().shouldNotContain("error");

Regex is possible too, and it will ignore carriage returns (which Java pre-processes like so \n -> \\n)

fwd.div(id("foo").getText().shouldMatch("\d bar");
fwd.div(id("foo").getText().shouldMatch("[1-9] bar");
fwd.div(id("formErrors").getText().shouldNotMatch("\d errors");

As shown above, you can transparently wait for the think to become true:

fwd.div(id("foo").getText().within(secs(10)).shouldBe("1 bar");

### Others

Any element has a location via getLocation(), which yields a Point
Any element has a size via getSize(), which yields a Dimension
Some elements have boolean from isDisplayed(), isEnabled() and isSelected()

All of these have assertions:

fwd.div(id("foo").getLocation().shouldBe(new Point(1, 1));
fwd.div(id("foo").getLocation().shouldNotBe(new Point(1, 1));

fwd.div(id("foo").getSize().shouldBe(new Dimension(640, 480));
fwd.div(id("foo").getSize().shouldNotBe(new Dimension(640, 480));

fwd.div(id("foo").isEnabled().shouldBe(true);
fwd.div(id("foo").isDisplayed().shouldNotBe(false);

## Locators

WebDriver's own "By" locator mechanism is what is used. Here are examples using that:

  By.id("id")
  By.className("name")
  By.tagName("table")

Class FluentBy adds a few more:

  FluentBy.attribute("ng-model")
  FluentBy.attribute("ng-model", "shopperSelection.payPalPreferred") {
  FluentBy.composite(tagName("table"), className("paymentType"))
  FluentBy.composite(tagName("table"), attribute("ng-click")) {

One more strictClassName is used like so:

  strictClassName("name")

Strict is where there is only one class for that element.  The built-in WebDriver one allows for many classes for an element,
with the one specified amongst them.





