package loadero;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestWithWireMock {
    private static final String token      = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID    = "6866";
    private static final LoaderoClient loaderoClient = new LoaderoClient(token,PROJECT_ID, TEST_ID);

    private static final int port               = 8089;
    private static final String localhost       = "http://localhost:" + port;
    private static final String loaderoTokenStr = "LoaderoAuth " + token;
    private final LoaderoHttpClient httpClient  = new LoaderoHttpClient(token);

    @Rule
    public static WireMockRule wmRule = new WireMockRule(8089);

    @BeforeAll
    public static void setup() {
        wmRule.start();
    }

    @AfterAll
    public static void teardown() {
        wmRule.stop();
    }

    @Test
    public void testGetTestWithId() throws IOException {
        String url = "/projects/"
                + loaderoClient.getProjectId()
                + "/tests/"
                + loaderoClient.getTestId();

        wmRule.givenThat(get(url)
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .withStatus(200))
        );
        // Make GET request to url on localhost
        HttpGet getTests = new HttpGet(localhost + url);
        CloseableHttpResponse response = httpClient.build().execute(getTests);

        // Verify that Wiremock received request
        wmRule.verify(getRequestedFor(urlPathMatching(url))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/json")));

        assertEquals(200,
                response.getStatusLine().getStatusCode());
        assertEquals("application/json",
                response.getEntity().getContentType().getValue());
    }

    @Test
    public void testTokenAccess() throws IOException {
        String url = "/projects/" + loaderoClient.getProjectId() +"/";
        wmRule.givenThat(get(urlMatching(url))
                .willReturn(aResponse()
                        .proxiedFrom(loaderoClient.getBASE_URL())
                        .withAdditionalRequestHeader(
                                HttpHeaders.AUTHORIZATION,
                                loaderoTokenStr)));

        HttpGet request = new HttpGet(localhost + url);
        CloseableHttpResponse response = httpClient.build().execute(request);
        String jsonRes = EntityUtils.toString(response.getEntity());

        wmRule.verify(getRequestedFor(urlPathMatching(url))
                .withHeader(HttpHeaders.AUTHORIZATION,
                        equalTo("LoaderoAuth " + token)));

        assertTrue(jsonRes.contains(loaderoClient.getProjectId()));
    }

    @Test
    public void negativeTokenAccess() throws IOException {
        String url = "/projects/" + loaderoClient.getProjectId() +"/";

        wmRule.givenThat(get(urlMatching(url))
                .willReturn(aResponse()
                        .proxiedFrom(loaderoClient.getBASE_URL())));

        HttpGet request = new HttpGet(localhost + url);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetTestOptionsProxyFromLoaderApi() throws IOException {
        String url = "/projects/"
                + loaderoClient.getProjectId()
                + "/tests/"
                + loaderoClient.getTestId()+"/";

        wmRule.givenThat(get(urlMatching(url))
                .willReturn(aResponse()
                        .proxiedFrom(loaderoClient.getBASE_URL())
                        .withAdditionalRequestHeader(
                                HttpHeaders.AUTHORIZATION,
                                loaderoTokenStr)));

        HttpGet request = new HttpGet(localhost + url);
        CloseableHttpResponse response = httpClient.build().execute(request);
        String jsonRes = EntityUtils.toString(response.getEntity());

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertTrue(jsonRes.contains(loaderoClient.getTestId()));
    }

    @Test
    public void negativeGetTestOptionsFromLoaderoApi() throws IOException {
        String url = "/projects/"
                + loaderoClient.getProjectId()
                + "/tests/"
                + ""
                +"/";

        wmRule.givenThat(get(urlMatching(url))
                .willReturn(aResponse()
                        .proxiedFrom(loaderoClient.getBASE_URL())
                        .withAdditionalRequestHeader(
                                HttpHeaders.AUTHORIZATION,
                                loaderoTokenStr)));

        HttpGet request = new HttpGet(localhost + url);
        CloseableHttpResponse response = httpClient.build().execute(request);
        String jsonRes = EntityUtils.toString(response.getEntity());

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
        assertFalse(jsonRes.contains(loaderoClient.getTestId()));
    }

    @Test
    public void testGettingProjectByIdFromSavedMappings() throws IOException {
        HttpGet get = new HttpGet(localhost + "/projects/" + loaderoClient.getProjectId() +"/");
        CloseableHttpResponse response = httpClient.build().execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }
}
