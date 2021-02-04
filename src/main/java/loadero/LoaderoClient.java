package loadero;

import com.google.gson.Gson;
import loadero.model.LoaderoTestDescription;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Objects;

// TODO: Parse return value into java object - Done
// TODO: Null checks
public class LoaderoClient {
    private static final String BASE_URL = "https://api.loadero.com/v2";
    private static final String LOADERO_API_TOKEN = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final Gson gson = new Gson();
    private static final HttpClientBuilder client = HttpClients.custom();
    // TODO: create some generic method to create uris.
    private static final URI uri = URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID+"/");

    /**
     * TODO: make return LoaderoTestDescription object.
     * Returns test description for the specific test in specific project.
     * @param projectId
     * @param testId
     */
    public static LoaderoTestDescription getTestDescription(String projectId, String testId) {
        LoaderoTestDescription test = null;
        HttpUriRequest get = RequestBuilder.get(uri).build();
        setDefaultHeaders(get);
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpClient client = HttpClients.custom().build();
             CloseableHttpResponse res = client.execute(get)) {
            test = jsonToTestDescr(res.getEntity());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return test;
    }


    // TODO: refactor, make more readable, add javadoc
    public static LoaderoTestDescription updateTestDescription(String projectId,
                                             String testId,
                                             LoaderoTestDescription newTest) {
        LoaderoTestDescription result = null;
        if (checkNull(newTest)) {
            throw new NullPointerException();
        }
        try {
            String testToJson = testDescrToJson(newTest);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(uri)
                    .setEntity(entity)
                    .build();
            setDefaultHeaders(put);
            try(CloseableHttpResponse res = client.build().execute(put)) {
                if (res.getStatusLine().getStatusCode() == 200 &&
                        !(checkNull(res.getEntity()))) {
                    result = jsonToTestDescr(res.getEntity());
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

    public static void main(String[] args) {
        LoaderoTestDescription getTest = getTestDescription(PROJECT_ID, TEST_ID);
        LoaderoTestDescription updateTest = updateTestDescription(PROJECT_ID, TEST_ID, null);
        System.out.println(updateTest);
    }

    private static boolean checkNull(Object test) {
        return Objects.isNull(test);
    }
    // Converts JSON from response into LoaderTestDescription object
    private static LoaderoTestDescription jsonToTestDescr(HttpEntity entity) {
        LoaderoTestDescription test = null;
        try {
            test = gson.fromJson(EntityUtils.toString(entity), LoaderoTestDescription.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return test;
    }

    private static String testDescrToJson(LoaderoTestDescription test) {
        return gson.toJson(test);
    }

    /**
     * Sets some default headers for Http methods.
     * @param req - Http request for which set default headers
     */
    private static void setDefaultHeaders(HttpUriRequest req) {
        req.setHeader(HttpHeaders.ACCEPT, "application/json");
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        req.setHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + LOADERO_API_TOKEN);
    }
}
