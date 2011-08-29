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
package org.seleniumhq.selenium.fluent;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;

import static org.hamcrest.core.IsEqual.equalTo;

public class Matchable<T> {
    private T val;
    private final T shouldOrShouldNotBe;
    private final String context;
    private final boolean shouldOrShouldNot;

    public Matchable(T val, T shouldOrShouldNotBe, String context, boolean shouldOrShouldNot) {
        this.val = val;
        this.shouldOrShouldNotBe = shouldOrShouldNotBe;
        this.context = context;
        this.shouldOrShouldNot = shouldOrShouldNot;
    }

    public Matchable<T> match() {
        Matcher<T> matcher = equalTo(shouldOrShouldNotBe);
        boolean passed = matcher.matches(val);
        StringDescription desc = new StringDescription();
        matcher.describeMismatch(val, desc);
        if (shouldOrShouldNot & !passed || !shouldOrShouldNot & passed) {
            throw new RuntimeException(context + " ~ but " + was(matcher, desc));
        }
        return this;
    }

    private String was(org.hamcrest.Matcher<T> matcher, StringDescription desc) {
        if (matcher instanceof IsEqual && !shouldOrShouldNot) {
          return "was.";
        }
        return desc.toString();
    }

  public T value() {
        return val;
    }
}
