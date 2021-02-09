package loadero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test.*;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TestWithWireMock {
    private static final String token = System.getenv("LOADERO_API_TOKEN");
    private static final String GROUP_ID = "48797";
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final LoaderoClient loaderoClient = new LoaderoClient(token,PROJECT_ID, TEST_ID);
//    private static final WireMockServer server = new WireMockServer(8089);
    private final String proxyTarget = loaderoClient.buildProjectURL();

    @Rule
    public WireMockRule wmRule = new WireMockRule(8089);

    @BeforeEach
    public void setup() {
        wmRule.start();
    }

    @AfterEach
    public void teardown() {
        wmRule.stop();
    }

    @Test
    public void basicWiremockTest() {
        MappingBuilder mappingBuilder = get(urlMatching("/blah"));
        wmRule.givenThat(mappingBuilder
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .withStatusMessage("Everything is ok")))
                .getResponse();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:8089/blah");
        HttpResponse res2 = null;

        try {
            res2 = client.execute(get);
        } catch (IOException ex) {
        }

        wmRule.verify(getRequestedFor(urlEqualTo("/blah")));
        assertEquals(200, res2.getStatusLine().getStatusCode());
        assertEquals("application/json", res2
                .getFirstHeader("Content-type").getValue());
    }
}
