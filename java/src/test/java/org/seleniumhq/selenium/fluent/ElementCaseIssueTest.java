package org.seleniumhq.selenium.fluent;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.mockito.Mockito.when;

public class ElementCaseIssueTest extends BaseTest {

    @Test
    public void abbr_mismatched_only_by_case_of_tag() {

        when(wd.findElement(By.linkText("mismatching_case_of_tag_name"))).thenReturn(we);
        when(we.getTagName()).thenReturn("ABBR");  // Safari does this.

        fwd.abbr(By.linkText("mismatching_case_of_tag_name"))
                .clearField();

    }


}
