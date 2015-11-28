package org.seleniumhq.selenium.fluent;

import java.util.HashMap;

public abstract class FluentWebElementMap<K,V> extends HashMap<K,V>{

    public abstract void map(FluentWebElement elem, int ix);

}
