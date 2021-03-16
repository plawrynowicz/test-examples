package com.github.plawrynowicz.exampleproject.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@KnownUriPath("index.html")
public class LoginPage extends PageBase {

    @FindBy(id = "LoginForm:username")
    private WebElement loginInput;

    @FindBy(id = "LoginForm:password")
    private WebElement passwordInput;

    @FindBy(id = "LoginForm:loginCmdLink")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedTitlePrefix() {
        return "LoginPage";
    }

    public String pageUrl() {
        return "index.html";
    }

    public void login(String login, String password) {
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
