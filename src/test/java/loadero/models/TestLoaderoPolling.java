package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import loadero.exceptions.LoaderoException;
import loadero.model.LoaderoRunInfo;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLoaderoPolling extends AbstractTestLoadero {
    
    @Test
    public void testPolling() {
        String scenarioName = "polling";
        String getRunInfo = ".*/runs/.*";
        
        wmRule.stubFor(post(urlMatching(".*/runs/"))
                .inScenario(scenarioName)
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_ACCEPTED)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-pending-ua.json"))
        );
        
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-pending-ua.json"))
                .willSetStateTo("pending")
        );
        
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("pending")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-initializing-ua.json"))
                .willSetStateTo("initializing")
        );
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("initializing")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-running-ua.json"))
                .willSetStateTo("running")
        );
        
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("running")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-waiting-for-results-ua.json"))
                .willSetStateTo("waiting for results")
        );
        
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("waiting for results")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-done-ua.json"))
        );
        
        LoaderoRunInfo poll = loaderoClient.startTestAndPollInfo(TEST_ID, 10, 40);
        assertEquals("done", poll.getStatus());
    }
    
    @Test
    public void negativePollingTestInvalidTestId() {
        assertThrows(NullPointerException.class, () ->
                loaderoClient.startTestAndPollInfo(111, 6, 40));
    }
    
    @Test
    public void negativePollingTestInvalidInterval() {
        assertThrows(LoaderoException.class, () ->
                loaderoClient.startTestAndPollInfo(111, 3, 40));
    }
}
