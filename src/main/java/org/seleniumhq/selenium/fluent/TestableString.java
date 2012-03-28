package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.StaleElementReferenceException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.seleniumhq.selenium.fluent.BaseFluentWebDriver.decorateAssertionError;
import static org.seleniumhq.selenium.fluent.BaseFluentWebDriver.decorateRuntimeException;

public class TestableString implements CharSequence {
    private String is;
    private final Period within;
    private final Execution<String> execution;
    private final BaseFluentWebDriver.Context context;

    public TestableString(Period within, Execution<String> execution, BaseFluentWebDriver.Context ctx) {
        this.within = within;
        this.execution = execution;
        this.context = ctx;
        if (within == null && execution == null && ctx == null) {
            return;
        }
        if (within == null) {
            try {
                is = execution.execute();
            } catch (UnsupportedOperationException e) {
                throw e;
            } catch (RuntimeException e) {
                throw decorateRuntimeException(ctx, e);
            } catch (AssertionError e) {
                throw decorateAssertionError(ctx, e);
            }
        }
    }

    public void shouldBe(String shouldBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldBe", null, shouldBe);
        try {
            if (within != null && (is == null || !is.equals(shouldBe))) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    assignValueIfNeeded();
                    passed = is != null && is.equals(shouldBe);
                } while (System.currentTimeMillis() < endMillis && !passed);
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

    private void assignValueIfNeeded() {
        if (is != null) {
            return;
        }
        is = execution.execute();
    }

    public void shouldNotBe(String shouldNotBe) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotBe", null, shouldNotBe);
        try {
            if (within != null && (is == null || is.equals(shouldNotBe))) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    assignValueIfNeeded();
                    passed = is != null && !is.equals(shouldNotBe);
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(is, not(equalTo(shouldNotBe)));
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (StaleElementReferenceException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public void shouldContain(String shouldContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldContain", null, shouldContain);
        try {
            if (within != null && (is == null || is.indexOf(shouldContain) == -1)) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    assignValueIfNeeded();
                    passed = is != null && is.indexOf(shouldContain) > -1;
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(is, containsString(shouldContain));
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (StaleElementReferenceException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public void shouldNotContain(String shouldNotContain) {
        BaseFluentWebDriver.Context ctx = BaseFluentWebDriver.Context.singular(context, "shouldNotContain", null, shouldNotContain);
        try {
            if (within != null && (is == null || is.indexOf(shouldNotContain) > -1)) {
                boolean passed;
                long endMillis = within.getEndMillis(System.currentTimeMillis());
                do {
                    assignValueIfNeeded();
                    passed = is != null && is.indexOf(shouldNotContain) == -1;
                } while (System.currentTimeMillis() < endMillis && !passed);
            }
            assertThat(is, not(containsString(shouldNotContain)));
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (StaleElementReferenceException e) {
            throw decorateRuntimeException(ctx, e);
        } catch (AssertionError e) {
            throw decorateAssertionError(ctx, e);
        }
    }

    public int length() {
        assignValueIfNeeded();
        return is.length();
    }

    public char charAt(int i) {
        assignValueIfNeeded();
        return is.charAt(i);
    }

    public CharSequence subSequence(int i, int i1) {
        assignValueIfNeeded();
        return is.subSequence(i, i1);
    }

    @Override
    public boolean equals(Object o) {
        assignValueIfNeeded();
        if (this == o) return true;
        return o.equals(is);
    }

    @Override
    public int hashCode() {
        assignValueIfNeeded();
        return is.hashCode();
    }

    @Override
    public String toString() {
        assignValueIfNeeded();
        return is;
    }
    
    public String replace(CharSequence from, CharSequence to) {
        return is.replace(from, to);
    }
}
