package com.github.plawrynowicz.exampleproject.frontend.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

@KnownUriPath("strategyPackages.html")
public class StrategyPackagesPage extends PageBase {

    @FindBy(id = "strategyPackagesForm:packageListTable_data")
    private WebElement tableOfPackages;

    public StrategyPackagesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedTitlePrefix() {
        return "StrategyPackagesPage";
    }

    public String pageUrl() {
        return "strategyPackages.html";
    }

    public void validateAnyPackageVisible() {
        assertThat(tableOfPackages.findElements(By.xpath("./tr")))
                .withFailMessage("No packages were visible")
                .isNotEmpty();
    }

    public PackageDetailsPage goToFirstPackage() {
        tableOfPackages.findElements(By.xpath("./tr/td/a")).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Found no package"))
                .click();

        return new PackageDetailsPage(this);
    }
}
