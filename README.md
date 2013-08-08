# FluentSelenium

Refer my [Fluent Selenium Examples Blog Entry](http://paulhammant.com/2013/05/19/fluent-selenium-examples)
about this, or the project that showcases Fluent Selenium - [Fluent Selenium Examples](https://github.com/paul-hammant/fluent-selenium-examples).

To use via Maven:

```xml
<dependency>
   <groupId>org.seleniumhq.selenium.fluent</groupId>
   <artifactId>fluent-selenium</artifactId>
   <version>1.11.1</version>
   <scope>test</scope>
</dependency>

<!-- you need to choose a hamcrest version that works for you too -->
<dependency>
   <groupId>org.hamcrest</groupId>
   <artifactId>hamcrest-all</artifactId>
   <version>1.3</version>
   <scope>test</scope>
</dependency>

<!-- If you're needing Coda Hale's Metrics integration (optional) -->
<dependency>
   <groupId>com.codahale.metrics</groupId>
   <artifactId>metrics-core</artifactId>
   <version>3.0.0</version>
</dependency>
```


Bear in mind that the FluentSelenium maven module has a transitive dependency on Selenium2. You may want to override the version for your project. You'll need an exclusion for FluentSelenium, and an explicit dependency for Selenium2.

For non Maven build systems, [download it yourself](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22fluent-selenium%22)

## Basic Use

```java
WebDriver wd = new FirefoxDriver();
FluentWebDriver fwd = new FluentWebDriver(wd);

fwd.div(id("foo")).div(className("bar")).button().click();

fwd.span(id("results")).getText().shouldBe("1 result");
```

## Situations where the DOM is changing slowly

### within()

There's a "within" capability in the fluent language. It will retry for an advised period,
giving the fluent expression a chance to get past a slowly appearing node:

```java
fwd.div(id("foo")).div(className("bar")).within(secs(5)).button().click();

fwd.span(id("results").within(millis(200)).getText().shouldBe("123");
```

This will throw an exception **after** the elapsed time, if it still hasn't appeared in the DOM.

### without()

There's a "without" capability in the fluent language. It will retry for an advised period,
giving the fluent expression observe that something in the page should disappear:

```java
fwd.div(id("foo")).div(className("bar")).without(secs(5)).button();
```

The element disappearing in the page means that the fluent expression stops
there. Also, disappear means that the locator used to find the element does
not find it, thus the following does not mean that there's no span element,
it just means that there is no span element with a class of "baz":

```java
fwd.div(id("foo")).div(className("bar")).without(secs(5)).span(className("baz"));
```

This will throw an exception **after** the elapsed time, if it still hasn't **disappeared** from the DOM.

### Stale Elements

WebDriver, by default, does not handle <code>findElement</code> traversals from elements that have
gone stale transparently. It prefers to throw <code>StaleElementReferenceException</code>, which you
have to catch and then do something with. Retry is one option. FluentSelenium has retry
capability:

```java
new RetryAfterStaleElement() {
    public void toRetry() {
        div(id("thirdAddress")).div(className("fromto-column")).getText().toString();
    }
}.stopAfter(secs(8));
```

In this example, the element can go stale any amount of times in eight seconds, and the whole
traversal is restarted again and again.  If you're trying to store values, you'll have a
problem with Java's inner-class rules, and have to **use member fields** or do dirty tricks like:

```java
final String selectedFlight[] = new String[1];
new RetryAfterStaleElement() {
    public void toRetry() {
        selectedFlight[0] = div(className("fromto-column")).getText().toString();
    }
}.stopAfter(secs(8));
```
Use of the one element array is the dirty trick, because of the need for final.   

FluentSelenium can recover from a subset of <code>StaleElementReferenceException</code> situations. 
If the item going stale is the one that is leaf-most in your fluent expression, then it can be recovered automatically (and silently). This is a one-time deal though - if it persistent in its staleness after recovery, then the exception is throw. Recovery means finding it again in the DOM, relative to its parent with the same locator. In the case above, the "fromto-column" div being stale can be recovered automatically - even during the <code>getText()</code>. The "thirdAddress" div cannot be, at least when execution has transferred to the next <code>div()</code>.

## Built-in Assertions

### String Assertions

Many things return a string (actually a TestableString). Some elements of a page
are designed to have a string representation.  Input fields and spans are obvious,
but any element supports getText() and WebDriver will try to make a chunk of text
that represents that (often with carriage returns).

```java
fwd.div(id("foo")).getText().shouldBe("1 bar");
fwd.div(id("foo")).getText().shouldNotBe("0 bars");
fwd.div(id("foo")).getText().shouldContain("bar");
fwd.div(id("foo")).getText().shouldNotContain("error");
```

Regex is possible too, and it will ignore carriage returns (which Java pre-processes like so \n -> \\\n)

```java
fwd.div(id("foo")).getText().shouldMatch("\d bar");
fwd.div(id("foo")).getText().shouldMatch("[1-9] bar");
fwd.div(id("formErrors")).getText().shouldNotMatch("\d errors");
```

As shown above, you can transparently wait for the thing to become true:

```java
fwd.div(id("foo")).getText().within(secs(10)).shouldBe("1 bar");
```

The assertion is retried for the advised period.

### Non-String Assertions

Any element has a location via getLocation(), which yields a Point
Any element has a size via getSize(), which yields a Dimension
Some elements have boolean from isDisplayed(), isEnabled() and isSelected()

All of these have assertions:

```java
fwd.div(id("foo")).getLocation().shouldBe(new Point(1, 1));
fwd.div(id("foo")).getLocation().shouldNotBe(new Point(1, 1));

fwd.div(id("foo")).getSize().shouldBe(new Dimension(640, 480));
fwd.div(id("foo")).getSize().shouldNotBe(new Dimension(640, 480));

fwd.div(id("foo")).isEnabled().shouldBe(true);
fwd.div(id("foo")).isDisplayed().shouldBe(false);
```

Like for Strings, you can transparently wait for the thing to become true:

```java
fwd.div(id("foo")).isDisplayed().within(secs(10)).shouldBe(true);
```

The assertion is retried for the advised period.

## Locating Elements

WebDriver's own "By" locator mechanism is what is used. Here are examples using that:

```java
by = By.id("id")
by = By.className("name")
by = By.tagName("table")
```

Class FluentBy adds a few more:

```java
by = FluentBy.attribute("ng-model")
by = FluentBy.attribute("ng-model", "shopperSelection.payPalPreferred") {
by = FluentBy.composite(tagName("table"), className("paymentType"))
by = FluentBy.composite(tagName("table"), attribute("ng-click")) {
```

One more strictClassName is used like so:

```java
by = FluentBy.strictClassName("name")
```

Strict is where there is only one class for that element. The built-in WebDriver one allows
for many classes for an element, with the one specified amongst them.

If an locator cannot find the element in the DOM, then an exception - 'FluentExecutionStopped' - is thrown (see below).


# Multiple elements

Just like WebDriver, FluentSelenium can return a collection of Elements matching a locator:

```java
FluentWebElements elems = fwd.div(id("foo")).div(className("bar")).buttons();
elems = fwd.div(id("foo")).divs(className("bar"));
elems = fwd.divs(id("foo");
```

Look at the pluralization of the methods above, and that it only makes sense if
it's the last in the fluent expression.

## Fluently traversing through multiple elements:

Use a FluentMatcher instance (which is just a predicate)

```java
FluentMatcher fm = new MyIntricateFluentMatcher();
// click on first matching one...
fwd.inputs(className("bar").first(fm).click();

// click on all matching matching ones...
fwd.inputs(className("bar").filter(fm).click();
```

There are no instances of FluentMatcher built in, other than CompositeFluentMatcher.

# Exceptions

Obviously you want tests using FluentSelenium to pass. Getting tests to be stable has also been a
historical challenge for the Selenium world, but a real failure of previously working test, is worth
debugging (before or after a developer commit that may have broken the build).

Fluent-Selenium throws 'FluentExecutionStopped' like so:

```
      "WebDriver exception during invocation of : ?.div(By.className: item-treasury-info-box')).h3()"
```

That exception's <code>getCause()</code> will be the WebDriverException derivative that happened during
the <code>h3()</code> invocation -  implicitly before any subsequent operation like click().  That could well be 'NoSuchElementException' for when an element was not found.

## Alternate boolean handling of missing elements.

Normal operation is for FluentSelenium to throw 'FluentExecutionStopped' wrapping 'NoSuchElementException' for the root cause.

You can receive true/false instead like so:

```java
boolean isMissing = fwd.hasMissing().div(id("foo"))
boolean isPresent = fwd.has().div(id("foo"))
```

# Monitoring

Fluent Selenium can generate monitors failing interactions with the browser. It can also see what fluent operation were started/ended. 
Refer the [Monitor](https://github.com/SeleniumHQ/fluent-selenium/blob/master/src/main/java/org/seleniumhq/selenium/fluent/Monitor.java) interface.

You specify a monitor choice by using the right constructor for FluentWebDriver (and pass in a Monitor instance). There's a default monitor that does nothing, so you don't have to choose a constructor that uses a monitor.

We have three implementations presently:

## TakesScreenshot 

When a 'FluentExecutionStopped' failure happens, you can get automatic screenshots.  In the case of running from JUnit or TestNG under Maven control do the following, to get automatic Test-Class name & Method name in the file-name of the PNG:

```java
ffd = new FirefoxDriver();
ssoe = new ScreenShotOnError.WithUnitTestFrameWorkContext(ffd, OneOfYourClasses.class, "test-classes", "surefire-reports/");
fwd = new FluentWebDriver(ffd, ssoe);
```

If you're not wanting that JUnit/TestNG automatic file naming, do this instead:

```java
ffd = new FirefoxDriver();
ssoe = new ScreenShotOnError(ffd, OneOfYourClasses.class, "test-classes", "surefire-reports/");
fwd = new FluentWebDriver(ffd, ssoe);

ssoe.setContext("something_that_has_meaning_in_a_file_name")
div(id("foo")).click();
ssoe.setContext("something_else_that_has_meaning_in_a_file_name")
input(id("bar")).sendKeys("abc");
```

## Coda Hale's Metrics library

Also shown here is how to hook that up to a JUnit4 suite running under Maven.

The separate listener class:

```java
public class MyRunListener extends RunListener {
    public static final CodaHaleMetricsMonitor codahaleMetricsMonitor = new CodaHaleMetricsMonitor("com.paulhammant.fluentSeleniumExamples.");
    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        final ConsoleReporter reporter = ConsoleReporter.forRegistry(codahaleMetricsMonitor.getMetrics())
                .convertRatesTo(TimeUnit.MILLISECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .outputTo(System.out)
                .build();
        reporter.report();
    }
	// More likely, you'd send stats to Graphite (etc).
}
```

Hooking that into each/all FluentWebDriver usages:

```java
public class Home extends FluentWebDriver {
    public Home(WebDriver delegate) {
        super(delegate, MyRunListener.codahaleMetricsMonitor);
    }
	// etc
}

// or the more conventional non inner-class style:

fwd = new FluentWebDriver(webDriver, MyRunListener.codahaleMetricsMonitor);
```

And in Maven's pom.xml:

```xml
<build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
          <properties>
            <property>
              <name>listener</name>
              <value>com.example.MyRunListener</value>
            </property>
          </properties>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

This will spit our stats in the log like so, that require some interpretation:

```text
package.MyClass.aMethod:div(By.className: aClassName)
             count = 2
         mean rate = 0.00 calls/millisecond
     1-minute rate = 0.00 calls/millisecond
     5-minute rate = 0.00 calls/millisecond
    15-minute rate = 0.00 calls/millisecond
               min = 31.95 milliseconds
               max = 36.66 milliseconds
              mean = 34.31 milliseconds
            stddev = 3.33 milliseconds
            median = 34.31 milliseconds
              75% <= 36.66 milliseconds
              95% <= 36.66 milliseconds
              98% <= 36.66 milliseconds
              99% <= 36.66 milliseconds
            99.9% <= 36.66 milliseconds
```
[There's a fuller example of stats in the 'Fluent Selenium Examples' project](https://github.com/paul-hammant/fluent-selenium-examples/blob/master/metrics.out)

Coda Hale's Metrics library has other [reporters you could attach](http://metrics.codahale.com/manual/core/#reporters).

