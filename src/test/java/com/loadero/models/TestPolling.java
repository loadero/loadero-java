package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.exceptions.ApiPollingException;
import com.loadero.model.TestRun;
import com.loadero.types.RunStatus;
import java.io.IOException;
import java.time.Duration;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class TestPolling extends AbstractTestLoadero {
    private StubMapping pendingStub1;
    private StubMapping pendingStub2;
    private StubMapping initStub;
    private StubMapping runningStub;
    private StubMapping resultStub;
    private StubMapping doneStub;

    @BeforeEach
    public void setupStubs() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        String scenarioName = "polling";
        String getRunInfo = ".*/runs/.*";

        pendingStub1 = wmRule.stubFor(post(urlMatching(".*/runs/"))
            .inScenario(scenarioName)
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_ACCEPTED)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-pending-ua.json"))
        );

        pendingStub2 = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-pending-ua.json"))
            .willSetStateTo("pending")
        );

        initStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("pending")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-initializing-ua.json"))
            .willSetStateTo("initializing")
        );
        runningStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("initializing")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-running-ua.json"))
            .willSetStateTo("running")
        );

        resultStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("running")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(
                    "body-projects-5040-tests-6866-runs-34778-waiting-for-results-ua.json"))
            .willSetStateTo("waiting for results")
        );

        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-done-ua.json"))
        );
    }

    @AfterEach
    public void removeStubs() {
        wmRule.removeStubMapping(pendingStub1);
        wmRule.removeStubMapping(pendingStub2);
        wmRule.removeStubMapping(initStub);
        wmRule.removeStubMapping(resultStub);
        wmRule.removeStubMapping(doneStub);
        wmRule.removeStubMapping(runningStub);
    }

    @Test
    @Order(1)
    public void testPollingWithTimeout() throws IOException {
        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(),
            Duration.ofSeconds(20), Duration.ofSeconds(100)
        );
        assertEquals(RunStatus.DONE, poll.getStatus());
    }

    @Test
    @Order(2)
    public void testPollingWithoutTimeout() throws IOException {
        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.DONE, poll.getStatus());
    }

    @Test
    @Order(3)
    public void negativeLaunch() {
        wmRule.stubFor(post(urlMatching(".*/runs/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND))
        );
        assertThrows(ApiException.class, () -> {
            com.loadero.model.TestRun launch = com.loadero.model.Test.launch(1);
        });
    }

    @Test
    @Order(4)
    public void negativePollingTimeout() {
        assertThrows(ApiPollingException.class, () -> {
            com.loadero.model.TestRun launch = com.loadero.model.Test.launch(7193);
            TestRun poll = TestRun.poll(
                launch.getTestId(), launch.getId(), Duration.ofSeconds(5), Duration.ofSeconds(10));
        });
    }

    @Test
    @Order(5)
    public void negativeDuration() {
        assertThrows(IllegalArgumentException.class, () -> {
            com.loadero.model.TestRun launch = com.loadero.model.Test.launch(7193);
            TestRun poll = TestRun.poll(
                launch.getTestId(), launch.getId(), Duration.ofSeconds(-5), Duration.ofSeconds(10));
        });
    }

    @Test
    @Order(6)
    public void timeoutZero() {
        assertThrows(ApiPollingException.class, () -> {
            com.loadero.model.TestRun launch = com.loadero.model.Test.launch(7193);
            TestRun poll = TestRun.poll(
                launch.getTestId(), launch.getId(), Duration.ofSeconds(5), Duration.ofSeconds(0));
        });
    }
}
