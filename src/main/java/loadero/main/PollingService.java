package loadero.main;

import loadero.exceptions.ClientInternalException;
import loadero.model.LoaderoModel;
import loadero.model.RunInfo;
import loadero.types.LoaderoModelType;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Implementation of AbstractService that is responsible for polling operation.
 */
final class PollingService extends AbstractService {
    private final CustomHttpClient client;
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder     = super.getUrlBuilder();
    private final Logger log = LogManager.getLogger(PollingService.class);

    public PollingService(CrudController crudController,
                          CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
        this.client = new CustomHttpClient(crudController.getLoaderoApiToken());
    }

    @Override
    public RunInfo getById(int... ids) {
        int testId = ids[0];
        int runId  = ids[1];
        ClientUtils.checkIfIntIsNegative(testId, runId);

        String getRunsUrl = buildUrl(testId, runId);
        return (RunInfo) crudController
                .get(getRunsUrl, LoaderoModelType.LOADERO_RUN_INFO);
    }
    
    // Building url for tests/testId/runs/runId/ endpoint.
    @Override
    public String buildUrl(int...id) {
        return String.format("%s/runs/%s/",
                urlBuilder.buildTestURLById(id[0]),
                id[1]);
    }
    
    /**
     * Starts test and polls for information about state of the running test with specified interval.
     * @param testId    ID of the test to start.
     * @param interval  Interval in seconds specifies how often information should be polled.
     * @param timeout   Timeout in seconds specifies for how long should be poll for information.
     * @throws ClientInternalException if testId, interval or timeout is negative.
     * @throws NullPointerException if test couldn't be started.
     * @return          RunInfo object containing information about test run.
     */
    public RunInfo startTestAndPoll(int testId, int interval, int timeout) {
        ClientUtils.checkIfIntIsNegative(testId, interval, timeout);
        RunInfo startTestRun = startTestRun(testId);
        ClientUtils.checkArgumentsForNull(startTestRun);

        log.info("Test {} is now running.", testId);
        return (RunInfo) startPolling(
                testId, startTestRun.getId(),
                interval, timeout);
    }

    /**
     * Start test run by sending POST request to tests/testId/runs/ endpoint.
     * @param testId - ID of the test we want to start.
     * @return       - Returns RunInfo object with information about test run.
     */
    private RunInfo startTestRun(int testId) {
        String startTestRunUrl = String.format("%s/runs/", urlBuilder.buildTestURLById(testId));

        log.info("Starting test with ID {} - {}", testId, startTestRunUrl);
        RunInfo result = null;
        try {
            HttpUriRequest postRun = RequestBuilder.post(startTestRunUrl).build();
            try (CloseableHttpResponse res = client.build().execute(postRun)) {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
                    result = (RunInfo) ClientUtils.httpEntityToModel(
                            res.getEntity(),
                            LoaderoModelType.LOADERO_RUN_INFO
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
     * @return         - On success will return RunInfo object, otherwise null.
     */
    private LoaderoModel startPolling(int testId, int runId,
                                      int interval, int timeout) {
        RunInfo result = null;
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
    private void stopTestRun(int testId, int runId) {
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
