package org.seleniumhq.selenium.fluent;
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

import org.openqa.selenium.By;
import org.openqa.selenium.IllegalLocatorException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Mechanism used to locate elements within a document. In order to create your own locating
 * mechanisms, it is possible to subclass this class and override the protected methods as required,
 * though it is expected that that all subclasses rely on the basic finding mechanisms provided
 * through static methods of this class:
 * <p/>
 * <code>
 * public WebElement findElement(WebDriver driver) {
 * WebElement element = driver.findElement(By.id(getSelector()));
 * if (element == null)
 * element = driver.findElement(By.name(getSelector());
 * return element;
 * }
 * </code>
 */
public abstract class FluentBy {

    /**
     * Finds elements based on the value of the "class" attribute.
     * If the second param is set to true, if an element has many classes then
     * this will match against each of them. For example if the value is "one two onone",
     * then the following "className"s will match: "one" and "two"
     *
     * @param className The value of the "class" attribute to search for
     * @return a By which locates elements by the value of the "class" attribute.
     */
    public static By strictClassName(final String className) {
        if (className == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        if (className.matches(".*\\s+.*")) {
            throw new IllegalLocatorException(
                    "Compound class names are not supported. Consider searching for one class name and filtering the results.");
        }

        return new ByStrictClassName(className);
    }

    /**
     * Finds elements by the presence of an attribute name irrespective
     * of element name. Currently implemented via XPath.
     */
    public static By attribute(final String name) {
        return attribute(name, null);
    }

    /**
     * Finds elements by an named attribute matching a given value,
     * irrespective of element name. Currently implemented via XPath.
     */
    public static By attribute(final String name, final String value) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the attribute name is null");

        return new ByAttribute(name, value);

    }

    /**
     * Finds elements a composite of other By strategies
     */
    public static By composite(final By... bys) {
        if (bys == null)
            throw new IllegalArgumentException("Cannot make composite with no varargs of Bys");
        if (bys.length != 2
                || !(bys[0] instanceof ByTagName)
                || !(bys[1] instanceof ByClassName | bys[1] instanceof ByAttribute)) {
            throw new IllegalArgumentException("can only do this with FluentBy.tagName " +
                    "followed one of FluentBy.className or FluentBy.attribute");
        }

        return new ByComposite(bys);
    }

    // Until WebDriver supports a composite in browser implementations, only
    // TagName + ClassName is allowed as it can easily map to XPath.
    private static class ByComposite extends By {

        private final By[] bys;

        public ByComposite(By... bys) {
            this.bys = bys;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return makeByXPath().findElements(context);
        }

        private ByXPath makeByXPath() {
            String xpathExpression = ".//"
                    + ((ByTagName) bys[0]).name;

            if (bys[1] instanceof ByClassName) {
                ByClassName by = (ByClassName) bys[1];
                xpathExpression = xpathExpression + "[" + by.classAmongstClasses() + "]";
            }

            if (bys[1] instanceof ByAttribute) {
                ByAttribute by = (ByAttribute) bys[1];
                xpathExpression = xpathExpression + "[" + by.nameAndValue() + "]";
            }

            return new ByXPath(xpathExpression);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            return makeByXPath().findElement(context);
        }

        @Override
        public String toString() {
            return "composite(" + asList(bys) + ")";
        }
    }

    private static class ByStrictClassName extends By {
        private final String className;

        public ByStrictClassName(String className) {
            this.className = className;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            if (context instanceof FindsByClassName)
                return ((FindsByClassName) context).findElementsByClassName(className);
            return ((FindsByXPath) context).findElementsByXPath(".//*["
                    + "@class = '" + className + "']");
        }

        @Override
        public WebElement findElement(SearchContext context) {
            if (context instanceof FindsByClassName)
                return ((FindsByClassName) context).findElementByClassName(className);
            return ((FindsByXPath) context).findElementByXPath(".//*["
                    + "@class = '" + className + "']");
        }

        @Override
        public String toString() {
            return "By.strictClassName: " + className;
        }
    }

    private static class ByAttribute extends By {
        private final String name;
        private final String value;

        public ByAttribute(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public WebElement findElement(SearchContext context) {
            return makeXPath().findElement(context);
        }

        private ByXPath makeXPath() {
            return new ByXPath(".//*[" + nameAndValue() + "]");
        }

        private String nameAndValue() {
            return "@" + name + val();
        }

        private String val() {
            return value == null ? "" : " = '" + value + "'";
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return makeXPath().findElements(context);
        }

        @Override
        public String toString() {
            return "By.attribute: " + name + val();
        }
    }

    // Copied from Selenium's By:

    /**
     * @param name The element's tagName
     * @return a By which locates elements by their tag name
     */
    public static By tagName(final String name) {
      if (name == null)
        throw new IllegalArgumentException(
            "Cannot find elements when name tag name is null.");

      return new ByTagName(name);
    }

    /**
     * Finds elements based on the value of the "class" attribute. If an element has many classes then
     * this will match against each of them. For example if the value is "one two onone", then the
     * following "className"s will match: "one" and "two"
     *
     * @param className The value of the "class" attribute to search for
     * @return a By which locates elements by the value of the "class" attribute.
     */
    public static By className(final String className) {
      if (className == null)
        throw new IllegalArgumentException(
            "Cannot find elements when the class name expression is null.");

      if (className.matches(".*\\s+.*")) {
        throw new IllegalLocatorException(
            "Compound class names are not supported. Consider searching for one class name and filtering the results.");
      }

      return new ByClassName(className);
    }


    private static class ByTagName extends By {
        private final String name;

        public ByTagName(String name) {
            this.name = name;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            if (context instanceof FindsByTagName)
                return ((FindsByTagName) context).findElementsByTagName(name);
            return ((FindsByXPath) context).findElementsByXPath(".//" + name);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            if (context instanceof FindsByTagName)
                return ((FindsByTagName) context).findElementByTagName(name);
            return ((FindsByXPath) context).findElementByXPath(".//" + name);
        }

        @Override
        public String toString() {
            return "By.tagName: " + name;
        }
    }

    private static class ByXPath extends By {
        private final String xpathExpression;

        public ByXPath(String xpathExpression) {
            this.xpathExpression = xpathExpression;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return ((FindsByXPath) context).findElementsByXPath(xpathExpression);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            return ((FindsByXPath) context).findElementByXPath(xpathExpression);
        }

        @Override
        public String toString() {
            return "By.xpath: " + xpathExpression;
        }
    }

    private static class ByClassName extends By {
        private final String className;

        public ByClassName(String className) {
            this.className = className;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            if (context instanceof FindsByClassName)
                return ((FindsByClassName) context).findElementsByClassName(className);
            return ((FindsByXPath) context).findElementsByXPath(".//*["
                    + containingWord("class", className) + "]");
        }

        @Override
        public WebElement findElement(SearchContext context) {
            if (context instanceof FindsByClassName)
                return ((FindsByClassName) context).findElementByClassName(className);
            return ((FindsByXPath) context).findElementByXPath(".//*["
                    + classAmongstClasses() + "]");
        }

        /**
         * Generates a partial xpath expression that matches an element whose specified attribute
         * contains the given CSS word. So to match &lt;div class='foo bar'&gt; you would say "//div[" +
         * containingWord("class", "foo") + "]".
         *
         * @param attribute name
         * @param word      name
         * @return XPath fragment
         */
        private String containingWord(String attribute, String word) {
            return "contains(concat(' ',normalize-space(@" + attribute + "),' '),' "
                    + word + " ')";
        }

        @Override
        public String toString() {
            return "By.className: " + className;
        }

        public String classAmongstClasses() {
            return containingWord("class", className);
        }
    }


}
