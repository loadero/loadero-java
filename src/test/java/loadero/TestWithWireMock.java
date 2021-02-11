package loadero;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.controller.LoaderoRestController;
import loadero.model.LoaderoParticipant;
import loadero.model.LoaderoRunInfo;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWithWireMock {
    private static final String token      = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID    = "6866";
    private static final LoaderoClient loaderoClient = new LoaderoClient(token, PROJECT_ID, TEST_ID);
    private static final Logger logger = LogManager.getLogger(TestWithWireMock.class);

    private static String localhost             = "http://localhost:";
    private static final String loaderoTokenStr = "LoaderoAuth " + token;
    private final LoaderoHttpClient httpClient  = new LoaderoHttpClient(token);

    @Rule
    public static WireMockRule wmRule = new WireMockRule(
            WireMockConfiguration.wireMockConfig().dynamicPort()
    );
//
//    @BeforeAll
//    public static void setup() {
//        wmRule.start();
//        localhost = localhost + wmRule.port();
//    }
//
//    @AfterAll
//    public static void teardown() {
//        wmRule.stop();
//    }

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Disabled
    public void negativeGetTestOptionsFromLoaderoApi() throws IOException {
        String url = "/projects/"
                + loaderoClient.getProjectId()
                + "/tests/"
                + "9"
                +"/";

        StubMapping stub = wmRule.givenThat(get(urlPathMatching(url))
                .willReturn(aResponse()
                        .proxiedFrom(loaderoClient.getBASE_URL())));

        logger.info("{}", url);
        assertEquals(HttpStatus.SC_NOT_FOUND, stub.getResponse().getStatus());
    }

    @Test
    @Order(5)
    public void testGettingProjectByIdFromSavedMappings() throws IOException {
        HttpGet get = new HttpGet(localhost + "/projects/" + loaderoClient.getProjectId() +"/");
        CloseableHttpResponse response = httpClient.build().execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    @Order(6)
    public void testGetParticipantById() {
        String participantId = "94633";
        LoaderoParticipant participant =
                loaderoClient.getParticipantById(participantId);
        assertEquals(participantId, String.valueOf(participant.getId()));
    }

    @Test
    public void testUpdateParticipantById() {
        String participantId = "94633";
        // name, group_id, count and browser params are required
        LoaderoParticipant newParticipant = new LoaderoParticipant();
        newParticipant.setName("New participant name from @Test");

        LoaderoParticipant updatedParticipant = loaderoClient
                .updateTestParticipantById(participantId, newParticipant);

        assertEquals("New participant name from @Test", updatedParticipant.getName());
        assertEquals(participantId, String.valueOf(updatedParticipant.getId()));
    }

    @Test
    @Disabled
    @Order(8)
    public void testFullFunctionalityFlow() {
        LoaderoClient localClient = new LoaderoClient(token, PROJECT_ID, TEST_ID);
        String testClientInitUrl = localClient.buildProjectURL();

        // Checking that client can establish connection and make requests
        StubMapping stub = wmRule.givenThat(get(urlMatching(testClientInitUrl))
                .willReturn(aResponse()
                        .proxiedFrom(localClient.getBASE_URL())
                        .withAdditionalRequestHeader(
                                HttpHeaders.AUTHORIZATION,
                                "LoaderoAuth " + localClient.getLoaderoApiToken()
                        )));

        assertEquals(HttpStatus.SC_OK, stub.getResponse().getStatus());
        // Checking that client is able to update test
        LoaderoTestOptions currentTestOptions = (LoaderoTestOptions) loaderoClient.getTestOptions();
        LoaderoTestOptions newTestOptions = new LoaderoTestOptions();
        newTestOptions.setName("New Test Name from @Test");
        // Setting script through path using URI
        newTestOptions.setScript(URI.create("src/main/resources/loadero/scripts/testui/LoaderoScriptJava.java"));
        newTestOptions.setMode("performance");
        newTestOptions.setStartInterval(30);
        LoaderoTestOptions updatedTestOptions = (LoaderoTestOptions)
                localClient.updateTestOptions(newTestOptions);

        logger.info("Before update: {}", currentTestOptions);
        logger.info("After update: {}", updatedTestOptions);
        // asserting that update did happen
        assertEquals("New Test Name from @Test", updatedTestOptions.getName());

        // Checking polling function
        LoaderoRunInfo startAndPollTest = (LoaderoRunInfo)
                    loaderoClient.startTestAndPollInfo(20, 40);
        assertEquals("done", startAndPollTest.getStatus());
    }
}
