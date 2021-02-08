package loadero.controller;

import loadero.model.LoaderoModel;
import loadero.model.LoaderoModelFactory;
import loadero.model.LoaderoRunInfo;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;


// TODO: log for test status
public class LoaderoPollController {
    private final LoaderoHttpClient     client;
    private final LoaderoModelFactory   factory;
    private final LoaderoRestController restController;
    private final Logger logger = LogManager.getLogger(LoaderoPollController.class);

    public LoaderoPollController(String loaderoApiToken) {
        this.factory = new LoaderoModelFactory();
        this.client  = new LoaderoHttpClient(loaderoApiToken);
        this.restController = new LoaderoRestController(loaderoApiToken);
    }

    public LoaderoModel startTestAndPoll(String uri, int interval, int timeout) {
        LoaderoRunInfo startTestRun = startTestRun(uri);
        logger.info("Test {} is now running.", startTestRun.getTestId());
        logger.info("Run ID {}", startTestRun.getId());
        return startPolling(uri,
                String.valueOf(startTestRun.getId()),
                1000 * interval, 1000 * timeout);
    }

    /**
     * Start test run by sending POST request to /runs url.
     * @param uri
     * @return
     */
    private LoaderoRunInfo startTestRun(String uri) {
        logger.info("Starting test...");
        LoaderoModel result = factory.getLoaderoModel(LoaderoType.LOADERO_RUN_INFO);
        try {
            HttpUriRequest postRun = RequestBuilder.post(uri).build();

            try (CloseableHttpResponse res = client.build().execute(postRun)) {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED &&
                        !LoaderoClientUtils.isNull(res.getEntity())) {
                    result = LoaderoClientUtils.jsonToObject(
                            res.getEntity(),
                            LoaderoType.LOADERO_RUN_INFO
                    );
                    logger.info("Test successfully started.");
                } else {
                    stopTestRun(uri);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (LoaderoRunInfo) result;
    }

    /**
     * Stops test run by calling GET on /stop.
     * @param uri
     */
    private void stopTestRun(String uri) {
        String stopURI = uri + "stop/";
        HttpUriRequest stopRun = RequestBuilder.post(stopURI).build();
        try {
            client.build().execute(stopRun);
            logger.error("Something went wrong. Test run has stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param uri      - uri from where to get info
     * @param interval - time in seconds between get requests
     * @param timeout  - maximum amount of time in seconds should spend polling
     * @return
     */
    private LoaderoModel startPolling(String uri, String runId,
                                      int interval, int timeout) {
        LoaderoRunInfo result = null;
        URI getRunsURI = URI.create(uri + runId + "/");
        int tries = timeout / interval;
        boolean done = false;

        long start = System.currentTimeMillis();
        long end = start + timeout;

        while (tries != 0 | System.currentTimeMillis() < end | done) {
            tries--;
            while (!done) {
                try {
                    result = (LoaderoRunInfo) restController.get(getRunsURI.toString(),
                            LoaderoType.LOADERO_RUN_INFO);
                    if (result.getStatus().equals("done")) {
                        done = true;
                        logger.info("Test run is done.");
                        logger.info("Test run information available on {}",
                                getRunsURI + "results/");
                    } else {
                        logger.info("Test status: {}", result.getStatus());
                    }
                    Thread.sleep(interval);
                } catch (Exception e) {
                    done = true;
                    stopTestRun(uri);
                }
            }
        }
        return result;
    }

}
