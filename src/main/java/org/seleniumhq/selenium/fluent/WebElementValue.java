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

import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.Execution;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebElementValue<T> extends Internal.BaseTestableObject {

    private final Context context;

    protected WebElementValue(Execution<T> execution, Context context) {
        this(null, execution, context);
    }

    public WebElementValue(Period period, Execution<T> execution, Context context) {
        super(period, execution);
        this.context = context;
    }

    public WebElementValue<T> within(Period period) {
        return new WebElementValue<T>(period, execution, context);
    }

    public WebElementValue<T> shouldBe(final T shouldBe) {
        Context ctx = Context.singular(context, "shouldBe", null, shouldBe);

        validateWrapRethrow(new Internal.Validation() {
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
                assertThat(durationIfNotZero(start), (T) is, equalTo(shouldBe));
            }
        }, ctx);
        return this;
    }

    public WebElementValue<T> shouldNotBe(final T shouldNotBe) {
        Context ctx = Context.singular(context, "shouldNotBe", null, shouldNotBe);
        validateWrapRethrow(new Internal.Validation() {
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
                assertThat(durationIfNotZero(start), (T) is, not(equalTo(shouldNotBe)));
            }
        }, ctx);
        return this;
    }

    public T value() {
        Context ctx = Context.singular(context, "getValue", null, "");
        validateWrapRethrow(new Internal.Validation() {
            @Override
            public void validate(long start) {
                if (is != null) {
                    return;
                }
                is = execution.execute();
            }
        }, ctx);
        return (T) is;
    }

}
