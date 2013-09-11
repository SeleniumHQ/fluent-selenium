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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Context implements Iterable<ContextElem> {

    private List<ContextElem> elems = new ArrayList<ContextElem>();

    public Iterator<ContextElem> iterator() {
        return Collections.unmodifiableList(elems).iterator();
    }

    public static Context singular(Context previous, String tagName, By by) {
        return make(previous, tagName, by, null);
    }

    private static Context make(Context previous, String tagName, By by, Object arg) {
        Context ctx = new Context();
        if (previous != null) {
            ctx.elems.addAll(previous.elems);
        }
        ctx.elems.add(new ContextElem(tagName, by, arg));
        return ctx;
    }

    public static Context plural(Context previous, String tagName, By by) {
        return make(previous, tagName + "s", by, null);
    }

    public static Context singular(Context previous, String tagName) {
        return make(previous, tagName, null, null);
    }

    public static Context singular(Context context, String tagName, Object arg) {
        return make(context, tagName, null, arg);
    }

    public static Context singular(Context previous, String tagName, By by, Object arg) {
        return make(previous, tagName, by, arg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("?");
        for (ContextElem elem : elems) {
            sb.append(elem.toString());
        }
        return sb.toString();
    }
}
