package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.model.Result;
import com.loadero.types.ResultStatus;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestResult extends AbstractTestLoadero {
    private static final String resultFile = "body-run-single-result.json";
    private static final String allResultsFile = "body-run-results-NyqBC.json";
    private static final String resultsFileWithWebRtc = "body-run-single-result-webrtc.json";
    private static final String resultsFileWithUnknownPaths = "body-run-single-result-unknown-assert-paths.json";
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
    public void testWebRtcDeserialization() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/results/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(resultsFileWithWebRtc))
        );
        Result result = Result.read(TEST_ID, RUN_ID, resultId);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getAsserts().get(0));
    }

    @Test
    public void testUnknownPathsDeserialization() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/results/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(resultsFileWithUnknownPaths))
        );
        Assertions.assertThrows(ApiException.class, () -> {
            Result result = Result.read(TEST_ID, RUN_ID, resultId);
        });
    }

    @Test
    public void testReadAll() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/results/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(allResultsFile)
            )
        );
        List<Result> results = Result.readAll(TEST_ID, RUN_ID);
        Assertions.assertNotNull(results);
    }

    @Test
    public void negativeReadAll() {
        wmRule.stubFor(get(urlMatching(".*/results/"))
            .willReturn(aResponse()
                .withStatus(404)
            )
        );

        Assertions.assertThrows(ApiException.class, () -> {
            List<Result> results = Result.readAll(1, RUN_ID);
        });

        Assertions.assertThrows(ApiException.class, () -> {
            List<Result> results = Result.readAll(TEST_ID, 1);
        });
    }
}
