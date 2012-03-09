package org.seleniumhq.selenium.fluent;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestableStringTest {
    

    @Test
    public void stringShouldBeSomething() {
        new TestableString("foo").shouldBe("foo");
        try {
            new TestableString("foo").shouldBe("bar");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: \"bar\"\n     but: was \"foo\""));
        }
    }
    
    @Test
    public void stringShouldNotBeSomething() {
        new TestableString("foo").shouldNotBe("bar");
        try {
            new TestableString("foo").shouldNotBe("foo");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: not \"foo\"\n     but: was \"foo\""));
        }
    }
    
    @Test
    public void stringShouldContainSomething() {
        new TestableString("foo").shouldContain("o");
        try {
            new TestableString("foo").shouldContain("a");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: a string containing \"a\"\n     but: was \"foo\""));
        }
    }

    @Test
    public void stringShouldNotContainSomething() {
        new TestableString("foo").shouldNotContain("a");
        try {
            new TestableString("foo").shouldNotContain("o");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: not a string containing \"o\"\n     but: was \"foo\""));
        }
    }

}
