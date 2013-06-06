package org.seleniumhq.selenium.fluent;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.seleniumhq.selenium.fluent.Internal.BaseFluentWebDriver.decorateAssertionError;
import static org.seleniumhq.selenium.fluent.Internal.BaseFluentWebDriver.decorateRuntimeException;

public class TestableString {
    private String is;
    private final Period within;
    private final Execution<String> execution;
    private final Context context;

    protected TestableString(Execution<String> execution, Context ctx) {
        this(null, execution, ctx);
    }

    private TestableString(Period within, Execution<String> execution, Context ctx) {
        this.within = within;
        this.execution = execution;
        this.context = ctx;
    }

    private static abstract class Validation {
        public abstract void validate(long start);
    }

    public TestableString shouldBe(final String shouldBe) {
        Context ctx = Context.singular(context, "shouldBe", null, shouldBe);

        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                if (!shouldBe.equals(is)) {
                    if (within != null) {
                        boolean passed;
                        long endMillis = calcEndMillis();
                        do {
                            is = execution.execute();
                            passed = is != null && is.equals(shouldBe);
                        } while (System.currentTimeMillis() < endMillis && !passed);
                    } else {
                        assignValueIfNeeded();
                    }
                }
                assertThat(durationIfNotZero(start), is, equalTo(shouldBe));
            }
        }, ctx);
        return this;
    }

    private void validateWrapRethrow(Validation validation, Context ctx) {
        try {
            validation.validate(System.currentTimeMillis());
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public TestableString within(Period period) {
        Context ctx = Context.singular(context, "within", null, period);
        return new TestableString(period, execution, ctx);
    }

    private void assignValueIfNeeded() {
        if (is != null) {
            return;
        }
        is = execution.execute();
    }

    public TestableString shouldNotBe(final String shouldNotBe) {
        Context ctx = Context.singular(context, "shouldNotBe", null, shouldNotBe);
        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                assignValueIfNeeded();
                if (shouldNotBe.equals(is) && within != null) {
                    boolean passed;
                    long endMillis = calcEndMillis();
                    do {
                        is = execution.execute();
                        passed = is != null && !is.equals(shouldNotBe);
                    } while (System.currentTimeMillis() < endMillis && !passed);
                }
                assertThat(durationIfNotZero(start), is, not(equalTo(shouldNotBe)));
            }
        }, ctx);
        return this;
    }

    public TestableString shouldContain(final String shouldContain) {
        Context ctx = Context.singular(context, "shouldContain", null, shouldContain);
        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                assignValueIfNeeded();
                if (is.indexOf(shouldContain) == -1 && within != null) {
                    boolean passed;
                    long endMillis = calcEndMillis();
                    do {
                        is = execution.execute();
                        passed = is != null && is.indexOf(shouldContain) > -1;
                    } while (System.currentTimeMillis() < endMillis && !passed);
                }
                assertThat(durationIfNotZero(start), is, containsString(shouldContain));
            }
        }, ctx);
        return this;
    }

    private String durationIfNotZero(long start) {
        long duration = System.currentTimeMillis() - start;
        if (duration > 0 ) {
            return "(after " + duration + " ms)";
        } else {
            return "";
        }

    }

    private long calcEndMillis() {
        return within.getEndMillis(System.currentTimeMillis());
    }

    public TestableString shouldNotContain(final String shouldNotContain) {
        Context ctx = Context.singular(context, "shouldNotContain", null, shouldNotContain);
        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                assignValueIfNeeded();
                if (is.indexOf(shouldNotContain) > -1 && within != null) {
                    boolean passed;
                    long endMillis = calcEndMillis();
                    do {
                        is = execution.execute();
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
        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                if (is != null) {
                    return;
                }
                is = execution.execute();
            }
        }, ctx);
        return is;
    }

    public TestableString shouldMatch(String regex) {
        Context ctx = Context.singular(context, "shouldMatch", null, regex);
        final MatchesRegex matcher = new MatchesRegex(regex);
        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                assignValueIfNeeded();

                if ((is != null && !matcher.matches(is)) && within != null) {
                    boolean passed;
                    long endMillis = calcEndMillis();
                    do {
                        is = execution.execute();
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
        validateWrapRethrow(new Validation() {
            @Override
            public void validate(long start) {
                assignValueIfNeeded();
                if ((is != null && matcher.matches(is)) && within != null) {
                    boolean passed;
                    long endMillis = calcEndMillis();
                    do {
                        is = execution.execute();
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
