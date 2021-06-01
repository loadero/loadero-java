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
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

@TestMethodOrder(OrderAnnotation.class)
public class TestPolling extends AbstractTestLoadero {
    private StubMapping pendingStub1;
    private StubMapping pendingStub2;
    private StubMapping initStub;
    private StubMapping runningStub;
    private StubMapping resultStub;
    private StubMapping doneStub;
    private final String scenarioName = "polling";
    private final String getRunInfo = ".*/runs/.*";
    private final String allTestRunsFile = "body-all-test-runs.json";

    @BeforeEach
    public void setupStubs() {
        Loadero.init(BASE_URL, token, PROJECT_ID);

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
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-done-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(),
            Duration.ofSeconds(5), Duration.ofSeconds(100)
        );
        assertEquals(RunStatus.DONE, poll.getStatus());
    }

    @Test
    @Order(2)
    public void testPollingWithoutTimeout() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-done-ua.json"))
        );

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

    @Test
    @Order(7)
    public void testPollingWithoutTimeoutDbError() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-db-error-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.DB_ERROR, poll.getStatus());
    }

    @Test
    @Order(8)
    public void testPollingWithoutTimeoutServerError() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-server-error-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.SERVER_ERROR, poll.getStatus());
    }

    @Test
    @Order(9)
    public void testPollingWithoutTimeoutNoUsers() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-no-users-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.NO_USERS, poll.getStatus());
    }

    @Test
    @Order(10)
    public void testPollingAborted() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-aborted-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.ABORTED, poll.getStatus());
    }

    @Test
    @Order(11)
    public void testPollingAwsError() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-aws-error-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.AWS_ERROR, poll.getStatus());
    }

    @Test
    @Order(12)
    public void testPollingTimeoutExceeded() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("body-projects-5040-tests-6866-runs-34778-timeout-exceeded-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.TIMEOUT_EXCEEDED, poll.getStatus());
    }

    @Test
    @Order(12)
    public void testPollingInsufficientResources() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(getRunInfo))
            .inScenario(scenarioName)
            .whenScenarioStateIs("waiting for results")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(
                    "body-projects-5040-tests-6866-runs-34778-insufficient-resources-ua.json"))
        );

        TestRun run = com.loadero.model.Test.launch(7193);
        Assertions.assertNotNull(run);
        TestRun poll = TestRun.poll(7193, run.getId(), Duration.ofSeconds(10));
        assertEquals(RunStatus.INSUFFICIENT_RESOURCES, poll.getStatus());
    }

    @Test
    @Order(13)
    public void testReadAllRuns() throws IOException {
        doneStub = wmRule.stubFor(get(urlMatching(".*/[0-9]*/runs/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(allTestRunsFile)
            )
        );
        List<TestRun> runs = TestRun.readAll(TEST_ID);
        Assertions.assertNotNull(runs);
    }

    @Test
    @Order(14)
    public void negativeReadAllRuns() {
        doneStub = wmRule.stubFor(get(urlMatching(".*/[0-9]*/runs/"))
            .willReturn(aResponse()
                .withStatus(404)
                .withBody("")
            )
        );

        Assertions.assertThrows(ApiException.class, () -> {
            List<TestRun> runs = TestRun.readAll(1);
        });
    }
}
