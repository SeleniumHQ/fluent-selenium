package org.seleniumhq.selenium.fluent.internal;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ContextTest {

    @Test
    public void iterator_should_be_unmodifiable() throws Exception {
        By bar = By.id("bar");
        Context context = Context.singular(null, "foo", bar);
        assertThat(context.iterator().getClass().getName(), containsString("Unmodifiable"));
        assertThat(context.iterator().next().toString(), equalTo(new ContextElem("foo", bar, null).toString()));
    }

}
