package org.seleniumhq.selenium.fluent;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.seleniumhq.selenium.fluent.BaseFluentWebDriver.decorateAssertionError;
import static org.seleniumhq.selenium.fluent.BaseFluentWebDriver.decorateRuntimeException;

public class TestableString {
    private String is;
    private final Period within;
    private final Execution<String> execution;
    private final BaseFluentWebDriver.Context context;

    public TestableString(Execution<String> execution, BaseFluentWebDriver.Context ctx) {
        this(null, execution, ctx);
    }

    private TestableString(Period within, Execution<String> execution, BaseFluentWebDriver.Context ctx) {
        this.within = within;
        this.execution = execution;
        this.context = ctx;
    }

    private static abstract class Validation {
        public abstract void validate(long start);
    }

    public void shouldBe(final String shouldBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldBe", null, shouldBe);

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
    }

    private void validateWrapRethrow(Validation validation, BaseFluentWebDriver.Context ctx) {
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
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "within", null, period);
        return new TestableString(period, execution, ctx);
    }

    private void assignValueIfNeeded() {
        if (is != null) {
            return;
        }
        is = execution.execute();
    }

    public void shouldNotBe(final String shouldNotBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotBe", null, shouldNotBe);
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
    }

    public void shouldContain(final String shouldContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldContain", null, shouldContain);
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

    public void shouldNotContain(final String shouldNotContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotContain", null, shouldNotContain);
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

    }

    @Override
    public String toString() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "toString", null, "");
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

    protected String assignValueAndWrapExceptionsIfNeeded(BaseFluentWebDriver.Context ctx, long start) {
        try {
            assignValueIfNeeded();
            return is;
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public void shouldMatch(String regex) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldMatch", null, regex);
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
    }

    public void shouldNotMatch(final String regex) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotMatch", null, regex);
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
