package org.seleniumhq.selenium.fluent;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class TestableStringTest {

    Execution<String> fooExecution = new Execution<String>() {
        public String execute() {
            return "foo";
        }
    };

    @Test
    public void stringShouldBeSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(secs(1), fooExecution, ctx).shouldBe("foo");
        try {
            new TestableString(secs(1), fooExecution, ctx).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldBe('bar')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldBeSomethingInNoTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(secs(1), fooExecution, ctx).shouldBe("foo");
        try {
            new TestableString(null, fooExecution, ctx).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldBe('bar')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotBeSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(secs(1), fooExecution, ctx).shouldNotBe("bar");
        try {
            new TestableString(secs(1), fooExecution, ctx).shouldNotBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotBe('foo')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }
    
    @Test
    public void stringShouldContainSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(secs(1), fooExecution, ctx).shouldContain("o");
        try {
            new TestableString(secs(1), fooExecution, ctx).shouldContain("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldContain('a')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(secs(1), fooExecution, ctx).shouldNotContain("a");
        try {
            new TestableString(secs(1), fooExecution, ctx).shouldNotContain("o");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotContain('o')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

}
