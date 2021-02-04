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
import loadero.LoaderoClientUtils;

import java.io.IOException;
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
        LoaderoClientUtils.setDefaultHeaders(get, LOADERO_API_TOKEN);
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


    // TODO: refactor, make more readable
    /**
     * Updates description for specified Loadero test in specified project.
     * Returns LoaderoTestDescription object.
     * @param projectId
     * @param testId
     * @param newTest - LoaderoTestDescription that will replace old one.
     * @return
     */
    public static LoaderoTestDescription updateTestDescription(String projectId,
                                             String testId,
                                             LoaderoTestDescription newTest) {
        LoaderoTestDescription result = null;
        if (LoaderoClientUtils.checkNull(newTest)) {
            throw new NullPointerException();
        }

        try {
            String testToJson = LoaderoClientUtils.testDescrToJson(newTest);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(uri)
                    .setEntity(entity)
                    .build();
            LoaderoClientUtils.setDefaultHeaders(put, LOADERO_API_TOKEN);

            try(CloseableHttpResponse res = client.build().execute(put)) {
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

    public static void main(String[] args) {
        LoaderoTestDescription test = new LoaderoTestDescription("new name 4",
                30, 10, "performance",
                "random", "function(client) {\n" +
                "    // Example of locating elements using CSS selectors\n" +
                "    client\n" +
                "        // Navigate to website google.com\n" +
                "        .url('https://www.google.com')\n" +
                "        // Wait up to 10 seconds until 'body' element is visible)\n" +
                "        .waitForElementVisible('body', 10 * 1000)\n" +
                "        // Type \"Loadero\" in the search bar\n" +
                "        .setValue('input[type=text]', 'Loadero')\n" +
                "         // Trigger search by sending \"Enter\" key event in the search bar\n" +
                "        .setValue('input[type=text]', client.Keys.ENTER);\n" +
                "}");
        LoaderoTestDescription getTest = getTestDescription(PROJECT_ID, TEST_ID);
        System.out.println(getTest);
        LoaderoTestDescription updateTest = updateTestDescription(PROJECT_ID, TEST_ID, test);
        System.out.println(updateTest);
    }
}
