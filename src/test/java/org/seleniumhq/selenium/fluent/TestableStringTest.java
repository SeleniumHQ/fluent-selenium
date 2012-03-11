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
        new TestableString(secs(1), fooExecution, "ha!").shouldBe("foo");
        try {
            new TestableString(secs(1), fooExecution, "ha!").shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ha!.shouldBe('bar')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldBeSomethingInNoTime() {
        new TestableString(secs(1), fooExecution, "ha!").shouldBe("foo");
        try {
            new TestableString(null, fooExecution, "ha!").shouldBe("bar");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ha!.shouldBe('bar')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotBeSomething() {
        new TestableString(secs(1), fooExecution, "ha!").shouldNotBe("bar");
        try {
            new TestableString(secs(1), fooExecution, "ha!").shouldNotBe("foo");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ha!.shouldNotBe('foo')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }
    
    @Test
    public void stringShouldContainSomething() {
        new TestableString(secs(1), fooExecution, "ha!").shouldContain("o");
        try {
            new TestableString(secs(1), fooExecution, "ha!").shouldContain("a");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ha!.shouldContain('a')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomething() {
        new TestableString(secs(1), fooExecution, "ha!").shouldNotContain("a");
        try {
            new TestableString(secs(1), fooExecution, "ha!").shouldNotContain("o");
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ha!.shouldNotContain('o')"));
            assertThat(e.getCause().getMessage(), equalTo("\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

}
