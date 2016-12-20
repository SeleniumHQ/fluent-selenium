/*
Copyright 2011-2013 Software Freedom Conservancy

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
package org.seleniumhq.selenium.fluent;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TestableString extends Internal.BaseTestableObject<String> {


    public interface Concatenator {
        void start(String text);
        void concat(TestableString text);
    }

    public interface StringChanger {
        String chg(String text);
    }

    public static class NoopStringChanger implements StringChanger {
        public String chg(String text) {
            return text;
        }
    }

    public static StringChanger trimmer() {
        return new StringChanger() {
            public String chg(String text) {
                return text.trim();
            }
        };
    }

    public static StringChanger multiSpaceEliminator() {
        return new StringChanger() {
            public String chg(String text) {
                StringBuilder sb = new StringBuilder(text.replaceAll("\t", " "));
                int ix = sb.indexOf("  ");
                while (ix >= 0) {
                    sb.replace(ix, ix +2, " ");
                    ix = sb.indexOf("  ");
                }
                return sb.toString();
            }
        };
    }

    public static StringChanger tabsToSpaces() {
        return new StringChanger() {
            public String chg(String text) {
                return text.replaceAll("\t", " ");
            }
        };
    }

    public static StringChanger crToChars(final String chars) {
        return new StringChanger() {
            public String chg(String text) {
                return text.replaceAll("\n", chars);
            }
        };
    }

    public static class DelimitWithChars implements Concatenator {

        private String delimChar;
        private String text = null;
        private boolean delimitNow = false;

        public DelimitWithChars(String delimChar) {
            this.delimChar = delimChar;
        }

        public void start(String text) {
            this.text = text;
            if (!text.equals("")) {
                delimitNow = true;
            }
        }

        public void concat(TestableString text) {
            this.text = this.text + (delimitNow ? this.delimChar : "") + text;
            delimitNow = true;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static class DefaultConcatenator extends DelimitWithChars {
        public DefaultConcatenator() {
            super("");
        }
    }

    public static Concatenator delimitWithChars(String charToDelimitWith) {
        return new DelimitWithChars(charToDelimitWith);
    }

    protected TestableString(Execution<String> execution, Context ctx, Monitor monitor) {
        this(null, execution, ctx, monitor);
    }

    private TestableString(Period within, Execution<String> execution, Context ctx, Monitor monitor) {
        super(within, execution, ctx, monitor);
    }

    public TestableString shouldBe(final String shouldBe) {
        baseShouldBe(shouldBe);
        return this;
    }

    public TestableString within(Period period) {
        Context ctx = Context.singular(context, "within", null, period);
        return new TestableString(period, execution, ctx, monitor);
    }


    public TestableString shouldNotBe(final String shouldNotBe) {
        baseShouldNotBe(shouldNotBe);
        return this;
    }

    public TestableString shouldContain(final String shouldContain) {
        validateWrapRethrow(new ShouldContainValidation(shouldContain), Context.singular(context, "shouldContain", null, shouldContain));
        return this;
    }

    public TestableString shouldNotContain(final String shouldNotContain) {
        validateWrapRethrow(new ShouldNotContainValidation(shouldNotContain),
                Context.singular(context, "shouldNotContain", null, shouldNotContain));
        return this;
    }

    @Override
    public String toString() {
        validateWrapRethrow(new ToStringValidation(),
                Context.singular(context, "toString", null, ""));
        return is;
    }

    public TestableString shouldMatch(String regex) {
        validateWrapRethrow(new ShouldMatchValidation(new MatchesRegex(regex)),
                Context.singular(context, "shouldMatch", null, regex));
        return this;
    }

    public TestableString shouldNotMatch(final String regex) {
        validateWrapRethrow(new ShouldNotMatchValidation(new MatchesRegex(regex)),
                Context.singular(context, "shouldNotMatch", null, regex));
        return this;
    }

    public TestableString shouldMatch(Matcher<String> hamcrestMatcher) {
        validateWrapRethrow(new ShouldHamcrestMatchValidation(hamcrestMatcher),
                Context.singular(context, "shouldMatch", null, hamcrestMatcher));
        return this;
    }

    public TestableString shouldNotMatch(Matcher<String> hamcrestMatcher) {
        validateWrapRethrow(new ShouldNotHamcrestMatchValidation(hamcrestMatcher),
                Context.singular(context, "shouldNotMatch", null, hamcrestMatcher));
        return this;
    }

    private static class MatchesRegex extends BaseMatcher<String> {
        private final String regex;
        private final Pattern pattern;

        public MatchesRegex(String regex) {
            this.regex = regex;
            pattern = Pattern.compile(regex, Pattern.MULTILINE);
        }

        public void describeTo(Description description) {
            description.appendText("a string matching /" + regex + "/");
        }

        final public void describeMismatch(Object item, Description description) {
            description.appendText("was ").appendValue(item);
        }

        public final boolean matches(Object item) {

            return pattern.matcher((String) item).find();
        }
    }

    private class ShouldContainValidation extends Internal.Validation {
        private final String shouldContain;

        public ShouldContainValidation(String shouldContain) {
            this.shouldContain = shouldContain;
        }

        @Override
        public void validate(long start) {
            assignValueIfNeeded();
            if (is.indexOf(shouldContain) == -1 && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.doExecution();
                    passed = is != null && is.indexOf(shouldContain) > -1;
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, containsString(shouldContain));
        }
    }

    private class ShouldMatchValidation extends Internal.Validation {
        private final MatchesRegex matcher;

        public ShouldMatchValidation(MatchesRegex matcher) {
            this.matcher = matcher;
        }

        @Override
        public void validate(long start) {
            assignValueIfNeeded();

            if ((is != null && !matcher.matches(is)) && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.doExecution();
                    passed = is != null && matcher.matches(is);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, matcher);
        }
    }
    private class ShouldHamcrestMatchValidation extends Internal.Validation {
        private final Matcher<String> hamcrestMatcher;

        public ShouldHamcrestMatchValidation(Matcher<String> hamcrestMatcher) {
            this.hamcrestMatcher = hamcrestMatcher;
        }

        @Override
        public void validate(long start) {
            assignValueIfNeeded();

            if ((is != null && !hamcrestMatcher.matches(is)) && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.doExecution();
                    passed = is != null && hamcrestMatcher.matches(is);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, hamcrestMatcher);
        }
    }

    private class ShouldNotHamcrestMatchValidation extends Internal.Validation {
        private final Matcher<String> hamcrestMatcher;

        public ShouldNotHamcrestMatchValidation(Matcher<String> hamcrestMatcher) {
            this.hamcrestMatcher = hamcrestMatcher;
        }

        @Override
        public void validate(long start) {
            assignValueIfNeeded();
            if ((is != null && hamcrestMatcher.matches(is)) && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.doExecution();
                    passed = is != null && !hamcrestMatcher.matches(is);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, not(hamcrestMatcher));
        }
    }


    private class ShouldNotMatchValidation extends Internal.Validation {
        private final MatchesRegex matcher;

        public ShouldNotMatchValidation(MatchesRegex matcher) {
            this.matcher = matcher;
        }

        @Override
        public void validate(long start) {
            assignValueIfNeeded();
            if ((is != null && matcher.matches(is)) && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.doExecution();
                    passed = is != null && !matcher.matches(is);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, not(matcher));
        }
    }

    private class ShouldNotContainValidation extends Internal.Validation {
        private final String shouldNotContain;

        public ShouldNotContainValidation(String shouldNotContain) {
            this.shouldNotContain = shouldNotContain;
        }

        @Override
        public void validate(long start) {
            assignValueIfNeeded();
            if (is.indexOf(shouldNotContain) > -1 && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.doExecution();
                    passed = is != null && is.indexOf(shouldNotContain) == -1;
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, not(containsString(shouldNotContain)));
        }
    }

    private class ToStringValidation extends Internal.Validation {
        @Override
        public void validate(long start) {
            if (is != null) {
                return;
            }
            is = execution.doExecution();
        }
    }
}
