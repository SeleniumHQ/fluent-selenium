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
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;

public class TestableString extends Internal.BaseTestableObject<String> {

    protected TestableString(Execution<String> execution, Context ctx, Monitor monitor) {
        this(null, execution, ctx, monitor);
    }

    protected TestableString(Period within, Execution<String> execution, Context ctx, Monitor monitor) {
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
        Context ctx = Context.singular(context, "shouldContain", null, shouldContain);
        validateWrapRethrow(new Internal.Validation() {
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
        }, ctx);
        return this;
    }
    
    public TestableString shouldEndWith(final String shouldEndWith) {
            Context ctx = Context.singular(context, "shouldEndWith", null, shouldEndWith);
            validateWrapRethrow(new Internal.Validation() {
                @Override
                public void validate(long start) {
                    assignValueIfNeeded();
                    if (!is.endsWith(shouldEndWith)  && within != null) {
                        boolean passed;
                        long endMillis = calcEndMillis();
                        do {
                            is = execution.doExecution();
                            passed = is != null && is.endsWith(shouldEndWith) ;
                        } while (System.currentTimeMillis() < endMillis && !passed);
                    }
                    assertThat(durationIfNotZero(start), is, endsWith(shouldEndWith));
                }
            }, ctx);
            return this;
        }

    public TestableString shouldNotContain(final String shouldNotContain) {
        Context ctx = Context.singular(context, "shouldNotContain", null, shouldNotContain);
        validateWrapRethrow(new Internal.Validation() {
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
        }, ctx);
        return this;
    }

    @Override
    public String toString() {
        Context ctx = Context.singular(context, "toString", null, "");
        validateWrapRethrow(new Internal.Validation() {
            @Override
            public void validate(long start) {
                if (is != null) {
                    return;
                }
                is = execution.doExecution();
            }
        }, ctx);
        return is;
    }

    public TestableString shouldMatch(String regex) {
        Context ctx = Context.singular(context, "shouldMatch", null, regex);
        final MatchesRegex matcher = new MatchesRegex(regex);
        validateWrapRethrow(new Internal.Validation() {
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
        }, ctx);
        return this;
    }

    public TestableString shouldNotMatch(final String regex) {
        Context ctx = Context.singular(context, "shouldNotMatch", null, regex);
        final MatchesRegex matcher = new MatchesRegex(regex);
        validateWrapRethrow(new Internal.Validation() {
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
        }, ctx);
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
}
