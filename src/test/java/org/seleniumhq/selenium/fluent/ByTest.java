package org.seleniumhq.selenium.fluent;
/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import org.jmock.Expectations;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByName;
import org.openqa.selenium.internal.FindsByXPath;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ByTest extends MockTestBase {

  @Test
  public void shouldUseFindsByNameToLocateElementByName() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {
      {
        one(driver).findElementByName("cheese");
      }
    });

    By by = By.name("cheese");
    by.findElement(driver);
  }

  @Test
  public void shouldUseFindsByNameToLocateElementsByName() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {
      {
        one(driver).findElementsByName("cheese");
      }
    });

    By by = By.name("cheese");
    by.findElements(driver);
  }

  @Test
  public void shouldMakeXPathForAttributeMatchWithValue() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementByXPath(".//*[@foo = 'bar']");
    }});

    By by = FluentBy.attribute("foo", "bar");

    by.findElement(driver);
  }

  @Test
  public void shouldMakeXPathForAttributeMatchWithValueForMultipleElements() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementsByXPath(".//*[@foo = 'bar']");
    }});

    By by = FluentBy.attribute("foo", "bar");

    by.findElements(driver);
  }

  @Test
  public void shouldMakeXPathForAttributeMatch() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementByXPath(".//*[@foo]");
    }});

    By by = FluentBy.attribute("foo");

    by.findElement(driver);
  }

  @Test
  public void shouldMakeXPathForAttributeMatchForMultipleElements() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementsByXPath(".//*[@foo]");
    }});

    By by = FluentBy.attribute("foo");

    by.findElements(driver);
  }

  @Test
  public void shouldMakeXPathForClassNameExactMatch() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementByXPath(".//*[@class = 'foo']");
    }});

    By by = FluentBy.strictClassName("foo");

    by.findElement(driver);
  }

  @Test
  public void shouldMakeXPathForClassNameExactMatchForMultipleElements() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementsByXPath(".//*[@class = 'foo']");
    }});

    By by = FluentBy.strictClassName("foo");

    by.findElements(driver);
  }

  @Test
  public void shouldMakeXpathFromCompositeOfTagNameAndClassName() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementByXPath(".//foo[contains(concat(' ',normalize-space(@class),' '),' bar ')]");
    }});

    By by = FluentBy.composite(By.tagName("foo"), By.className("bar"));

    by.findElement(driver);
  }

  @Test
  public void shouldMakeXpathFromCompositeOfTagNameAndClassNameForMultipleElements() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementsByXPath(".//foo[contains(concat(' ',normalize-space(@class),' '),' bar ')]");
    }});

    By by = FluentBy.composite(By.tagName("foo"), By.className("bar"));

    by.findElements(driver);
  }

  @Test
  public void shouldMakeXpathFromCompositeOfTagNameAndAttribute() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementByXPath(".//foo[@bar = 'baz']");
    }});

    By by = FluentBy.composite(By.tagName("foo"), FluentBy.attribute("bar", "baz"));

    by.findElement(driver);
  }

  @Test
  public void shouldMakeXpathFromCompositeOfTagNameAndAttributeForMultipleElements() {
    final AllDriver driver = mock(AllDriver.class);

    checking(new Expectations() {{
      one(driver).findElementsByXPath(".//foo[@bar]");
    }});

    By by = FluentBy.composite(By.tagName("foo"), FluentBy.attribute("bar"));

    by.findElements(driver);
  }

  @Test
  public void shouldBarfIfNotCompositeOfTagNameAndClassName() {

    try {
      FluentBy.composite((By[])null);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(),
              is("Cannot make composite with no varargs of Bys"));
    }

    try {
      FluentBy.composite((By[]) new By[0]);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(),
              is("can only do this with By.tagName followed one of By.className or FluentBy.attribute"));
    }

    try {
      FluentBy.composite(By.tagName("foo"), By.xpath("bar"));
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(),
              is("can only do this with By.tagName followed one of By.className or FluentBy.attribute"));
    }

    try {
      FluentBy.composite(By.tagName("foo"), By.className("bar"), By.className("baz"));
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(),
              is("can only do this with By.tagName followed one of By.className or FluentBy.attribute"));
    }

    try {
      FluentBy.composite(By.tagName("foo"));
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(),
              is("can only do this with By.tagName followed one of By.className or FluentBy.attribute"));
    }
  }

  private interface AllDriver
      extends FindsById, FindsByLinkText, FindsByName, FindsByXPath, SearchContext {
    // Place holder
  }
}
