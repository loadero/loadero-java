package loadero.controller;

import loadero.LoaderoClientUtils;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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

    public LoaderTestController(URI uri, String loaderoApiToken) {
        super(uri, loaderoApiToken);
    }

    @Override
    public LoaderoModel getById(String projectId, String testId) {
        LoaderoTestOptions test = null;
        HttpUriRequest get = RequestBuilder.get(super.getUri()).build();
        LoaderoClientUtils.setDefaultHeaders(get, super.getLoaderoApiToken());
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpClient client = HttpClients.custom().build();
             CloseableHttpResponse res = client.execute(get)) {
            HttpEntity entity = res.getEntity();
            test = Objects.requireNonNull(
                    LoaderoClientUtils.jsonToTestDescr(entity),
                    "Response returned null");
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
        return test;
    }
//
//    public LoaderoTestOptions getTestDescription(String projectId, String testId) {
//        LoaderoTestOptions test = null;
//        HttpUriRequest get = RequestBuilder.get(uri).build();
//        LoaderoClientUtils.setDefaultHeaders(get, loaderoApiToken);
//        // Try-catch with resources statement that will close
//        // everything for us after we are done.
//        try (CloseableHttpClient client = HttpClients.custom().build();
//             CloseableHttpResponse res = client.execute(get)) {
//            HttpEntity entity = res.getEntity();
//            test = Objects.requireNonNull(
//                    LoaderoClientUtils.jsonToTestDescr(entity),
//                    "Response returned null");
//        } catch (NullPointerException | IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return test;
//    }


    // TODO: refactor, make more readable
    /**
     * Updates description for specified Loadero test in specified project.
     * Returns LoaderoTestDescription object.
     * @param projectId
     * @param testId
     * @param newTest - LoaderoTestDescription that will replace old one.
     * @return
     */
    public LoaderoTestOptions updateTestDescription(String projectId,
                                                           String testId,
                                                           LoaderoTestOptions newTest) {
        LoaderoTestOptions result = null;
        if (LoaderoClientUtils.checkNull(newTest)) {
            throw new NullPointerException();
        }

        try {
            String testToJson = LoaderoClientUtils.testDescrToJson(newTest);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(super.getUri())
                    .setEntity(entity)
                    .build();
            LoaderoClientUtils.setDefaultHeaders(put, super.getLoaderoApiToken());

            try(CloseableHttpResponse res = super.getClient().build().execute(put)) {
                if (res.getStatusLine().getStatusCode() == 200 &&
                        !(LoaderoClientUtils.checkNull(res.getEntity()))) {
                    result = LoaderoClientUtils.jsonToTestDescr(res.getEntity());
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
