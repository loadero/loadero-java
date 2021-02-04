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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class LoaderoClient {
    private static final String BASE_URL = "https://api.loadero.com/v2";
    private static final String LOADERO_API_TOKEN = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    // TODO: create some generic method to create uris.
    private static final URI uri = URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID+"/");

    /**
     * Sets some default headers for Http methods.
     * @param req - Http request for which set default headers
     */
    private static void setDefaultHeaders(HttpUriRequest req) {
        req.setHeader(HttpHeaders.ACCEPT, "application/json");
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        req.setHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + LOADERO_API_TOKEN);
    }

    /**
     * TODO: make return LoaderoTestDescription object.
     * Returns test description for the specific test in specific project.
     * @param projectId
     * @param testId
     */
    public static void getTestDescription(String projectId, String testId) {
        HttpUriRequest get = RequestBuilder.get(uri).build();
        setDefaultHeaders(get);
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpClient client = HttpClients.custom().build();
             CloseableHttpResponse res = client.execute(get)) {
            HttpEntity entity = res.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // TODO
    public static void updateTestDescription(String projectId, String testId) {
        LoaderoTestDescription test = new LoaderoTestDescription("New name 2",
                30, 10,
                "load", "random", "new script");
        Gson gson = new Gson();
        try (CloseableHttpClient client = HttpClients.custom().build()){
            HttpEntity entity = new StringEntity(gson.toJson(test));
            System.out.println("Object: " + entity);
            System.out.println("Object: " + gson.toJson(test));
            HttpUriRequest put = RequestBuilder.put(uri)
                    .setEntity(entity)
                    .build();
            setDefaultHeaders(put);
            CloseableHttpResponse res = client.execute(put);

            if (res.getStatusLine().getStatusCode() != 200) {
                System.out.println(res.getStatusLine().getReasonPhrase());
            } else {
                System.out.println("Successfully updated.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        getTestDescription(PROJECT_ID, TEST_ID);
        updateTestDescription(PROJECT_ID, TEST_ID);
    }
}
