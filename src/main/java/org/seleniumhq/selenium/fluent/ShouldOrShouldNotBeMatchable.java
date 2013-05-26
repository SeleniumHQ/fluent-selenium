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

import java.awt.*;

import static java.lang.String.valueOf;
import static org.hamcrest.core.IsEqual.equalTo;

public class ShouldOrShouldNotBeMatchable<T> {
    private T val;
    private final T shouldOrShouldNotBe;
    private final String context;
    private final boolean shouldOrShouldNot;

    public ShouldOrShouldNotBeMatchable(T val, T shouldOrShouldNotBe, String context, boolean shouldOrShouldNot) {
        this.val = val;
        this.shouldOrShouldNotBe = shouldOrShouldNotBe;
        this.context = context;
        this.shouldOrShouldNot = shouldOrShouldNot;
    }

    public ShouldOrShouldNotBeMatchable<T> match() {
        Matcher<T> matcher = equalTo(shouldOrShouldNotBe);
        boolean passed = matcher.matches(val);
        StringDescription desc = new StringDescription();
        matcher.describeTo(desc);
        if (shouldOrShouldNot & !passed || !shouldOrShouldNot & passed) {
            String s = context + " ~ but " + was(matcher, desc);
            throw new AssertionError(s);
        }
        return this;
    }

    private String was(org.hamcrest.Matcher<T> matcher, StringDescription desc) {
        if (matcher instanceof IsEqual && !shouldOrShouldNot) {
          return "was.";
        }
        return "was " + appendValue(val);
    }

    private String appendValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return toJavaSyntax((String) value);
        } else if (value instanceof Dimension) {
            return valueOf(value);
        } else if (value instanceof Point) {
            return valueOf(value);
        } else {
            return "<" + valueOf(value) + ">";
        }
    }

    protected String append(String str) {
        String retVal = "";
        for (int i = 0; i < str.length(); i++) {
            retVal = retVal + str.charAt(i);
        }
        return retVal;
    }

    private String toJavaSyntax(String unformatted) {
        String retVal  = "";
        retVal = retVal + append("\"");
        for (int i = 0; i < unformatted.length(); i++) {
            retVal = retVal + toJavaSyntax(unformatted.charAt(i));
        }
        retVal = retVal + append("\"");
        return retVal;
    }

    private String toJavaSyntax(char ch) {
        switch (ch) {
            case '"':
                return append("\\\"");
            case '\n':
                return append("\\n");
            case '\r':
                return append("\\r");
            case '\t':
                return append("\\t");
            default:
                return "" + ch;
        }
    }

  public T value() {
        return val;
    }
}
