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

public abstract class RetryAfterStaleElement {

    public abstract void toRetry();

    public void stopAfter(Period period) {
        boolean again = true;
        long start = System.currentTimeMillis();
        long endMillis = period.getEndMillis(start);
        FluentExecutionStopped.BecauseOfStaleElement ex = null;
        while (again && System.currentTimeMillis() < endMillis) {
            ex = null;
            try {
                toRetry();
                again = false;
            } catch (FluentExecutionStopped.BecauseOfStaleElement e) {
                ex = e;
            }
        }
        if (ex != null) {
            ex.setDuration(System.currentTimeMillis() - start);
            throw ex;
        }

    }
}