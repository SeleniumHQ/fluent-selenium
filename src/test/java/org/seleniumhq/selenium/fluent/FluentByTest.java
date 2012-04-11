package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FluentByTest {

    private Field xpathExpression;

    @Before
    public void setup() throws NoSuchFieldException {
        xpathExpression = By.ByXPath.class.getDeclaredField("xpathExpression");
        xpathExpression.setAccessible(true);
    }

    @Test
    public void last_xpath_directive_should_be_addable_to_xpath_expression() throws IllegalAccessException {

        FluentBy.ByAttribute fooBar = (FluentBy.ByAttribute) FluentBy.attribute("foo", "bar");
        
        assertThat(fooBar.toString(), is("FluentBy.attribute: foo = 'bar'"));
        assertThat(fooBar.makeByXPath().toString(), is("By.xpath: .//*[@foo = 'bar']"));

        FluentBy.ByLast lastFooBar = FluentBy.last(fooBar);

        assertThat(lastFooBar.toString(), is("FluentBy.last(FluentBy.attribute: foo = 'bar')"));
        assertThat(lastFooBar.makeXPath().toString(), is("By.xpath: .//*[@foo = 'bar' and position() = last()]"));

    }

    @Test
    public void last_xpath_directive_should_be_specifiable() throws IllegalAccessException {

        FluentBy.ByLast lastFooBar = FluentBy.last();

        assertThat(lastFooBar.toString(), is("FluentBy.last()"));
        assertThat(lastFooBar.makeXPath().toString(), is("By.xpath: .//*[position() = last()]"));

    }

}
