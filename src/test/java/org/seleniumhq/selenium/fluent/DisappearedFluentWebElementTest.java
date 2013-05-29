package org.seleniumhq.selenium.fluent;


import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class DisappearedFluentWebElementTest {

    @Test
    public void meaningless_methods() {

        WebDriver wd = mock(WebDriver.class);
        BaseFluentWebDriver.Context ctx = mock(BaseFluentWebDriver.Context.class);

        DisappearedFluentWebElement dfwe = new DisappearedFluentWebElement(wd, ctx);

        try {
            dfwe.click();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("click() has no meaning for element that has disappeared from the DOM"));
        }

        try {
            dfwe.clearField();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("clearField() has no meaning for element that has disappeared from the DOM"));
        }

        try {
            dfwe.getText();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("getText() has no meaning for element that has disappeared from the DOM"));
        }

        try {
            dfwe.size();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("size() has no meaning for element that has disappeared from the DOM"));
        }

        try {
            dfwe.button();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("button() has no meaning for element that has disappeared from the DOM"));
        }



    }

}
