package com.github.plawrynowicz.exampleproject.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class PageBase {

    private final WebDriver driver;

    public PageBase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        assertThat(driver.getTitle()).startsWith(expectedTitlePrefix());
    }

    public PageBase(PageBase pageBase) {
        this(pageBase.driver);
    }

    public abstract String expectedTitlePrefix();

}
