package com.loadero.models;

import com.loadero.AbstractTestLoadero;
import loadero.model.TestRunParticipantResult;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestTestRunParticipantResult extends AbstractTestLoadero {
    @Test
    public void testGetSingleRunResultsFromWireMock() {
        String resultsUrl = String.format(".*/tests/%s/runs/%s/results/%s/",
                TEST_ID, RUN_ID, RESULT_ID);
        
        wmRule.stubFor(get(urlMatching(resultsUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-participant-results-iHbtF.json")));
        
        TestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResultById(TEST_ID, RUN_ID, RESULT_ID);
        assertNotNull(result);
        assertNotNull(result.getLogPaths());
        assertNotNull(result.getArtifacts());
        // log_paths fields shouldn't be null
        assertNotNull(result.getLogPaths().get("browser"));
        assertNotNull(result.getLogPaths().get("webrtc"));
        assertNotNull(result.getLogPaths().get("selenium"));
        assertNotNull(result.getLogPaths().get("rru"));
    }
    
    @Test
    public void negativeGetSingleRunResultsInvalidResultId() {
        wmRule.resetMappings();
        TestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResultById(TEST_ID, RUN_ID, 2341);
        assertNull(result);
    }
}
