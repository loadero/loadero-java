package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoRunInfo;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import loadero.utils.LoaderoUrlBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Implementation of AbstractLoaderoService that is responsible for polling operation.
 */
public class LoaderoPollingService extends AbstractLoaderoService<LoaderoRunInfo> {
    private final LoaderoHttpClient     client;
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder     urlBuilder     = super.getUrlBuilder();
    private final Logger log = LogManager.getLogger(LoaderoPollingService.class);

    public LoaderoPollingService(LoaderoCrudController crudController,
                                 LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
        this.client = new LoaderoHttpClient(crudController.getLoaderoApiToken());
    }

    @Override
    public LoaderoRunInfo getById(String... id) {
        String testId = id[0];
        String runId  = id[1];
        LoaderoClientUtils.checkArgumentsForNull(testId, runId);

        String getRunsUrl = buildUrl(testId, runId);
        return (LoaderoRunInfo) crudController
                .get(getRunsUrl, LoaderoType.LOADERO_RUN_INFO);
    }

    // Not needed.
    @Override
    public LoaderoRunInfo updateById(LoaderoRunInfo newModel, String... id) {
        return null;
    }

    // Building url for tests/testId/runs/runId/ endpoint.
    @Override
    protected String buildUrl(String...id) {
        return String.format("%s/runs/%s/",
                urlBuilder.buildTestURLById(id[0]),
                id[1]);
    }

    public LoaderoRunInfo startTestAndPoll(String testId, int interval, int timeout) {
        LoaderoRunInfo startTestRun = startTestRun(testId);
        LoaderoClientUtils.checkArgumentsForNull(startTestRun, testId, interval, timeout);

        log.info("Test {} is now running.", testId);
        return (LoaderoRunInfo) startPolling(
                testId, String.valueOf(startTestRun.getId()),
                interval, timeout);
    }

    /**
     * Start test run by sending POST request to tests/testId/runs/ endpoint.
     * @param testId - ID of the test we want to start.
     * @return       - Returns LoaderoRunInfo object with information about test run.
     */
    private LoaderoRunInfo startTestRun(String testId) {
        String startTestRunUrl = String.format("%s/runs/", urlBuilder.buildTestURLById(testId));

        log.info("Starting test with ID {} - {}", testId, startTestRunUrl);
        LoaderoRunInfo result = null;
        try {
            HttpUriRequest postRun = RequestBuilder.post(startTestRunUrl).build();
            try (CloseableHttpResponse res = client.build().execute(postRun)) {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
                    result = (LoaderoRunInfo) LoaderoClientUtils.httpEntityToModel(
                            res.getEntity(),
                            LoaderoType.LOADERO_RUN_INFO
                    );
                    log.info("{} - {} - Test successfully started",
                            postRun.getMethod(),
                            res.getStatusLine());
                } else {
                    log.info("{} - {} - Test stopped with value: {}",
                            postRun.getMethod(),
                            res.getStatusLine(),
                            result);
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param interval - time in seconds between get requests
     * @param timeout  - maximum amount of time in seconds should spend polling
     * @return         - On success will return LoaderoRunInfo object, otherwise null.
     */
    private LoaderoModel startPolling(String testId, String runId,
                                      int interval, int timeout) {
        LoaderoRunInfo result = null;
        String getRunInfo = buildUrl(testId, runId);
        log.info("Run ID {} - {}", runId, getRunInfo);
        // converting seconds into ms
        interval = interval * 1000;
        timeout = timeout * 1000;

        int totalAttempts = timeout / interval;
        int attemptNo = 1;
        boolean done = false;

        long start = System.currentTimeMillis();
        long end = start + timeout;

        while (!done || System.currentTimeMillis() < end) {
            log.info("Attempt number {}/{}", attemptNo, totalAttempts);
            if (attemptNo >= totalAttempts) {
                log.info("Number of attempts expired.");
                return result;
            }

            while (!done) {
                try {
                    result = getById(testId, runId);
                    if (result.getStatus().equals("done")) {
                        done = true;
                        log.info("Test run is done.");
                        log.info("Test run information available at {}",
                                getRunInfo + "results/");
                    } else {
                        log.info("Test status: {}", result.getStatus());
                        Thread.sleep(interval);
                    }
                } catch (Exception e) {
                    log.warn("{}", e.getMessage());
                    stopTestRun(testId, runId);
                    return result;
                }
                attemptNo++;
            }
        }
        return result;
    }

    /**
     * Stops test run by calling POST on runs/rundId/stop/
     */
    private void stopTestRun(String testId, String runId) {
        String stopURI = buildUrl(testId, runId);
        HttpUriRequest stopRun = RequestBuilder.post(stopURI).build();
        try (CloseableHttpResponse res = client.build().execute(stopRun)) {
            client.build().execute(stopRun);
            log.error("{} : {} : Something went wrong. Test run has stopped.",
                    stopRun.getMethod(),
                    res.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
