package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.model.Result;
import com.loadero.types.ResultStatus;
import com.loadero.types.RunStatus;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class TestResult extends AbstractTestLoadero {
    private static final String resultFile = "body-run-single-result.json";
    private static final String resultsFile = "body-run-results-NyqBC.json";
    private static final int resultId = 1007803;

    @BeforeAll
    public void init() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
    }

    @Test
    public void retrieveResult() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/results/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(resultFile))
        );

        Result result = Result.read(TEST_ID, RUN_ID, resultId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(ResultStatus.FAIL, result.getStatus());
        Assertions.assertNotNull(result.getArtifacts().getScreenshots());
        Assertions.assertNotNull(result.getLogPaths());
        Assertions.assertNotNull(result.getParticipantDetails());
        Assertions.assertNotNull(result.getAsserts());
    }

    @Test
    public void NotFoundResult() {
        wmRule.stubFor(get(urlMatching(".*/results/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(404))
        );

        Assertions.assertThrows(ApiException.class, () -> {
            Result result = Result.read(TEST_ID, RUN_ID, 1);
        });
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testReadAll() throws IOException {
        List<Result> results = Result.readAll(TEST_ID, RUN_ID);
        Assertions.assertNotNull(results);
    }
}
