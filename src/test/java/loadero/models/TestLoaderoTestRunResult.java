package loadero.models;

import loadero.model.LoaderoTestRunResult;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestLoaderoTestRunResult extends AbstractTestLoadero {
    
    @Test
    public void testGetAllResultsFromWireMock() {
        String allResultsUrl = String.format(".*/tests/%s/runs/%s/results/", TEST_ID, RUN_ID);
        
        wmRule.givenThat(get(urlMatching(allResultsUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-run-results-NyqBC.json")));
        
        LoaderoTestRunResult testRunResult = loaderoClient.getTestRunResultById(TEST_ID, RUN_ID);
        assertNotNull(testRunResult);
    }
    
    @Test
    public void negativeGetAllTestResultsInvalidTestId() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResultById(2323, RUN_ID);
        assertNull(results);
    }
}
