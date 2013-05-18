package org.seleniumhq.selenium.fluent;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class TestableStringTest {

    private static final Execution<String> FOO_EXECUTION = new Execution<String>() {
        public String execute() {
            return "foo";
        }
    };

    private static final Execution<String> MARY_EXECUTION = new Execution<String>() {
        public String execute() {
            return "Mary Has 12 Little Lambs";
        }
    };

    @Test
    public void stringShouldBeSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldBe("foo");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldBe('bar')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldBeSomethingWithinSomeTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldBe("foo");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldBe('bar')"));
            assertThat(e.getCause().getMessage(), equalTo("(after 1000 ms)\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotBeSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldNotBe("bar");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldNotBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotBe('foo')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotBeSomethingWithinSomeTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotBe("bar");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldNotBe('foo')"));
            assertThat(e.getCause().getMessage(), equalTo("(after 1000 ms)\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }
    
    @Test
    public void stringShouldContainSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldContain("o");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldContain("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldContain('a')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldContainSomethingWithinSomeTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldContain("o");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldContain("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldContain('a')"));
            assertThat(e.getCause().getMessage(), equalTo("(after 1000 ms)\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldNotContain("a");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldNotContain("o");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotContain('o')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomethingWithinSomeTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotContain("a");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotContain("o");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldNotContain('o')"));
            assertThat(e.getCause().getMessage(), equalTo("(after 1000 ms)\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldRegexMatchSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).shouldMatch(".* Has \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).shouldMatch(".* blort \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldMatch('.* blort \\d\\d.*')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: a string matching /.* blort \\d\\d.*/\n     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    @Test
    public void stringShouldNotRegexMatchSomething() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).shouldNotMatch(".* blort \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).shouldNotMatch(".* Has \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotMatch('.* Has \\d\\d.*')"));
            assertThat(e.getCause().getMessage(), equalTo("\n" +
                    "Expected: not a string matching /.* Has \\d\\d.*/\n" +
                    "     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    @Test
    public void stringShouldRegexMatchSomethingWithinSomeTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldMatch(".* Has \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldMatch(".* blort \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldMatch('.* blort \\d\\d.*')"));
            assertThat(e.getCause().getMessage(), equalTo("(after 1000 ms)\nExpected: a string matching /.* blort \\d\\d.*/\n     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    @Test
    public void stringShouldNotRegexMatchSomethingWithinSomeTime() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldNotMatch(".* blort \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldNotMatch(".* Has \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldNotMatch('.* Has \\d\\d.*')"));
            assertThat(e.getCause().getMessage(), equalTo("(after 1000 ms)\n" +
                    "Expected: not a string matching /.* Has \\d\\d.*/\n" +
                    "     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

}
