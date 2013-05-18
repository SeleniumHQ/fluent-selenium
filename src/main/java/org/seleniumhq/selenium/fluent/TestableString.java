package org.seleniumhq.selenium.fluent;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

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

    public void shouldBe(String shouldBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldBe", null, shouldBe);
        long start = System.currentTimeMillis();
        try {
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

    public void shouldNotBe(String shouldNotBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotBe", null, shouldNotBe);
        long start = System.currentTimeMillis();
        try {
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
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public void shouldContain(String shouldContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldContain", null, shouldContain);
        long start = System.currentTimeMillis();
        try {
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
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
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

    public void shouldNotContain(String shouldNotContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotContain", null, shouldNotContain);
        long start = System.currentTimeMillis();
        try {
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
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    @Override
    public String toString() {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "toString", null, "");
        long start = System.currentTimeMillis();
        assignValueAndWrapExceptionsIfNeeded(ctx, start);
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
        long start = System.currentTimeMillis();
        try {
            assignValueIfNeeded();
            if ((is != null && !is.matches(regex)) && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.execute();
                    passed = is != null && is.matches(regex);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, matchesRegex(regex));
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public void shouldNotMatch(String regex) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotMatch", null, regex);
        long start = System.currentTimeMillis();
        try {
            assignValueIfNeeded();
            if ((is != null && is.matches(regex)) && within != null) {
                boolean passed;
                long endMillis = calcEndMillis();
                do {
                    is = execution.execute();
                    passed = is != null && !is.matches(regex);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(durationIfNotZero(start), is, not(matchesRegex(regex)));
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public static Matcher<String> matchesRegex(String substring) {
        return new MatchesRegex(substring);
    }

    private static class MatchesRegex extends BaseMatcher<String> {
        private final String regex;

        public MatchesRegex(String regex) {
            this.regex = regex;
        }

        public void describeTo(Description description) {
            description.appendText("a string matching /" + regex + "/");
        }

        final public void describeMismatch(Object item, Description description) {
            description.appendText("was ").appendValue(item);
        }

        public final boolean matches(Object item) {
            return ((String) item).matches(regex);
        }

    }
}
