package com.github.plawrynowicz.exampleproject.frontend.test;

import com.github.plawrynowicz.exampleproject.frontend.page.KnownUriPath;
import com.github.plawrynowicz.exampleproject.frontend.page.LoginPage;
import com.github.plawrynowicz.exampleproject.frontend.page.PageBase;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.NginxContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;

@Testcontainers
public class FrontendTestBase {

    private WebDriver driver;

    @Container
    public NginxContainer<?> nginx = new NginxContainer<>("nginx")
            .withCustomContent("src/test/resources/mock-website/");

    @BeforeAll
    public static void setupBrowserDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @SneakyThrows
    public LoginPage loginPage() {
        driver.navigate().to(nginx.getBaseUrl("http", 80));
        return new LoginPage(driver);
    }

    @SneakyThrows
    <PAGE extends PageBase> PAGE goToPageUsingUrl(Class<PAGE> pageClass) {
        KnownUriPath knownUriPath = pageClass.getAnnotation(KnownUriPath.class);

        if (knownUriPath == null) {
            throw new IllegalStateException(pageClass.getSimpleName() +
                    " has no known page url. Access it from another page object");
        }

        URI currentUri = URI.create(driver.getCurrentUrl());
        URI newUri = currentUri.resolve(knownUriPath.value());

        driver.navigate().to(newUri.toString());

        return pageClass.getConstructor(WebDriver.class).newInstance(driver);
    }


}
