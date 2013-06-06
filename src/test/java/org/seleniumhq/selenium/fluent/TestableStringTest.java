package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;
import org.seleniumhq.selenium.fluent.internal.TestableString;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
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

    private static final Execution<String> MARY_EXECUTION_WITH_NEWLINES = new Execution<String>() {
        public String execute() {
            return "Mary Has \n12\n Little Lambs";
        }
    };

    @Test
    public void stringShouldBeSomething() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldBe("foo");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldBe('bar')"));
            assertThat(getCauseMessage(e), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldBeSomethingWithinSomeTime() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldBe("foo");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldBe('bar')"));
            assertThat(getCauseMessage(e), equalTo("(after 1000 ms)\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void unsupportedOperationExceptionShouldBeReThrownAsIs() {
        Context ctx = Context.singular(null, "dummy2");
        try {
            new TestableString(new Execution<String>() {
                public String execute() {
                    throw new UnsupportedOperationException("oops");
                }
            }, ctx).within(secs(1)).shouldBe("bar");
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("oops"));

        }
    }

    @Test
    public void runtimeExceptionShouldBeWrappedAndReThrown() {
        Context ctx = Context.singular(null, "dummy2");
        try {
            new TestableString(new Execution<String>() {
                public String execute() {
                    throw new RuntimeException("oops");
                }
            }, ctx).within(secs(1)).shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("RuntimeException during invocation of: ?.dummy2().within(secs(1)).shouldBe('bar')"));
            // TODO, no time recorded.
            assertThat(e.getCause().getMessage(), equalTo("oops"));
        }
    }

    @Test
    public void stringShouldNotBeSomething() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldNotBe("bar");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldNotBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotBe('foo')"));
            assertThat(getCauseMessage(e), equalTo("\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotBeSomethingWithinSomeTime() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotBe("bar");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldNotBe('foo')"));
            assertThat(getCauseMessage(e), equalTo("(after 1000 ms)\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }
    
    @Test
    public void stringShouldContainSomething() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldContain("o");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldContain("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldContain('a')"));
            assertThat(getCauseMessage(e), equalTo("\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldContainSomethingWithinSomeTime() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldContain("o");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldContain("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldContain('a')"));
            assertThat(getCauseMessage(e), equalTo("(after 1000 ms)\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomething() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).shouldNotContain("a");
        try {
            new TestableString(FOO_EXECUTION, ctx).shouldNotContain("o");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotContain('o')"));
            assertThat(getCauseMessage(e), equalTo("\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomethingWithinSomeTime() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotContain("a");
        try {
            new TestableString(FOO_EXECUTION, ctx).within(secs(1)).shouldNotContain("o");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldNotContain('o')"));
            assertThat(getCauseMessage(e), equalTo("(after 1000 ms)\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldRegexMatchSomething() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).shouldMatch(".* Has \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).shouldMatch(".* blort \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldMatch('.* blort \\d\\d.*')"));
            assertThat(getCauseMessage(e), equalTo("\nExpected: a string matching /.* blort \\d\\d.*/\n     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    @Test
    public void stringShouldRegexMatchSomethingEvenIfThereAreNewlines() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION_WITH_NEWLINES, ctx).shouldMatch("(.*)12(.*)");
    }

    @Test
    public void stringShouldNotRegexMatchSomething() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).shouldNotMatch(".* blort \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).shouldNotMatch(".* Has \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotMatch('.* Has \\d\\d.*')"));
            assertThat(getCauseMessage(e), equalTo("\n" +
                    "Expected: not a string matching /.* Has \\d\\d.*/\n" +
                    "     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    @Test
    public void stringShouldNotRegexMatchSomethingEvenIfThereAreNewlines() {
        Context ctx = Context.singular(null, "dummy2");
        try {
            new TestableString(MARY_EXECUTION_WITH_NEWLINES, ctx).shouldNotMatch("(.*)12(.*)");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().shouldNotMatch('(.*)12(.*)')"));
            String causeMessage = getCauseMessage(e);
            assertEquals("\nExpected: not a string matching /(.*)12(.*)/\n" +
                    "     but: was \"Mary Has \\n12\\n Little Lambs\"", causeMessage);
        }
    }


    @Test
    public void stringShouldRegexMatchSomethingWithinSomeTime() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldMatch(".* Has \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldMatch(".* blort \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldMatch('.* blort \\d\\d.*')"));
            assertThat(getCauseMessage(e), equalTo("(after 1000 ms)\nExpected: a string matching /.* blort \\d\\d.*/\n     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    @Test
    public void stringShouldNotRegexMatchSomethingWithinSomeTime() {
        Context ctx = Context.singular(null, "dummy2");
        new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldNotMatch(".* blort \\d\\d.*");
        try {
            new TestableString(MARY_EXECUTION, ctx).within(secs(1)).shouldNotMatch(".* Has \\d\\d.*");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.dummy2().within(secs(1)).shouldNotMatch('.* Has \\d\\d.*')"));
            assertThat(getCauseMessage(e), equalTo("(after 1000 ms)\n" +
                    "Expected: not a string matching /.* Has \\d\\d.*/\n" +
                    "     but: was \"Mary Has 12 Little Lambs\""));
        }
    }

    private String getCauseMessage(FluentExecutionStopped e) {
        return e.getCause().getMessage()
                .replace("1001", "1000")
                .replace("1002", "1000")
                .replace("(after 1 ms)", "")
                .replace("(after 2 ms)", "");
    }

}
