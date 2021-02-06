package loadero.controller;

import loadero.LoaderoClientUtils;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoModelFactory;
import loadero.model.LoaderoRunInfo;
import loadero.model.LoaderoType;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * REST controller class responsible for CRUD actions related to tests.
 * Meaning here is defined logic for creating, updetaing, retrieving and deleting Loadero tests.
 */
@Getter
public class LoaderoController {
    private final String loaderoApiToken;
//    private final String projectId;
//    private final String testId;
    private final HttpClientBuilder client = HttpClientBuilder.create();
    private final LoaderoModelFactory factory = new LoaderoModelFactory();
    private final List<Header> headers = new ArrayList<>();

    public LoaderoController(String loaderoApiToken) {
        this.loaderoApiToken = loaderoApiToken;
//        this.projectId = projectId;
//        this.testId = testId;
        headers.addAll(List.of(
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + loaderoApiToken)));
        client.setDefaultHeaders(headers);
    }

    /**
     * Retrieves LoaderoModel i.e test, participant or group description
     * from the specific URI.
     *
     * @param uri  - GET endpoint of data
     * @param type - type of the returned data
     */
    /*
    @GET
@Path("retrieve/{uuid}")
public Response retrieveSomething(@PathParam("uuid") String uuid) {
    if(uuid == null || uuid.trim().length() == 0) {
        return Response.serverError().entity("UUID cannot be blank").build();
    }
    Entity entity = service.getById(uuid);
    if(entity == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for UUID: " + uuid).build();
    }
    String json = //convert entity to json
    return Response.ok(json, MediaType.APPLICATION_JSON).build();
}
     */
    public LoaderoModel get(String uri, LoaderoType type) {
        LoaderoModel result = null;
        HttpUriRequest get = RequestBuilder.get(uri).build();
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpResponse res = client.build().execute(get)) {
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = LoaderoClientUtils.jsonToObject(entity, type);
                System.out.println("Result: " + result);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Update operation on existing LoaderoModel by supplying new LoaderoModel
     *
     * @param uri      - URI of the API pointing to which LoaderoModel(test, group or participant) to update
     * @param type     - type of the model to be created by factory
     * @param newModel - new model that will replace old one
     * @return
     */
    public LoaderoModel update(URI uri, LoaderoType type, LoaderoModel newModel) {
        LoaderoModel result = factory.getLoaderoModel(type);

        if (LoaderoClientUtils.isNull(newModel)) {
            throw new NullPointerException();
        }

        try {
            String testToJson = LoaderoClientUtils.modelToJson(newModel);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(uri)
                    .setEntity(entity)
                    .build();

            try (CloseableHttpResponse res = client.build().execute(put)) {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK &&
                        !(LoaderoClientUtils.isNull(res.getEntity()))) {
                    result = LoaderoClientUtils.jsonToObject(
                            res.getEntity(),
                            type);
                    System.out.println(type.toString() + " successfully updated.");
                } else {
                    System.out.println(res.getStatusLine());
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getLocalizedMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public LoaderoModel startTestAndPoll(URI uri, int interval, int timeout) {
        LoaderoRunInfo startTestRun = (LoaderoRunInfo) startTestRun(uri);
        System.out.println("Started test run ID: " + startTestRun.getId());
        return startPolling(uri,
                String.valueOf(startTestRun.getId()),
                1000 * interval, 1000 * timeout);
    }


    // Private helper methods below. TODO: maybe separate into different class?

    /**
     * Start test run by sending POST request to /runs url.
     *
     * @param uri
     * @return
     */
    private LoaderoModel startTestRun(URI uri) {
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
        return result;
    }

    /**
     * Stops test run by calling GET on /stop.
     * @param uri
     */
    private void stopTestRun(URI uri) {
        URI stopURI = URI.create(uri + "stop/");
        HttpUriRequest stopRun = RequestBuilder.post(stopURI).build();

        try (CloseableHttpResponse res = client.build().execute(stopRun)) {
            System.out.println("Stopping test run...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param uri      - uri from wher to get info
     * @param interval - time in seconds between get requests
     * @param timeout  - maximum amount of time in seconds should spend polling
     * @return
     */
    private LoaderoModel startPolling(URI uri, String runId,
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
                    result = (LoaderoRunInfo) get(getRunsURI.toString(),
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
