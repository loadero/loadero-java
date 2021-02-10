package loadero;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.gson.Gson;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TestWithWireMock {
    private static final String token      = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID    = "6866";
    private static final LoaderoClient loaderoClient = new LoaderoClient(token,PROJECT_ID, TEST_ID);

    private static final int port              = 8089;
    private static final String localhost      = "http://localhost:" + port;
    private final LoaderoHttpClient httpClient = new LoaderoHttpClient(token);

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

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("application/json", response.getEntity().getContentType().getValue());
    }

    @Test
    public void testTokenAccess() throws IOException {
        String url = "/projects/" + loaderoClient.getProjectId() +"/";
        wmRule.givenThat(get(url)
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + token)
                        .withStatus(200)));

        HttpGet request = new HttpGet(localhost + url);
        CloseableHttpResponse response = httpClient.build().execute(request);

        wmRule.verify(getRequestedFor(urlPathMatching(url))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("LoaderoAuth " + token)));

        assertEquals(HttpHeaders.AUTHORIZATION, response.getFirstHeader(HttpHeaders.AUTHORIZATION).getName());
        assertEquals("LoaderoAuth " + token, response.getFirstHeader(HttpHeaders.AUTHORIZATION).getValue());
    }

    @Test
    public void testGettingProjectByIdFromSavedMappings() throws IOException {
        HttpGet get = new HttpGet(localhost + "/projects/" + loaderoClient.getProjectId() + "/");
        CloseableHttpResponse response = httpClient.build().execute(get);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
}
