package loadero.controller;

import loadero.LoaderoClientUtils;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoModelFactory;
import loadero.model.LoaderoType;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;

/**
 * REST controller class responsible for CRUD actions related to tests.
 * Meaning here is defined logic for creating, updetaing, retrieving and deleting Loadero tests.
 */
@Getter
public class LoaderoController {
    private final String loaderoApiToken;
    private final String projectId;
    private final String testId;
    private final HttpClientBuilder client = HttpClients.custom();
    private final LoaderoModelFactory factory = new LoaderoModelFactory();

    public LoaderoController(String loaderoApiToken, String projectId,
                             String testId) {
        this.loaderoApiToken = loaderoApiToken;
        this.projectId = projectId;
        this.testId = testId;
    }

    /**
     * Retrieves LoaderoModel i.e test, participant or group description
     * from the specific URI.
     * @param uri - URI of the API to get data from
     * @param type - type of the returned data
     */
    public LoaderoModel get(URI uri, LoaderoType type) {
        LoaderoModel result = factory.getLoaderoModel(type);

        HttpUriRequest get = RequestBuilder.get(uri).build();
        LoaderoClientUtils.setDefaultHeaders(get, loaderoApiToken);
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpResponse res = client.build().execute(get)) {
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = LoaderoClientUtils.jsonToObject(entity, type);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Update operation on existing LoaderoModel by supplying new LoaderoModel
     * @param uri - URI of the API pointing to which LoaderoModel(test, group or participant) to update
     * @param type - type of the model to be created by factory
     * @param newModel - new model that will replace old one
     * @return
     */
    public LoaderoModel update(URI uri, LoaderoType type, LoaderoModel newModel) {
        LoaderoModel result = factory.getLoaderoModel(type);

        if (LoaderoClientUtils.isNull(newModel)) {
            throw new NullPointerException();
        }

        try {
            String testToJson = LoaderoClientUtils.modelDescrToJson(newModel);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(uri)
                    .setEntity(entity)
                    .build();
            LoaderoClientUtils.setDefaultHeaders(put, loaderoApiToken);

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

    /**
     *
     * @param uri
     * @return
     */
    public LoaderoModel startTestRun(URI uri) {
        LoaderoModel result = factory.getLoaderoModel(LoaderoType.LOADERO_RUN_INFO);
        try {
            HttpUriRequest postRun = RequestBuilder.post(uri).build();
            LoaderoClientUtils.setDefaultHeaders(postRun, loaderoApiToken);

            try (CloseableHttpResponse res = client.build().execute(postRun)) {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED &&
                        !LoaderoClientUtils.isNull(res.getEntity())) {
                    result = LoaderoClientUtils.jsonToObject(
                            res.getEntity(),
                            LoaderoType.LOADERO_RUN_INFO
                    );
                    System.out.println("Test successfully started.");
                } else {
                    System.out.println(res.getStatusLine());
                }
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return result;
    }

    public void stopTestRun(URI uri) {

    }
    /**
     *
     * @param uri
     * @param interval
     * @param time
     * @param timeout
     * @return
     */
    public LoaderoModel pollTestRunResults(URI uri, int interval,
                                           int time, int timeout) {

        return null;
    }
}
