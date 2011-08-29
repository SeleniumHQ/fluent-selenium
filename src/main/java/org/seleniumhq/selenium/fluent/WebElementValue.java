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

public class WebElementValue<T> {

    private T val;
    private final String context;

    public WebElementValue(T val, String context) {
        this.val = val;
        this.context = context;
    }

    public Matchable<T> shouldBe(T shouldBe) {
        Matchable<T> tMatchable = new Matchable<T>(val, shouldBe, context + ".shouldBe(" + shouldBe + ")", true);
        tMatchable.match();
        return tMatchable;
    }

    public Matchable<T> shouldNotBe(T shouldNotBe) {
        Matchable<T> tMatchable = new Matchable<T>(val, shouldNotBe, context + ".shouldNotBe(" + shouldNotBe + ")", false);
        tMatchable.match();
        return tMatchable;
    }
}
