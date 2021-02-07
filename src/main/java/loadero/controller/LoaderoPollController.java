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

import java.io.IOException;
import java.net.URI;


// TODO: log for test status
public class LoaderoPollController {
    private final LoaderoHttpClient     client;
    private final LoaderoModelFactory   factory;
    private final LoaderoRestController restController;

    public LoaderoPollController(String loaderoApiToken) {
        this.factory = new LoaderoModelFactory();
        this.client  = new LoaderoHttpClient(loaderoApiToken);
        this.restController = new LoaderoRestController(loaderoApiToken);
    }

    public LoaderoModel startTestAndPoll(String uri, int interval, int timeout) {
        LoaderoRunInfo startTestRun = startTestRun(uri);
        System.out.println("Started test run ID: " + startTestRun.getId());
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
                    System.out.println("Test successfully started.");
                } else {
                    stopTestRun(uri);
                    System.out.println(res.getStatusLine());
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
            System.out.println("Stopping test run...");
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
            System.out.println("try number: " + tries);
            tries--;
            while (!done) {
                try {
                    result = (LoaderoRunInfo) restController.get(getRunsURI.toString(),
                            LoaderoType.LOADERO_RUN_INFO);
                    System.out.println(result);
                    if (result.getStatus().equals("done")) {
                        done = true;
                        System.out.println("Done! Test run results: " + result);
                    } else {
                        System.out.println("Test run results are still not available.");
                        System.out.println("Test status: " + result.getStatus());
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
