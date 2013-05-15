package org.seleniumhq.selenium.fluent;

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

    public TestableString(Period within, Execution<String> execution, BaseFluentWebDriver.Context ctx) {
        this.within = within;
        this.execution = execution;
        this.context = ctx;
    }

    public void shouldBe(String shouldBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldBe", null, shouldBe);
        try {
            if (!shouldBe.equals(is)) {
                if (within != null) {
                    boolean passed;
                    long endMillis = within.getEndMillis(System.currentTimeMillis());
                    do {
                        is = execution.execute();
                        passed = is != null && is.equals(shouldBe);
                    } while (System.currentTimeMillis() < endMillis && !passed);
                } else {
                    assignValueIfNeeded();
                }
            }
            assertThat(is, equalTo(shouldBe));
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
        try {
            assignValueIfNeeded();
            if (shouldNotBe.equals(is) && within != null) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    is = execution.execute();
                    passed = is != null && !is.equals(shouldNotBe);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(is, not(equalTo(shouldNotBe)));
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
        try {
            assignValueIfNeeded();
            if (is.indexOf(shouldContain) == -1 && within != null) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    is = execution.execute();
                    passed = is != null && is.indexOf(shouldContain) > -1;
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(is, containsString(shouldContain));
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public void shouldNotContain(String shouldNotContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotContain", null, shouldNotContain);
        try {
            assignValueIfNeeded();
            if (is.indexOf(shouldNotContain) > -1 && within != null) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    is = execution.execute();
                    passed = is != null && is.indexOf(shouldNotContain) == -1;
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(is, not(containsString(shouldNotContain)));
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
        assignValueAndWrapExceptionsIfNeeded(ctx);
        return is;
    }

    protected String assignValueAndWrapExceptionsIfNeeded(BaseFluentWebDriver.Context ctx) {
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
        try {
            assignValueIfNeeded();
            if ((is != null && !is.matches(regex)) && within != null) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    is = execution.execute();
                    passed = is != null && !is.matches(regex);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            if (!is.matches(regex)) {
                throw new AssertionError("'" + is + "' should, but did not, match regex: /" + regex + "/");
            }
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
        try {
            assignValueIfNeeded();
            if ((is != null && is.matches(regex)) && within != null) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    is = execution.execute();
                    passed = is != null && !is.matches(regex);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            if (is.matches(regex)) {
                throw new AssertionError("'" + is + "' did, but should not, match regex: /" + regex + "/");
            }
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }

    }
}
