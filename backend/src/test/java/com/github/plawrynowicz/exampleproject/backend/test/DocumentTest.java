package com.github.plawrynowicz.exampleproject.backend.test;

import com.github.plawrynowicz.exampleproject.backend.dao.Status;
import com.github.plawrynowicz.exampleproject.backend.dto.CreateStrategyDroolsResultPostDto;
import com.github.plawrynowicz.exampleproject.backend.dto.StatusDto;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.http.ContentType;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentTest extends BackendTestBase {

    public static final String STRATEGY_PATH_CREATE = "/strategy-process-service/rest/technical/strategyDroolsResult/create";
    public static final String STRATEGY_PATH_EXECUTION = "/strategy-process-service/rest/strategyAction/DOCUMENT/10000/execution";


    //TC.1.1 – Uruchomienie procesu wysyłki dokumentów (Działanie strategiczne Dokument)
    @Test
    public void startSendDocumentsProcess() {

        // 1
        // we don't have an application to test, so let's use a mock
        wiremock.stubFor(WireMock.post(STRATEGY_PATH_CREATE)
                .willReturn(WireMock.aResponse().withStatus(200)));

        given()
                .header("requestId", "test-request")
                .header("userId", "test-user")
                .contentType(ContentType.JSON)
                .sessionId("test-session")
                .and()
                .body(CreateStrategyDroolsResultPostDto.builder()
                        .clientIds(List.of(1L, 2L, 3L, 4L, 5L))
                        .actionDefinitionId(1000L)
                        .segmentationId(10_000L)
                        .build())
//                .body("""
//                        {
//                          "clientIds" : [
//                            1,2,3,4,5
//                          ],
//                          "actionDefinitionId": 1000,
//                          "segmentationId" : 10000
//                        }""")
                .when()
                .post(wiremock.url(STRATEGY_PATH_CREATE))
                .then()
                .statusCode(200);


        // 2
        // we don't have an application to test, so let's use a mock
        wiremock.stubFor(WireMock.get(STRATEGY_PATH_EXECUTION)
                .willReturn(WireMock.aResponse().withStatus(200)));

        given()
                .contentType(ContentType.JSON)
                .header("userId", "test-user")
                .sessionId("test-session")
                .header("requestId", "test-request")
                .when()
                .get(wiremock.url(STRATEGY_PATH_EXECUTION))
                .then()
                .statusCode(200);


        // 3
        Awaitility.await()
                .atMost(Duration.ofSeconds(60))
                .until(() -> dao.getStrategyDroolsResultDao().findStatuses(Status.FOR_REALIZATION.toString(), 10000, "DOCUMENT").isEmpty());

        // 4
        List<String> statuses = dao.getStrategyDroolsResultDao().findStatuses(Status.IN_PROGRESS.toString(), 10000, "DOCUMENT");
        assertThat(statuses.stream().filter(s -> !s.equals(Status.IN_PROGRESS.toString())).count()).isZero();

        // 5
        List<StatusDto> statusesFromStrategyDroolsResultAndDocument = dao.getStrategyDroolsResultDao().findStatusesFromStrategyDroolsResultAndDocument(Status.IN_PROGRESS.toString(), 10000, "DOCUMENT");
        int documentCount = dao.getDocumentDao().count();
        // TODO perhaps 0 documents here should result in an exception

        assertThat(statusesFromStrategyDroolsResultAndDocument.size()).isEqualTo(documentCount);

        // 6
        int documentsInProgressCount = dao.getDocumentDao().countByStatus(Status.IN_PROGRESS.toString());
        assertThat(documentCount).isEqualTo(documentsInProgressCount);
    }


}


