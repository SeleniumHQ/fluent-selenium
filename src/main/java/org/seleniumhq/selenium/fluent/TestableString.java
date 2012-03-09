package org.seleniumhq.selenium.fluent;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestableString implements CharSequence {
    private final String is;

    public TestableString(String is) {
        this.is = is;
    }

    public void shouldBe(String shouldBe) {        
        assertThat(is, equalTo(shouldBe));
    }

    public void shouldNotBe(String shouldNotBe) {        
        assertThat(is, not(equalTo(shouldNotBe)));
    }

    public void shouldContain(String shouldContain) {
        assertThat(is, containsString(shouldContain));
    }

    public void shouldNotContain(String shouldNotContain) {
        assertThat(is, not(containsString(shouldNotContain)));
    }

    public int length() {
        return is.length();
    }

    public char charAt(int i) {
        return is.charAt(i);
    }

    public CharSequence subSequence(int i, int i1) {
        return is.subSequence(i, i1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o.equals(is);
    }

    @Override
    public int hashCode() {
        return is.hashCode();
    }

    @Override
    public String toString() {
        return is;
    }
}
