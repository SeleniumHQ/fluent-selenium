/*
Copyright 2011 Software Freedom Conservancy

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
package org.seleniumhq.selenium.fluent.internal;

import org.seleniumhq.selenium.fluent.internal.Context;
import org.seleniumhq.selenium.fluent.internal.ShouldBeMatchable;
import org.seleniumhq.selenium.fluent.internal.ShouldNotBeMatchable;

public class WebElementValue<T> {

    private T val;
    private final Context context;

    public WebElementValue(T val, Context context) {
        this.val = val;
        this.context = context;
    }

    public ShouldBeMatchable<T> shouldBe(T shouldBe) {
        ShouldBeMatchable<T> shouldBeMatchable = new ShouldBeMatchable<T>(val, shouldBe, context + ".shouldBe(" + shouldBe + ")");
        shouldBeMatchable.match();
        return shouldBeMatchable;
    }

    public ShouldNotBeMatchable<T> shouldNotBe(T shouldNotBe) {
        ShouldNotBeMatchable<T> shouldNotBeMatchable = new ShouldNotBeMatchable<T>(val, shouldNotBe, context + ".shouldNotBe(" + shouldNotBe + ")");
        shouldNotBeMatchable.match();
        return shouldNotBeMatchable;
    }

    public T getValue() {
        return val;
    }
}
