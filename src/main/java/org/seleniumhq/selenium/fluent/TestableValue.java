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

public class TestableValue<T> extends Internal.BaseTestableObject {

    protected TestableValue(Execution<T> execution, Context context) {
        this(null, execution, context);
    }

    public TestableValue(Period period, Execution<T> execution, Context context) {
        super(period, execution, context);
    }

    public TestableValue<T> within(Period period) {
        return new TestableValue<T>(period, execution, context);
    }

    public TestableValue<T> shouldBe(final T shouldBe) {
        baseShouldBe(shouldBe);
        return this;
    }

    public TestableValue<T> shouldNotBe(final T shouldNotBe) {
        baseShouldNotBe(shouldNotBe);
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
