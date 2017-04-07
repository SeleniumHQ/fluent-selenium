package org.seleniumhq.selenium.fluent;
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

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByXPath;

import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * <p>
 * Mechanism used to locate elements within a document. In order to create your own locating
 * mechanisms, it is possible to subclass this class and override the protected methods as required,
 * though it is expected that that all subclasses rely on the basic finding mechanisms provided
 * through static methods of this class:
 * </p>
 * <code>
 * public WebElement findElement(WebDriver driver) {
 * WebElement element = driver.findElement(By.id(getSelector()));
 * if (element == null)
 * element = driver.findElement(By.name(getSelector());
 * return element;
 * }
 * </code>
 */
public abstract class FluentBy extends By {


    public void beforeFindElement(WebDriver driver) {
        // nothing by default.
    }

    /**
     * Finds elements based on the value of the "class" attribute.
     * If the second param is set to true, if an element has many classes then
     * this will match against each of them. For example if the value is "one two onone",
     * then the following "className"s will match: "one" and "two"
     *
     * @param className The value of the "class" attribute to search for
     * @return a By which locates elements by the value of the "class" attribute.
     */
    public static ByStrictClassName strictClassName(final String className) {
        if (className == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        if (className.matches(".*\\s+.*")) {
            throw new InvalidSelectorException(
                    "Compound class names are not supported. Consider searching for one class name and filtering the results.");
        }

        return new ByStrictClassName(className);
    }

    /**
     * Finds elements by the presence of an attribute name irrespective
     * of element name. Currently implemented via XPath.
     */
    public static ByAttribute attribute(final String name) {
        return attribute(name, null);
    }

    /**
     * Finds elements by an named attribute matching a given value,
     * irrespective of element name. Currently implemented via XPath.
     */
    public static ByAttribute attribute(final String name, final String value) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the attribute name is null");

        return new ByAttribute(name, value);
    }

    /**
     * Finds elements by an named attribute not being present in the element,
     * irrespective of element name. Currently implemented via XPath.
     */
    public static ByAttribute notAttribute(final String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the attribute name is null");

        return new NotByAttribute(name);
    }

    /**
     * Finds elements a composite of other By strategies
     */
    public static ByComposite composite(By.ByTagName b0, By.ByClassName b1) {
        return new ByComposite(b0, b1);
    }
    public static ByComposite composite(By.ByTagName b0, FluentBy.ByAttribute b1) {
        return new ByComposite(b0, b1);
    }

    public static ByLast last(By by) {
        if (by instanceof ByAttribute) {
            return new ByLast((ByAttribute) by);
        }
        throw new UnsupportedOperationException("last() not allowed for " + by.getClass().getName() + " type");
    }

    public static ByLast last() {
        return new ByLast();
    }
	public static ByXTitle xtitle(final String xTitle) {
		return new ByXTitle(xTitle, "");
	}

	public static ByXTitle xtitle(final String xTitle, final String xPathPrefixSelector) {
		return new ByXTitle(xTitle, xPathPrefixSelector);
	}

	public static ByXTitle xtitle(final String xTitles[]) {
		return new ByXTitle(xTitles, "");
	}
	
	public static ByXTitle xtitle(final String xTitles[], final String xPathPrefixSelector) {
		return new ByXTitle(xTitles, xPathPrefixSelector);
	}
	
	public static ByXID xid(String xID) {
		return new ByXID(xID, "");
	}

	public static ByXID xid(String xID, final String xPathPrefixSelector) {
		return new ByXID(xID, xPathPrefixSelector);
	}

	public static ByXID xid(String xIDs[]) {
		return new ByXID(xIDs, "");
	}

	public static ByXID xid(final String xIDs[], final String xPathPrefixSelector) {
		return new ByXID(xIDs, xPathPrefixSelector);
	}	

	public static class ByXID extends FluentBy {
		protected final String[] xIDs;
		protected final String xPathPrefixSelector;

		public ByXID(String xID[], String xPathPrefixSelector) {
			this.xIDs = xID;
			this.xPathPrefixSelector = xPathPrefixSelector;
		}

		public ByXID(String xID, String xPathPrefixSelector) {
			this.xIDs = new String[1];
			this.xIDs[0] = xID;
			this.xPathPrefixSelector = xPathPrefixSelector;
		}

		private By makeByXPath() {
			return By.xpath(this.makeXPath());
		}

		private String makeXPath() {
			String xpath = ".";

			for (String xid : this.xIDs) {
				xpath += "//*[@data-xtest-id='" + xid + "']";
			}

			if (this.xPathPrefixSelector.length() > 0) {
				xpath = this.xPathPrefixSelector + xpath;
			}

			return xpath;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return makeByXPath().findElements(context);
		}

		@Override
		public String toString() {
			return "FluentBy.xtest-id: " + Arrays.toString(this.xIDs) + " with prefix: " + this.xPathPrefixSelector;
		}
	}

	public static class ByXTitle extends FluentBy {
		protected final String[] xTitles;
		protected final String xPathPrefixSelector;

		public ByXTitle(String xTitle[], String xPathPrefixSelector) {
			this.xTitles = xTitle;
			this.xPathPrefixSelector = xPathPrefixSelector;
		}

		public ByXTitle(String xTitle, String xPathPrefixSelector) {
			this.xTitles = new String[1];
			this.xTitles[0] = xTitle;
			this.xPathPrefixSelector = xPathPrefixSelector;
		}

		private By makeByXPath() {
			return By.xpath(this.makeXPath());
		}

		private String makeXPath() {
			String xpath = ".";

			for (String xtitle : this.xTitles) {
				if (xtitle.endsWith("*")) {
					xtitle = xtitle.replace("*", "");
					xpath += "//*[starts-with(@data-xtest-title, '" + xtitle + "')]";
				} else {
					xpath += "//*[contains(@data-xtest-title, '" + xtitle + "')]";
				}
			}

			if (this.xPathPrefixSelector.length() > 0) {
				xpath = this.xPathPrefixSelector + xpath;
			}

			return xpath;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return makeByXPath().findElements(context);
		}

		@Override
		public String toString() {
			return "FluentBy.xtest-title: " + Arrays.toString(this.xTitles) + " and prefix: " + this.xPathPrefixSelector;
		}
	}

    private static class ByLast extends FluentBy {
        
        private final String orig;
        private final ByAttribute by;

        public ByLast(ByAttribute by) {
            this.by = by;
            this.orig = by.makeXPath();
        }
        public ByLast() {
            this.orig = ".//*[]";
            by = null;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return makeXPath().findElements(context);
        }

        private By makeXPath() {
            return By.xpath((orig.substring(0, orig.length() - 1)
                    + " and position() = last()]").replace("[ and ", "["));
        }

        @Override
        public WebElement findElement(SearchContext context) {
            return makeXPath().findElement(context);
        }

        @Override
        public String toString() {
            return "FluentBy.last(" + (by == null ? "" : by)  + ")";
        }

    }

    // Until WebDriver supports a composite in browser implementations, only
    // TagName + ClassName is allowed as it can easily map to XPath.
    private static class ByComposite extends FluentBy {

        private final By[] bys;

        private ByComposite(By... bys) {
            this.bys = bys;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return makeByXPath().findElements(context);
        }

        private String containingWord(String attribute, String word) {
          return "contains(concat(' ',normalize-space(@" + attribute + "),' '),' "
              + word + " ')";
        }

        private By makeByXPath() {
            String xpathExpression = makeXPath();
            return By.xpath(xpathExpression);
        }

        private String makeXPath() {
            String xpathExpression = ".//" + getTagName();

            if (bys[1] instanceof ByClassName) {
                String className = bys[1].toString().substring("By.className: ".length());
                xpathExpression = xpathExpression + "[" + containingWord("class", className) + "]";
            } else if (bys[1] instanceof ByAttribute) {
                ByAttribute by = (ByAttribute) bys[1];
                xpathExpression = xpathExpression + "[" + by.nameAndValue() + "]";
            }
            return xpathExpression;
        }

        private String getTagName() {
            return bys[0].toString().substring("By.tagName: ".length());
        }

        @Override
        public WebElement findElement(SearchContext context) {
            return makeByXPath().findElement(context);
        }

        @Override
        public String toString() {
            return "FluentBy.composite(" + asList(bys) + ")";
        }
    }

    private static class ByStrictClassName extends FluentBy {
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
            return "FluentBy.strictClassName: " + className;
        }
    }

    public static class NotByAttribute extends ByAttribute {
        public NotByAttribute(String name) {
            super(name, null);
        }

        @Override
        protected String nameAndValue() {
            return "not(" + super.nameAndValue() + ")";
        }

        public String toString() {
            return "FluentBy.notAttribute: " + name ;
        }

    }

    public static class ByAttribute extends FluentBy {
        protected final String name;
        private final String value;

        public ByAttribute(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public WebElement findElement(SearchContext context) {
            return makeByXPath().findElement(context);
        }

        private By makeByXPath() {
            return By.xpath(makeXPath());
        }

        private String makeXPath() {
            return ".//*[" + nameAndValue() + "]";
        }

        protected String nameAndValue() {
            return "@" + name + val();
        }

        private String val() {
            return value == null ? "" : " = '" + value + "'";
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return makeByXPath().findElements(context);
        }

        @Override
        public String toString() {
            return "FluentBy.attribute: " + name + val();
        }
    }

}
