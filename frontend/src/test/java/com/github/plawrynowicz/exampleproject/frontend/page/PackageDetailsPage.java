package com.github.plawrynowicz.exampleproject.frontend.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PackageDetailsPage extends PageBase {

    @FindBy(xpath = "//th[contains(@id, 'packageDetailsForm:packageDetailsListTable')]/div/div/span/input")
    private WebElement selectAllCheckboxes;

    @FindBy(id = "packageDetailsForm:confirmProcessBtn")
    private WebElement confirmButton;

    @FindBy(tagName = "tr")
    private List<WebElement> rows;

    public PackageDetailsPage(PageBase pageBase) {
        super(pageBase);
    }

    @Override
    public String expectedTitlePrefix() {
        return "PackageDetailsPage";
    }


    public void selectAllRecords() {
        selectAllCheckboxes.click();
    }

    public void confirmAction() {
        confirmButton.click();
    }

    public boolean noDetailsVisible() {
        return rows.size() == 1;
    }
}
