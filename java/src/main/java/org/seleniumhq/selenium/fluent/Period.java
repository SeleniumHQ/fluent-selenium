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

import java.util.concurrent.TimeUnit;

public abstract class Period {

    public abstract int howLong();
    public abstract TimeUnit timeUnit();
    public abstract long getEndMillis(long from);

    public static class Seconds extends Milliseconds {
        private final int secs;

        public Seconds(int secs) {
            super(secs * 1000);
            this.secs = secs;
        }

        @Override
        public int howLong() {
            return secs;
        }

        @Override
        public TimeUnit timeUnit() {
            return TimeUnit.SECONDS;
        }

        @Override
        public String toString() {
            return "secs(" + secs + ")";
        }
    }

    public static class Minutes extends Seconds {
        private final int mins;

        public Minutes(int mins) {
            super(mins * 60);
            this.mins = mins;
        }

        @Override
        public int howLong() {
            return mins;
        }

        @Override
        public TimeUnit timeUnit() {
            return TimeUnit.MINUTES;
        }

        @Override
        public String toString() {
            return "mins(" + mins + ")";
        }
    }

    public static class Milliseconds extends Period {
        private final int millis;

        public Milliseconds(int millis) {
            this.millis = millis;
        }

        @Override
        public int howLong() {
            return millis;
        }

        @Override
        public TimeUnit timeUnit() {
            return TimeUnit.MILLISECONDS;
        }

        public final long getEndMillis(long from) {
            return from + millis;
        }

        @Override
        public String toString() {
            return "millis(" + millis + ")";
        }
    }

    public static Period millis(int millis) {
        return new Milliseconds(millis);
    }

    public static Period secs(int secs) {
        return new Seconds(secs);
    }

    public static Period mins(int mins) {
        return new Minutes(mins);
    }


}
