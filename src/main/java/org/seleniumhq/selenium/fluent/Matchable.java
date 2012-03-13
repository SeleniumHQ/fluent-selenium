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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.hamcrest.internal.ArrayIterator;
import org.hamcrest.internal.SelfDescribingValueIterator;

import java.util.Arrays;
import java.util.Iterator;

import static java.lang.String.valueOf;
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
        matcher.describeTo(desc);
        if (shouldOrShouldNot & !passed || !shouldOrShouldNot & passed) {
            String s = context + " ~ but " + was(matcher, desc);
            throw new RuntimeException(s);
        }
        return this;
    }

    private String was(org.hamcrest.Matcher<T> matcher, StringDescription desc) {
        if (matcher instanceof IsEqual && !shouldOrShouldNot) {
          return "was.";
        }
        return "was " + appendValue(val);
    }

    public String appendValue(Object value) {
        String retVal = "";
        if (value == null) {
            retVal = retVal + append("null");
        } else if (value instanceof String) {
            retVal = retVal + toJavaSyntax((String) value);
        } else if (value instanceof Character) {
            retVal = retVal + append('"');
            retVal = retVal + toJavaSyntax((Character) value);
            retVal = retVal + append('"');
        } else if (value instanceof Short) {
            retVal = retVal + append('<');
            retVal = retVal + append(valueOf(value));
            retVal = retVal + append("s>");
        } else if (value instanceof Long) {
            retVal = retVal + append('<');
            retVal = retVal + append(valueOf(value));
            retVal = retVal + append("L>");
        } else if (value instanceof Float) {
            retVal = retVal + append('<');
            retVal = retVal + append(valueOf(value));
            retVal = retVal + append("F>");
        } else if (value.getClass().isArray()) {
            retVal = retVal + appendValueList("[",", ","]", (T[]) value);
        } else {
            retVal = retVal + append('<');
            retVal = retVal + append(valueOf(value));
            retVal = retVal + append('>');
        }
        return retVal;
    }

    public String appendValueList(String start, String separator, String end, T... values) {
        return appendValueList(start, separator, end, Arrays.asList(values));
    }

    public String appendValueList(String start, String separator, String end, Iterable<T> values) {
        return appendValueList(start, separator, end, values.iterator());
    }

    private String appendValueList(String start, String separator, String end, Iterator<T> values) {
        return appendList(start, separator, end, new SelfDescribingValueIterator<T>(values));
    }

    private String appendList(String start, String separator, String end, Iterator<? extends SelfDescribing> i) {
        String retVal = "";
        boolean separate = false;

        retVal = retVal + append(start);
        while (i.hasNext()) {
            if (separate) append(separator);
            retVal = retVal + appendDescriptionOf(i.next());
            separate = true;
        }
        retVal = retVal + append(end);

        return retVal;
    }

    public String appendDescriptionOf(SelfDescribing value) {
        return value.toString();
    }
    protected String
    append(char chr) {
        return "" + chr;
    }

    protected String append(String str) {
        String retVal = "";
        for (int i = 0; i < str.length(); i++) {
            retVal = retVal + append(str.charAt(i));
        }
        return retVal;
    }

    private String toJavaSyntax(String unformatted) {
        String retVal  = "";
        retVal = retVal + append('"');
        for (int i = 0; i < unformatted.length(); i++) {
            retVal = retVal + toJavaSyntax(unformatted.charAt(i));
        }
        retVal = retVal + append('"');
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
                return append(ch);
        }
    }

  public T value() {
        return val;
    }
}
