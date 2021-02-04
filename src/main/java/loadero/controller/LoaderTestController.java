package loadero.controller;

import loadero.LoaderoClientUtils;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Objects;

/**
 * REST controller class responsible for CRUD actions related to tests.
 * Meaning here is defined logic for creating, updetaing, retrieving and deleting Loadero tests.
 */
public class LoaderTestController extends LoaderoAbstractController {

    public LoaderTestController(URI uri, String loaderoApiToken,
                                String projectId, String testId) {
        super(uri, loaderoApiToken, projectId, testId);
    }

    @Override
    public LoaderoTestOptions get() {
        LoaderoTestOptions test = new LoaderoTestOptions();
        HttpUriRequest get = RequestBuilder.get(super.getUri()).build();
        LoaderoClientUtils.setDefaultHeaders(get, super.getLoaderoApiToken());
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpClient client = HttpClients.custom().build();
             CloseableHttpResponse res = client.execute(get)) {
            HttpEntity entity = res.getEntity();
            test = (LoaderoTestOptions) Objects.requireNonNull(
                    LoaderoClientUtils.jsonToObject(
                            entity,
                            LoaderoType.LOADERO_TEST),
                    "Response returned null");
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
        return test;
    }

    @Override
    public LoaderoTestOptions update(LoaderoModel newTest) {
        LoaderoTestOptions result = new LoaderoTestOptions();
        if (LoaderoClientUtils.checkNull(newTest)) {
            throw new NullPointerException();
        }

        try {
            String testToJson = LoaderoClientUtils.modelDescrToJson(newTest);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(super.getUri())
                    .setEntity(entity)
                    .build();
            LoaderoClientUtils.setDefaultHeaders(put, super.getLoaderoApiToken());

            try(CloseableHttpResponse res = super.getClient().build().execute(put)) {
                if (res.getStatusLine().getStatusCode() == 200 &&
                        !(LoaderoClientUtils.checkNull(res.getEntity()))) {
                    result = (LoaderoTestOptions) LoaderoClientUtils.jsonToObject(
                            res.getEntity(),
                            LoaderoType.LOADERO_TEST);
                    System.out.println("Successfully updated.");
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
}
