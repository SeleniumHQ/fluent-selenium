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
package org.seleniumhq.selenium.fluent.internal;

import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.seleniumhq.selenium.fluent.Period;

public class ContextElem {
    private final String tagName;
    private final By by;
    private final Object arg;

    public ContextElem(String tagName, By by, Object arg) {
        this.tagName = tagName;
        this.by = by;
        this.arg = arg;
    }

    @Override
    public String toString() {
        StringBuilder sb =
                new StringBuilder(".")
                        .append(tagName);
        if (by == null && arg == null) {
            return sb.append("()").toString();
        }
        if (by != null) {
            return sb.append("(").append(by).append(")").toString();

        }
        String quote = "'";
        if (arg instanceof Number || arg instanceof Period || arg instanceof FluentMatcher) {
            quote = "";
        }
        return sb.append("(").append(quote).append(arg).append(quote).append(")").toString();
    }
}
