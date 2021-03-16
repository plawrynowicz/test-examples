package com.github.plawrynowicz.exampleproject.frontend.test;

import com.github.plawrynowicz.exampleproject.frontend.page.LoginPage;
import com.github.plawrynowicz.exampleproject.frontend.page.PackageDetailsPage;
import com.github.plawrynowicz.exampleproject.frontend.page.StrategyPackagesPage;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.time.Duration;


public class MassSendTest extends FrontendTestBase {

    // TC.1.2 – Zakończenie procesu wysyłki masowej dokumentów
    @Test
    public void finishDocumentMassSend() {
        // 1
        LoginPage loginPage = loginPage();
        loginPage.login("test", "test");

        // 2
        StrategyPackagesPage strategyPackagesPage = goToPageUsingUrl(StrategyPackagesPage.class);

        // 3
        strategyPackagesPage.validateAnyPackageVisible();

        // 4
        PackageDetailsPage packageDetailsPage = strategyPackagesPage.goToFirstPackage();

        // 5
        packageDetailsPage.selectAllRecords();

        // 6
        packageDetailsPage.confirmAction();

        // 7
        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .until(packageDetailsPage::noDetailsVisible);


    }
}
